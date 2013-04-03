/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.impl;

import java.util.Collection;
import java.util.LinkedList;
import sf.wicklet.dsl.css.api.ICSSVisitor;
import sf.wicklet.dsl.css.api.IDeclaration;
import sf.wicklet.dsl.css.api.IPage;
import sf.wicklet.dsl.css.api.IStylesheet;

public class Page implements IPage {

	private static final long serialVersionUID = 1L;
	private final String name;
	private final Collection<IDeclaration> declarations = new LinkedList<IDeclaration>();

	public Page(final String name, final IDeclaration...decls) {
		this.name = name == null ? "" : name;
		add(decls);
	}

	@Override
	public <T> void accept(final ICSSVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<IDeclaration> getDeclarations() {
		return declarations;
	}

	@Override
	public IPage add(final IDeclaration...decls) {
		for (final IDeclaration d: decls) {
			declarations.add(d);
		}
		return this;
	}

	@Override
	public void addTo(final IStylesheet stylesheet) {
		stylesheet.add(this);
	}
}
