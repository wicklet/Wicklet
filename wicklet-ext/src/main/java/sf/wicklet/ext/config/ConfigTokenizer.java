/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.config;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.ajax.json.JSONTokener;

public class ConfigTokenizer extends JSONTokener {

	public ConfigTokenizer(final String s) {
		super(s);
	}

	@Override
	public Object nextValue() throws JSONException {
		char c = nextClean();
		String string;
		switch (c) {
		case '"':
		case '\'':
			return nextString(c);
		case '{':
			back();
			return ConfigObject.parse(this);
		case '[':
			back();
			return ConfigArray.parse(this);
		}
		/*
		 * Handle unquoted text. This could be the values true, false, or
		 * null, or it can be a number. An implementation (such as this one)
		 * is allowed to also accept non-standard forms.
		 *
		 * Accumulate characters until we reach the end of the text or a
		 * formatting character.
		 */
		final StringBuffer sb = new StringBuffer();
		while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
			sb.append(c);
			c = this.next();
		}
		back();
		string = sb.toString().trim();
		if ("".equals(string)) {
			throw syntaxError("Missing value");
		}
		return JSONObject.stringToValue(string);
	}
}
