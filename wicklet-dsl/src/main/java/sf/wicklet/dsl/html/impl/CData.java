/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.impl;

import sf.blacksun.util.text.TextUtil;
import sf.wicklet.dsl.html.api.ICData;

public class CData extends Text implements ICData {
	private static final long serialVersionUID = 1L;
	public CData(final String...content) {
		super(content);
	}
	@Override
	public String toString() {
		return "<![CDATA[" + TextUtil.joinln(getContent()) + "]]>";
	}
}
