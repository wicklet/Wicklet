/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.shared;

public enum Role {
	admin, editor, writer, reviewer, user, guest; 
	public static Role get(final String name) {
		for (final Role r: values()) {
			if (r.name().equals(name)) {
				return r;
		}}
		return null;
	}
}
