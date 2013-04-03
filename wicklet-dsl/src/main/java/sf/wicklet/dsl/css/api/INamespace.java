/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.api;

import sf.wicklet.dsl.css.api.support.IStylesheetChild;

public interface INamespace extends IStylesheetChild {
	String getPrefix();
	String getURI();
}
