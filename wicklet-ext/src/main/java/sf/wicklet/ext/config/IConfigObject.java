/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.config;

import java.util.Collection;
import org.apache.wicket.ajax.json.JSONException;

public interface IConfigObject {

	int size();
	boolean has(String key);
	Collection<String> keys();

	IConfigObject getObject(String key) throws JSONException;
	IConfigArray getArray(String key) throws JSONException;
	String getString(String key) throws JSONException;
	boolean getBool(String key) throws JSONException;
	int getInt(String key) throws JSONException;
	long getLong(String key) throws JSONException;
	double getDouble(String key) throws JSONException;

	IConfigObject getObject(String key, IConfigObject def);
	IConfigArray getArray(String key, IConfigArray def);
	String getString(String key, String def);
	boolean getBool(String key, boolean def);
	int getInt(String key, int def);
	long getLong(String key, long def);
	double getDouble(String key, double def);
}
