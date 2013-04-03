/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.pages.p;

import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.https.RequireHttps;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sf.wicklet.dsl.html.api.INode;
import sf.wicklet.ext.application.IWickletApplication;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.ext.auth.WxSignInPanel;
import sf.wicklet.gwt.server.ajax.GwtAuthenticatedWebApplication;
import sf.wicklet.gwt.site.server.Config;
import sf.wicklet.gwt.site.server.MyAuthenticatedWebSession;
import sf.wicklet.gwt.site.server.pages.Home;
import sf.wicklet.gwt.site.server.support.WickletGwtSiteTemplate;
import sf.wicklet.gwt.site.shared.ICS;
import sf.wicklet.gwt.site.shared.WID;

@RequireHttps
public class Login extends WebPage {
    public static final String LABEL = "Login";
    public static final String PATH = "/p/login";
    public static final String CAPTCHA = "gsye7O";
    public static final String MODULE = "../WickletGwtSiteLogin";
    private static final long serialVersionUID = 1L;
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
                        a(id(WID.topHome), href(support.getContextPath(Home.PATH)), txt("Home")),
                        span(id(WID.topUser), txt("")),
                        a(id(WID.topLogin), href(support.getHttpsUrl(Login.PATH)), txt("Login")),
                        a(id(WID.topLogout), href(support.getHttpsUrl(Logout.PATH)), txt("Logout"))),
                    div(
                        id(WID.bottomPanel),
                        component(id(WID.signinPanel)).div(attr("align", "center"), istyle("margin: 10px;")))));
        }
        @Override
        protected INode headstart() {
            return top(super.headstart(), title(LABEL));
        }
    }.build();

    public Login(final PageParameters params) {
        super(params);
        setStatelessHint(false);
        getSession().replaceSession();
        add(
            new WxSignInPanel(WID.signinPanel.toString()) {
                private static final long serialVersionUID = 1L;
                @Override
                protected void onSignInSucceeded() {
                    continueToOriginalDestination();
                    // Redirect to home page on secure connection after successful signin.
                    final MyAuthenticatedWebSession session = MyAuthenticatedWebSession.get();
                    final boolean isadmin = session.isAdmin();
                    final String url = ((IWickletApplication)WebApplication.get()).getWickletSupport().getHttpsUrl(
                        isadmin ? Admin.PATH : Home.PATH);
                    RequestCycle.get().scheduleRequestHandlerAfterCurrent(new RedirectRequestHandler(url));
                }
                @Override
                protected String challegetext() {
                    return Config.DEBUG ? CAPTCHA : null;
                }
            }.resetCaptchaOnRetry(true));
    }

    protected Login(final PageParameters params, final Object dummy) {
        super(params);
    }

    @Override
    public IRootMarkup getAssociatedMarkup() {
        return MARKUP;
    }
}
