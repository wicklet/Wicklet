/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.impl;

import sf.wicklet.dsl.css.api.ICSSVisitor;
import sf.wicklet.dsl.css.api.INamespace;
import sf.wicklet.dsl.css.api.IStylesheet;

public class Namespace implements INamespace {

	private static final long serialVersionUID = 1L;
	private final String prefix;
	private final String uri;

	public Namespace(final String prefix, final String uri) {
		this.prefix = prefix;
		this.uri = uri;
	}

	@Override
	public <T> void accept(final ICSSVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public String getURI() {
		return uri;
	}

	@Override
	public void addTo(final IStylesheet stylesheet) {
		stylesheet.add(this);
	}
}
