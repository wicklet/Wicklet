/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.ui.panels;

import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.model.IModel;
import sf.wicklet.dsl.html.impl.WicketBuilder;

/** A simple WickletPanel that wrap a single component as content. */
public class SimpleWickletPanel extends WickletPanel {

	////////////////////////////////////////////////////////////////////////

	protected static final IRootMarkup MARKUP = new WicketBuilder() {
		public IRootMarkup build() {
			return serialize(wicket("panel", component(WID.content).div()));
		}
	}.build();
	private static final long serialVersionUID = 1L;

	////////////////////////////////////////////////////////////////////////

	protected enum WID {
		content; 
	}

	////////////////////////////////////////////////////////////////////////

	public SimpleWickletPanel(final Object wid) {
		super(wid.toString());
	}

	public SimpleWickletPanel(final Object wid, final IModel<?> model) {
		super(wid.toString(), model);
	}

	@Override
	public IRootMarkup getAssociatedMarkup() {
		return MARKUP;
	}

	////////////////////////////////////////////////////////////////////////
}
