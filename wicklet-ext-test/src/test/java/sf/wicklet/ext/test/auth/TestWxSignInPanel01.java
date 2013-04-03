/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.test.auth;

import static org.junit.Assert.assertEquals;
import org.apache.wicket.Session;
import org.apache.wicket.ThreadContext;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.dsl.html.impl.WicketSerializer;
import sf.wicklet.ext.auth.WxSignInPanel;
import sf.wicklet.test.support.TestSession;

public class TestWxSignInPanel01 {

	static final boolean DEBUG = true;
	private WicketTester tester;

	@Before
	public void setup() {
		// make sure no leaked threadlocals are present
		ThreadContext.detach();
		final WebApplication application = new MockApplication() {
			@Override
			public Session newSession(final Request request, final Response response) {
				return new TestSession(request);
			}
		};
		tester = new WicketTester(application);
	}

	@After
	public void teardown() {
		tester.destroy();
	}

	public static class Test01Page extends WebPage {
		private static final long serialVersionUID = 1L;
		private static final IRootMarkup MARKUP = WicketSerializer.serialize(
			new WicketBuilder() {
				IElement build() {
					return html(head(), body(component(WID.signinPanel, div())));
				}
			}.build());
		enum WID {
			signinPanel 
		}
		public Test01Page() {
			add(new WxSignInPanel(WID.signinPanel.name()));
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
	}

	public static class Test02Page extends WebPage {
		private static final long serialVersionUID = 1L;
		private static final IRootMarkup MARKUP = WicketSerializer.serialize(
			new WicketBuilder() {
				IElement build() {
					return html(head(), body(component(id(WID.signinPanel), div())));
				}
			}.build());
		private enum WID {
			signinPanel 
		}
		public Test02Page() {
			add(new WxSignInPanel(WID.signinPanel.name()).useRememberMe(true).useCaptcha(false));
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
	}

	@Test
	public void testVisibility01() {
		tester.startPage(Test01Page.class);
		if (DEBUG) {
			System.out.println(tester.getLastResponseAsString());
		}
		tester.assertVisible("signinPanel:feedback");
		tester.assertInvisible("signinPanel:signinForm:rememberTr:rememberMe");
		tester.assertVisible("signinPanel:signinForm:captchaTr:captcha");
	}

	@Test
	public void testVisibility02() {
		tester.startPage(Test02Page.class);
		if (DEBUG) {
			System.out.println(tester.getLastResponseAsString());
		}
		tester.assertVisible("signinPanel:feedback");
		tester.assertVisible("signinPanel:signinForm:rememberTr:rememberMe");
		tester.assertInvisible("signinPanel:signinForm:captchaTr:captcha");
	}

	@Test
	public void testLogin01() {
		tester.startPage(Test01Page.class);
		if (DEBUG) {
			System.out.println(tester.getLastResponseAsString());
		}
		final FormTester form = tester.newFormTester("signinPanel:signinForm");
		form.setValue("username", "writer");
		form.setValue("password", "writer");
		form.submit();
		if (DEBUG) {
			System.out.println(tester.getLastResponseAsString());
		}
		final TagTester username = tester.getTagByWicketId("username");
		final TagTester password = tester.getTagByWicketId("password");
		assertEquals("writer", username.getAttribute("value"));
		assertEquals("", password.getAttribute("value"));
	}
}
