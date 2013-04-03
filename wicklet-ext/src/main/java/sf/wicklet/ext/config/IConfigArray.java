/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.config;

import org.apache.wicket.ajax.json.JSONException;

public interface IConfigArray {

	int size();

	IConfigObject getObject(int index) throws JSONException;
	IConfigArray getArray(int index) throws JSONException;
	String getString(int index) throws JSONException;
	boolean getBool(int index) throws JSONException;
	int getInt(int index) throws JSONException;
	long getLong(int index) throws JSONException;
	double getDouble(int index) throws JSONException;

	IConfigObject getObject(int index, IConfigObject def);
	IConfigArray getArray(int index, IConfigArray def);
	String getString(int index, String def);
	boolean getBool(int index, boolean def);
	int getInt(int index, int def);
	long getLong(int index, long def);
	double getDouble(int index, double def);
}
