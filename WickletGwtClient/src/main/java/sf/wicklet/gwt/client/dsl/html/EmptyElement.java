/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.dsl.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sf.wicklet.gwt.client.dsl.Attribute;
import sf.wicklet.gwt.client.dsl.TAG;

public class EmptyElement implements IElement {
	private static final long serialVersionUID = 1L;
	private String tag;
	private String id;
	protected final List<IAttribute> attributes = new ArrayList<IAttribute>(4);
	public EmptyElement(final String tagname) {
		tag = tagname;
	}
	public EmptyElement(final String tagname, final IAttr...attrs) {
		tag = tagname;
		for (final IAttr a: attrs) {
			a.addTo(this);
		}
	}
	public EmptyElement(final TAG tag) {
		this.tag = tag.name();
	}
	@Override
	public String tag() {
		return tag;
	}
	@Override
	public String id() {
		return id;
	}
	@Override
	public int childCount() {
		return 0;
	}
	@Override
	public int attrCount() {
		return attributes.size();
	}
	@Override
	public Iterable<IChild> children() {
		return Collections.emptyList();
	}
	@Override
	public Iterable<IAttribute> attributes() {
		return attributes;
	}
	@Override
	public IElement id(final String id) {
		this.id = id;
		return this;
	}
	@Override
	public IElement a(final ITop c) {
		throw new UnsupportedOperationException("Empty element cannot has children: " + c.getClass());
	}
	@Override
	public IElement a(final IElement c) {
		throw new UnsupportedOperationException("Empty element cannot has children: " + c.getClass());
	}
	@Override
	public IElement a(final IText c) {
		throw new UnsupportedOperationException("Empty element cannot has children: " + c.getClass());
	}
	@Override
	public IElement a(final ICData c) {
		throw new UnsupportedOperationException("Empty element cannot has children: " + c.getClass());
	}
	@Override
	public IElement a(final IComment c) {
		throw new UnsupportedOperationException("Empty element cannot has children: " + c.getClass());
	}
	@Override
	public IElement a(final IPI c) {
		throw new UnsupportedOperationException("Empty element cannot has children: " + c.getClass());
	}
	@Override
	public IElement a(final IDeclaration c) {
		throw new UnsupportedOperationException("Empty element cannot has children: " + c.getClass());
	}
	@Override
	public IElement a(final INode c) {
		throw new UnsupportedOperationException("Empty element cannot has children: " + c.getClass());
	}
	@Override
	public IElement a(final IAttributes a) {
		for (final IAttribute aa: a) {
			attributes.add(aa);
		}
		return this;
	}
	@Override
	public IElement a(final IAttribute a) {
		attributes.add(a);
		return this;
	}
	@Override
	public IElement a(final String name, final String value) {
		attributes.add(new Attribute(name, value));
		return this;
	}
	@Override
	public IElement a(final String name, final String...values) {
		attributes.add(new Attribute(name, values));
		return this;
	}
	@Override
	public IElement c(final IChild...children) {
		for (final INode child: children) {
			child.addTo(this);
		}
		return this;
	}
	@Override
	public void addTo(final IElement e) {
		e.a(this);
	}
	@Override
	public <T> void accept(final INodeVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}
	protected void setTag(final String tagname) {
		tag = tagname;
	}
}
