/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.util;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;

public class WickletUtil {

	private WickletUtil() {
	}

	/** Find parent of the component that has the given id attribute. */
	public static MarkupContainer getParent(final Component c, final String id) {
		for (MarkupContainer parent = c.getParent(); parent != null; parent = parent.getParent()) {
			if (id.equals(parent.getId())) {
				return parent;
		}}
		return null;
	}
}
