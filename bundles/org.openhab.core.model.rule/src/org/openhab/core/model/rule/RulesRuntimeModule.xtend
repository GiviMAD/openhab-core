/**
 * Copyright (c) 2010-2025 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package
/*
 * generated by Xtext
 */
org.openhab.core.model.rule

import com.google.inject.Binder
import com.google.inject.name.Names
import org.openhab.core.model.rule.scoping.RulesImplicitlyImportedTypes
import org.openhab.core.model.rule.scoping.RulesJavaReflectAccess
import org.openhab.core.model.script.interpreter.ScriptInterpreter
import org.openhab.core.model.script.jvmmodel.ScriptTypeComputer
import org.openhab.core.model.script.scoping.ActionClassLoader
import org.openhab.core.model.script.scoping.ScriptImportSectionNamespaceScopeProvider
import org.openhab.core.model.script.scoping.StateAndCommandProvider
import org.eclipse.xtext.common.types.access.IJvmTypeProvider
import org.eclipse.xtext.common.types.access.reflect.ReflectionTypeProviderFactory
import org.eclipse.xtext.common.types.access.reflect.ReflectionTypeScopeProvider
import org.eclipse.xtext.common.types.util.JavaReflectAccess
import org.eclipse.xtext.common.types.xtext.AbstractTypeScopeProvider
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.generator.IGenerator.NullGenerator
import org.eclipse.xtext.linking.lazy.LazyURIEncoder
import org.eclipse.xtext.scoping.IScopeProvider
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider
import org.eclipse.xtext.xbase.interpreter.IExpressionInterpreter
import org.eclipse.xtext.xbase.scoping.batch.ImplicitlyImportedFeatures
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputer

/** 
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 * @author Oliver Libutzki - Initial contribution
 */
@SuppressWarnings("restriction") class RulesRuntimeModule extends AbstractRulesRuntimeModule {

    def Class<? extends ITypeComputer> bindITypeComputer() {
        return ScriptTypeComputer
    }

    def Class<? extends JavaReflectAccess> bindJavaReflectAccess() {
        return RulesJavaReflectAccess
    }

    def Class<? extends ImplicitlyImportedFeatures> bindImplicitlyImportedTypes() {
        return RulesImplicitlyImportedTypes
    }

    def Class<StateAndCommandProvider> bindStateAndCommandProvider() {
        return StateAndCommandProvider
    }

    override Class<? extends IGenerator> bindIGenerator() {
        return NullGenerator
    }

    override Class<? extends IExpressionInterpreter> bindIExpressionInterpreter() {
        return ScriptInterpreter
    }

    override void configureIScopeProviderDelegate(Binder binder) {
        binder.bind(IScopeProvider).annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE)).to(
            ScriptImportSectionNamespaceScopeProvider)
    }

    override Class<? extends IJvmTypeProvider.Factory> bindIJvmTypeProvider$Factory() {
        return ReflectionTypeProviderFactory
    }

    override Class<? extends AbstractTypeScopeProvider> bindAbstractTypeScopeProvider() {
        return ReflectionTypeScopeProvider
    }

    override ClassLoader bindClassLoaderToInstance() {
        return new ActionClassLoader(super.bindClassLoaderToInstance())
    }

    override void configureUseIndexFragmentsForLazyLinking(Binder binder) {
        binder.bind(Boolean.TYPE).annotatedWith(Names.named(LazyURIEncoder.USE_INDEXED_FRAGMENTS_BINDING)).toInstance(
            Boolean.FALSE)
    }
}
