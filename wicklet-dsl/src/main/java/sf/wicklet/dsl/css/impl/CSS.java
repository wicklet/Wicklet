/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.impl;

import sf.wicklet.dsl.css.api.ICSS;
import sf.wicklet.dsl.html.api.IAttribute;
import sf.wicklet.dsl.html.api.IAttributes;
import sf.wicklet.dsl.html.api.IElement;

/**
 * enum can be used for CSS classes, but that cannot be extended.
 * This is a convenient substitution.
 */
public class CSS implements ICSS {

	private static final long serialVersionUID = 1L;
	protected final String name;
	protected final String ref;

	protected CSS() {
		name = null;
		ref = null;
	}
	public CSS(final String name) {
		this.name = name;
		ref = "." + name;
	}
	public CSS(final Object name) {
		this.name = name.toString();
		ref = "." + this.name;
	}

	////////////////////////////////////////////////////////////////////////
	// ICSSProvider

	@Override
	public String name() {
		return name;
	}

	@Override
	public String ref() {
		return ref;
	}

	@Override
	public IAttribute att() {
		return this;
	}

	////////////////////////////////////////////////////////////////////////
	// IAttribute

	@Override
	public void addTo(final IAttributes attrs) {
		attrs.a(this);
	}

	@Override
	public void addTo(final IElement e) {
		e.a(this);
	}

	@Override
	public String aname() {
		return "class";
	}

	@Override
	public String avalue() {
		return name;
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return name;
	}

	////////////////////////////////////////////////////////////////////////
}
