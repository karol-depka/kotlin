/*
 * Copyright 2010-2012 JetBrains s.r.o.
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

package org.jetbrains.jet.renderer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.lang.descriptors.DeclarationDescriptor;
import org.jetbrains.jet.lang.descriptors.FunctionDescriptor;
import org.jetbrains.jet.lang.descriptors.ValueParameterDescriptor;
import org.jetbrains.jet.lang.diagnostics.rendering.Renderer;
import org.jetbrains.jet.lang.types.JetType;

public interface DescriptorRenderer extends Renderer<DeclarationDescriptor> {
    DescriptorRenderer COMPACT_WITH_MODIFIERS = new DescriptorRendererBuilder().setWithDefinedIn(false).build();

    DescriptorRenderer COMPACT = new DescriptorRendererBuilder()
            .setWithDefinedIn(false)
            .setModifiers(false).build();

    DescriptorRenderer STARTS_FROM_NAME = new DescriptorRendererBuilder()
            .setWithDefinedIn(false)
            .setModifiers(false)
            .setStartFromName(true).build();

    DescriptorRenderer TEXT = new DescriptorRendererBuilder().build();

    DescriptorRenderer SHORT_NAMES_IN_TYPES = new DescriptorRendererBuilder().setShortNames(true).build();

    DescriptorRenderer DEBUG_TEXT = new DescriptorRendererBuilder().setDebugMode(true).build();

    DescriptorRenderer HTML = new DescriptorRendererBuilder().setTextFormat(TextFormat.HTML).build();

    String renderType(JetType type);

    @NotNull
    @Override
    String render(@NotNull DeclarationDescriptor declarationDescriptor);

    String renderFunctionParameters(@NotNull FunctionDescriptor functionDescriptor);

    enum TextFormat {
        PLAIN, HTML
    }

    interface ValueParametersHandler {
        // by default, renders "("
        void appendBeforeValueParameters(@NotNull FunctionDescriptor function, @NotNull StringBuilder stringBuilder);

        // by default, renders ")"
        void appendAfterValueParameters(@NotNull FunctionDescriptor function, @NotNull StringBuilder stringBuilder);

        // by default, renders nothing
        void appendBeforeValueParameter(@NotNull ValueParameterDescriptor parameter, @NotNull StringBuilder stringBuilder);

        // by default, renders ", " if its not last parameter
        void appendAfterValueParameter(@NotNull ValueParameterDescriptor parameter, @NotNull StringBuilder stringBuilder);
    }
}
