/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.api;

import sf.wicklet.dsl.html.api.IAttrProvider;
import sf.wicklet.dsl.html.api.IAttribute;

public interface ICSSProvider extends IAttrProvider {
	/** @return The CSS class name. */
	String name();
	/** @return A CSS class reference, eg. .topPanel. */
	String ref();
	/** @return A CSS class attribute. */
	@Override
	IAttribute att();
}
