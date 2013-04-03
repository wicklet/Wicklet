/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.wicket.core.test;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IMarkupElement;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.MarkupParser;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import sf.blacksun.util.FileUtil;

public class MarkupParser01 {

	static final boolean DEBUG = true;

	@Test
	public void test01() throws IOException, ResourceStreamNotFoundException {
		final String DATA = "/sf/wicklet/wicketext/wicket/core/test/MarkupParser01Test01.html";
		final String content = FileUtil.getResourceAsString(DATA);
		new WicketTester();
		final MarkupParser parser = new MarkupParser(content);
		final IMarkupFragment ret = parser.parse();
		int components = 0;
		if (DEBUG) {
			System.out.println("### Markup: " + ret.size());
		}
		for (final IMarkupElement e: ret) {
			if (DEBUG) {
				System.out.println(e.getClass().getName() + ": " + e.toUserDebugString());
			}
			if (e instanceof ComponentTag) {
				++components;
		}}
		assertEquals(7, components);
	}
}
