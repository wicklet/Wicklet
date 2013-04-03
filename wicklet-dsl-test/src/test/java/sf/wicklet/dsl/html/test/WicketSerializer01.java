/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.apache.wicket.markup.IMarkupElement;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.RawMarkup;
import org.junit.Test;
import sf.blacksun.util.text.DOCTYPE;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.api.ITop;
import sf.wicklet.dsl.html.impl.WID;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.dsl.html.impl.WicketSerializer;
import sf.wicklet.dsl.html.impl.XHtmlSerializer;

public class WicketSerializer01 {

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
		if (DEBUG) {
			System.out.println(XHtmlSerializer.serialize(dom));
		}
		final IRootMarkup ret = WicketSerializer.serialize("    ", true, dom);
		final StringBuilder b = new StringBuilder();
		int i = 0;
		for (final IMarkupElement e: ret) {
			final String s = e.toString();
			b.append(s);
			if (DEBUG) {
				System.out.printf(
					"### %d:%s%n%s%n", i++, (e instanceof RawMarkup ? "raw" : "markup"), s);
		}}
		// It should be 14 with empty component tags, but 16 for without empty component tags.
		//		assertEquals(14, ret.size());
		assertEquals(16, ret.size());
		DslTestUtil.verify(DEBUG, getClass().getSimpleName() + "Test01.html", b.toString());
	}

	@Test
	public void testWithoutComponent() {
		final IRootMarkup markup = WicketSerializer.serialize(
			new WicketBuilder() {
				public IElement build() {
					return html(
						head(),
						body(
							div(
								id("accordion"),
								div(a(href("#"), txt("Panel1"))),
								div(txt("Content1")),
								div(a(href("#"), txt("Panel2"))),
								div(txt("Content2")),
								div(a(href("#"), txt("Panel3"))),
								div(txt("Content3")))));
				}
			}.build());
		final String ret = markup.toString();
		if (DEBUG) {
			System.out.println(ret);
		}
		assertTrue(ret.indexOf("<div>Content3</div>") > 0);
	}
}
