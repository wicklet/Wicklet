/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.clients;

import java.util.logging.Logger;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;

public class CConf {

	public static final boolean DEBUG = true;
	public static final boolean USE_ASYNC = !DEBUG;
	private static final Logger logger = Logger.getLogger("");

	public static Logger getLogger() {
		return logger;
	}

	public static void debug(final String msg) {
		if (DEBUG) {
			getLogger().finest(msg);
	}}

	public static void debug(final String msg, final Throwable e) {
		if (DEBUG) {
			getLogger().finest(msg + (e == null ? "" : ": Exception: " + e.getMessage()));
	}}

	public static void runAsync(final RunAsyncCallback cb) {
		if (USE_ASYNC) {
			GWT.runAsync(cb);
		} else {
			cb.onSuccess();
	}}
}
