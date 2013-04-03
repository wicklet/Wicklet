/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.test;

import static org.junit.Assert.assertNull;
import org.apache.wicket.markup.IRootMarkup;
import org.junit.Test;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.dsl.html.impl.WicketSerializer;

public class TestWicketSerializer01 {

	static final boolean DEBUG = true;

	@Test
	public void test() {
		final IRootMarkup markup = WicketSerializer.serialize(
			new WicketBuilder() {
				public IElement build() {
					return wicketPanel(div());
				}
			}.build());
		if (DEBUG) {
			System.out.println(markup);
		}
		assertNull(
			TextUtil.differIgnoringWhitespaces(
				"<wicket:panel><div></div></wicket:panel>", markup.toString(), null, false));
	}
}
