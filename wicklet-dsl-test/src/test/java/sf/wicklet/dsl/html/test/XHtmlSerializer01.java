/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.test;

import org.junit.Test;

import sf.blacksun.util.text.DOCTYPE;
import sf.wicklet.dsl.html.api.ITop;
import sf.wicklet.dsl.html.impl.XHtmlBuilder;
import sf.wicklet.dsl.html.impl.XHtmlSerializer;

public class XHtmlSerializer01 {

	static final boolean DEBUG = true;

	static abstract class DomBuilder extends XHtmlBuilder {
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
						lb()),
					link(
						atts("rel", "stylesheet").lb().type("text/css").lb().href(
							"css/index.css")),
					body(p(txt("Content")))));
		}
		abstract String mytitle();
	}

	@Test
	public void test() {
		String ret = XHtmlSerializer.serialize(
			"    ",
			new DomBuilder() {
				@Override
				String mytitle() {
					return "Testing";
				}
			}.build());
		DslTestUtil.verify(DEBUG, getClass().getSimpleName() + "Test01.html", ret);
	}
}
