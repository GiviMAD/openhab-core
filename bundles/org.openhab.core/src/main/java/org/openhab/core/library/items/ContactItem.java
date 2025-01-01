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
package org.openhab.core.library.items;

import java.util.List;
import java.util.Locale;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.items.GenericItem;
import org.openhab.core.library.CoreItemFactory;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.core.types.Command;
import org.openhab.core.types.CommandDescription;
import org.openhab.core.types.RefreshType;
import org.openhab.core.types.State;
import org.openhab.core.types.TimeSeries;
import org.openhab.core.types.UnDefType;

/**
 * A ContactItem can be used for sensors that return an "open" or "close" as a state.
 * This is useful for doors, windows, etc.
 *
 * @author Kai Kreuzer - Initial contribution
 */
@NonNullByDefault
public class ContactItem extends GenericItem {

    private static final List<Class<? extends State>> ACCEPTED_DATA_TYPES = List.of(OpenClosedType.class,
            UnDefType.class);
    private static final List<Class<? extends Command>> ACCEPTED_COMMAND_TYPES = List.of(RefreshType.class);

    public ContactItem(String name) {
        super(CoreItemFactory.CONTACT, name);
    }

    @Override
    public List<Class<? extends State>> getAcceptedDataTypes() {
        return ACCEPTED_DATA_TYPES;
    }

    @Override
    public List<Class<? extends Command>> getAcceptedCommandTypes() {
        return ACCEPTED_COMMAND_TYPES;
    }

    @Override
    public void setState(State state) {
        if (isAcceptedState(ACCEPTED_DATA_TYPES, state)) {
            applyState(state);
        } else {
            logSetTypeError(state);
        }
    }

    @Override
    public void setTimeSeries(TimeSeries timeSeries) {
        if (timeSeries.getStates().allMatch(s -> s.state() instanceof OpenClosedType)) {
            applyTimeSeries(timeSeries);
        } else {
            logSetTypeError(timeSeries);
        }
    }

    @Override
    public @Nullable CommandDescription getCommandDescription(@Nullable Locale locale) {
        return getCommandOptions(locale);
    }
}
