/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.ui.pages;

import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sf.blacksun.util.text.DOCTYPE;
import sf.wicklet.dsl.html.impl.WicketBuilder;

/** A simple page that has a single content component. */
public class SimpleWickletPage extends WebPage {

	private static final long serialVersionUID = 1L;
	private static IRootMarkup MARKUP = new WicketBuilder() {
		public IRootMarkup build() {
			return serialize(
				top(
					doctype(DOCTYPE.HTML5),
					html(
						head(contenttype("text/html; charset=UTF-8")),
						body(component(WID.content).div()))));
		}
	}.build();

	protected enum WID {
		content; 
	}

	////////////////////////////////////////////////////////////////////////

	public SimpleWickletPage() {
	}

	public SimpleWickletPage(final PageParameters parameters) {
		super(parameters);
	}

	@Override
	public IRootMarkup getAssociatedMarkup() {
		return MARKUP;
	}

	////////////////////////////////////////////////////////////////////////
}
