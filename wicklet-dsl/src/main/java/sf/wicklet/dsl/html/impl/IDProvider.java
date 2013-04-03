/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.impl;

import sf.wicklet.dsl.css.api.IIDProvider;
import sf.wicklet.dsl.html.api.Attribute;
import sf.wicklet.dsl.html.api.IAttribute;

/** Convenient base class for an IIDProvider. */
public class IDProvider implements IIDProvider {

	private final IAttribute attribute;
	private final String ref;

	public IDProvider(final String id) {
		attribute = new Attribute("id", id);
		ref = "#" + id;
	}

	@Override
	public IAttribute att() {
		return attribute;
	}

	@Override
	public String ref() {
		return ref;
	}
}
