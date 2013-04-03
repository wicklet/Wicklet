/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;

public class JavascriptContentHeaderBehavior extends Behavior {
	private static final long serialVersionUID = 1L;
	private final JavaScriptContentHeaderItem item;
	public JavascriptContentHeaderBehavior(final CharSequence javascript, final String id, final String condition) {
		this(new JavaScriptContentHeaderItem(javascript, id, condition));
	}
	public JavascriptContentHeaderBehavior(final JavaScriptContentHeaderItem item) {
		this.item = item;
	}
	@Override
	public void renderHead(final Component component, final IHeaderResponse response) {
		response.render(item);
	}
}
