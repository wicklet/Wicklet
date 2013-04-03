/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.dsl.css.api.ICSSVisitor;
import sf.wicklet.dsl.css.api.IDeclaration;
import sf.wicklet.dsl.css.api.IDeclarations;
import sf.wicklet.dsl.css.api.IRuleset;

public class Declarations implements IDeclarations {

	private static final long serialVersionUID = 1L;
	private final Collection<IDeclaration> declarations = new LinkedList<IDeclaration>();
	private final TreeMap<String, IDeclaration> properties = new TreeMap<String, IDeclaration>();

	public Declarations(final IDeclaration...decls) {
		for (final IDeclaration decl: decls) {
			add(decl);
		}
	}

	@Override
	public Collection<IDeclaration> getDeclarations() {
		return declarations;
	}

	@Override
	public boolean add(final Collection<IDeclaration> decls) {
		boolean added = false;
		for (final IDeclaration decl: decls) {
			added |= add(decl);
		}
		return added;
	}

	@Override
	public boolean add(final IDeclaration decl) {
		final String p = decl.getProperty();
		final IDeclaration o = properties.get(decl.getProperty());
		if (o != null && TextUtil.equals(o.getExpr(), decl.getExpr())) {
			// Avoid adding the same declaration again.
			return false;
		}
		// If decl is new or differ from existing one, append.
		properties.put(p, decl);
		declarations.add(decl);
		return true;
	}

	@Override
	public void addTo(final IRuleset ruleset) {
		for (final IDeclaration decl: declarations) {
			ruleset.add(decl);
	}}

	@Override
	public Iterator<IDeclaration> iterator() {
		return declarations.iterator();
	}

	@Override
	public <T> void accept(final ICSSVisitor<T> visitor, final T data) {
		throw new AssertionError("ASSERT: Should not reach here.");
	}
}
