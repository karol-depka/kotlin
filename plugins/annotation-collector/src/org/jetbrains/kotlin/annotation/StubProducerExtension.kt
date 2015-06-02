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

package org.jetbrains.kotlin.annotation

import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.cli.common.CLICompiler
import org.jetbrains.kotlin.cli.common.ExitCode
import org.jetbrains.kotlin.cli.common.output.outputUtils.writeAllTo
import org.jetbrains.kotlin.codegen.*
import org.jetbrains.kotlin.codegen.state.GenerationState
import org.jetbrains.kotlin.codegen.state.Progress
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.psi.JetFile
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.BindingTraceContext
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.kotlin.resolve.jvm.extensions.AnalyzeCompleteHandlerExtension
import org.jetbrains.org.objectweb.asm.ClassWriter
import java.io.File

public class StubProducerExtension(val stubsOutputDir: File) : AnalyzeCompleteHandlerExtension {

    override fun handleAnalyzeResult(project: Project, module: ModuleDescriptor, bindingContext: BindingContext, files: Collection<JetFile>) {
        val forExtraDiagnostics = BindingTraceContext()

        val buildFactory = object : ClassBuilderFactory {

            override fun getClassBuilderMode() = ClassBuilderMode.LIGHT_CLASSES

            override fun newClassBuilder(origin: JvmDeclarationOrigin) = AbstractClassBuilder.Concrete(BinaryClassWriter())

            override fun asText(builder: ClassBuilder) = throw UnsupportedOperationException("BINARIES generator asked for text")

            override fun asBytes(builder: ClassBuilder): ByteArray {
                val visitor = builder.getVisitor() as ClassWriter
                return visitor.toByteArray()
            }

            override fun close() {

            }
        }

        val generationState = GenerationState(
                project,
                buildFactory,
                Progress.DEAF,
                module,
                bindingContext,
                files.toArrayList(),
                /*disableCallAssertions =*/ false,
                /*disableParamAssertions =*/ false,
                GenerationState.GenerateClassFilter.GENERATE_ALL,
                /*disableInline =*/ false,
                /*disableOptimization =*/ false,
                /*packagesWithObsoleteParts =*/ null,
                /*moduleId =*/ null,
                forExtraDiagnostics,
                /*outDirectory =*/ null)

        KotlinCodegenFacade.compileCorrectFiles(generationState, CompilationErrorHandler.THROW_EXCEPTION)

        if (!stubsOutputDir.exists()) stubsOutputDir.mkdirs()
        generationState.getFactory().writeAllTo(stubsOutputDir)

        generationState.destroy()
        throw CLICompiler.CompilationInterruptedException(ExitCode.OK)
    }
}

private class BinaryClassWriter : ClassWriter(ClassWriter.COMPUTE_FRAMES or ClassWriter.COMPUTE_MAXS) {

    override fun getCommonSuperClass(type1: String, type2: String): String {
        try {
            return super.getCommonSuperClass(type1, type2)
        }
        catch (t: Throwable) {
            return "java/lang/Object"
        }

    }
}