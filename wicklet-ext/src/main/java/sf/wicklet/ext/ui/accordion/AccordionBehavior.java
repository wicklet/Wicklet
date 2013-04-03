/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.ui.accordion;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * A simple accordion that hide/show the content when its header is clicked.
 * If CSS class for header is specified, any decendent of the accordion element with the given class is
 * considered as a header. Otherwise the first and every other child is consider as a header.
 * The element that follows the header is the content.
 */
public class AccordionBehavior extends Behavior {

	private static final long serialVersionUID = 1L;
	private final String id;
	private final String header;
	private final ResourceReference[] refs;
	public AccordionBehavior(final Object id, final ResourceReference...refs) {
		this.id = id.toString();
		this.refs = refs;
		header = null;
	}
	public AccordionBehavior(final Object id, final Object headerclass, final ResourceReference...refs) {
		this.id = id.toString();
		header = headerclass.toString();
		this.refs = refs;
	}
	@Override
	public void renderHead(final Component component, final IHeaderResponse response) {
		if (refs != null && refs.length > 0) {
			for (final ResourceReference ref: refs) {
				response.render(JavaScriptHeaderItem.forReference(ref));
		}}
		response.render(
			OnDomReadyHeaderItem.forScript(
				String.format(
					"$('#%s %s').click(function() { $(this).next().toggle(); })",
					id,
					(header == null ? " > *:nth-child(odd)" : " ." + header))));
	}
}
