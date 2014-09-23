/*
 * Copyright 2010-2014 JetBrains s.r.o.
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

package org.jetbrains.jet.lang.resolve.android

import java.util.ArrayList
import java.util.HashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.io.File
import javax.xml.parsers.SAXParserFactory
import java.io.File
import java.io.FileInputStream
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.psi.JetPsiFactory
import org.jetbrains.kotlin.psi.JetFile
import javax.xml.parsers.SAXParser
import java.util.HashMap
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.VirtualFileAdapter
import com.intellij.openapi.vfs.VirtualFileEvent
import com.intellij.openapi.project.Project
import java.util.concurrent.ConcurrentLinkedQueue
import com.intellij.openapi.util.Key
import com.intellij.testFramework.LightVirtualFile
import com.intellij.psi.PsiManager
import java.io.FileInputStream
import org.xml.sax.helpers.DefaultHandler
import org.xml.sax.Attributes
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiModificationTracker
import com.intellij.psi.impl.PsiModificationTrackerImpl
import java.util.Queue
import com.intellij.psi.PsiFile
import com.intellij.openapi.diagnostic.Logger
import org.jetbrains.jet.lang.resolve.android.AndroidConst.*

public abstract class AndroidUIXmlProcessor(protected val project: Project) {

    public class NoAndroidManifestFound : Exception("No android manifest file found in project root")

    private enum class CacheAction { HIT; MISS }

    private val androidImports = arrayListOf("android.app.Activity",
                                     "android.view.View",
                                     "android.widget.*")

    protected abstract val searchPath: String?
    protected abstract val androidAppPackage: String

    private val fileCache = HashMap<PsiFile, String>()
    var lastCachedPsi: JetFile? = null
        private set
    protected val fileModificationTime: HashMap<PsiFile, Long> = HashMap()

    protected val filesToProcess: Queue<PsiFile> = ConcurrentLinkedQueue()

    public abstract val resourceManager: AndroidResourceManager

    protected val LOG: Logger = Logger.getInstance(this.javaClass)

    public fun parseToString(): String? {
        val cacheState = doParse()
        if (cacheState == null) return null
        return renderString()
    }


    public fun parseToPsi(project: Project): JetFile? {
        val cacheState = doParse()
        if (cacheState == null) return null
        return if (cacheState == CacheAction.MISS || lastCachedPsi == null) {
            try {
                val vf = LightVirtualFile(SYNTHETIC_FILENAME, renderString())
                val psiFile = PsiManager.getInstance(project).findFile(vf) as JetFile
                psiFile.putUserData(ANDROID_USER_PACKAGE, androidAppPackage)
                lastCachedPsi = psiFile
                psiFile
            }
            catch (e: Exception) {
                invalidateCaches()
                null
            }
        }
        else lastCachedPsi
    }

    private fun writeImports(kw: KotlinStringWriter): KotlinWriter {
        kw.writePackage(androidAppPackage)
        for (elem in androidImports)
            kw.writeImport(elem)
        kw.writeEmptyLine()
        return kw
    }

    private fun parseSingleFileWithCache(file: PsiFile): Pair<String, CacheAction> {
        val lastRecorded = fileModificationTime[file] ?: -1
        if (file.getModificationStamp() > lastRecorded)
            return Pair(parseSingleFile(file), CacheAction.MISS)
        else
            return Pair(fileCache[file]!!, CacheAction.HIT)
    }

    private fun parseSingleFile(file: PsiFile): String {
        val res = parseSingleFileImpl(file)
        fileModificationTime[file] = file.getModificationStamp()
        fileCache[file] = res
        return res
    }

    protected abstract fun parseSingleFileImpl(file: PsiFile): String

    private fun doParse(): CacheAction? {
        if (searchPath == null || searchPath == "") return null
        populateQueue()
        var overallCacheMiss = false
        var file = filesToProcess.poll()
        while (file != null) {
            val res = parseSingleFileWithCache(file!!)
            overallCacheMiss = overallCacheMiss or (res.second == CacheAction.MISS)
            file = filesToProcess.poll()
        }
        return if (overallCacheMiss) CacheAction.MISS else CacheAction.HIT
    }

    private fun renderString(): String {
        val buffer = writeImports(KotlinStringWriter()).output()
        for (buf in fileCache.entrySet().sortBy({it.key.getName()}))
            buffer.append(buf.value)
        return buffer.toString()
    }

    private fun invalidateCaches() {
        fileCache.clear()
        fileModificationTime.clear()
        lastCachedPsi = null
    }

    protected fun populateQueue() {
        filesToProcess.addAll(resourceManager.getLayoutXmlFiles())
    }


    protected fun produceKotlinProperties(kw: KotlinStringWriter, ids: Collection<AndroidWidget>): StringBuffer {
        for (id in ids) {
            val body = arrayListOf("return findViewById(0) as ${id.className}")
            kw.writeImmutableExtensionProperty(receiver = "Activity",
                                               name = id.id,
                                               retType = id.className,
                                               getterBody = body)
        }
        return kw.output()
    }
}