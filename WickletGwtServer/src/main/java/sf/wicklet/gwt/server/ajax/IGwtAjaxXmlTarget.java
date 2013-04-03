/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.ajax;

import java.io.PrintWriter;

/** Simple xml target instead of wicket style ajax-response target. */
public interface IGwtAjaxXmlTarget extends IGwtAjaxTarget {
	PrintWriter getWriter();
	void write(CharSequence s);
}
