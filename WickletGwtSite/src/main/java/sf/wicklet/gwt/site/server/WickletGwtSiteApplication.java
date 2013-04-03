/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server;

import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.https.HttpsConfig;
import org.apache.wicket.protocol.https.HttpsMapper;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.info.DefaultPageComponentInfoCodec;
import org.apache.wicket.util.crypt.CachingSunJceCryptFactory;
import sf.blacksun.util.RandomUtil;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.ext.auth.WxSecureAuthenticationStrategy;
import sf.wicklet.ext.ui.mappers.MultiplePageMapper;
import sf.wicklet.gwt.server.ajax.GwtAuthenticatedWebApplication;
import sf.wicklet.gwt.site.server.pages.Home;
import sf.wicklet.gwt.site.server.pages.p.Admin;
import sf.wicklet.gwt.site.server.pages.p.Login;
import sf.wicklet.gwt.site.server.pages.p.Logout;
import sf.wicklet.gwt.site.server.pages.p.User;
import sf.wicklet.gwt.site.server.pages.s.AdminService;
import sf.wicklet.gwt.site.server.pages.s.Service;

public class WickletGwtSiteApplication extends GwtAuthenticatedWebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return Home.class;
	}

	@Override
	protected void init() {
		super.init();
		getSecuritySettings().setAuthenticationStrategy(
			new WxSecureAuthenticationStrategy(IConfig.LOGGED_IN_COOKIE_NAME, true));
		getSecuritySettings().setCryptFactory(
			new CachingSunJceCryptFactory(Config.get().getDefaultEncryptionKey()));
		getPageSettings().setPageComponentInfoCodec(
			new DefaultPageComponentInfoCodec(RandomUtil.getSingleton().getString(8, 16)));
		mount(new MyMultiMapper("s", AdminService.class, Service.class));
		mount(new MyMultiMapper("p", Login.class, Logout.class, Admin.class, User.class));
		final IWickletSupport support = getWickletSupport();
		final HttpsConfig config = new HttpsConfig(support.getHttpPort(), support.getHttpsPort());
		config.setPreferStateful(false);
		setRootRequestMapper(new HttpsMapper(getRootRequestMapper(), config));
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return MyAuthenticatedWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return Login.class;
	}

	/** Translate Login to login and back. */
	private static class MyMultiMapper extends MultiplePageMapper {
		@SafeVarargs
		public MyMultiMapper(final String mountpath, final Class<? extends IRequestablePage>...pages) {
			super(mountpath, pages);
		}
		@Override
		protected String toAlias(final Class<? extends IRequestablePage> page) {
			return page.getSimpleName().toLowerCase();
		}
	}
}
