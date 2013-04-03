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
import sf.wicklet.dsl.css.api.IFontface;
import sf.wicklet.dsl.css.api.IStylesheet;

public class Fontface implements IFontface {

	private static final long serialVersionUID = 1L;
	private final Collection<IDeclaration> declarations = new LinkedList<IDeclaration>();

	public Fontface(final IDeclaration...decls) {
		add(decls);
	}

	@Override
	public Collection<IDeclaration> getDeclarations() {
		return declarations;
	}

	@Override
	public IFontface add(final IDeclaration...decls) {
		for (final IDeclaration d: decls) {
			declarations.add(d);
		}
		return this;
	}

	@Override
	public void addTo(final IStylesheet stylesheet) {
		stylesheet.add(this);
	}

	@Override
	public <T> void accept(final ICSSVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}
}
