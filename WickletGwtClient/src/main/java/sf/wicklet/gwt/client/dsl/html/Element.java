/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.dsl.html;

import java.util.ArrayList;
import java.util.List;

public class Element extends EmptyElement {
	private static final long serialVersionUID = 1L;
	protected final List<IChild> children = new ArrayList<IChild>(4);
	public Element(final Object name, final INode...children) {
		super(name.toString());
		for (final INode c: children) {
			c.addTo(this);
		}
	}
	@Override
	public int childCount() {
		return children.size();
	}
	@Override
	public Iterable<IChild> children() {
		return children;
	}
	@Override
	public IElement a(final ITop child) {
		for (final IChild c: child.children()) {
			children.add(c);
		}
		return this;
	}
	@Override
	public IElement a(final IElement c) {
		children.add(c);
		return this;
	}
	@Override
	public IElement a(final IText c) {
		children.add(c);
		return this;
	}
	@Override
	public IElement a(final ICData c) {
		children.add(c);
		return this;
	}
	@Override
	public IElement a(final IComment c) {
		children.add(c);
		return this;
	}
	@Override
	public IElement a(final IPI c) {
		children.add(c);
		return this;
	}
	@Override
	public IElement a(final INode c) {
		throw new AssertionError("ERROR: Should not reach here: " + c.getClass());
	}
}
