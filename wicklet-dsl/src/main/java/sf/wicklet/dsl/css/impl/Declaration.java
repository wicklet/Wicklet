/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.impl;

import sf.wicklet.dsl.css.api.ICSSVisitor;
import sf.wicklet.dsl.css.api.IDeclaration;
import sf.wicklet.dsl.css.api.IRuleset;

public class Declaration implements IDeclaration {

	private static final long serialVersionUID = 1L;
	private String property;
	private String expr;

	public Declaration() {
	}

	public Declaration(final String property, final String expr) {
		this.property = property;
		this.expr = expr;
	}

	public Declaration(final Object property, final Object expr) {
		this.property = property.toString();
		this.expr = expr == null ? null : expr.toString();
	}

	@Override
	public <T> void accept(final ICSSVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}

	@Override
	public String getProperty() {
		return property;
	}

	@Override
	public String getExpr() {
		return expr;
	}

	@Override
	public void addTo(final IRuleset ruleset) {
		ruleset.add(this);
	}

	@Override
	public String toString() {
		return property + ": " + expr + ";";
	}

	////////////////////////////////////////////////////////////////////////
}
