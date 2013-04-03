/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.pages.p;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.protocol.https.RequireHttps;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sf.blacksun.util.net.IHttpConstants.HttpStatus;
import sf.blacksun.util.text.XMLStringWriter;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.api.INode;
import sf.wicklet.dsl.html.impl.XHtmlBuilder;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.gwt.client.util.GwtXmlUtil;
import sf.wicklet.gwt.server.UserInfo;
import sf.wicklet.gwt.server.ajax.GwtAuthenticatedWebApplication;
import sf.wicklet.gwt.server.ajax.IGwtAjaxRequestHandler;
import sf.wicklet.gwt.server.ajax.IGwtAjaxTarget;
import sf.wicklet.gwt.server.ajax.impl.GwtAjaxWickletTarget;
import sf.wicklet.gwt.server.ajax.impl.GwtAjaxXmlTarget;
import sf.wicklet.gwt.site.server.MyAuthenticatedWebSession;
import sf.wicklet.gwt.site.server.behavior.UserInfoBehavior;
import sf.wicklet.gwt.site.server.pages.Home;
import sf.wicklet.gwt.site.server.pages.s.Service;
import sf.wicklet.gwt.site.server.support.WickletGwtSiteTemplate;
import sf.wicklet.gwt.site.shared.ICS;
import sf.wicklet.gwt.site.shared.Shared.Services;
import sf.wicklet.gwt.site.shared.WID;

@RequireHttps
@AuthorizeInstantiation("user")
public class User extends WebPage {
    public static final String PATH = "/p/user";
    private static final long serialVersionUID = 1L;
    private static final String TITLE = "User";
    private static final String MODULE = "../WickletGwtSiteHome";
    private static final String PARAM_ACTION = "action";

    private static class Lazy {
        static IRootMarkup MARKUP = new WickletGwtSiteTemplate(MODULE, PATH) {
            @Override
            protected INode content() {
                final IWickletSupport support = GwtAuthenticatedWebApplication.get().getWickletSupport();
                return div(
                    id(WID.bodyContent),
                    div(
                        id(WID.threePane),
                        css(ICS.threePane),
                        div(
                            id(WID.topPanel),
                            a(id(WID.topHome), href(support.getContextPath(Home.PATH)), txt("Home")),
                            component(id(WID.topUser)).span(),
                            a(id(WID.topLogin), href(support.getHttpsUrl(Login.PATH)), txt("Login")),
                            a(id(WID.topLogout), href(support.getHttpsUrl(Logout.PATH)), txt("Logout"))),
                        div(id(WID.leftPanel), component(id(WID.leftTopPanel)).div()), //
                        div(
                            id(WID.rightPanel), //
                            div(id(WID.userRightPanel))),
                        component(id(WID.commentPanel)).div()));
            }
            @Override
            protected INode headstart() {
                return top(super.headstart(), title(TITLE));
            }
        }.build();
    }

    public User(final PageParameters params) {
        super(params);
        setStatelessHint(false);
        final UserInfo userinfo = MyAuthenticatedWebSession.get().getUserInfo();
        add(new UserInfoBehavior(userinfo));
        add(
            new WebMarkupContainer(WID.topUser.toString()) {
                private static final long serialVersionUID = 1L;
                @Override
                protected void onRender() {
                    if (userinfo != null) {
                        getResponse().write(
                            new XMLStringWriter().element(
                                "a",
                                GwtXmlUtil.escXml(userinfo.getUsername()),
                                "id",
                                WID.topUser.toString(),
                                "href",
                                "#").toString());
                    } else {
                        getResponse().write("<span id=\"" + WID.topUser + "\"></span>");
                }}
            });
        add(leftTopPanel());
        add(new Label(WID.commentPanel.toString(), ""));
        final IRequestParameters p = getRequest().getRequestParameters();
        final String action = p.getParameterValue(PARAM_ACTION).toString();
        if (action != null) {
            handleActions(p, action);
            return;
        }
    }

    private void handleActions(final IRequestParameters p, final String action) {
        IGwtAjaxTarget target;
        if (Services.WikiComment.equalsIgnoreCase(action)) {
            target = Service.wikiComment(new GwtAjaxWickletTarget(this), p);
        } else {
            target = new GwtAjaxXmlTarget(this);
            target.error(HttpStatus.ServiceUnavailable, "ERROR: Invalid action: " + action, null);
        }
        final IGwtAjaxRequestHandler handler = GwtAuthenticatedWebApplication.get().getGwtAjaxSupport()
            .createGwtAjaxRequestHandler(this, target);
        RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);
    }

    @Override
    public IRootMarkup getAssociatedMarkup() {
        return Lazy.MARKUP;
    }

    private WebMarkupContainer leftTopPanel() {
        return new WebMarkupContainer(WID.leftTopPanel.toString()) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void onRender() {
                // Let's create the left panel directly instead of using wicket components.
                getResponse().write(
                    new XHtmlBuilder() {
                        String build() {
                            return serialize(div(id(WID.leftTopPanel), preferencepanel()));
                        }
                        IElement preferencepanel() {
                            final IElement ret = div(id(WID.userPanel), css(ICS.userPanel));
                            ret.c(a(href("#user-preference"), txt("Preference")));
                            return ret;
                        }
                    }.build());
            }
        };
    }
}
