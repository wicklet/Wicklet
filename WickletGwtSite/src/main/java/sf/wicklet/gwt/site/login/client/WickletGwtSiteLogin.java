/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.login.client;

import sf.wicklet.gwt.client.HistoryChangeListener;
import sf.wicklet.gwt.site.clients.CConf;
import sf.wicklet.gwt.site.clients.WickletGwtSiteBuilder;
import sf.wicklet.gwt.site.clients.WickletGwtSiteUtil;
import sf.wicklet.gwt.site.shared.WID;
import sf.wicklet.gwt.site.shared.Shared.ContextPath;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.ui.UIObject;

public class WickletGwtSiteLogin implements EntryPoint {
	@Override
	public void onModuleLoad() {
		HistoryChangeListener.init();
		if (ContextPath.Logout.equals(WickletGwtSiteUtil.getPageToken())) {
			CConf.runAsync(
				new RunAsyncCallback() {
					@Override
					public void onSuccess() {
						initLogout();
					}
					@Override
					public void onFailure(final Throwable e) {
						GWT.log("ERROR: initLogout()", e);
					}
				});
		} else {
			CConf.runAsync(
				new RunAsyncCallback() {
					@Override
					public void onSuccess() {
						initLogin();
					}
					@Override
					public void onFailure(final Throwable e) {
						GWT.log("ERROR: initLogin()", e);
					}
				});
	}}

	void initLogin() {
		new WickletGwtSiteBuilder() {
			void build() {
				rootpanel(WID.bodyContent.toString()).c(
					panel(
						byId(WID.twoPane), //
						topPanel(),
						panel(byId(WID.bottomPanel))));
				UIObject.setVisible(byId(WID.topLogin).getParentElement(), false);
				UIObject.setVisible(byId(WID.searchForm), false);
			}
		}.build();
	}

	void initLogout() {
		new WickletGwtSiteBuilder() {
			void build() {
				rootpanel(WID.bodyContent.toString()).c(
					panel(
						byId(WID.twoPane), //
						topPanel(),
						panel(byId(WID.bottomPanel))));
				UIObject.setVisible(byId(WID.searchForm), false);
			}
		}.build();
	}
}
