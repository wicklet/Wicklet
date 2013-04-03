/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client;

import java.util.Map;
import java.util.TreeMap;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

public class HistoryChangeListener implements ValueChangeHandler<String> {
    private static HistoryChangeListener singleton;
    public static synchronized void init() {
        if (singleton == null) {
            singleton = new HistoryChangeListener();
            History.addValueChangeHandler(singleton);
    }}
    public static synchronized HistoryChangeListener get() {
        return singleton;
    }
    private ValueChangeHandler<String> handler;
    private final Map<String, ValueChangeHandler<String>> handlers = new TreeMap<String, ValueChangeHandler<String>>();

    public void setDefaultHandler(final ValueChangeHandler<String> h) {
        handler = h;
    }
    public void putHandler(final String token, final ValueChangeHandler<String> h) {
        if (handlers.containsKey(token)) {
            throw new RuntimeException("ERROR: Handler is already registered for token: " + token);
        }
        handlers.put(token, h);
    }
    public ValueChangeHandler<String> getHandler(final String token) {
        return handlers.get(token);
    }
    @Override
    public void onValueChange(final ValueChangeEvent<String> event) {
        final String token = event.getValue();
        WickletGwtClient.debug("# HistoryChangeListener():  token: " + token);
        final ValueChangeHandler<String> h = handlers.get(token);
        if (h != null) {
            h.onValueChange(event);
        } else if (handler != null) {
            handler.onValueChange(event);
        } else {
            WickletGwtClient.debug("# HistoryChangeListener(): no handler for token: " + token);
    }}
}
