/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.impl;

import sf.wicklet.dsl.css.api.IAOp;
import sf.wicklet.dsl.css.api.ICSSVisitor;
import sf.wicklet.dsl.css.api.IRuleset;
import sf.wicklet.dsl.css.api.ISelector;

public class Selector implements ISelector {

	private static final long serialVersionUID = 1L;
	private final StringBuilder expr = new StringBuilder();

	public Selector() {
	}

	public Selector(final Object sel) {
		sel(sel);
	}

	public Selector(final Object...sels) {
		sel(sels);
	}

	@Override
	public <T> void accept(final ICSSVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}

	@Override
	public ISelector sel(final Object sel) {
		if (expr.length() > 0) {
			expr.append(", ");
		}
		expr.append(sel.toString());
		return this;
	}

	@Override
	public ISelector sel(final Object...sels) {
		for (final Object sel: sels) {
			if (expr.length() > 0) {
				expr.append(", ");
			}
			expr.append(sel.toString());
		}
		return this;
	}

	@Override
	public ISelector id(final Object id) {
		expr.append('#');
		expr.append(id.toString());
		return this;
	}

	@Override
	public ISelector css(final Object cls) {
		expr.append('.');
		expr.append(cls.toString());
		return this;
	}

	@Override
	public ISelector psc(final Object pseudo) {
		expr.append(':');
		expr.append(pseudo.toString());
		return this;
	}

	@Override
	public ISelector pse(final Object pseudo) {
		expr.append("::");
		expr.append(pseudo.toString());
		return this;
	}

	@Override
	public ISelector attr(final Object expr) {
		this.expr.append('[');
		this.expr.append(expr.toString());
		this.expr.append(']');
		return this;
	}

	@Override
	public ISelector attr(final Object name, final IAOp op, final Object value) {
		expr.append('[');
		expr.append(name.toString());
		expr.append(op.toString());
		expr.append('"');
		expr.append(value.toString());
		expr.append("\"]");
		return this;
	}

	@Override
	public ISelector desc(final Object sel) {
		return a(sel);
	}

	@Override
	public ISelector desc(final Object...sels) {
		return a(sels);
	}

	@Override
	public ISelector child(final Object sel) {
		return c(sel);
	}

	@Override
	public ISelector child(final Object...sels) {
		return c(sels);
	}

	@Override
	public ISelector silbing(final Object sel) {
		expr.append('+');
		expr.append(sel.toString());
		return this;
	}

	@Override
	public ISelector c(final Object sel) {
		expr.append('>');
		expr.append(sel.toString());
		return this;
	}

	@Override
	public ISelector c(final Object...sels) {
		for (final Object sel: sels) {
			expr.append('>');
			expr.append(sel.toString());
		}
		return this;
	}

	@Override
	public ISelector a(final Object sel) {
		expr.append(' ');
		expr.append(sel.toString());
		return this;
	}

	@Override
	public ISelector a(final Object...sels) {
		for (final Object sel: sels) {
			expr.append(' ');
			expr.append(sel.toString());
		}
		return this;
	}

	@Override
	public void addTo(final IRuleset ruleset) {
		ruleset.add(this);
	}

	@Override
	public String toString() {
		return expr.toString();
	}
}
