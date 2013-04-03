/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.behaviors.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Add an OnDomReadyHeaderItem.
 */
public class OnReadyBehavior extends Behavior {
	private static final long serialVersionUID = 1L;
	private final CharSequence javascript;
	private final ResourceReference[] refs;
	public OnReadyBehavior(final CharSequence javascript, final ResourceReference...refs) {
		this.javascript = javascript;
		this.refs = refs;
	}
	@Override
	public void renderHead(final Component component, final IHeaderResponse response) {
		if (refs != null) {
			for (final ResourceReference ref: refs) {
				response.render(JavaScriptHeaderItem.forReference(ref));
		}}
		response.render(OnDomReadyHeaderItem.forScript(javascript));
	}
}
