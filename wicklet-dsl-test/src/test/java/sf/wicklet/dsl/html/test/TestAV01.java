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
import sf.wicklet.dsl.html.api.AV;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.dsl.html.impl.XHtmlSerializer;

public class TestAV01 {

	static final boolean DEBUG = true;

	@Test
	public void test() {
		final String ret = XHtmlSerializer.serialize(
			new WicketBuilder() {
				IElement build() {
					return div(AV.Align.Right);
				}
			}.build());
		if (DEBUG) {
			System.out.println(ret);
		}
		assertEquals("<div align=\"right\" />\n", ret);
	}
}
