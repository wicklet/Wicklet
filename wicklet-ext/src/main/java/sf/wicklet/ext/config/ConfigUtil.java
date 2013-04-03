/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.config;

import org.apache.wicket.ajax.json.JSONException;

class ConfigUtil {

	ConfigUtil() {
	}

	static IConfigObject getObject(final Object value) throws JSONException {
		if (value == null) {
			return null;
		}
		if (value instanceof IConfigObject) {
			return (IConfigObject)value;
		}
		throw new JSONException("Value is not an IConfigObject");
	}

	static IConfigArray getArray(final Object value) throws JSONException {
		if (value == null) {
			return null;
		}
		if (value instanceof IConfigArray) {
			return (IConfigArray)value;
		}
		throw new JSONException("Value is not an IConfigArray");
	}

	static String getString(final Object value) throws JSONException {
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return (String)value;
		}
		throw new JSONException("Value is not a String");
	}

	static boolean getBool(final Object value) throws JSONException {
		if (value == null) {
			return false;
		}
		if (value instanceof Boolean) {
			return (Boolean)value;
		}
		throw new JSONException("Value is not a Boolean");
	}

	static int getInt(final Object value) throws JSONException {
		if (value instanceof Integer) {
			return (Integer)value;
		}
		throw new JSONException("Value is not a Integer");
	}

	public static long getLong(final Object value) throws JSONException {
		if (value instanceof Integer) {
			return (Integer)value;
		}
		if (value instanceof Long) {
			return (Long)value;
		}
		throw new JSONException("Value is not a Long");
	}

	public static double getDouble(final Object value) throws JSONException {
		if (value instanceof Double) {
			return (Double)value;
		}
		throw new JSONException("Value is not a Double");
	}

	public static IConfigObject getObject(final Object object, final IConfigObject def) {
		if (object == null) {
			return null;
		}
		if (object instanceof IConfigObject) {
			return (IConfigObject)object;
		}
		return def;
	}

	public static IConfigArray getArray(final Object object, final IConfigArray def) {
		if (object == null) {
			return null;
		}
		if (object instanceof IConfigArray) {
			return (IConfigArray)object;
		}
		return def;
	}

	public static String getString(final Object object, final String def) {
		if (object == null) {
			return null;
		}
		if (object instanceof String) {
			return (String)object;
		}
		return def;
	}

	public static boolean getBool(final Object object, final boolean def) {
		if (object instanceof Boolean) {
			return (Boolean)object;
		}
		return def;
	}

	public static int getInt(final Object object, final int def) {
		if (object instanceof Integer) {
			return (Integer)object;
		}
		return def;
	}

	public static long getLong(final Object object, final long def) {
		if (object instanceof Integer) {
			return (Integer)object;
		}
		if (object instanceof Long) {
			return (Long)object;
		}
		return def;
	}

	public static double getDouble(final Object object, final double def) {
		if (object instanceof Double) {
			return (Double)object;
		}
		return def;
	}
}
