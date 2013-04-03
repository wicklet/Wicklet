/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.pages.p;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.https.RequireHttps;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sf.wicklet.dsl.html.api.INode;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.ext.ui.panels.SimpleFeedbackPanel;
import sf.wicklet.gwt.server.ajax.GwtAuthenticatedWebApplication;
import sf.wicklet.gwt.site.server.pages.Home;
import sf.wicklet.gwt.site.server.support.WickletGwtSiteTemplate;
import sf.wicklet.gwt.site.shared.ICS;
import sf.wicklet.gwt.site.shared.WID;

@RequireHttps
public class Logout extends WebPage {
	private static final long serialVersionUID = 1L;
	public static final String LABEL = "Logout";
	public static final String PATH = "/p/logout";
	public static final String MODULE = "../WickletGwtSiteLogin";

	private static IRootMarkup MARKUP = new WickletGwtSiteTemplate(MODULE, PATH) {
		@Override
		protected INode content() {
			final IWickletSupport support = GwtAuthenticatedWebApplication.get().getWickletSupport();
			return div(
				id(WID.bodyContent),
				div(
					id(WID.twoPane),
					css(ICS.twoPane),
					div(
						id(WID.topPanel), //
						a(
							id(WID.topHome),
							href(support.getContextPath(Home.PATH)),
							txt("Home")),
						span(id(WID.topUser), txt("")),
						a(
							id(WID.topLogin),
							href(support.getHttpsUrl(Login.PATH)),
							txt("Login")),
						a(
							id(WID.topLogout),
							href(support.getHttpsUrl(Logout.PATH)),
							txt("Logout"))),
					div(
						id(WID.bottomPanel),
						component(WID.logoutPanel).div(
							attr("align", "center"), istyle("padding: 10px;")))));
		}
		@Override
		protected INode headstart() {
			return top(super.headstart(), title(LABEL));
		}
	}.build();

	public Logout(final PageParameters params) {
		super(params);
		setStatelessHint(true);
		final AuthenticatedWebSession as = AuthenticatedWebSession.get();
		final boolean signedin = as.isSignedIn();
		add(
			new SimpleFeedbackPanel(
				WID.logoutPanel,
				this,
				(signedin ? "You have successfully logged out" : "You are not logged in"),
				FeedbackMessage.SUCCESS));
		as.invalidate();
	}

	@Override
	public IRootMarkup getAssociatedMarkup() {
		return MARKUP;
	}
}
