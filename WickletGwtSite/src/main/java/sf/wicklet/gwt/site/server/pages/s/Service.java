/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.pages.s;

import java.io.File;
import java.io.IOException;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sf.blacksun.util.FileUtil;
import sf.blacksun.util.net.IHttpConstants.HttpStatus;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.dsl.html.impl.XHtmlBuilder;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.ext.util.IFileManager;
import sf.wicklet.gwt.client.util.GwtTextUtil;
import sf.wicklet.gwt.server.ajax.GwtAuthenticatedWebApplication;
import sf.wicklet.gwt.server.ajax.IGwtAjaxRequestHandler;
import sf.wicklet.gwt.server.ajax.IGwtAjaxTarget;
import sf.wicklet.gwt.server.ajax.IGwtAjaxWickletTarget;
import sf.wicklet.gwt.server.ajax.IGwtAjaxXmlTarget;
import sf.wicklet.gwt.server.ajax.impl.GwtAjaxWickletTarget;
import sf.wicklet.gwt.server.ajax.impl.GwtAjaxXmlTarget;
import sf.wicklet.gwt.site.server.Config;
import sf.wicklet.gwt.site.server.MyAuthenticatedWebSession;
import sf.wicklet.gwt.site.server.pages.p.Login;
import sf.wicklet.gwt.site.server.panels.WikiCommentPanel;
import sf.wicklet.gwt.site.shared.Role;
import sf.wicklet.gwt.site.shared.WID;
import sf.wicklet.gwt.site.shared.Shared.Href;
import sf.wicklet.gwt.site.shared.Shared.Services;

/** Provide ajax service at /s/service/ that require no session/conversation state. */
public class Service extends Page {

	private static final long serialVersionUID = 1L;
	public static final String PATH = "/s/service";
	public static final String PARAM_ACTION = "action";
	public static final String PARAM_HREF = "href";
	public static final String PARAM_CONTENT = "content";

	public Service(final PageParameters params) {
		final IRequestParameters p = getRequest().getRequestParameters();
		final String action = p.getParameterValue(PARAM_ACTION).toString();
		IGwtAjaxTarget target;
		if (Services.ListWiki.equalsIgnoreCase(action)) {
			target = listwiki(new GwtAjaxXmlTarget(this), p);
		} else if (Services.GetWiki.equalsIgnoreCase(action)) {
			target = getwiki(new GwtAjaxXmlTarget(this), p);
		} else if (Services.PutWiki.equalsIgnoreCase(action)) {
			target = putwiki(new GwtAjaxXmlTarget(this), p);
		} else if (Services.WikiComment.equalsIgnoreCase(action)) {
			target = wikiComment(new GwtAjaxWickletTarget(this), p);
		} else {
			target = new GwtAjaxXmlTarget(this);
			target.error(HttpStatus.ServiceUnavailable, "ERROR: Invalid action: " + action, null);
		}
		final IGwtAjaxRequestHandler handler = GwtAuthenticatedWebApplication.get().getGwtAjaxSupport()
			.createGwtAjaxRequestHandler(this, target);
		RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);
	}

	public static IGwtAjaxXmlTarget listwiki(final IGwtAjaxXmlTarget target, final IRequestParameters p) {
		target.write(
			new XHtmlBuilder() {
				String build() {
					return serialize(
						div(
							a(href("#" + Href.WikiHome), txt("Wiki")), //
							a(href("#" + Href.WikiProjects), txt("Projects")),
							a(href("#" + Href.WikiSources), txt("Sources")),
							a(href("#" + Href.WikiLicense), txt("License")),
							a(href("#" + Href.WikiAbout), txt("About"))));
				}
			}.build());
		return target;
	}

	public static IGwtAjaxXmlTarget getwiki(final IGwtAjaxXmlTarget target, final IRequestParameters p) {
		final String href = p.getParameterValue(PARAM_HREF).toString();
		if (TextUtil.isEmpty(href)) {
			target.error(HttpStatus.BadRequest, "href parameter not specified", null);
			return target;
		}
		final IWickletSupport support = GwtAuthenticatedWebApplication.get().getWickletSupport();
		final File file = support.getContextFile("/wiki/" + href + ".html");
		if (!file.exists()) {
			target.error(HttpStatus.NotFound, "Requested wiki page not exists", null);
			return target;
		}
		String content;
		try {
			content = Config.get().getWikiFileManager().read(file);
		} catch (final IOException e) {
			target.error(HttpStatus.InternalServerError, "ERROR: Reading wiki file", e);
			return target;
		}
		target.write(content);
		return target;
	}

	public static IGwtAjaxXmlTarget putwiki(final IGwtAjaxXmlTarget target, final IRequestParameters params) {
		final MyAuthenticatedWebSession session = MyAuthenticatedWebSession.get();
		if (!session.hasRole(Role.writer)) {
			target.error(HttpStatus.Forbidden, "You must be member of writer group to edit wiki", null);
			return target;
		}
		final String href = params.getParameterValue(PARAM_HREF).toString();
		if (GwtTextUtil.isEmpty(href)) {
			target.error(HttpStatus.BadRequest, "You must specify the wiki path", null);
			return target;
		}
		final IWickletSupport support = GwtAuthenticatedWebApplication.get().getWickletSupport();
		final File wikidir = support.getContextFile("/wiki");
		final File file = new File(wikidir, href + ".html");
		if (!file.exists()) {
			target.error(HttpStatus.NotFound, "Requested wiki page not exists", null);
			return target;
		}
		if (!FileUtil.isBaseDir(wikidir, file)) {
			target.error(HttpStatus.BadRequest, "Invalid wiki path", null);
			return target;
		}
		final String content = params.getParameterValue(WID.commentText.toString()).toString();
		if (content == null) {
			target.error(HttpStatus.BadRequest, "You must provide the wiki content", null);
			return target;
		}
		final IFileManager fm = Config.get().getWikiFileManager();
		try {
			fm.write(file, false, content);
		} catch (final IOException e) {
			target.error(
				HttpStatus.InternalServerError,
				"Error writing the wiki content, please try again later",
				null);
			return target;
		}
		target.write("<p>Wiki page " + href + " update OK.</p>");
		return target;
	}

	public static IGwtAjaxWickletTarget wikiComment(final IGwtAjaxWickletTarget target, final IRequestParameters p) {
		final Component c = new WikiCommentPanel(WID.commentPanel, p) {
			private static final long serialVersionUID = 1L;
			@Override
			protected String challengetext() {
				return Config.DEBUG ? Login.CAPTCHA : null;
			}
		}.setOutputMarkupId(true);
		target.getPage().replace(c);
		target.add(c);
		return target;
	}
}
