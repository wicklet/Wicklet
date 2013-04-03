/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.template;

import org.apache.wicket.markup.IRootMarkup;
import sf.blacksun.util.text.DOCTYPE;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.dsl.html.api.INode;
import sf.wicklet.dsl.html.impl.Top;
import sf.wicklet.dsl.html.impl.WicketBuilder;

public class GwtPageTemplate extends WicketBuilder {
	private static final INode EMPTY = new Top();
	protected final String module;
	public GwtPageTemplate(final String module) {
		this.module = module;
	}
	public IRootMarkup build() {
		final String basename = TextUtil.fileName(module);
		return serialize(
			top(
				doctype(DOCTYPE.HTML5),
				html(
					head(
						headstart(),
						javascript(module + "/" + basename + ".nocache.js"),
						headend()),
					body(
						iframe(
							src("javascript:''"),
							id("__gwt_historyFrame"),
							attr("tabIndex", "-1"),
							istyle("position:absolute; width:0; height:0; border:0")),
						noscript(
							div(
								istyle(
									"width: 22em; position: absolute; left: 50%; margin-left: -11em; "
										+ "color: red; background-color: white; font-family: sans-serif; "
										+ "border: 1px solid red; padding: 4px; "),
								txt(
									"Your web browser must have JavaScript enabled in order for this application to display correctly."))),
						content()))));
	}

	/** Head content before default gwt script.*/
	protected INode headstart() {
		return EMPTY;
	}

	/** Extra head content after default gwt script. */
	protected INode headend() {
		return EMPTY;
	}

	/** Body content. */
	protected INode content() {
		return EMPTY;
	}
}
