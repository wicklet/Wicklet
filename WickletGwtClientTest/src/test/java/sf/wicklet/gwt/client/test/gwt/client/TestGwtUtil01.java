/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.test.gwt.client;

import org.junit.Test;
import sf.wicklet.gwt.client.util.DomCache;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.HTMLPanel;

public class TestGwtUtil01 extends GWTTestCase {

	static final boolean DEBUG = true;

	@Override
	public String getModuleName() {
		return "sf.wicklet.gwt.client.test.gwt.GwtTest01";
	}

	@Test
	public void testDomCache01() throws Exception {
		final HTMLPanel html = new HTMLPanel(
			"<!DOCTYPE html>"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>"
				+ "<meta content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\" />"
				+ "<link href=\"/assets/default.css\" type=\"text/css\" rel=\"stylesheet\" wicket:id=\"wicket_relative_path_prefix_\" />"
				+ "<script type=\"text/javascript\" language=\"javascript\" wicket:id=\"_ScriptStyle\">"
				+ "window.WickletSiteGwt = { contextPath : '',pageToken : '/p/login',};</script>"
				+ "<title>Login</title>"
				+ "<script src=\"../WickletSiteGwtLogin/WickletSiteGwtLogin.nocache.js\" type=\"text/javascript\" "
				+ "language=\"javascript\" wicket:id=\"_ScriptStyle\"></script>"
				+ "<script defer=\"defer\">WickletSiteGwtLogin.onInjectionDone('WickletSiteGwtLogin')</script>"
				+ "<link rel=\"stylesheet\" href=\"https://localhost:8443/WickletSiteGwtLogin/gwt/wicklet/wicklet.css\" />"
				+ "</head><body>"
				+ "<iframe style=\"position:absolute; width:0; height:0; border:0\" tabindex=\"-1\" id=\"__gwt_historyFrame\" src=\"javascript:''\"></iframe>"
				+ "<noscript>"
				+ "&lt;div style=\"width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; font-family: sans-serif; border: 1px solid red; padding: 4px; \"&gt;Your web browser must have JavaScript enabled in order for this application to display correctly.&lt;/div&gt;"
				+ "</noscript>"
				+ "<div id=\"bodyContent\">"
				+ "<div class=\"twoPane\" id=\"twoPane\">"
				+ "<div id=\"topPanel\"><div class=\"topMenu\"><a href=\"/\" id=\"topHome\">Home</a>"
				+ "<span class=\"gwt-InlineLabel\"> | </span><span style=\"display: none;\" aria-hidden=\"true\">"
				+ "<a class=\"gwt-Hyperlink\" href=\"#admin\" id=\"topAdmin\">Admin</a><span class=\"gwt-InlineLabel\"> "
				+ "</span></span><div class=\"loginPanel\"><span style=\"display: none;\" aria-hidden=\"true\">"
				+ "<span id=\"topUser\"></span><span class=\"gwt-InlineLabel\"> </span></span>"
				+ "<span aria-hidden=\"true\" style=\"display: none;\">"
				+ "<a href=\"https://localhost:8443/p/login\" id=\"topLogin\">Login</a><span class=\"gwt-InlineLabel\"> "
				+ "</span></span><span style=\"display: none;\" aria-hidden=\"true\">"
				+ "<a href=\"https://localhost:8443/p/logout\" id=\"topLogout\">Logout</a>"
				+ "<span class=\"gwt-InlineLabel\"> </span></span></div>"
				+ "<form target=\"FormPanel_WickletSiteGwtLogin_1\" id=\"searchForm\" class=\"searchForm\" "
				+ "style=\"display: none;\" aria-hidden=\"true\"><div>"
				+ "<input type=\"text\" class=\"gwt-TextBox searchText\" id=\"searchText\" /></div></form></div></div>"
				+ "<div id=\"bottomPanel\"><div align=\"center\" style=\"margin: 10px;\" id=\"signinPanel\" wicket:id=\"signinPanel\">"
				+ "<wicket:panel><div style=\"text-align: left\" wicket:id=\"feedback\"><wicket:panel>"
				+ "</wicket:panel></div>"
				+ "<form action=\"./login;jsessionid=7104109CD8660C48DB1DF4F3C923803C?wIcklEtpcI0-9sLf5JFHSXowArKd_Lb2Vg\" "
				+ "method=\"post\" id=\"signinForm1\" wicket:id=\"signinForm\">"
				+ "<div style=\"width:0px;height:0px;position:absolute;left:-100px;top:-100px;overflow:hidden\">"
				+ "<input type=\"hidden\" id=\"signinForm1_hf_0\" name=\"signinForm1_hf_0\" /></div><table>"
				+ "<tbody><tr><td align=\"right\">Username</td><td>"
				+ "<input type=\"text\" name=\"username\" value=\"\" wicket:id=\"username\" /></td></tr>"
				+ "<tr><td align=\"right\">Password</td><td>"
				+ "<input type=\"password\" name=\"password\" value=\"\" wicket:id=\"password\" /></td></tr>"
				+ "<tr wicket:id=\"captchaTextTr\"><td align=\"right\">Enter captcha text</td><td>"
				+ "<input type=\"text\" name=\"captchaTextTr:captchaText\" value=\"\" wicket:id=\"captchaText\" />"
				+ "</td></tr><tr wicket:id=\"captchaTr\"><td align=\"right\" colspan=\"2\">"
				+ "<img src=\"./login;jsessionid=7104109CD8660C48DB1DF4F3C923803C?wIcklEtpcI0-QmBdmxDvoLhQA7Z03r-3xQ\""
				+ " id=\"captcha2\" wicket:id=\"captcha\" /></td></tr><tr><td></td><td align=\"right\">"
				+ "<input type=\"submit\" value=\"Sign In\" name=\"submit\" />"
				+ "<input type=\"reset\" value=\"Reset\" />"
				+ "</td></tr>"
				+ "</tbody></table>"
				+ "</form></wicket:panel></div></div></div></div>"
				+ "<iframe src=\"javascript:''\" id=\"WickletSiteGwtLogin\" "
				+ "style=\"position: absolute; width: 0px; height: 0px; border: medium none;\" tabindex=\"-1\"></iframe>"
				+ "<iframe style=\"position:absolute;width:0;height:0;border:0\" name=\"FormPanel_WickletSiteGwtLogin_1\" "
				+ "src=\"javascript:''\"></iframe></body></html>\");");
		final DomCache cache = new DomCache(html.getElement(), "id", "class", "#tags");
		assertEquals(17, cache.byIdKeys().size());
		assertEquals(8, cache.byClassKeys().size());
		assertEquals(17, cache.byTagKeys().size());
		assertNotNull(cache.byId("twoPane"));
		assertEquals(5, cache.byClass("gwt-InlineLabel").size());
		assertEquals(4, cache.byTag("a").size());
	}
}
