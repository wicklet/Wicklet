/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client;

import java.util.logging.Logger;

public class WickletGwtClient {

	public static final boolean DEBUG = true;
	private static final Logger logger = DEBUG ? Logger.getLogger(HistoryChangeListener.class.getName()) : null;

	public static void debug(final String msg) {
		if (DEBUG) {
			logger.finest(msg);
	}}
}
