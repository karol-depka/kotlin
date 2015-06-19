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

package org.jetbrains.kotlin.resolve

import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.psi.JetNamedFunction
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
import org.jetbrains.kotlin.resolve.lazy.DeclarationScopeProviderImpl
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil
import org.jetbrains.kotlin.resolve.lazy.LazyDeclarationResolver
import org.jetbrains.kotlin.resolve.lazy.TopLevelDescriptorProvider
import org.jetbrains.kotlin.resolve.scopes.JetScope
import org.jetbrains.kotlin.storage.StorageManager
import javax.inject.Inject
import kotlin.properties.Delegates

public data class BodyResolveContext(
        val outerDataFlowInfo: DataFlowInfo,
        val baseTrace: BindingTrace,
        val functionDescriptor: FunctionDescriptor,
        val declaringScope: JetScope
)

public data class BodyResolveResult(
        val resultTrace: DelegatingBindingTrace,
        val resolveContext: BodyResolveContext
)

public interface ResolveTaskManager {
    public open fun resolveFunctionBody(function: JetNamedFunction): BodyResolveResult
}

public open class DummyResolveManager : ResolveTaskManager {
    public override fun resolveFunctionBody(function: JetNamedFunction): BodyResolveResult {
        throw UnsupportedOperationException()
    }
}

public open class BodyResolveTaskManager : ResolveTaskManager {
    public var bodyResolver: BodyResolver by Delegates.notNull()
        @Inject set

    public var storageManager: StorageManager by Delegates.notNull()
        @Inject set

    public var declarationScopeProvider: DeclarationScopeProviderImpl by Delegates.notNull()
        @Inject set

    public var trace: BindingTrace by Delegates.notNull()
        @Inject set

    public var lazyDeclarationResolver: LazyDeclarationResolver by Delegates.notNull()
        @Inject set

    public override fun resolveFunctionBody(function: JetNamedFunction): BodyResolveResult {
        val scope = declarationScopeProvider.getResolutionScopeForDeclaration(function)
        val functionDescriptor = lazyDeclarationResolver.resolveToDescriptor(function) as FunctionDescriptor
        val dataFlowInfo = DataFlowInfo.EMPTY

        val bodyResolveResult = resolveFunctionBody(function, bodyResolver, BodyResolveContext(dataFlowInfo, trace, functionDescriptor, scope))

        return bodyResolveResult
    }

    companion object {
        public fun resolveFunctionBody(function: JetNamedFunction, bodyResolver: BodyResolver, context: BodyResolveContext): BodyResolveResult {
            val delegatingBindingTrace = DelegatingBindingTrace(context.baseTrace.getBindingContext(), "Trace to resolve element", function)

            ForceResolveUtil.forceResolveAllContents(context.functionDescriptor)
            bodyResolver.resolveFunctionBody(context.outerDataFlowInfo, delegatingBindingTrace, function, context.functionDescriptor, context.declaringScope)

            return BodyResolveResult(delegatingBindingTrace, context)
        }
    }
}