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
import sf.wicklet.dsl.css.api.IMedia;
import sf.wicklet.dsl.css.api.IMedium;
import sf.wicklet.dsl.css.api.IRuleset;
import sf.wicklet.dsl.css.api.IStylesheet;

public class Media implements IMedia {

	private static final long serialVersionUID = 1L;
	private final Collection<IMedium> mediums = new LinkedList<IMedium>();
	private final Collection<IRuleset> rulesets = new LinkedList<IRuleset>();

	public Media(final IMedium...mediums) {
		for (final IMedium m: mediums) {
			this.mediums.add(m);
		}
	}

	@Override
	public <T> void accept(final ICSSVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}

	@Override
	public Collection<IMedium> getMediums() {
		return mediums;
	}

	@Override
	public Collection<IRuleset> getRulesets() {
		return rulesets;
	}

	@Override
	public IMedia add(final IMedium...mediums) {
		for (final IMedium m: mediums) {
			this.mediums.add(m);
		}
		return this;
	}

	@Override
	public IMedia add(final IRuleset...rulesets) {
		for (final IRuleset r: rulesets) {
			this.rulesets.add(r);
		}
		return this;
	}

	@Override
	public void addTo(final IStylesheet stylesheet) {
		stylesheet.add(this);
	}
}
