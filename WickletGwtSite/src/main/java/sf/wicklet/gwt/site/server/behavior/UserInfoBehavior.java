/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import sf.blacksun.util.io.StringPrintWriter;
import sf.wicklet.gwt.client.util.GwtJsUtil;
import sf.wicklet.gwt.server.UserInfo;
import sf.wicklet.gwt.site.shared.WID;

public class UserInfoBehavior extends Behavior {
	private static final long serialVersionUID = 1L;
	private final UserInfo info;
	public UserInfoBehavior(final UserInfo info) {
		this.info = info;
	}
	@Override
	public void renderHead(final Component component, final IHeaderResponse response) {
		final StringPrintWriter b = new StringPrintWriter();
		b.println();
		b.println("window.WickletGwtSite.user = {");
		if (info != null) {
			b.println("name : " + GwtJsUtil.quote(info.getUsername()) + ",");
			b.println("roles: " + GwtJsUtil.quote(info.getRoles()) + ",");
		}
		b.println("};");
		response.render(new JavaScriptContentHeaderItem(b, WID.scriptUsers.toString(), null));
	}
}
