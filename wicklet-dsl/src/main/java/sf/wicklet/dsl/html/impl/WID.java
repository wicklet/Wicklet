/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.impl;

import org.apache.wicket.markup.parser.filter.HtmlHeaderSectionHandler;
import org.apache.wicket.markup.parser.filter.RelativePathPrefixHandler;

public class WID {

	//////////////////////////////////////////////////////////////////////

	public static WID HEADER = WID.of(HtmlHeaderSectionHandler.HEADER_ID);
	public static final WID SCRIPT_STYLE = of("_ScriptStyle");
	public static final WID RELATIVE_PATH = of(
		"wicket" + RelativePathPrefixHandler.WICKET_RELATIVE_PATH_PREFIX_CONTAINER_ID);

	//////////////////////////////////////////////////////////////////////

	public static WID of(final String wid) {
		return new WID(wid);
	}

	//////////////////////////////////////////////////////////////////////

	private final String wid;

	//////////////////////////////////////////////////////////////////////

	private WID(final String wid) {
		this.wid = wid;
	}

	@Override
	public String toString() {
		return wid;
	}

	//////////////////////////////////////////////////////////////////////
}
