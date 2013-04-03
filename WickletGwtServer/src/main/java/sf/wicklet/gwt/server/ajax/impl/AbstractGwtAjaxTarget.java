/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.ajax.impl;

import org.apache.wicket.request.http.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sf.blacksun.util.net.IHttpConstants.HttpStatus;
import sf.wicklet.gwt.server.ajax.IGwtAjaxTarget;

public abstract class AbstractGwtAjaxTarget implements IGwtAjaxTarget {

	private static final Logger logger = LoggerFactory.getLogger(AbstractGwtAjaxTarget.class);

	private boolean hasError;
	private HttpStatus status;
	private String msg;
	private Throwable exception;

	@Override
	public void error(final HttpStatus status, final String msg, final Throwable e) {
		logger.error(msg, e);
		hasError = true;
		this.status = status;
		this.msg = msg;
		exception = e;
	}

	public boolean hasError() {
		return hasError;
	}

	public HttpStatus getErrorStatus() {
		return status;
	}

	public String getErrorMessage() {
		return msg;
	}

	public Throwable getErrorException() {
		return exception;
	}

	protected boolean errorResponse(final WebResponse res) {
		if (hasError()) {
			res.setStatus(status.code());
			return true;
		}
		return false;
	}
}
