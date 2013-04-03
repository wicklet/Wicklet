/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.test;

import java.io.IOException;

import org.junit.Test;

import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.impl.WicketBuilder;

public class TestForm01 {

	static final boolean DEBUG = true;

	@Test
	public void test01() throws IOException {
		String expected = "TestForm01Test01.html";
		IElement e = new WicketBuilder() {
			public IElement create() {
				return top(
					xml(),
					form(atts("action", "/form1").a("method", "POST")).c(
						input(atts().type("text").value("input1")),
						button(atts().name("submit").value("confirm")),
						optgroup(
							option(atts().label("option1")),
							option(
								atts().label("option2").a("selected", "true"),
								txt("default")),
							option(atts().label("option3"))),
						select(atts().name("select1")).c(
							option(atts().label("label1"), txt("option1")),
							option(txt("option2")),
							option(atts().label("label3").a("selected", "true"))),
						textarea(atts().name("textarea1").a("cols", "40"))));
			}
		}.create();
		DslTestUtil.verify(DEBUG, "    ", expected, e);
	}
}
