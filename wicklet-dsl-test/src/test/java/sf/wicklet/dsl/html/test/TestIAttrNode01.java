/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sf.wicklet.dsl.html.api.A;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.impl.XHtmlBuilder;
import sf.wicklet.dsl.html.impl.XHtmlSerializer;

public class TestIAttrNode01 {

	static final boolean DEBUG = true;

	/* Check that input() accept both IAttribute and IAttributes. */
	@Test
	public void test() {
		final String ret = XHtmlSerializer.serialize(
			new XHtmlBuilder() {
				IElement build() {
					return input(atts(), type("textarea"), A.Cols.s(40), A.Rows.s(20));
				}
			}.build());
		if (DEBUG) {
			System.out.println(ret);
		}
		assertEquals("<input type=\"textarea\" cols=\"40\" rows=\"20\" />\n", ret);
	}
}
