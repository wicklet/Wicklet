/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.clients;

import sf.wicklet.gwt.client.dsl.gwt.GwtBuilder;
import sf.wicklet.gwt.client.util.GwtDomUtil;
import sf.wicklet.gwt.client.util.GwtTextUtil;
import sf.wicklet.gwt.client.util.IRunnable;
import sf.wicklet.gwt.site.shared.ICS;
import sf.wicklet.gwt.site.shared.WID;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class WickletGwtSiteBuilder extends GwtBuilder {

	public static String getUser() {
		final Element e = DOM.getElementById(WID.topUser.toString());
		if (e != null) {
			final String user = e.getInnerText();
			if (!GwtTextUtil.isEmpty(user)) {
				return user;
		}}
		return null;
	}

	protected IGwtComplexPanel topPanel() {
		final Element homeelm = GwtDomUtil.findAndDetach(WID.topHome);
		final Element userelm = GwtDomUtil.findAndDetach(WID.topUser);
		final Element loginelm = GwtDomUtil.findAndDetach(WID.topLogin);
		final Element logoutelm = GwtDomUtil.findAndDetach(WID.topLogout);
		// User username from html template, may use WickletGwtSiteUtil.getUsername() instead.
		boolean loggedin = false;
		IGwtWidget userwidget;
		if (userelm != null && "a".equalsIgnoreCase(userelm.getTagName())) {
			loggedin = true;
			userwidget = anchor(userelm);
		} else {
			userwidget = label(userelm).istyle("font-weight: bold");
		}
		return panel(
			byId(WID.topPanel),
			panel(
				css(ICS.topMenu),
				panel(anchor(homeelm), label(" | ")),
				panel(
					css(ICS.loginPanel),
					ipanel(userwidget, label(" | ")).hide(!loggedin), //
					ipanel(anchor(loginelm), label(" | ")).hide(loggedin),
					ipanel(anchor(logoutelm), label(" | ")).hide(!loggedin)),
				form(
					id(WID.searchForm),
					css(ICS.searchForm),
					textbox("To be implemented").id(WID.searchText).css(ICS.searchText))));
	}

	protected IGwtComplexPanel rightPanel() {
		return panel(byId(WID.rightPanel));
	}

	/**
	 * Create a panel with all a elements in a template with id=action+"Panel":
	 * <div id="wikiPanel">
	 *     <a href="#home">Wiki</a>
	 *     <a href="#documentation">Documentation</a>
	 *     ...
	 * </div>
	 */
	protected IGwtWithHeader populatePanel(final String header, final String id, final IRunnable<String> runnable) {
		final IGwtCellPanel ret = vpanel();
		final Element top = DOM.getElementById(id);
		IRunnable<Element> cb = null;
		if (top != null) {
			top.removeFromParent();
			cb = ConfigureCssCallback.create(top.getClassName());
			new GwtBuilder() {
				void build() {
					for (com.google.gwt.dom.client.Element c;
						(c = top.getFirstChildElement()) != null;) {
						final String href = c.getAttribute("href");
						if (GwtTextUtil.isEmpty(href)) {
							continue;
						}
						// href is in form #target
						ret.a(
							hyperlink((Element)c.cast()).setLink(href.substring(1))
								.onactivate(runnable));
				}}
			}.build();
		}
		return header(header, ret, cb);
	}

	public static class ConfigureCssCallback implements IRunnable<Element> {
		public static ConfigureCssCallback create(final String css) {
			if (GwtTextUtil.isEmpty(css)) {
				return null;
			}
			return new ConfigureCssCallback(css);
		}
		private final String css;
		ConfigureCssCallback(final String css) {
			this.css = css;
		}
		@Override
		public void run(final Element data) {
			data.getParentElement().setClassName(css);
		}
	}
}
