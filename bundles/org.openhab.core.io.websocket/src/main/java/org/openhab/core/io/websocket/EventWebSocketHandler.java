/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
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
package org.openhab.core.io.websocket;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.openhab.core.events.Event;
import org.openhab.core.events.EventPublisher;
import org.openhab.core.events.EventSubscriber;
import org.openhab.core.items.ItemRegistry;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;

/**
 * The {@link EventWebSocketHandler} allows subscription to oh events over WebSocket
 *
 * @author Jan N. Klug - Initial contribution
 */
@NonNullByDefault
@Component(immediate = true, service = { EventSubscriber.class, WebSocketHandler.class })
public class EventWebSocketHandler implements EventSubscriber, WebSocketHandler {
    public static String HANDLER_ID = "event-subscriber";
    private final Gson gson = new Gson();
    private final EventPublisher eventPublisher;

    private final ItemEventUtility itemEventUtility;
    private final Set<EventWebSocket> webSockets = new CopyOnWriteArraySet<>();

    @Activate
    public EventWebSocketHandler(@Reference EventPublisher eventPublisher, @Reference ItemRegistry itemRegistry) {
        this.eventPublisher = eventPublisher;
        itemEventUtility = new ItemEventUtility(gson, itemRegistry);
    }

    @Override
    public Set<String> getSubscribedEventTypes() {
        return Set.of(EventSubscriber.ALL_EVENT_TYPES);
    }

    @Override
    public void receive(Event event) {
        webSockets.forEach(ws -> ws.processEvent(event));
    }

    public void registerListener(EventWebSocket eventWebSocket) {
        webSockets.add(eventWebSocket);
    }

    public void unregisterListener(EventWebSocket eventWebSocket) {
        webSockets.remove(eventWebSocket);
    }

    @Override
    public String getId() {
        return HANDLER_ID;
    }

    @Override
    public Object createWebSocket(@Nullable ServletUpgradeRequest servletUpgradeRequest,
            @Nullable ServletUpgradeResponse servletUpgradeResponse) {
        return new EventWebSocket(gson, EventWebSocketHandler.this, itemEventUtility, eventPublisher);
    }
}
