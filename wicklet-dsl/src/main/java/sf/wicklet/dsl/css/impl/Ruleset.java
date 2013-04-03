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
import java.util.TreeMap;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.dsl.css.api.ICSSVisitor;
import sf.wicklet.dsl.css.api.IDeclaration;
import sf.wicklet.dsl.css.api.IDeclarations;
import sf.wicklet.dsl.css.api.IRuleset;
import sf.wicklet.dsl.css.api.ISelector;
import sf.wicklet.dsl.css.api.IStylesheet;
import sf.wicklet.dsl.css.api.support.IRulesetChild;

public class Ruleset implements IRuleset {

	////////////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 1L;
	private final Collection<ISelector> selectors = new LinkedList<ISelector>();
	private final Collection<IDeclaration> declarations = new LinkedList<IDeclaration>();
	private final TreeMap<String, IDeclaration> properties = new TreeMap<String, IDeclaration>();

	////////////////////////////////////////////////////////////////////////

	public Ruleset(final Object sel, final IRulesetChild...children) {
		add(new Selector(sel.toString()));
		for (final IRulesetChild c: children) {
			c.addTo(this);
		}
	}

	public Ruleset(final IRulesetChild...children) {
		for (final IRulesetChild c: children) {
			c.addTo(this);
		}
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public Collection<ISelector> getSelectors() {
		return selectors;
	}

	@Override
	public Collection<IDeclaration> getDeclarations() {
		return declarations;
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public IRuleset add(final ISelector...sels) {
		for (final ISelector sel: sels) {
			selectors.add(sel);
		}
		return this;
	}

	@Override
	public IRuleset add(final IDeclaration...decls) {
		for (final IDeclaration decl: decls) {
			a(decl);
		}
		return this;
	}

	@Override
	public IRuleset add(final IRuleset...rules) {
		for (final IRuleset rule: rules) {
			for (final IDeclaration decl: rule.getDeclarations()) {
				a(decl);
		}}
		return this;
	}

	@Override
	public IRuleset add(final IDeclarations...declss) {
		for (final IDeclarations decls: declss) {
			for (final IDeclaration decl: decls.getDeclarations()) {
				a(decl);
		}}
		return this;
	}

	@Override
	public void addTo(final IStylesheet stylesheet) {
		stylesheet.add(this);
	}

	@Override
	public void addTo(final IRuleset ruleset) {
		ruleset.add(this);
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public IRuleset add(final ISelector sel) {
		selectors.add(sel);
		return this;
	}

	@Override
	public IRuleset add(final IDeclaration decl) {
		a(decl);
		return this;
	}

	@Override
	public IRuleset add(final IRuleset rule) {
		for (final IDeclaration decl: rule.getDeclarations()) {
			a(decl);
		}
		return this;
	}

	@Override
	public IRuleset add(final IDeclarations decls) {
		for (final IDeclaration decl: decls) {
			a(decl);
		}
		return this;
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public <T> void accept(final ICSSVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}

	////////////////////////////////////////////////////////////////////////

	private boolean a(final IDeclaration decl) {
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

	////////////////////////////////////////////////////////////////////////
}
