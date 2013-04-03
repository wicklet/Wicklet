/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.test.junit;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sf.wicklet.gwt.client.util.GwtXmlUtil;

public class TestGwtXmlUtil01 {

	static final boolean DEBUG = true;

	@Test
	public void test01() {
		final String input = "ab<>\tc&amp;\"'ef\n";
		final String output = GwtXmlUtil.escXml(input);
		if (DEBUG) {
			System.out.println(output);
		}
		assertEquals("ab&lt;&gt;\tc&amp;amp;&quot;&#39;ef\n", output);
		final String s = GwtXmlUtil.unescXml(output);
		if (DEBUG) {
			System.out.println(s);
		}
		assertEquals(input, s);
	}

	@Test
	public void test02() {
		final String input = "ab<>\tc&amp;\"'ef\nab\rc\0x01\0x02;";
		final String output = GwtXmlUtil.escXmlString(input);
		if (DEBUG) {
			System.out.println(output);
		}
		assertEquals("ab&lt;&gt;\tc&amp;amp;&quot;&#39;ef&#10;ab&#13;c&#0;x01&#0;x02;", output);
		final String s = GwtXmlUtil.unescXmlString(output);
		if (DEBUG) {
			System.out.println(s);
		}
		assertEquals(input, s);
	}
}
