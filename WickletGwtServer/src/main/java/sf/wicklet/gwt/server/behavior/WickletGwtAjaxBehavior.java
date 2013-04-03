/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.Strings;
import sf.blacksun.util.text.TextUtil;

public abstract class WickletGwtAjaxBehavior extends AbstractAjaxBehavior {
	private static final long serialVersionUID = 1L;
	@Override
	public void renderHead(final Component component, final IHeaderResponse response) {
		final RequestCycle requestCycle = component.getRequestCycle();
		final String baseUrl = requestCycle.getUrlRenderer().getBaseUrl().toString();
		final CharSequence ajaxBaseUrl = Strings.escapeMarkup(baseUrl);
		response.render(
			JavaScriptHeaderItem.forScript(
				TextUtil.joinln(
					"window.Wicket = {", //
					"  Ajax : {",
					"    baseUrl : '" + ajaxBaseUrl + "',",
					"}};"),
				"wicket-ajax-base-url"));
		response.render(
			JavaScriptHeaderItem.forScript(
				TextUtil.joinln(
					"window.Wicklet = {",
					"  GwtAjaxBehavior : {", //
					"    url : '" + Strings.escapeMarkup(getCallbackUrl()) + "',",
					"}};"),
				"WickletGwtAjaxBehavior"));
	}
}
