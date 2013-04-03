/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.ajax.impl;

import org.apache.wicket.Page;
import sf.wicklet.gwt.server.ajax.IGwtAjaxRequestHandler;
import sf.wicklet.gwt.server.ajax.IGwtAjaxSupport;
import sf.wicklet.gwt.server.ajax.IGwtAjaxTarget;

public class GwtAjaxSupport implements IGwtAjaxSupport {

	@Override
	public IGwtAjaxRequestHandler createGwtAjaxRequestHandler(final Page page, final IGwtAjaxTarget target) {
		return new GwtAjaxRequestHandler(page, target);
	}
}
