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

package com.google.dart.compiler.backend.js.ast.metadata

abstract class HasMetadata {
    private val metadata: MutableMap<String, Any?> = hashMapOf()

    fun <T> getData(key: String): T {
        @suppress("UNCHECKED_CAST")
        return metadata[key] as T
    }

    fun <T> setData(key: String, value: T) {
        metadata[key] = value
    }

    fun hasData(key: String): Boolean {
        return metadata.containsKey(key)
    }

    fun removeData(key: String) {
        metadata.remove(key)
    }

    fun copyMetadataFrom(other: HasMetadata) {
        metadata.putAll(other.metadata)
    }
}
