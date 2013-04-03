/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.dsl.html;

public class XHtmlBuilder extends XHtmlBuilderBase<IElement> {

	public String serialize(final IElement e) {
		return XHtmlSerializer.serialize(e, true);
	}

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
}
