/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.test;

import java.util.Locale;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ThreadContext;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import sf.wicklet.wicketext.util.WicketExtrasUtil;

public class TestMarkupParser02 {

	static final boolean DEBUG = true;
	private WicketTester tester;

	////////////////////////////////////////////////////////////////////////

	@Test
	public void test01() {
		ThreadContext.detach();
		tester = new WicketTester(new Application01());
		tester.getSession().setLocale(Locale.ENGLISH);
		tester.startPage(HomePage01.class);
		tester.dumpPage();
	}

	////////////////////////////////////////////////////////////////////////

	public static class Application01 extends WebApplication {
		@Override
		public java.lang.Class<? extends org.apache.wicket.Page> getHomePage() {
			return HomePage01.class;
		}
	}

	public static class HomePage01 extends WebPage {
		public HomePage01() {
			add(new Panel01("panel01"));
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			final IRootMarkup ret = super.getAssociatedMarkup();
			WicketExtrasUtil.dump(System.out, DEBUG, ret);
			return ret;
		}
	}

	public static class Panel01 extends Panel {
		public Panel01(final String id) {
			super(id);
			final RepeatingView r = new RepeatingView("testPageLinks");
			add(r);
			final MarkupContainer m = new WebMarkupContainer(r.newChildId());
			final BookmarkablePageLink<HomePage01> link
				= new BookmarkablePageLink<TestMarkupParser02.HomePage01>("link", HomePage01.class);
			m.add(link);
			link.add(new Label("label", Model.of("Link")));
			r.add(m);
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			final IRootMarkup ret = super.getAssociatedMarkup();
			WicketExtrasUtil.dump(System.out, DEBUG, ret);
			return ret;
		}
	}

	////////////////////////////////////////////////////////////////////////
}
