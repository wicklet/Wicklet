/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sf.wicklet.wicketext.markup.api.IXmlUtil;
import sf.wicklet.wicketext.util.XmlUtil;

public class TestXmlUtil01 {

	static final boolean DEBUG = true;

	private IXmlUtil xmlUtil;

	@Before
	public void setup() {
		xmlUtil = new XmlUtil();
	}
	@Test
	public void test() {
		check("", "");
		check("a=&quot;b&quot;", "a=\"b\"");
		check("&lt;abc&gt;+10%=&quot;123&quot; &amp;&amp; 789;", "<abc>+10%=\"123\" && 789;");
	}

	private void check(final String expected, final String data) {
		final String ret = xmlUtil.escAttr(data).toString();
		if (TestXmlUtil01.DEBUG) {
			System.out.println("### result:");
			System.out.println(ret);
		}
		Assert.assertEquals(expected, ret);
	}
}
