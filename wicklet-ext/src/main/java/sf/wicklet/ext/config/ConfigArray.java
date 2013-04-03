/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.config;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;

public class ConfigArray implements IConfigArray {

	////////////////////////////////////////////////////////////////////////

	public static ConfigArray parse(final ConfigTokenizer x) throws JSONException {
		final ConfigArray ret = new ConfigArray();
		if (x.nextClean() != '[') {
			throw x.syntaxError("A JSONArray text must start with '['");
		}
		if (x.nextClean() != ']') {
			x.back();
			for (;;) {
				if (x.nextClean() == ',') {
					x.back();
					ret.add(JSONObject.NULL);
				} else {
					x.back();
					ret.add(x.nextValue());
				}
				switch (x.nextClean()) {
				case ';':
				case ',':
					if (x.nextClean() == ']') {
						return ret;
					}
					x.back();
					break;
				case ']':
					return ret;
				default :
					throw x.syntaxError("Expected a ',' or ']'");
		}}}
		return ret;
	}

	////////////////////////////////////////////////////////////////////////

	private final List<Object> list = new ArrayList<Object>();

	////////////////////////////////////////////////////////////////////////

	public ConfigArray() {
	}

	@Override
	public int size() {
		return list.size();
	}

	////////////////////////////////////////////////////////////////////////

	private void add(final Object a) {
		list.add(a);
	}

	private Object get(final int index) throws JSONException {
		if (index > list.size()) {
			throw new JSONException("Index out of bound: size=" + list.size() + ", index=" + index);
		}
		return list.get(index);
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public IConfigObject getObject(final int index) throws JSONException {
		final Object v = get(index);
		return ConfigUtil.getObject(v);
	}

	@Override
	public IConfigArray getArray(final int index) throws JSONException {
		return ConfigUtil.getArray(get(index));
	}

	@Override
	public String getString(final int index) throws JSONException {
		return ConfigUtil.getString(get(index));
	}

	@Override
	public boolean getBool(final int index) throws JSONException {
		return ConfigUtil.getBool(get(index));
	}

	@Override
	public int getInt(final int index) throws JSONException {
		return ConfigUtil.getInt(get(index));
	}

	@Override
	public long getLong(final int index) throws JSONException {
		return ConfigUtil.getLong(get(index));
	}

	@Override
	public double getDouble(final int index) throws JSONException {
		return ConfigUtil.getDouble(get(index));
	}

	@Override
	public IConfigObject getObject(final int index, final IConfigObject def) {
		try {
			return ConfigUtil.getObject(get(index), def);
		} catch (final JSONException e) {
			return def;
	}}

	@Override
	public IConfigArray getArray(final int index, final IConfigArray def) {
		try {
			return ConfigUtil.getArray(get(index), def);
		} catch (final JSONException e) {
			return def;
	}}

	@Override
	public String getString(final int index, final String def) {
		try {
			return ConfigUtil.getString(get(index), def);
		} catch (final JSONException e) {
			return def;
	}}

	@Override
	public boolean getBool(final int index, final boolean def) {
		try {
			return ConfigUtil.getBool(get(index), def);
		} catch (final JSONException e) {
			return def;
	}}

	@Override
	public int getInt(final int index, final int def) {
		try {
			return ConfigUtil.getInt(get(index), def);
		} catch (final JSONException e) {
			return def;
	}}

	@Override
	public long getLong(final int index, final long def) {
		try {
			return ConfigUtil.getLong(get(index), def);
		} catch (final JSONException e) {
			return def;
	}}

	@Override
	public double getDouble(final int index, final double def) {
		try {
			return ConfigUtil.getDouble(get(index), def);
		} catch (final JSONException e) {
			return def;
	}}

	////////////////////////////////////////////////////////////////////////
}
