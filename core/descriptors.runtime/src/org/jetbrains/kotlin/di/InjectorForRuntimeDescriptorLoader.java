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

package org.jetbrains.kotlin.di;

import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.resolve.jvm.JavaDescriptorResolver;
import org.jetbrains.kotlin.load.java.components.ExternalSignatureResolver;
import org.jetbrains.kotlin.load.java.components.MethodSignatureChecker;
import org.jetbrains.kotlin.load.java.components.JavaResolverCache;
import org.jetbrains.kotlin.load.java.components.ExternalAnnotationResolver;
import org.jetbrains.kotlin.load.java.structure.JavaPropertyInitializerEvaluator;
import org.jetbrains.kotlin.load.java.components.SamConversionResolver;
import org.jetbrains.kotlin.load.java.components.RuntimeErrorReporter;
import org.jetbrains.kotlin.load.java.components.RuntimeSourceElementFactory;
import org.jetbrains.kotlin.load.java.lazy.SingleModuleClassResolver;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.load.java.reflect.ReflectJavaClassFinder;
import org.jetbrains.kotlin.load.kotlin.reflect.ReflectKotlinClassFinder;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaPackageFragmentProvider;
import org.jetbrains.kotlin.load.java.lazy.GlobalJavaResolverContext;
import org.jetbrains.kotlin.load.kotlin.DeserializedDescriptorResolver;
import org.jetbrains.kotlin.load.kotlin.DeserializationComponentsForJava;
import org.jetbrains.kotlin.load.kotlin.JavaClassDataFinder;
import org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl;
import org.jetbrains.annotations.NotNull;
import javax.annotation.PreDestroy;

/* This file is generated by org.jetbrains.kotlin.generators.injectors.InjectorsPackage. DO NOT EDIT! */
@SuppressWarnings("all")
public class InjectorForRuntimeDescriptorLoader {

    private final ClassLoader classLoader;
    private final ModuleDescriptor moduleDescriptor;
    private final JavaDescriptorResolver javaDescriptorResolver;
    private final ExternalSignatureResolver externalSignatureResolver;
    private final MethodSignatureChecker methodSignatureChecker;
    private final JavaResolverCache javaResolverCache;
    private final ExternalAnnotationResolver externalAnnotationResolver;
    private final JavaPropertyInitializerEvaluator javaPropertyInitializerEvaluator;
    private final SamConversionResolver samConversionResolver;
    private final RuntimeErrorReporter runtimeErrorReporter;
    private final RuntimeSourceElementFactory runtimeSourceElementFactory;
    private final SingleModuleClassResolver singleModuleClassResolver;
    private final LockBasedStorageManager lockBasedStorageManager;
    private final ReflectJavaClassFinder reflectJavaClassFinder;
    private final ReflectKotlinClassFinder reflectKotlinClassFinder;
    private final LazyJavaPackageFragmentProvider lazyJavaPackageFragmentProvider;
    private final GlobalJavaResolverContext globalJavaResolverContext;
    private final DeserializedDescriptorResolver deserializedDescriptorResolver;
    private final DeserializationComponentsForJava deserializationComponentsForJava;
    private final JavaClassDataFinder javaClassDataFinder;
    private final BinaryClassAnnotationAndConstantLoaderImpl binaryClassAnnotationAndConstantLoader;

    public InjectorForRuntimeDescriptorLoader(
        @NotNull ClassLoader classLoader,
        @NotNull ModuleDescriptor moduleDescriptor
    ) {
        this.classLoader = classLoader;
        this.moduleDescriptor = moduleDescriptor;
        this.lockBasedStorageManager = new LockBasedStorageManager();
        this.reflectJavaClassFinder = new ReflectJavaClassFinder(classLoader);
        this.reflectKotlinClassFinder = new ReflectKotlinClassFinder(classLoader);
        this.runtimeErrorReporter = RuntimeErrorReporter.INSTANCE$;
        this.deserializedDescriptorResolver = new DeserializedDescriptorResolver(runtimeErrorReporter);
        this.externalAnnotationResolver = ExternalAnnotationResolver.EMPTY;
        this.externalSignatureResolver = ExternalSignatureResolver.DO_NOTHING;
        this.methodSignatureChecker = MethodSignatureChecker.DO_NOTHING;
        this.javaResolverCache = JavaResolverCache.EMPTY;
        this.javaPropertyInitializerEvaluator = JavaPropertyInitializerEvaluator.DO_NOTHING;
        this.samConversionResolver = SamConversionResolver.EMPTY;
        this.runtimeSourceElementFactory = RuntimeSourceElementFactory.INSTANCE$;
        this.singleModuleClassResolver = new SingleModuleClassResolver();
        this.globalJavaResolverContext = new GlobalJavaResolverContext(lockBasedStorageManager, reflectJavaClassFinder, reflectKotlinClassFinder, deserializedDescriptorResolver, externalAnnotationResolver, externalSignatureResolver, runtimeErrorReporter, methodSignatureChecker, javaResolverCache, javaPropertyInitializerEvaluator, samConversionResolver, runtimeSourceElementFactory, singleModuleClassResolver);
        this.lazyJavaPackageFragmentProvider = new LazyJavaPackageFragmentProvider(globalJavaResolverContext, getModuleDescriptor());
        this.javaDescriptorResolver = new JavaDescriptorResolver(lazyJavaPackageFragmentProvider, getModuleDescriptor());
        this.javaClassDataFinder = new JavaClassDataFinder(reflectKotlinClassFinder, deserializedDescriptorResolver);
        this.binaryClassAnnotationAndConstantLoader = new BinaryClassAnnotationAndConstantLoaderImpl(getModuleDescriptor(), lockBasedStorageManager, reflectKotlinClassFinder, runtimeErrorReporter);
        this.deserializationComponentsForJava = new DeserializationComponentsForJava(lockBasedStorageManager, getModuleDescriptor(), javaClassDataFinder, binaryClassAnnotationAndConstantLoader, lazyJavaPackageFragmentProvider);

        singleModuleClassResolver.setResolver(javaDescriptorResolver);

        deserializedDescriptorResolver.setComponents(deserializationComponentsForJava);

    }

    @PreDestroy
    public void destroy() {
    }

    public ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    public JavaDescriptorResolver getJavaDescriptorResolver() {
        return this.javaDescriptorResolver;
    }

}