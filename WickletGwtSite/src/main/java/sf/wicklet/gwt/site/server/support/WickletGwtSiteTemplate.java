/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.support;

import sf.wicklet.dsl.html.api.INode;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.gwt.server.ajax.GwtAuthenticatedWebApplication;
import sf.wicklet.gwt.server.template.GwtPageTemplate;

public class WickletGwtSiteTemplate extends GwtPageTemplate {

    public static final String CSS_PATH = "assets/default.css";

    private final String page;

    public WickletGwtSiteTemplate(final String module, final String page) {
        super(module);
        this.page = page;
    }

    @Override
    protected INode headstart() {
        final IWickletSupport support = GwtAuthenticatedWebApplication.get().getWickletSupport();
        return top(
            contenttype("text/html; charset=UTF-8"), //
            stylesheet(support.getContextPath("/assets/default.css")),
            link(attr("rel", "icon"), type("image/ico"), href(support.getContextPath("/favicon.ico"))),
            javascript().c(
                txt(
                    "window.WickletGwtSite = { ",
                    ("  contextPath : '" + support.getContextPath() + "',"),
                    ("  pageToken : '" + page + "',"), //
                    "};")));
    }
}
