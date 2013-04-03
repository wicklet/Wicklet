/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.pages;

import java.io.File;
import java.util.TreeSet;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sf.blacksun.util.FileUtil;
import sf.blacksun.util.FileUtil.FileTimestampComparator;
import sf.blacksun.util.net.IHttpConstants.HttpStatus;
import sf.blacksun.util.struct.ReversedComparator;
import sf.blacksun.util.text.XMLStringWriter;
import sf.wicklet.dsl.html.api.INode;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.ext.components.link.SimpleLink;
import sf.wicklet.gwt.client.util.GwtXmlUtil;
import sf.wicklet.gwt.server.UserInfo;
import sf.wicklet.gwt.server.ajax.GwtAuthenticatedWebApplication;
import sf.wicklet.gwt.server.ajax.IGwtAjaxRequestHandler;
import sf.wicklet.gwt.server.ajax.IGwtAjaxTarget;
import sf.wicklet.gwt.server.ajax.impl.GwtAjaxWickletTarget;
import sf.wicklet.gwt.server.ajax.impl.GwtAjaxXmlTarget;
import sf.wicklet.gwt.server.behavior.WickletGwtAjaxBehavior;
import sf.wicklet.gwt.site.server.MyAuthenticatedWebSession;
import sf.wicklet.gwt.site.server.behavior.UserInfoBehavior;
import sf.wicklet.gwt.site.server.pages.p.Admin;
import sf.wicklet.gwt.site.server.pages.p.Login;
import sf.wicklet.gwt.site.server.pages.p.Logout;
import sf.wicklet.gwt.site.server.pages.p.User;
import sf.wicklet.gwt.site.server.pages.s.Service;
import sf.wicklet.gwt.site.server.support.WickletGwtSiteTemplate;
import sf.wicklet.gwt.site.shared.ICS;
import sf.wicklet.gwt.site.shared.Shared.Services;
import sf.wicklet.gwt.site.shared.WID;

/** Standard GWT application home page. */
public class Home extends WebPage {

    private static final long serialVersionUID = 1L;
    public static final String MODULE = "WickletGwtSiteHome";
    public static final String PATH = "/";
    public static final String TITLE = "Wicklet";
    public static final String PARAM_ACTION = "action";

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
                            a(id(WID.topHome), href(support.getContextPath(PATH)), txt("Home")),
                            component(id(WID.topUser)).span(),
                            a(id(WID.topLogin), href(support.getHttpsUrl(Login.PATH)), txt("Login")),
                            a(id(WID.topLogout), href(support.getHttpsUrl(Logout.PATH)), txt("Logout"))),
                        div(
                            id(WID.leftPanel),
                            div(id(WID.leftTopPanel)),
                            component(id(WID.wikiPanel)).div(css(ICS.wikiPanel), component(WID.wikiRepeater).a()),
                            component(id(WID.newsPanel)).div(css(ICS.newsPanel), component(WID.newsRepeater).a()),
                            component(id(WID.forumPanel)).div(css(ICS.forumPanel), component(WID.forumRepeater).a()),
                            component(id(WID.bugsPanel)).div(css(ICS.bugsPanel), component(WID.bugsRepeater).a())),
                        div(id(WID.rightPanel)),
                        component(id(WID.commentPanel)).div()));
            }
            @Override
            protected INode headstart() {
                return top(super.headstart(), title(TITLE));
            }
        }.build();
    }

    public Home(final PageParameters params) {
        super(params);
        setStatelessHint(false);
        final IWickletSupport support = GwtAuthenticatedWebApplication.get().getWickletSupport();
        final MyAuthenticatedWebSession session = MyAuthenticatedWebSession.get();
        final String href = support.getHttpsUrl(session.isAdmin() ? Admin.PATH : User.PATH);
        final UserInfo userinfo = session.getUserInfo();
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
                                href).toString());
                    } else {
                        getResponse().write("<span id=\"" + WID.topUser + "\"></span>");
                }}
            });
        add(wikiPanel(), newsPanel(), forumPanel(), bugsPanel());
        add(new Label(WID.commentPanel.toString(), ""));
        add(
            new WickletGwtAjaxBehavior() {
                private static final long serialVersionUID = 1L;
                @Override
                public void onRequest() {
                    final IRequestParameters p = getRequest().getRequestParameters();
                    final String action = p.getParameterValue(PARAM_ACTION).toString();
                    if (action != null) {
                        handleActions(p, action);
                }}
            });
    }

    void handleActions(final IRequestParameters p, final String action) {
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

    private WebMarkupContainer wikiPanel() {
        final WebMarkupContainer ret = new WebMarkupContainer(WID.wikiPanel.toString());
        final RepeatingView r = new RepeatingView(WID.wikiRepeater.toString());
        for ( final String name: new String[] { "home", "projects", "sources", "license", "about" }) {
            r.add(
                new SimpleLink(r.newChildId()).href("#" + name).body(
                    "" + Character.toUpperCase(name.charAt(0)) + name.substring(1)));
        }
        ret.add(r);
        return ret;
    }

    private WebMarkupContainer newsPanel() {
        final WebMarkupContainer ret = new WebMarkupContainer(WID.newsPanel.toString());
        final RepeatingView r = new RepeatingView(WID.newsRepeater.toString());
        ret.add(r);
        final IWickletSupport support = GwtAuthenticatedWebApplication.get().getWickletSupport();
        final File newsdir = support.getContextFile("/news");
        final TreeSet<File> set = new TreeSet<File>(
            new ReversedComparator<File>(FileTimestampComparator.getSingleton()));
        set.addAll(FileUtil.files(newsdir, ".*\\.html$"));
        for ( final File filename: set) {
            final String base = FileUtil.basename(filename.getName());
            r.add(
                new SimpleLink(r.newChildId()).href("#" + base).body(
                    "" + Character.toUpperCase(base.charAt(0)) + base.substring(1)));
        }
        return ret;
    }

    private WebMarkupContainer forumPanel() {
        final WebMarkupContainer ret = new WebMarkupContainer(WID.forumPanel.toString());
        final RepeatingView r = new RepeatingView(WID.forumRepeater.toString());
        ret.add(r);
        r.add(new SimpleLink(r.newChildId()).href("#wiki-forum1").body("What the hell"));
        r.add(new SimpleLink(r.newChildId()).href("#wiki-forum2").body("What the ..."));
        return ret;
    }

    private WebMarkupContainer bugsPanel() {
        final WebMarkupContainer ret = new WebMarkupContainer(WID.bugsPanel.toString());
        final RepeatingView r = new RepeatingView(WID.bugsRepeater.toString());
        ret.add(r);
        r.add(new SimpleLink(r.newChildId()).href("#wiki-bugs134237").body("No bug today ..."));
        return ret;
    }
}
