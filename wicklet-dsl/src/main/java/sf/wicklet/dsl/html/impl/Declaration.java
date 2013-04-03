/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.impl;

import sf.blacksun.util.struct.Empty;
import sf.wicklet.dsl.html.api.IDeclaration;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.api.INodeVisitor;

public class Declaration implements IDeclaration {

	private static final long serialVersionUID = 1L;
	private final String name;
	private final String[] content;

	public Declaration(final String name, final String...content) {
		this.name = name;
		this.content = (content == null ? Empty.STRING_ARRAY : content);
	}

	public String getName() {
		return name;
	}

	public String[] getContent() {
		return content;
	}

	@Override
	public void addTo(final IElement e) {
		e.a(this);
	}

	@Override
	public <T> void accept(final INodeVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder(64);
		b.append("<!");
		b.append(name);
		for (final String s: content) {
			b.append(' ');
			b.append(s);
		}
		b.append(">");
		return b.toString();
	}
}
