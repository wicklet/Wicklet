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
import sf.wicklet.dsl.css.api.ICSSVisitor;
import sf.wicklet.dsl.css.api.IRulesets;
import sf.wicklet.dsl.css.api.IStylesheet;
import sf.wicklet.dsl.css.api.support.IStylesheetChild;

public class Rulesets implements IRulesets {

	private static final long serialVersionUID = 1L;
	private final Collection<IStylesheetChild> children = new LinkedList<IStylesheetChild>();

	@Override
	public Collection<IStylesheetChild> getChildren() {
		return children;
	}

	public Rulesets(final IStylesheetChild...children) {
		add(children);
	}

	@Override
	public boolean add(final IStylesheetChild...children) {
		boolean added = false;
		for (final IStylesheetChild child: children) {
			added |= add(child);
		}
		return added;
	}

	@Override
	public boolean add(final Collection<IStylesheetChild> children) {
		boolean added = false;
		for (final IStylesheetChild child: children) {
			added |= add(child);
		}
		return added;
	}

	@Override
	public boolean add(final IStylesheetChild ruleset) {
		return children.add(ruleset);
	}

	@Override
	public void addTo(final IStylesheet stylesheet) {
		for (final IStylesheetChild child: children) {
			child.addTo(stylesheet);
	}}

	@Override
	public Iterator<IStylesheetChild> iterator() {
		return children.iterator();
	}

	@Override
	public <T> void accept(final ICSSVisitor<T> visitor, final T data) {
		throw new AssertionError("ASSERT: Should not reach here");
	}
}
