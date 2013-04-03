/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.ajax;

import org.apache.wicket.Page;
import org.apache.wicket.request.IRequestCycle;
import sf.blacksun.util.net.IHttpConstants.HttpStatus;

public interface IGwtAjaxTarget {
	Page getPage();
	void error(HttpStatus httpstatus, String msg, Throwable e);
	void detach(IRequestCycle requestcycle);
	void respond(IRequestCycle requestcycle);
}
