/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.codegen.inline

import com.google.common.collect.LinkedListMultimap
import java.util.ArrayList
import com.intellij.util.containers.Stack
import org.jetbrains.kotlin.codegen.optimization.common.isMeaningful
import org.jetbrains.org.objectweb.asm.Opcodes
import org.jetbrains.org.objectweb.asm.tree.*
import java.util.Comparator
import java.util.Collections

public abstract class CoveringTryCatchNodeProcessor(parameterSize: Int) {

    public val tryBlocksMetaInfo: IntervalMetaInfo<TryCatchBlockNodeInfo> = IntervalMetaInfo()

    public val localVarsMetaInfo: IntervalMetaInfo<LocalVarNodeWrapper> = IntervalMetaInfo()

    public var nextFreeLocalIndex: Int = parameterSize
        private set

    public fun getStartNodes(label: LabelNode): List<TryCatchBlockNodeInfo> {
        return tryBlocksMetaInfo.intervalStarts.get(label)
    }

    public fun getEndNodes(label: LabelNode): List<TryCatchBlockNodeInfo> {
        return tryBlocksMetaInfo.intervalEnds.get(label)
    }

    public open fun processInstruction(curInstr: AbstractInsnNode, directOrder: Boolean) {
        if (curInstr is VarInsnNode || curInstr is IincInsnNode) {
            val argSize = InlineCodegenUtil.getLoadStoreArgSize(curInstr.getOpcode())
            val varIndex = if (curInstr is VarInsnNode) curInstr.`var` else (curInstr as IincInsnNode).`var`
            nextFreeLocalIndex = Math.max(nextFreeLocalIndex, varIndex + argSize)
        }

        if (curInstr is LabelNode) {
            tryBlocksMetaInfo.processCurrent(curInstr, directOrder)
            localVarsMetaInfo.processCurrent(curInstr, directOrder)
        }
    }

    public abstract fun instructionIndex(inst: AbstractInsnNode): Int

    public fun sortTryCatchBlocks(intervals: List<TryCatchBlockNodeInfo>): List<TryCatchBlockNodeInfo> {
        val comp = Comparator { t1: TryCatchBlockNodeInfo, t2: TryCatchBlockNodeInfo ->
            var result = instructionIndex(t1.handler) - instructionIndex(t2.handler)
            if (result == 0) {
                result = instructionIndex(t1.startLabel) - instructionIndex(t2.startLabel)
                if (result == 0) {
                    assert(false, "Error: support multicatch finallies: ${t1.handler}, ${t2.handler}")
                    result = instructionIndex(t1.endLabel) - instructionIndex(t2.endLabel)
                }
            }
            result
        }

        Collections.sort<TryCatchBlockNodeInfo>(intervals, comp)
        return intervals;
    }

    protected fun substituteTryBlockNodes(node: MethodNode) {
        node.tryCatchBlocks.clear()
        sortTryCatchBlocks(tryBlocksMetaInfo.allIntervals)
        for (info in tryBlocksMetaInfo.getMeaningfulIntervals()) {
            node.tryCatchBlocks.add(info.node)
        }
    }


    public fun substituteLocalVarTable(node: MethodNode) {
        node.localVariables.clear()
        for (info in localVarsMetaInfo.getMeaningfulIntervals()) {
            node.localVariables.add(info.node)
        }
    }
}

class IntervalMetaInfo<T : SplittableInterval<T>> {

    val intervalStarts = LinkedListMultimap.create<LabelNode, T>()

    val intervalEnds = LinkedListMultimap.create<LabelNode, T>()

    val allIntervals: ArrayList<T> = arrayListOf()

    val currentIntervals: MutableSet<T> = linkedSetOf()

    fun addNewInterval(newInfo: T) {
        intervalStarts.put(newInfo.startLabel, newInfo)
        intervalEnds.put(newInfo.endLabel, newInfo)
        allIntervals.add(newInfo)
    }

    private fun remapStartLabel(oldStart: LabelNode, remapped: T) {
        intervalStarts.remove(oldStart, remapped)
        intervalStarts.put(remapped.startLabel, remapped)
    }

    private fun remapEndLabel(oldEnd: LabelNode, remapped: T) {
        intervalEnds.remove(oldEnd, remapped)
        intervalEnds.put(remapped.endLabel, remapped)
    }

    fun splitCurrentIntervals(by : Interval, keepStart: Boolean): List<SplittedPair<T>> {
        return currentIntervals.map { split(it, by, keepStart) }
    }

    fun processCurrent(curIns: LabelNode, directOrder: Boolean) {
        getInterval(curIns, directOrder).forEach {
            val b = currentIntervals.add(it)
            assert(b, "Wrong interval structure: $curIns, $it")
        }

        getInterval(curIns, !directOrder).forEach {
            val b = currentIntervals.remove(it)
            assert(b, "Wrong interval structure: $curIns, $it")
        }
    }

    fun split(interval: T, by : Interval, keepStart: Boolean): SplittedPair<T> {
        val splittedPair = interval.split(by, keepStart)
        if (!keepStart) {
            remapStartLabel(splittedPair.newPart.startLabel, splittedPair.patchedPart)
        } else {
            remapEndLabel(splittedPair.newPart.endLabel, splittedPair.patchedPart)
        }
        addNewInterval(splittedPair.newPart)
        return splittedPair
    }

    fun splitAndRemoveInterval(interval: T, by : Interval, keepStart: Boolean): SplittedPair<T> {
        val splittedPair = interval.split(by, keepStart)
        if (!keepStart) {
            remapStartLabel(splittedPair.newPart.startLabel, splittedPair.patchedPart)
        } else {
            remapEndLabel(splittedPair.newPart.endLabel, splittedPair.patchedPart)
        }
        val b = currentIntervals.remove(splittedPair.patchedPart)
        assert(b)
        addNewInterval(splittedPair.newPart)
        return splittedPair
    }

    fun getInterval(curIns: LabelNode, isOpen: Boolean) = if (isOpen) intervalStarts.get(curIns) else intervalEnds.get(curIns)
}

private fun Interval.isMeaningless(): Boolean {
    val start = this.startLabel
    var end: AbstractInsnNode = this.endLabel
    while (end != start && !end.isMeaningful) {
        end = end.getPrevious()
    }
    return start == end
}

public fun <T : SplittableInterval<T>> IntervalMetaInfo<T>.getMeaningfulIntervals(): List<T> {
    return allIntervals.filterNot { it.isMeaningless() }
}

public class DefaultProcessor(val node: MethodNode, parameterSize: Int) : CoveringTryCatchNodeProcessor(parameterSize) {

    init {
        node.tryCatchBlocks.forEach { addTryNode(it) }
        node.localVariables.forEach { addLocalVarNode(it) }
    }

    fun addLocalVarNode(it: LocalVariableNode) {
        localVarsMetaInfo.addNewInterval(LocalVarNodeWrapper(it))
    }

    fun addTryNode(node: TryCatchBlockNode) {
        tryBlocksMetaInfo.addNewInterval(TryCatchBlockNodeInfo(node, false))
    }

    override fun instructionIndex(inst: AbstractInsnNode): Int {
        return node.instructions.indexOf(inst)
    }
}

public class LocalVarNodeWrapper(val node: LocalVariableNode) : Interval, SplittableInterval<LocalVarNodeWrapper> {
    override val startLabel: LabelNode
        get() = node.start
    override val endLabel: LabelNode
        get() = node.end

    override fun split(split: Interval, keepStart: Boolean): SplittedPair<LocalVarNodeWrapper> {
        val newPartInterval = if (keepStart) {
            val oldEnd = endLabel
            node.end = split.startLabel
            Pair(split.endLabel, oldEnd)
        }
        else {
            val oldStart = startLabel
            node.start = split.endLabel
            Pair(oldStart, split.startLabel)
        }

        return SplittedPair(this, LocalVarNodeWrapper(
                LocalVariableNode(node.name, node.desc, node.signature, newPartInterval.first, newPartInterval.second, node.index)
        ))
    }

}