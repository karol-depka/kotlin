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
import org.jetbrains.annotations.Nullable;

public class DescriptorRendererBuilder {
    private boolean shortNames = false;
    private boolean withDefinedIn = true;
    private boolean modifiers = true;
    private boolean startFromName = false;
    private boolean debugMode = false;
    @Nullable
    private DescriptorRenderer.ValueParametersHandler valueParametersHandler = null;
    @NotNull
    private DescriptorRenderer.TextFormat textFormat = DescriptorRenderer.TextFormat.PLAIN;

    public DescriptorRendererBuilder() {
    }

    public DescriptorRendererBuilder setShortNames(boolean shortNames) {
        this.shortNames = shortNames;
        return this;
    }

    public DescriptorRendererBuilder setWithDefinedIn(boolean withDefinedIn) {
        this.withDefinedIn = withDefinedIn;
        return this;
    }

    public DescriptorRendererBuilder setModifiers(boolean modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public DescriptorRendererBuilder setStartFromName(boolean startFromName) {
        this.startFromName = startFromName;
        return this;
    }

    public DescriptorRendererBuilder setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        return this;
    }

    public DescriptorRendererBuilder setValueParametersHandler(@Nullable DescriptorRenderer.ValueParametersHandler valueParametersHandler) {
        this.valueParametersHandler = valueParametersHandler;
        return this;
    }

    public DescriptorRendererBuilder setTextFormat(@NotNull DescriptorRenderer.TextFormat textFormat) {
        this.textFormat = textFormat;
        return this;
    }

    public DescriptorRenderer build() {
        return new DescriptorRendererImpl(shortNames, withDefinedIn, modifiers, startFromName, debugMode, valueParametersHandler, textFormat);
    }
}
