/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.ajax.impl;

import java.io.PrintWriter;
import org.apache.wicket.Page;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.http.WebResponse;
import sf.blacksun.util.io.StringPrintWriter;
import sf.wicklet.gwt.server.ajax.IGwtAjaxXmlTarget;
import sf.wicklet.gwt.shared.GwtHttp;

/**
 * Response with a plain xml document instead of wicket style ajax-response.
 */
public class GwtAjaxXmlTarget extends AbstractGwtAjaxTarget implements IGwtAjaxXmlTarget {

	public static final String EPOCH = "Thu, 01 Jan 1970 00:00:00 GMT";

	private final Page page;
	private StringPrintWriter out;

	public GwtAjaxXmlTarget(final Page page) {
		this.page = page;
		out = new StringPrintWriter();
	}

	@Override
	public Page getPage() {
		return page;
	}

	@Override
	public void detach(final IRequestCycle requestcycle) {
		out.close();
		out = null;
	}

	@Override
	public void respond(final IRequestCycle requestcycle) {
		final WebResponse res = (WebResponse)requestcycle.getResponse();
		if (!errorResponse(res)) {
			// Set content-type as text/xml as it is expected by xmlHttpRequest.
			res.setHeader(GwtHttp.Header.ContentType, "text/xml;charset=UTF-8");
			res.setHeader(GwtHttp.Header.CacheControl, "no-cache, no-store");
			res.setHeader(GwtHttp.Header.Pragma, "no-cache");
			res.setHeader(GwtHttp.Header.Expires, EPOCH);
			res.setHeader(GwtHttp.Wicklet.Content, "text/xml");
			res.write(out);
	}}

	@Override
	public PrintWriter getWriter() {
		return out;
	}

	@Override
	public void write(final CharSequence s) {
		out.append(s);
	}
}
