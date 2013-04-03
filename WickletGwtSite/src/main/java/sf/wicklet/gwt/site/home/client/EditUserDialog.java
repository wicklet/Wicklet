/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.home.client;

import sf.wicklet.gwt.client.ajax.WickletAjax;
import sf.wicklet.gwt.client.ajax.WickletAjax.XmlWickletAjaxCallback;
import sf.wicklet.gwt.client.dsl.gwt.GwtBuilder;
import sf.wicklet.gwt.client.dsl.gwt.GwtBuilder.GwtPanel;
import sf.wicklet.gwt.client.dsl.gwt.IGwtBuilder.IGwtPanel;
import sf.wicklet.gwt.client.dsl.gwt.IGwtBuilder.IGwtTextBox;
import sf.wicklet.gwt.client.ui.dialog.ModalDialogBase;
import sf.wicklet.gwt.client.util.GwtXmlUtil;
import sf.wicklet.gwt.shared.GwtHttp;
import sf.wicklet.gwt.site.clients.CConf;
import sf.wicklet.gwt.site.clients.WickletGwtSiteUtil;
import sf.wicklet.gwt.site.shared.Shared;
import sf.wicklet.gwt.site.shared.WID;
import sf.wicklet.gwt.site.shared.Shared.ContextPath;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.DOM;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

public class EditUserDialog extends ModalDialogBase {

	Runnable callback;
	IGwtTextBox textboxUsername;
	IGwtTextBox textboxRoles;

	public EditUserDialog(final Element user, final Runnable callback) {
		super("Edit User");
		this.callback = callback;
		final IGwtPanel panel = new GwtPanel();
		setWidget(panel.asWidget());
		populateContent(panel, user);
	}

	private void populateContent(final IGwtPanel panel, final Element user) {
		new GwtBuilder() {
			void build() {
				final String username = GwtXmlUtil.unescXml(user.getAttribute("username"));
				final String roles = GwtXmlUtil.unescXml(user.getAttribute("roles"));
				panel.c(
					grid(
						2,
						2, //
						label("username"),
						(textboxUsername = textbox(username)).name("username"),
						label("roles"),
						(textboxRoles = textbox(roles)).name("roles")), //
					panel(id(WID.editUserDialogFeedback)),
					createCancelAndOKButtons());
			}
		}.build();
	}

	@Override
	protected void onOK() {
		WickletAjax.ajax(
			WickletGwtSiteUtil.getContextUrl(ContextPath.AdminService),
			"action="
				+ Shared.Admin.EditUser
				+ "&username="
				+ URL.encodeQueryString(textboxUsername.getText())
				+ "&roles="
				+ URL.encode(textboxRoles.getText()),
			new XmlWickletAjaxCallback() {
				@Override
				public void onResponseReceived(final Request request, final Response response) {
					if (!GwtHttp.Status.isOK(response.getStatusCode())) {
						onError(request, new Exception(response.getStatusText()));
						return;
					}
					final Document doc = XMLParser.parse(response.getText());
					final Element top = doc.getDocumentElement();
					if (top == null || !"success".equals(top.getTagName())) {
						onError(request, new Exception(GwtXmlUtil.unescTextUnder(top)));
						return;
					}
					callback.run();
					hide();
				}
				@Override
				public void onError(final Request request, final Throwable e) {
					CConf.debug(
						"ERROR: request=" + request + (e == null ? "" : ": " + e.getMessage()));
					DOM.getElementById(WID.editUserDialogFeedback.toString()).setInnerText(
						e.getMessage());
					hide();
				}
			});
	}
}
