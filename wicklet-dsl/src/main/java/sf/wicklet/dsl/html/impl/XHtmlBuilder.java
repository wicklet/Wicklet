/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.impl;

import sf.wicklet.dsl.html.api.IAttr;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.api.INode;

public class XHtmlBuilder extends XHtmlBuilderBase<IElement> {

	//////////////////////////////////////////////////////////////////////

	@Override
	public IElement elm(final String name, final INode...children) {
		return new Element(name, children);
	}

	@Override
	public IElement empty(final String name, final IAttr...attrs) {
		return new EmptyElement(name, attrs);
	}

	//////////////////////////////////////////////////////////////////////

	public String serialize(final IElement e) {
		return XHtmlSerializer.serialize("", "    ", true, e);
	}

	public String serialize(final String tab, final IElement e) {
		return XHtmlSerializer.serialize("", tab, true, e);
	}

	public String serialize(final String indent, final String tab, final IElement e) {
		return XHtmlSerializer.serialize(indent, tab, true, e);
	}

	public String serialize(final String indent, final String tab, final boolean noemptytag, final IElement e) {
		return XHtmlSerializer.serialize(indent, tab, noemptytag, e);
	}

	//////////////////////////////////////////////////////////////////////
}
