/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.dsl.html;

import sf.wicklet.gwt.client.util.GwtTextUtil;

public class Comment implements IComment {
	private static final long serialVersionUID = 1L;
	private final String[] content;
	public Comment(final String...content) {
		this.content = content;
	}
	@Override
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
		return "<!-- " + GwtTextUtil.joinln(content) + " -->";
	}
}
