/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.shared;

import sf.wicklet.gwt.client.dsl.IDProvider;

public class WID extends IDProvider {
	public static final WID bodyContent = new WID("bodyContent");
	public static final WID threePane = new WID("threePane");
	public static final WID twoPane = new WID("twoPane");
	public static final WID topPanel = new WID("topPanel");
	public static final WID leftPanel = new WID("leftPanel");
	public static final WID rightPanel = new WID("rightPanel");
	// TopPanel
	public static final WID topMenu = new WID("topMenu");
	public static final WID topLinks = new WID("topLinks");
	public static final WID topHome = new WID("topHome");
	public static final WID topAdmin = new WID("topAdmin");
	public static final WID topUser = new WID("topUser");
	public static final WID topLogin = new WID("topLogin");
	public static final WID topLogout = new WID("topLogout");
	public static final WID topLoginPanel = new WID("topLoginPanel");
	public static final WID searchPanel = new WID("searchPanel");
	public static final WID searchForm = new WID("searchForm");
	public static final WID searchText = new WID("searchText");
	//
	public static final WID topDialog = new WID("topDialog");
	public static final WID loginDialogUser = new WID("loginDialogUser");
	public static final WID loginDialogPass = new WID("loginDialogPass");
	//
	public static final WID leftTopPanel = new WID("leftTopPanel");
	public static final WID indexPanel = new WID("indexPanel");
	public static final WID newsPanel = new WID("newsPanel");
	public static final WID wikiPanel = new WID("wikiPanel");
	public static final WID forumPanel = new WID("forumPanel");
	public static final WID bugsPanel = new WID("bugsPanel");
	public static final WID adminPanel = new WID("adminPanel");
	public static final WID userPanel = new WID("userPanel");
	public static final WID newsRepeater = new WID("newsRepeater");
	public static final WID wikiRepeater = new WID("wikiRepeater");
	public static final WID forumRepeater = new WID("forumRepeater");
	public static final WID bugsRepeater = new WID("bugsRepeater");
	public static final WID leftAccordion = new WID("leftAccordion");
	public static final WID rightPanelContent = new WID("rightPanelContent");
	public static final WID usersTable = new WID("usersTable");
	public static final WID bottomPanel = new WID("bottomPanel");
	public static final WID signinPanel = new WID("signinPanel");
	// Admin page
	public static final WID adminLeftPanel = new WID("adminLeftPanel");
	public static final WID adminRightPanel = new WID("adminRightPanel");
	// User page
	public static final WID userLeftPanel = new WID("userLeftPanel");
	public static final WID userRightPanel = new WID("userRightPanel");
	// Logout page
	public static final WID logoutPanel = new WID("logoutPanel");
	//
	public static final WID toolbarPanel = new WID("toolbarPanel");
	public static final WID ajaxHtml = new WID("ajaxHtml");
	// comment
	public static final WID commentPanel = new WID("commentPanel");
	public static final WID commentForm = new WID("commentForm");
	public static final WID commentSubject = new WID("commentSubject");
	public static final WID commentPath = new WID("commentPath");
	public static final WID commentText = new WID("commentText");
	public static final WID commentCaptchaImage = new WID("commentCaptchaImage");
	public static final WID commentCaptchaText = new WID("commentCaptchaText");
	public static final WID commentFeedback = new WID("commentFeedback");
	public static final WID commentSubmit = new WID("commentSubmit");
	//
	public static final WID rightPanelFeedback = new WID("rightPanelFeedback");
	public static final WID editUserDialogFeedback = new WID("editUserDialogFeedback");
	// javascript
	public static final WID scriptUsers = new WID("scriptUsers");
	//
	public WID(final String name) {
		super(name);
	}
}
