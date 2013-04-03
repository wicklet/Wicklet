/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.ui.dialog;

import sf.wicklet.gwt.client.dsl.IDProvider;
import sf.wicklet.gwt.client.dsl.gwt.GwtBuilder;
import sf.wicklet.gwt.client.dsl.gwt.GwtBuilder.GwtPanel;
import sf.wicklet.gwt.client.dsl.gwt.IGwtBuilder.IGwtPanel;
import sf.wicklet.gwt.client.util.GwtTextUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Label;

public abstract class LoginDialog extends ModalDialogBase {

	private final String idPrefix;
	private final IDProvider msgId;
	private final IDProvider userId;
	private final IDProvider passId;
	private final IDProvider cancelId;
	private final IDProvider okId;
	private Label message;

	/** Override to do something. */
	@Override
	protected abstract void onOK();

	public LoginDialog() {
		this("logindialog", "Please login");
	}

	public LoginDialog(final String idprefix, final String title) {
		super(title);
		idPrefix = idprefix;
		msgId = new IDProvider(idPrefix + "Message");
		userId = new IDProvider(idPrefix + "User");
		passId = new IDProvider(idPrefix + "Pass");
		cancelId = new IDProvider(idPrefix + "Cancel");
		okId = new IDProvider(idPrefix + "Login");
		final IGwtPanel panel = new GwtPanel();
		setWidget(panel.asWidget());
		populateContent(panel);
	}

	private void populateContent(final IGwtPanel panel) {
		new GwtBuilder() {
			@SuppressWarnings("synthetic-access")
			void build() {
				panel.c(
					label("").id(msgId).istyle("color: red; margin: 4px"),
					grid(2, 2).spacing(8).c(
						label("User name").istyle("text-align: right"),
						textbox("").id(userId).istyle("padding: 2px 0px 2px 4px").onfocus(
							new FocusHandler() {
								@Override
								public void onFocus(final FocusEvent event) {
									setMessage("");
								}
							}),
						label("Password").istyle("text-align: right"),
						password("").id(passId).istyle("padding: 2px 0px 2px 4px").onfocus(
							new FocusHandler() {
								@Override
								public void onFocus(final FocusEvent event) {
									setMessage("");
								}
							})),
					populateButtons(
						button("Cancel").id(cancelId).onclick(
							new ClickHandler() {
								@Override
								public void onClick(final ClickEvent event) {
									event.preventDefault();
									onCancel();
								}
							}),
						button("OK").id(okId).onclick(
							new ClickHandler() {
								@Override
								public void onClick(final ClickEvent event) {
									event.preventDefault();
									onOK();
								}
							})));
			}
		}.build();
	}

	public void setMessage(final String text) {
		if (!GwtTextUtil.equals(text, getMessage())) {
			message.setText(text);
	}}

	public String getMessage() {
		return message.getText();
	}
}
