/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.components.link;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class SimpleLink extends AbstractLink {

	private static final long serialVersionUID = 1L;

	private String href;

	public SimpleLink(final Object id) {
		this(id.toString());
	}

	public SimpleLink(final Object id, final IModel<?> model) {
		this(id.toString(), model);
	}

	public SimpleLink(final String id) {
		super(id);
	}

	public SimpleLink(final String id, final IModel<?> model) {
		super(id, model);
	}

	public SimpleLink body(final String text) {
		setBody(Model.of(text));
		return this;
	}

	public SimpleLink href(final String href) {
		this.href = href;
		return this;
	}

	@Override
	protected void onComponentTag(final ComponentTag tag) {
		super.onComponentTag(tag);
		if (href != null) {
			tag.getAttributes().put("href", href);
	}}
}
