/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.wicket.ajax.json.JSONException;

/** A read only equivalent of a JSONObject. */
public class ConfigObject implements IConfigObject {

	////////////////////////////////////////////////////////////////////////

	public static ConfigObject parse(final String source) throws JSONException {
		return parse(new ConfigTokenizer(source));
	}

	public static ConfigObject parse(final ConfigTokenizer x) throws JSONException {
		final ConfigObject ret = new ConfigObject();
		char c;
		String key;
		if (x.nextClean() != '{') {
			throw x.syntaxError("A JSONObject text must begin with '{'");
		}
		for (;;) {
			c = x.nextClean();
			switch (c) {
			case 0:
				throw x.syntaxError("A JSONObject text must end with '}'");
			case '}':
				return ret;
			default :
				x.back();
				key = x.nextValue().toString();
			}
			// The key is followed by ':'. We will also tolerate '=' or '=>'.
			c = x.nextClean();
			if (c == '=') {
				if (x.next() != '>') {
					x.back();
			}} else if (c != ':') {
				throw x.syntaxError("Expected a ':' after a key");
			}
			ret.putOnce(key, x.nextValue());
			// Pairs are separated by ','. We will also tolerate ';'.
			switch (x.nextClean()) {
			case ';':
			case ',':
				if (x.nextClean() == '}') {
					return ret;
				}
				x.back();
				break;
			case '}':
				return ret;
			default :
				throw x.syntaxError("Expected a ',' or '}'");
	}}}

	////////////////////////////////////////////////////////////////////////

	private final Map<String, Object> map = new LinkedHashMap<String, Object>();

	////////////////////////////////////////////////////////////////////////

	public ConfigObject() {
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean has(final String key) {
		return map.containsKey(key);
	}

	@Override
	public Collection<String> keys() {
		return new ArrayList<String>(map.keySet());
	}

	private void putOnce(final String key, final Object value) throws JSONException {
		if (key != null && value != null) {
			if (map.containsKey(key)) {
				throw new JSONException("Duplicate key \"" + key + "\"");
			}
			map.put(key, value);
	}}

	private Object get(final String key) throws JSONException {
		if (map.containsKey(key)) {
			return map.get(key);
		}
		throw new JSONException("Key not exists: " + key);
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public IConfigObject getObject(final String key) throws JSONException {
		return ConfigUtil.getObject(get(key));
	}

	@Override
	public IConfigArray getArray(final String key) throws JSONException {
		return ConfigUtil.getArray(get(key));
	}

	@Override
	public String getString(final String key) throws JSONException {
		return ConfigUtil.getString(get(key));
	}

	@Override
	public boolean getBool(final String key) throws JSONException {
		return ConfigUtil.getBool(get(key));
	}

	@Override
	public int getInt(final String key) throws JSONException {
		return ConfigUtil.getInt(get(key));
	}

	@Override
	public long getLong(final String key) throws JSONException {
		return ConfigUtil.getLong(get(key));
	}

	@Override
	public double getDouble(final String key) throws JSONException {
		return ConfigUtil.getDouble(get(key));
	}

	@Override
	public IConfigObject getObject(final String key, final IConfigObject def) {
		if (!map.containsKey(key)) {
			return def;
		}
		return ConfigUtil.getObject(map.get(key), def);
	}

	@Override
	public IConfigArray getArray(final String key, final IConfigArray def) {
		if (!map.containsKey(key)) {
			return def;
		}
		return ConfigUtil.getArray(map.get(key), def);
	}

	@Override
	public String getString(final String key, final String def) {
		if (!map.containsKey(key)) {
			return def;
		}
		return ConfigUtil.getString(map.get(key), def);
	}

	@Override
	public boolean getBool(final String key, final boolean def) {
		if (!map.containsKey(key)) {
			return def;
		}
		return ConfigUtil.getBool(map.get(key), def);
	}

	@Override
	public int getInt(final String key, final int def) {
		if (!map.containsKey(key)) {
			return def;
		}
		return ConfigUtil.getInt(map.get(key), def);
	}

	@Override
	public long getLong(final String key, final long def) {
		if (!map.containsKey(key)) {
			return def;
		}
		return ConfigUtil.getLong(map.get(key), def);
	}

	@Override
	public double getDouble(final String key, final double def) {
		if (!map.containsKey(key)) {
			return def;
		}
		return ConfigUtil.getDouble(map.get(key), def);
	}

	////////////////////////////////////////////////////////////////////////
}
