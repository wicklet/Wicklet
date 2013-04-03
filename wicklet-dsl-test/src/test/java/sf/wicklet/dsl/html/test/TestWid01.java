/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.test;

import static org.junit.Assert.assertEquals;
import org.apache.wicket.markup.IRootMarkup;
import org.junit.Test;
import sf.wicklet.dsl.css.api.IIDProvider;
import sf.wicklet.dsl.html.api.Attribute;
import sf.wicklet.dsl.html.api.IAttribute;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.dsl.html.impl.WicketSerializer;

public class TestWid01 {

	static final boolean DEBUG = true;

	private static class A extends Attribute implements IIDProvider {
		private static final long serialVersionUID = 1L;
		public static final A c1 = new A("c1");
		public A(final String value) {
			super("id", value);
		}
		@Override
		public IAttribute att() {
			return new Attribute("id", "x1");
		}
		@Override
		public String ref() {
			return null;
		}
	}

	private enum WID {
		c2, c3; 
	}

	@Test
	public void test() {
		final IRootMarkup markup = WicketSerializer.serialize(
			new WicketBuilder() {
				public IElement build() {
					return body(
						component(A.c1).div(),
						component(id(WID.c2)).div(),
						component(WID.c3).div());
				}
			}.build());
		final String ret = markup.toString();
		if (DEBUG) {
			System.out.println(ret);
		}
		assertEquals(
			"<body>"
				+ "<div wicket:id=\"x1\" id=\"x1\"></div>"
				+ "<div wicket:id=\"c2\" id=\"c2\"></div>"
				+ "<div wicket:id=\"c3\"></div>"
				+ "</body>\n",
			ret);
	}
}
