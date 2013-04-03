/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.wicket.ajax.json.JSONException;
import sf.blacksun.util.FileUtil;
import sf.wicklet.ext.config.ConfigObject;
import sf.wicklet.ext.config.IConfigArray;
import sf.wicklet.ext.config.IConfigObject;

public class WickletConfiguration implements IWickletConfiguration {

	private static final long serialVersionUID = 1L;
	private final IConfigObject root;

	/**
	 * @return null if input is not valid.
	 * @throws IOException
	 * @throws JSONException
	 */
	public static IWickletConfiguration parse(final File jsonfile) throws JSONException, IOException {
		return new WickletConfiguration(ConfigObject.parse(FileUtil.asString(jsonfile)));
	}

	public static IWickletConfiguration parse(final InputStream json) throws JSONException, IOException {
		return new WickletConfiguration(ConfigObject.parse(FileUtil.asString(new InputStreamReader(json))));
	}

	public static IWickletConfiguration parse(final String json) throws JSONException, IOException {
		return new WickletConfiguration(ConfigObject.parse(json));
	}

	public WickletConfiguration() {
		root = new ConfigObject();
	}

	private WickletConfiguration(final IConfigObject a) {
		root = a;
	}

	@Override
	public IConfigObject root() {
		return root;
	}

	@Override
	public IConfigObject getObject(final String...segments) {
		return getObject(root, segments);
	}

	@Override
	public IConfigArray getArray(final String...segments) {
		return getArray(root, segments);
	}

	@Override
	public String getString(final String...segments) {
		return getString(root, segments);
	}

	@Override
	public IConfigObject getObject(final IConfigObject parent, final String...segments) {
		return getobject(parent, segments, 0, segments.length);
	}

	@Override
	public IConfigArray getArray(final IConfigObject parent, final String...segments) {
		final int len = segments.length;
		final IConfigObject a = getobject(parent, segments, 0, len - 1);
		if (a != null) {
			return a.getArray(segments[len - 1], null);
		}
		return null;
	}

	@Override
	public String getString(final IConfigObject parent, final String...segments) {
		final int len = segments.length;
		final IConfigObject a = getobject(parent, segments, 0, len - 1);
		if (a != null) {
			return a.getString(segments[len - 1], null);
		}
		return null;
	}

	private IConfigObject getobject(
		final IConfigObject parent, final String[] segments, final int start, final int end) {
		IConfigObject n = parent;
		for (int i = start; i < end; ++i) {
			final String key = segments[i];
			if (n == null || !n.has(key)) {
				return null;
			}
			n = n.getObject(key, null);
		}
		return n;
	}
}
