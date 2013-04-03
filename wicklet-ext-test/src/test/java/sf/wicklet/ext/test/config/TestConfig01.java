/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.test.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.wicket.ajax.json.JSONException;
import org.junit.Test;
import sf.blacksun.util.FileUtil;
import sf.wicklet.ext.application.IWickletConfiguration;
import sf.wicklet.ext.application.WickletConfiguration;
import sf.wicklet.ext.config.IConfigArray;
import sf.wicklet.ext.config.IConfigObject;

public class TestConfig01 {

	static final boolean DEBUG = true;
	private static final String TestConfig01Json = "sf/wicklet/ext/test/config/TestConfig01.json";

	@Test
	public void test01() throws JSONException, IOException {
		final IWickletConfiguration c = WickletConfiguration.parse(
			FileUtil.asString(
				new InputStreamReader(
					getClass().getClassLoader().getResourceAsStream(TestConfig01Json))));
		assertEquals("teststring", c.root().getObject("LeftPanel").getString("testString"));
		assertEquals("teststring", c.getString("LeftPanel", "testString"));
		final IConfigObject leftpanel = c.getObject("LeftPanel");
		assertEquals(true, leftpanel.getBool("visible"));
		assertEquals(false, leftpanel.getBool("xxx", false));
		assertEquals(123, leftpanel.getInt("counter"));
		assertEquals(234, leftpanel.getInt("xxx", 234));
		assertEquals(123, leftpanel.getLong("counter"));
		assertEquals(234, leftpanel.getLong("xxx", 234));
		assertEquals(0.5123, leftpanel.getDouble("opacity"), 0.001);
		assertEquals(0.2, leftpanel.getDouble("xxx", 0.2), 0.001);
		final IConfigArray wiki = c.getArray("LeftPanel", "Indexes", "Wiki");
		assertEquals("Documentation", wiki.getObject(1).getString("title"));
		assertEquals(null, leftpanel.getObject("xxx", null));
		assertEquals(null, leftpanel.getArray("xxx", null));
		assertEquals(null, leftpanel.getString("xxx", null));
		assertEquals(true, leftpanel.getBool("xxx", true));
		assertEquals(111, leftpanel.getInt("xxx", 111));
		assertEquals(222l, leftpanel.getLong("xxx", 222));
		assertEquals(0.333, leftpanel.getDouble("xxx", 0.333), 0.001);
		checkThrows(leftpanel, "Object", "xxx");
		checkThrows(leftpanel, "Array", "xxx");
		checkThrows(leftpanel, "String", "xxx");
		checkThrows(leftpanel, "Bool", "xxx");
		checkThrows(leftpanel, "Int", "xxx");
		checkThrows(leftpanel, "Long", "xxx");
		checkThrows(leftpanel, "Double", "xxx");
	}

	private void checkThrows(final IConfigObject a, final String type, final String key) {
		try {
			switch (type) {
			case "Object":
				a.getObject(key);
				break;
			case "Array":
				a.getArray(key);
				break;
			case "String":
				a.getString(key);
				break;
			case "Bool":
				a.getBool(key);
				break;
			case "Int":
				a.getInt(key);
				break;
			case "Long":
				a.getLong(key);
				break;
			case "Double":
				a.getDouble(key);
				break;
		}} catch (final JSONException e) {
			return;
		}
		fail("Expected expcetion do not occurs: type=" + type + ", key=" + key);
	}
}
