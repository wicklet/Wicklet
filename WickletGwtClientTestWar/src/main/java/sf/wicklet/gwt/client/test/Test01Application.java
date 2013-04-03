/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.test;

import org.apache.wicket.Page;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sf.blacksun.util.text.XMLStringWriter;
import sf.wicklet.dsl.html.api.INode;
import sf.wicklet.dsl.html.impl.XHtmlBuilder;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.gwt.client.test.test01.shared.WID;
import sf.wicklet.gwt.client.util.GwtXmlUtil;
import sf.wicklet.gwt.server.ajax.GwtWebApplication;
import sf.wicklet.gwt.server.ajax.IGwtAjaxRequestHandler;
import sf.wicklet.gwt.server.ajax.IGwtAjaxWickletTarget;
import sf.wicklet.gwt.server.ajax.impl.GwtAjaxWickletTarget;
import sf.wicklet.gwt.server.template.GwtPageTemplate;

public class Test01Application extends GwtWebApplication {

    ////////////////////////////////////////////////////////////////////////

    @Override
    public Class<? extends Page> getHomePage() {
        return Test01Application.HomePage.class;
    }

    @Override
    protected void init() {
        super.init();
        mountPage(ServicePage.PATH, ServicePage.class);
    }

    ////////////////////////////////////////////////////////////////////////

    public static class HomePage extends WebPage {
        private static final long serialVersionUID = 1L;
        private static final IRootMarkup MARKUP = new GwtPageTemplate("Test01") {
            @Override
            protected INode content() {
                return top(
                    div(
                        id(WID.bodyContent),
                        div(
                            id(WID.topPanel),
                            a(id(WID.topHome), href("/"), txt("Home")),
                            span(id(WID.topUser)),
                            a(id(WID.topLogin)),
                            a(id(WID.topLogout), href("/p/logout"), txt("Logout"))),
                        div(id(WID.menubar)),
                        div(id(WID.stackpanel)),
                        div(
                            id(WID.ajaxpanel),
                            a(id(WID.ajaxshow), txt("show")),
                            a(id(WID.ajaxshowstatic), txt("show_static")),
                            a(id(WID.ajaxhiddenbylink), css(WID.ajaxhiddenbylink), txt("hidden_by_link")),
                            a(id(WID.ajaxhiddenbystyle), css(WID.ajaxhiddenbystyle), txt("hidden_by_style")),
                            div(id(WID.ajaxresult)))));
            }
            @Override
            protected INode headstart() {
                return top(title("HomePage"), stylesheet("assets/default.css"));
            }
        }.build();
        public HomePage() {
        }
        @Override
        public IRootMarkup getAssociatedMarkup() {
            return MARKUP;
        }
    }

    ////////////////////////////////////////////////////////////////////////

    public static class ServicePage extends WebPage {
        public static final String PATH = "/service";
        private static final long serialVersionUID = 1L;
        public ServicePage(final PageParameters params) {
            super(params);
            final GwtWebApplication app = (GwtWebApplication)getApplication();
            final IGwtAjaxWickletTarget target = new GwtAjaxWickletTarget(this);
            final IGwtAjaxRequestHandler handler = app.getGwtAjaxSupport().createGwtAjaxRequestHandler(this, target);
            RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);
            respond(target);
        }

        private void respond(final IGwtAjaxWickletTarget target) {
            final IRequestParameters p = getRequest().getRequestParameters();
            final String name = p.getParameterValue("test").toString();
            final IWickletSupport support = GwtWebApplication.get().getWickletSupport();
            target.putHeaderXml(
                "ajaxheadercontribution",
                new XMLStringWriter().start("head").empty(
                    "link",
                    "rel",
                    "stylesheet",
                    "type",
                    "text/css",
                    "href",
                    support.getContextPath("/assets/hidden.css")).start(
                    "style", "type", "text/css", "id", "style_ajaxhiddenbystyle").raw(
                    ".ajaxhiddenbystyle { display: none; }").end(2).toString());
            final String xml = GwtXmlUtil.escXmlString(
                new XHtmlBuilder() {
                    String build() {
                        return serialize(
                            div(
                                id(WID.ajaxresult), //
                                span(id(WID.ajaxname), txt(name)),
                                div(id(WID.ajaxcontent))));
                    }
                }.build()).toString();
            if ("static".equals(name)) {
                target.appendJavaScript("$wnd.Test01Api.testAjaxStatic01('" + xml + "');");
            } else {
                target.appendJavaScript("$wnd.Test01Api.testAjax01('" + xml + "');");
        }}
    }

    ////////////////////////////////////////////////////////////////////////
}
