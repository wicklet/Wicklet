/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.api;

import sf.blacksun.util.text.TextUtil;

public class Attribute implements IAttribute {

	private static final long serialVersionUID = 1L;
	public static Attribute LB = new Attribute(null, (String)null);

	private final String name;
	private final String value;

	public Attribute(final Object name, final Object value) {
		this.name = name.toString();
		this.value = value == null ? null : value.toString();
	}

	public Attribute(final Object name, final Object...value) {
		this.name = name.toString();
		this.value = TextUtil.join(" ", value);
	}

	public Attribute(final String name, final String value) {
		this.name = name;
		this.value = value;
	}

	public Attribute(final String name, final String...value) {
		this.name = name;
		this.value = TextUtil.join(" ", value);
	}

	@Override
	public String aname() {
		return name;
	}

	@Override
	public String avalue() {
		return value;
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public void addTo(final IElement e) {
		e.a(this);
	}

	@Override
	public void addTo(final IAttributes a) {
		a.a(this);
	}

	//////////////////////////////////////////////////////////////////////
}
