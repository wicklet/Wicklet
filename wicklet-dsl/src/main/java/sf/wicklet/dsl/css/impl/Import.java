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
import sf.wicklet.dsl.css.api.IImport;
import sf.wicklet.dsl.css.api.IMedium;
import sf.wicklet.dsl.css.api.IStylesheet;

public class Import implements IImport {

	private static final long serialVersionUID = 1L;
	private final String uri;
	private final Collection<IMedium> mediums = new LinkedList<IMedium>();

	public Import(final String uri, final IMedium...mediums) {
		this.uri = uri;
		for (final IMedium m: mediums) {
			this.mediums.add(m);
		}
	}

	@Override
	public <T> void accept(final ICSSVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}

	@Override
	public String getURI() {
		return uri;
	}

	@Override
	public Collection<IMedium> getMediums() {
		return mediums;
	}

	@Override
	public IImport add(final IMedium...mediums) {
		for (final IMedium m: mediums) {
			this.mediums.add(m);
		}
		return this;
	}

	@Override
	public void addTo(final IStylesheet stylesheet) {
		stylesheet.add(this);
	}
}
