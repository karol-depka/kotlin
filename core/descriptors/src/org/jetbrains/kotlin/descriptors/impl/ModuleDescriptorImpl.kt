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

package org.jetbrains.kotlin.descriptors.impl

import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.resolve.scopes.JetScope
import org.jetbrains.kotlin.resolve.scopes.LazyScopeAdapter
import org.jetbrains.kotlin.storage.LockBasedStorageManager
import org.jetbrains.kotlin.storage.StorageManager
import java.util.ArrayList
import java.util.LinkedHashSet
import kotlin.properties.Delegates

public class ModuleDescriptorImpl(
        moduleName: Name,
        private val storageManager: StorageManager,
        private val moduleParameters: ModuleParameters
) : DeclarationDescriptorImpl(Annotations.EMPTY, moduleName), ModuleDescriptor, ModuleParameters by moduleParameters {
    init {
        if (!moduleName.isSpecial()) {
            throw IllegalArgumentException("Module name must be special: $moduleName")
        }
    }
    private var isSealed = false

    /*
     * Sealed module cannot have its dependencies modified. Seal the module after you're done configuring it.
     * Module will be sealed automatically as soon as you query its contents.
     */
    public fun seal() {
        if (isSealed) return

        assert(this in dependencies) { "Module $id is not contained in his own dependencies, this is probably a misconfiguration" }
        isSealed = true
    }

    private val dependencies: MutableList<ModuleDescriptorImpl> = ArrayList()
    //TODO_R:!!
    override var packageViewManager: PackageViewManager = PackageViewManagerImpl(this, LockBasedStorageManager.NO_LOCKS)

    private var packageFragmentProviderForModuleContent: PackageFragmentProvider? = null

    private val packageFragmentProviderForWholeModuleWithDependencies by Delegates.lazy {
        seal()
        dependencies.forEach {
            dependency ->
            assert(dependency.isInitialized) {
                "Dependency module ${dependency.id} was not initialized by the time contents of dependent module ${this.id} were queried"
            }
        }
        CompositePackageFragmentProvider(dependencies.map {
            it.packageFragmentProviderForModuleContent!!
        })
    }

    public val isInitialized: Boolean
        get() = packageFragmentProviderForModuleContent != null

    public fun addDependencyOnModule(dependency: ModuleDescriptorImpl) {
        assert(!isSealed) { "Can't modify dependencies of sealed module $id" }
        assert(dependency !in dependencies) {
            "Trying to add dependency on module ${dependency.id} a second time for module ${this.id}, this is probably a misconfiguration"
        }
        dependencies.add(dependency)
    }

    private val id: String
        get() = getName().toString()

    /*
     * Call initialize() to set module contents. Uninitialized module cannot be queried for its contents.
     * Initialize() and seal() can be called in any order.
     */
    public fun initialize(providerForModuleContent: PackageFragmentProvider) {
        assert(!isInitialized) { "Attempt to initialize module $id twice" }
        this.packageFragmentProviderForModuleContent = providerForModuleContent
        this.packageViewManager = packageViewManager
    }

    val packageFragmentProvider: PackageFragmentProvider
        get() = packageFragmentProviderForWholeModuleWithDependencies

    private val friendModules = LinkedHashSet<ModuleDescriptor>()

    override fun isFriend(other: ModuleDescriptor) = other == this || other in friendModules

    public fun addFriend(friend: ModuleDescriptorImpl): Unit {
        assert(friend != this) { "Attempt to make module $id a friend to itself" }
        assert(!isSealed) { "Attempt to add friend module ${friend.id} to sealed module $id" }
        friendModules.add(friend)
    }

    override val builtIns: KotlinBuiltIns
        get() = KotlinBuiltIns.getInstance()
}

class PackageViewManagerImpl(private val module: ModuleDescriptorImpl, private val storageManager: StorageManager) : PackageViewManager {
    override fun getPackage(fqName: FqName): PackageViewDescriptor? {
        val fragments = module.packageFragmentProvider.getPackageFragments(fqName)
        return if (!fragments.isEmpty()) PackageViewDescriptorImpl(module, fqName, fragments) else null
    }

    override fun getSubPackagesOf(fqName: FqName, nameFilter: (Name) -> Boolean): Collection<FqName> {
        return module.packageFragmentProvider.getSubPackagesOf(fqName, nameFilter)
    }

    override fun getParentView(packageView: PackageViewDescriptor): PackageViewDescriptor? {
        val fqName = packageView.getFqName()
        return if (fqName.isRoot()) null else return LazyPackageViewWrapper(fqName.parent(), module, storageManager)
    }
}

/*
 this wrapper should only be created to save computation for package view
 that is known to exist but we do not necessarily need to query its contents

 ModuleDescriptor#getPackage should be used for most use cases
  */
public class LazyPackageViewWrapper(
        fqName: FqName, module: ModuleDescriptor, storageManager: StorageManager
)
: AbstractPackageViewDescriptor(fqName, module) {
    private val _delegate = storageManager.createNullableLazyValue {
        module.packageViewManager.getPackage(getFqName())
    }

    private val delegate: PackageViewDescriptor?
        get() = _delegate()

    private val scope = LazyScopeAdapter(storageManager.createLazyValue { delegate?.getMemberScope() ?: JetScope.Empty })

    override fun getMemberScope(): JetScope {
        return scope
    }

    override fun getFragments(): MutableList<PackageFragmentDescriptor> {
        return delegate?.getFragments() ?: listOf()
    }
}
