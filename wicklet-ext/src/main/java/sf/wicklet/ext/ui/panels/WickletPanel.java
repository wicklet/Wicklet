/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.ui.panels;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/** A Panel that provides its own IRootMarkup. */
public abstract class WickletPanel extends Panel implements IMarkupCacheKeyProvider {

	private static final long serialVersionUID = 1L;

	@Override
	public abstract IRootMarkup getAssociatedMarkup();

	public WickletPanel(final Object id) {
		super(id.toString());
	}

	public WickletPanel(final Object id, final IModel<?> model) {
		super(id.toString(), model);
	}

	@Override
	public String getCacheKey(final MarkupContainer container, final Class<?> containerClass) {
		throw new AssertionError("Should not reach here.");
	}
}
