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
import sf.wicklet.dsl.html.impl.XHtmlBuilder;

public class TestTable01 {

	static final boolean DEBUG = true;

	@Test
	public void test01() throws IOException {
		String expected = "TestTable01Test01.html";
		IElement e = new XHtmlBuilder() {
			public IElement create() {
				return top(
					table(
						atts("summary", "This is a test"),
						elm("caption", txt("caption text")),
						colgroup(),
						colgroup(atts("span", "3")),
						colgroup(atts("span", "3")),
						thead(
							tr(
								th(atts("rowspan", "2"), txt("Head1")),
								th(atts("rowspan", "2"), txt("Head2")),
								th(
									atts("rowspan", "2"),
									atts("colspan", "3"),
									txt("Head3"))),
							tr(th(), th(), th())),
						tbody( //
							tr(td(), td(txt("col2")), td()), //
							tr(td(), td(), td())),
						tfoot(tr(td(), td(), td()))));
			}
		}.create();
		DslTestUtil.verify(DEBUG, "    ", expected, e);
	}
}
