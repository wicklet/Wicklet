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
import org.junit.Test;
import sf.blacksun.util.text.DOCTYPE;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.api.INode;
import sf.wicklet.dsl.html.api.ITop;
import sf.wicklet.dsl.html.impl.WID;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.dsl.html.impl.WicketSerializer;

public class TestXhtmlSerializer02 {

	static final boolean DEBUG = true;

	////////////////////////////////////////////////////////////////////////

	protected abstract class TemplateBuilder extends WicketBuilder {
		public ITop build() {
			return top(xml(), doctype(DOCTYPE.XHTML10), html(head(), body()));
		}
		private INode head() {
			return head(
				contenttype("text/html; charset=UTF-8"),
				title(title()),
				stylesheet(prefix() + "css/default.css"));
		}
		private INode body() {
			return body(
				table(
					atts("class", "top").a("align", "center").a("width", "95%").a(
						"cellspacing", "0").a("summary", ""),
					ruler(),
					tr(td(atts("class", "title").a("colspan", cols()), txt(title()))),
					navbar("navbar-top"),
					topmenu(),
					tr(
						td(
							component(WID.of("content")).span(
								attr("class", "content"),
								component(WID.of("panel")).div()))),
					ruler(),
					navbar("navbar-bottom")));
		}
		private INode ruler() {
			return tr(td(atts("class", "ruler", "colspan", colstr())));
		}
		private INode navbar(final String styleclass) {
			return tr(
				td(
					atts("class", styleclass, "colspan", colstr()),
					b(txt("| "), a(attr("href", navup()), txt("Up")), txt(" |"))));
		}
		private INode topmenu() {
			return tr(
				td(
					component(WID.of("top-menu")).span(
						atts("class", "top-menu").a("colspan", colstr()), b(txt("")))));
		}
		int cols() {
			return 2;
		}
		String colstr() {
			return "2";
		}
		abstract String title();
		abstract String prefix();
		abstract String navup();
	}

	////////////////////////////////////////////////////////////////////////

	@Test
	public void test01() {
		final IRootMarkup ret = WicketSerializer.serialize(
			new TemplateBuilder() {
				@Override
				String title() {
					return "WiQuery Examples";
				}
				@Override
				String prefix() {
					return "";
				}
				@Override
				String navup() {
					return "";
				}
			}.build());
		final StringBuilder b = new StringBuilder();
		for (final IMarkupElement e: ret) {
			b.append(e.toString());
		}
		if (DEBUG) {
			System.out.println("### Result");
			System.out.println(b);
	}}

	////////////////////////////////////////////////////////////////////////

	@Test
	public void test02() {
		final IRootMarkup ret = WicketSerializer.serialize(
			new WicketBuilder() {
				IElement build() {
					return wicketPanel(
						div(
							txt("This is a test"),
							br(),
							input(type("text"), name("textinput"))));
				}
			}.build());
		final StringBuilder b = new StringBuilder();
		for (final IMarkupElement e: ret) {
			b.append(e.toString());
		}
		if (DEBUG) {
			System.out.println("### Result");
			System.out.println(b);
		}
		final String expected = "<wicket:panel><div>This is a test<br>"
			+ "<input type=\"text\" name=\"textinput\">"
			+ "</div>"
			+ "</wicket:panel>";
		assertEquals(null, TextUtil.differIgnoringWhitespaces(expected, b, null, false));
	}

	////////////////////////////////////////////////////////////////////////
}
