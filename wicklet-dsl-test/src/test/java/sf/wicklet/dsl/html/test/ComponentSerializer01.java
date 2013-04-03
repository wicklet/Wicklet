/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.test;

import static org.junit.Assert.assertEquals;
import org.apache.wicket.markup.IMarkupElement;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.RawMarkup;
import org.junit.Test;
import sf.blacksun.util.text.DOCTYPE;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.api.ITop;
import sf.wicklet.dsl.html.impl.ComponentSerializer;
import sf.wicklet.dsl.html.impl.WID;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.dsl.html.impl.XHtmlSerializer;

public class ComponentSerializer01 {

	static final boolean DEBUG = true;

	static abstract class DomBuilder extends WicketBuilder {
		public ITop build() {
			return top(
				xml(),
				declaration("DOCTYPE", DOCTYPE.XHTML10.content()),
				html(
					head(
						meta(
							atts("http-equiv", "Content-Type").a(
								"content", "text/html; charset=UTF-8")),
						title(mytitle()),
						stylesheet("css/index.css")),
					body(
						component(WID.of("content")).span(
							ul(
								li(txt("line 1")),
								li(wicket("wicket:panel", wicket("wicket:child"))),
								li(txt("line 3")))))));
		}

		abstract String mytitle();
	}

	@Test
	public void test() {
		final IElement dom = new DomBuilder() {
			@Override
			String mytitle() {
				return "Testing";
			}
		}.build();
		final String xhtml = XHtmlSerializer.serialize(dom);
		if (DEBUG) {
			System.out.println(xhtml);
		}
		final IRootMarkup ret = ComponentSerializer.serialize("    ", true, dom);
		final StringBuilder b = new StringBuilder();
		int i = 0;
		for (final IMarkupElement e: ret) {
			final String s = e.toString();
			b.append(s);
			if (DEBUG) {
				System.out.printf(
					"### %d:%s%n%s%n", i++, (e instanceof RawMarkup ? "raw" : "markup"), s);
		}}
		// It should be 8 with empty components, but 10 for without empty component
		//		assertEquals(8, ret.size());
		assertEquals(10, ret.size());
	}
}
