/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.dsl.html;

public class Linebreak extends Text implements ILine {
	private static final long serialVersionUID = 1L;
	public Linebreak() {
		super("");
	}
	@Override
	public <T> void accept(final INodeVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}
}
