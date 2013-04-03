/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.test.arquillian.testauth01;

import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.protocol.https.HttpsConfig;
import org.apache.wicket.protocol.https.HttpsMapper;
import org.apache.wicket.protocol.https.RequireHttps;
import org.apache.wicket.util.crypt.CachingSunJceCryptFactory;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.dsl.html.impl.WicketSerializer;
import sf.wicklet.ext.auth.WxSecureAuthenticationStrategy;
import sf.wicklet.ext.auth.WxSignInPanel;
import sf.wicklet.ext.ui.pages.SimpleWickletPage;

public class TestApplication extends AuthenticatedWebApplication {

	public static final String LOGGED_IN_COOKIE_NAME = "LoggedIn";
	public static final String DEFAUT_ENCRYPTION_KEY = "AbcdEfg";
	public static final int HTTP_PORT = 8080;
	public static final int HTTPS_PORT = 8443;

	@Override
	public Class<? extends Page> getHomePage() {
		return TestApplication.HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		getSecuritySettings().setAuthenticationStrategy(
			new WxSecureAuthenticationStrategy(LOGGED_IN_COOKIE_NAME, true));
		getSecuritySettings().setCryptFactory(new CachingSunJceCryptFactory(DEFAUT_ENCRYPTION_KEY));
		mountPage(TestSigninPage.MNT_PATH, TestApplication.TestSigninPage.class);
		mountPage(TestRememberPage.MNT_PATH, TestApplication.TestRememberPage.class);
		mountPage(TestLogoutPage.MNT_PATH, TestApplication.TestLogoutPage.class);
		mountPage(TestSignoutPage.MNT_PATH, TestApplication.TestSignoutPage.class);
		mountPage(TestSecurePage.MNT_PATH, TestApplication.TestSecurePage.class);
		final HttpsConfig config = new HttpsConfig(HTTP_PORT, HTTPS_PORT);
		config.setPreferStateful(false);
		setRootRequestMapper(new HttpsMapper(getRootRequestMapper(), config));
	}

	public static class HomePage extends SimpleWickletPage {
		private static final long serialVersionUID = 1L;
		public static final String CONTENT = "Test01 home page";
		public HomePage() {
			add(new Label(WID.content.name(), CONTENT));
		}
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return TestSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return TestApplication.TestSigninPage.class;
	}

	@RequireHttps
	public static class TestSigninPage extends WebPage {
		public static final String MNT_PATH = "sigin01";
		public static final String CAPTCHA_TEXT = "dfajsdkr";
		private static final long serialVersionUID = 1L;
		private static final IRootMarkup MARKUP = WicketSerializer.serialize(
			new WicketBuilder() {
				IElement build() {
					return html(head(), body(component(WID.signinPanel, div())));
				}
			}.build());
		private enum WID {
			signinPanel 
		}
		public TestSigninPage() {
			getSession().replaceSession();
			add(
				new WxSignInPanel(WID.signinPanel.name()) {
					private static final long serialVersionUID = 1L;
					@Override
					protected String challegetext() {
						return CAPTCHA_TEXT;
					}
				});
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
	}

	@RequireHttps
	public static class TestRememberPage extends WebPage {
		public static final String MNT_PATH = "remember01";
		public static final String CAPTCHA_TEXT = "123456";
		private static final long serialVersionUID = 1L;
		private static final IRootMarkup MARKUP = WicketSerializer.serialize(
			new WicketBuilder() {
				IElement build() {
					return html(head(), body(component(WID.signinPanel, div())));
				}
			}.build());
		private enum WID {
			signinPanel 
		}
		public TestRememberPage() {
			getSession().replaceSession();
			add(
				new WxSignInPanel(WID.signinPanel.name()) {
					private static final long serialVersionUID = 1L;
					@Override
					protected String challegetext() {
						return CAPTCHA_TEXT;
					}
				}.useRememberMe(true));
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
	}

	@RequireHttps
	public static class TestLogoutPage extends WebPage {
		public static final String MNT_PATH = "logout01";
		private static final long serialVersionUID = 1L;
		private static final IRootMarkup MARKUP = WicketSerializer.serialize(
			new WicketBuilder() {
				IElement build() {
					// FIXME
					return html(head(), body(div()));
				}
			}.build());
		public TestLogoutPage() {
			AuthenticatedWebSession.get().invalidate();
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
	}

	@RequireHttps
	public static class TestSignoutPage extends WebPage {
		public static final String MNT_PATH = "signout01";
		private static final long serialVersionUID = 1L;
		private static final IRootMarkup MARKUP = WicketSerializer.serialize(
			new WicketBuilder() {
				IElement build() {
					// FIXME
					return html(head(), body(div()));
				}
			}.build());
		public TestSignoutPage() {
			AuthenticatedWebSession.get().signOut();
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
	}

	@RequireHttps
	@AuthorizeInstantiation("writer")
	public static class TestSecurePage extends WebPage {
		public static final String MNT_PATH = "secure01";
		public static final String TITLE = "Secure Page";
		private static final long serialVersionUID = 1L;
		private static final IRootMarkup MARKUP = WicketSerializer.serialize(
			new WicketBuilder() {
				IElement build() {
					return html(head(title(TITLE)), body(div()));
				}
			}.build());
		public TestSecurePage() {
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
	}
}
