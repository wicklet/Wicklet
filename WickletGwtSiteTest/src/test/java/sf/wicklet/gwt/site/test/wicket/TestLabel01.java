/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.test.wicket;

import static org.junit.Assert.assertEquals;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.test.support.WicketTestBase;

public class TestLabel01 extends WicketTestBase {

	static final boolean DEBUG = true;

	@Before
	@Override
	public void setup() {
		super.setup();
	}

	@After
	@Override
	public void teardown() {
		super.teardown();
	}

	public static class Test01Page extends WebPage {
		private static final long serialVersionUID = 1L;
		static final String CONTENT = "<h1>Title</h1><ul><li>Item1</li></ul>";
		private static final IRootMarkup MARKUP = new WicketBuilder() {
			IRootMarkup build() {
				return serialize(component("label").div());
			}
		}.build();
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
		public Test01Page() {
			add(new Label("label", CONTENT));
		}
	}

	public static class Test02Page extends WebPage {
		private static final long serialVersionUID = 1L;
		static final String CONTENT = "<h1>Title</h1><ul><li>Item1</li></ul>";
		private static final IRootMarkup MARKUP = new WicketBuilder() {
			IRootMarkup build() {
				return serialize(component("label").div());
			}
		}.build();
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
		public Test02Page() {
			add(new Label("label", CONTENT).setEscapeModelStrings(false));
		}
	}

	/* Check that Label component escape html content. */
	@Test
	public void test01() {
		tester.startPage(Test01Page.class);
		final String ret = tester.getLastResponseAsString();
		if (DEBUG) {
			System.out.println(ret);
		}
		assertEquals(
			"<div wicket:id=\"label\">&lt;h1&gt;Title&lt;/h1&gt;&lt;ul&gt;&lt;li&gt;Item1&lt;/li&gt;&lt;/ul&gt;</div>",
			ret);
	}

	/* Check that Label component do not escape html content when setEscapeModelStrings(false). */
	@Test
	public void test02() {
		tester.startPage(Test02Page.class);
		final String ret = tester.getLastResponseAsString();
		if (DEBUG) {
			System.out.println(ret);
		}
		assertEquals("<div wicket:id=\"label\"><h1>Title</h1><ul><li>Item1</li></ul></div>", ret);
	}
}
