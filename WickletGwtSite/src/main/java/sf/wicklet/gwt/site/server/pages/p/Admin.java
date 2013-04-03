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
import org.apache.wicket.protocol.https.RequireHttps;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sf.blacksun.util.text.XMLStringWriter;
import sf.wicklet.dsl.html.api.INode;
import sf.wicklet.dsl.html.impl.XHtmlBuilder;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.gwt.client.util.GwtXmlUtil;
import sf.wicklet.gwt.server.UserInfo;
import sf.wicklet.gwt.server.ajax.GwtAuthenticatedWebApplication;
import sf.wicklet.gwt.site.server.MyAuthenticatedWebSession;
import sf.wicklet.gwt.site.server.behavior.UserInfoBehavior;
import sf.wicklet.gwt.site.server.pages.Home;
import sf.wicklet.gwt.site.server.support.WickletGwtSiteTemplate;
import sf.wicklet.gwt.site.shared.ICS;
import sf.wicklet.gwt.site.shared.Shared.Href;
import sf.wicklet.gwt.site.shared.WID;

@RequireHttps
@AuthorizeInstantiation("admin")
public class Admin extends WebPage {
    public static final String PATH = "/p/admin";
    private static final long serialVersionUID = 1L;
    private static final String TITLE = "Admin";
    private static final String MODULE = "../WickletGwtSiteHome";

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
                        div(
                            id(WID.leftPanel),
                            div(
                                id(WID.leftTopPanel), //
                                component(id(WID.adminLeftPanel)).div())),
                        div(
                            id(WID.rightPanel), //
                            component(id(WID.adminRightPanel)).div())));
            }
            @Override
            protected INode headstart() {
                return top(super.headstart(), title(TITLE));
            }
        }.build();
    }

    public Admin(final PageParameters params) {
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
        add(adminLeftPanel());
        add(adminRightPanel());
    }

    @Override
    public IRootMarkup getAssociatedMarkup() {
        return Lazy.MARKUP;
    }

    private WebMarkupContainer adminLeftPanel() {
        return new WebMarkupContainer(WID.adminLeftPanel.toString()) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void onRender() {
                getResponse().write(
                    new XHtmlBuilder() {
                        String build() {
                            return serialize(
                                div(
                                    id(WID.adminPanel),
                                    css(ICS.adminPanel),
                                    a(href("#" + Href.AdminUsers), txt("Users")), //
                                    a(href("#" + Href.AdminWikis), txt("Wikis")),
                                    a(href("#" + Href.AdminForums), txt("Forums")),
                                    a(href("#" + Href.AdminBugs), txt("Bugs"))));
                        }
                    }.build());
            }
        };
    }

    private WebMarkupContainer adminRightPanel() {
        return new WebMarkupContainer(WID.adminRightPanel.toString()) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void onRender() {
                getResponse().write(
                    new XHtmlBuilder() {
                        String build() {
                            return serialize(top(txt("TODO")));
                        }
                    }.build());
            }
        };
    }
}
