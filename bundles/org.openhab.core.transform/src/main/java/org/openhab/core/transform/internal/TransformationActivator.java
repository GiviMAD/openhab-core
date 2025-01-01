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
package org.openhab.core.transform.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extension of the default OSGi bundle activator
 *
 * @author Thomas Eichstaedt-Engelen - Initial contribution
 * @author Kai Kreuzer - Initial contribution
 */
@NonNullByDefault
public final class TransformationActivator implements BundleActivator {

    private final Logger logger = LoggerFactory.getLogger(TransformationActivator.class);

    private static @Nullable BundleContext context;

    /**
     * Called whenever the OSGi framework starts our bundle
     */
    @Override
    public void start(@Nullable BundleContext bc) throws Exception {
        context = bc;
        logger.debug("Transformation Service has been started.");
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    @Override
    public void stop(@Nullable BundleContext bc) throws Exception {
        context = null;
        logger.debug("Transformation Service has been stopped.");
    }

    /**
     * Returns the bundle context of this bundle
     * 
     * @return the bundle context
     */
    public static @Nullable BundleContext getContext() {
        return context;
    }
}
