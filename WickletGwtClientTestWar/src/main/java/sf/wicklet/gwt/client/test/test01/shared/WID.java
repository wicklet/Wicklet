/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.test.test01.shared;

import sf.wicklet.gwt.client.dsl.Attribute;
import sf.wicklet.gwt.client.dsl.IIDProvider;
import sf.wicklet.gwt.client.dsl.html.IAttribute;

public class WID implements IIDProvider {
	public static final WID bodyContent = new WID("bodyContent");
	public static final WID threePane = new WID("threePane");
	public static final WID loginDialog = new WID("loginDialog");
	public static final WID login = new WID("login");
	public static final WID menubar = new WID("menubar");
	// TopPanel
	public static final WID topPanel = new WID("topPanel");
	public static final WID topMenu = new WID("topMenu");
	public static final WID topHome = new WID("topHome");
	public static final WID topAdmin = new WID("topAdmin");
	public static final WID topUser = new WID("topUser");
	public static final WID topLogin = new WID("topLogin");
	public static final WID topLogout = new WID("topLogout");
	public static final WID loginPanel = new WID("loginPanel");
	public static final WID searchPanel = new WID("searchPanel");
	public static final WID searchForm = new WID("searchForm");
	public static final WID searchText = new WID("searchText");
	// Test panels
	public static final WID stackpanel = new WID("stackpanel");
	public static final WID ajaxpanel = new WID("ajaxpanel");
	public static final WID ajaxshow = new WID("ajaxshow");
	public static final WID ajaxshowstatic = new WID("ajaxshowstatic");
	public static final WID ajaxhiddenbylink = new WID("ajaxhiddenbylink");
	public static final WID ajaxhiddenbystyle = new WID("ajaxhiddenbystyle");
	public static final WID ajaxresult = new WID("ajaxresult");
	public static final WID ajaxname = new WID("ajaxname");
	public static final WID ajaxcontent = new WID("ajaxcontent");
	//
	protected IAttribute att;
	protected String ref;
	//
	public WID(final String name) {
		att = new Attribute("id", name);
		ref = "#" + name;
	}
	@Override
	public IAttribute att() {
		return att;
	}
	@Override
	public String ref() {
		return ref;
	}
	@Override
	public String toString() {
		return att.avalue();
	}
}
