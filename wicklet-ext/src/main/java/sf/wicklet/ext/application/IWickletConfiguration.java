/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.application;

import java.io.Serializable;
import sf.wicklet.ext.config.IConfigArray;
import sf.wicklet.ext.config.IConfigObject;

public interface IWickletConfiguration extends Serializable {
	IConfigObject root();
	/** Same as getObject(root(), segments). */
	IConfigObject getObject(String...segments);
	/** Same as getArray(root(), segments). */
	IConfigArray getArray(String...segments);
	/** Same as getString(root(), segments). */
	String getString(String...segments);
	IConfigObject getObject(IConfigObject parent, String...segments);
	IConfigArray getArray(IConfigObject parent, String...segments);
	String getString(IConfigObject parent, String...segments);
}
