/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.impl;

import sf.wicklet.dsl.html.api.IAttrProvider;
import sf.wicklet.dsl.html.api.IAttribute;
import sf.wicklet.dsl.html.api.IChild;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.api.INode;
import sf.wicklet.dsl.html.api.INodeVisitor;
import sf.wicklet.dsl.html.api.IWicketComponent;

public class WicketComponent extends Element implements IWicketComponent {
	private static final long serialVersionUID = 1L;
	private final static int AUTO_COMPONENT = 0x0008;
	private final String wicketId;
	private int flags;
	public WicketComponent(final Object wicketid, final IElement e) {
		super(e.tag());
		wicketId = wicketid(wicketid);
		for (final IAttribute attr: e.attributes()) {
			a(attr);
		}
		for (final IChild c: e.children()) {
			c.addTo(this);
		}
	}
	public WicketComponent(final Object wicketid, final Object tag, final INode...children) {
		super(tag.toString());
		wicketId = wicketid(wicketid);
		for (final INode c: children) {
			c.addTo(this);
		}
	}
	public WicketComponent(final String wicketid, final IElement e) {
		super(e.tag());
		wicketId = wicketid;
		for (final IAttribute attr: e.attributes()) {
			a(attr);
		}
		for (final IChild c: e.children()) {
			c.addTo(this);
		}
	}
	public WicketComponent(final String wicketid, final Object tag, final INode...children) {
		super(tag.toString());
		wicketId = wicketid;
		for (final INode c: children) {
			c.addTo(this);
		}
	}
	private String wicketid(final Object wicketid) {
		String wid = null;
		if (wicketid instanceof IAttrProvider) {
			final IAttribute attr = ((IAttrProvider)wicketid).att();
			wid = attr.avalue();
			a(attr);
		} else if (wicketid instanceof IAttribute) {
			final IAttribute attr = (IAttribute)wicketid;
			wid = attr.avalue();
			a(attr);
		} else {
			wid = wicketid.toString();
		}
		a("wicket:id", wid);
		return wid;
	}
	@Override
	public String getWicketId() {
		return wicketId;
	}
	@Override
	public boolean isAuto() {
		return (flags & AUTO_COMPONENT) != 0;
	}
	@Override
	public IWicketComponent setAuto(final boolean b) {
		flags = (b ? (flags | AUTO_COMPONENT) : (flags & (~AUTO_COMPONENT)));
		return this;
	}
	@Override
	public int getFlags() {
		return flags;
	}
	@Override
	public IWicketComponent setFlags(final int flags) {
		this.flags |= flags;
		return this;
	}
	@Override
	public <T> void accept(final INodeVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}
}
