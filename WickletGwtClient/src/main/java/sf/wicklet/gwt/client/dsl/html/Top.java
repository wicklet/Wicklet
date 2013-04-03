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

/**
 * An invisible holder element with no attributes that simply group nodes together.
 * When this is added to another element, it adds the children to the element instead.
 *
 */
public class Top implements ITop {

	private static final long serialVersionUID = 1L;
	private final List<IChild> children = new ArrayList<IChild>(4);

	public Top() {
	}

	@Override
	public <T> void accept(final INodeVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}

	@Override
	public String tag() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String id() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int childCount() {
		return 0;
	}

	@Override
	public int attrCount() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<IChild> children() {
		return children;
	}

	@Override
	public Iterable<IAttribute> attributes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IElement id(final String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IElement a(final IAttributes a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IElement a(final IAttribute a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IElement a(final String name, final String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IElement a(final String name, final String...values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IElement a(final ITop child) {
		for (final IChild c: child.children()) {
			children.add(c);
		}
		return this;
	}

	@Override
	public IElement a(final IElement child) {
		children.add(child);
		return this;
	}

	@Override
	public IElement a(final IText child) {
		children.add(child);
		return this;
	}

	@Override
	public IElement a(final ICData child) {
		children.add(child);
		return this;
	}

	@Override
	public IElement a(final IComment child) {
		children.add(child);
		return this;
	}

	@Override
	public IElement a(final IPI child) {
		children.add(child);
		return this;
	}

	@Override
	public IElement a(final IDeclaration child) {
		children.add(child);
		return this;
	}

	@Override
	public IElement a(final INode child) {
		throw new AssertionError("ERROR: Should not reach here");
	}

	@Override
	public void addTo(final IElement e) {
		for (final INode c: children) {
			c.addTo(e);
	}}

	@Override
	public IElement c(final IChild...children) {
		for (final IChild child: children) {
			child.addTo(this);
		}
		return this;
	}
}
