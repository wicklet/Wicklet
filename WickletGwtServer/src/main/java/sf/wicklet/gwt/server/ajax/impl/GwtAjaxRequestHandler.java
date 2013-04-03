/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.ajax.impl;

import org.apache.wicket.Page;
import org.apache.wicket.core.request.handler.logger.PageLogData;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Args;
import sf.wicklet.gwt.server.ajax.IGwtAjaxRequestHandler;
import sf.wicklet.gwt.server.ajax.IGwtAjaxTarget;

public class GwtAjaxRequestHandler implements IGwtAjaxRequestHandler {

	private final IGwtAjaxTarget target;
	/** The associated Page */
	private final Page page;
	private PageLogData logData;

	public GwtAjaxRequestHandler(final Page page, final IGwtAjaxTarget target) {
		this.page = Args.notNull(page, "page");
		this.target = target;
	}

	/**
	 * @see org.apache.wicket.core.request.handler.IPageRequestHandler#getPage()
	 */
	@Override
	public Page getPage() {
		return page;
	}

	@Override
	public IGwtAjaxTarget getGwtAjaxTarget() {
		return target;
	}

	/**
	 * @see org.apache.wicket.core.request.handler.IPageRequestHandler#detach(org.apache.wicket.request.IRequestCycle)
	 */
	@Override
	public void detach(final IRequestCycle requestCycle) {
		if (logData == null) {
			logData = new PageLogData(page);
		}
		target.detach(requestCycle);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof GwtAjaxRequestHandler) {
			final GwtAjaxRequestHandler that = (GwtAjaxRequestHandler)obj;
			return target.equals(that.target);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = "AjaxRequestHandler".hashCode();
		result += target.hashCode() * 17;
		return result;
	}

	/**
	 * @see org.apache.wicket.core.request.handler.IPageRequestHandler#respond(org.apache.wicket.request.IRequestCycle)
	 */
	@Override
	public final void respond(final IRequestCycle requestCycle) {
		target.respond(requestCycle);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[AjaxRequestHandler@" + hashCode() + " target [" + target + "]";
	}

	/**
	 * @see org.apache.wicket.core.request.handler.IPageRequestHandler#getPageClass()
	 */
	@Override
	public Class<? extends IRequestablePage> getPageClass() {
		return page.getPageClass();
	}

	/**
	 * @see org.apache.wicket.core.request.handler.IPageRequestHandler#getPageId()
	 */
	@Override
	public Integer getPageId() {
		return page.getPageId();
	}

	/**
	 * @see org.apache.wicket.core.request.handler.IPageRequestHandler#getPageParameters()
	 */
	@Override
	public PageParameters getPageParameters() {
		return page.getPageParameters();
	}

	@Override
	public final boolean isPageInstanceCreated() {
		return true;
	}

	@Override
	public final Integer getRenderCount() {
		return page.getRenderCount();
	}

	/** {@inheritDoc} */
	@Override
	public PageLogData getLogData() {
		return logData;
	}
}
