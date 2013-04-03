/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.ui.dialog;

import sf.wicklet.gwt.client.dsl.gwt.GwtBuilder;
import sf.wicklet.gwt.client.dsl.gwt.GwtBuilder.GwtPanel;
import sf.wicklet.gwt.client.dsl.gwt.IGwtBuilder.IGwtPanel;

public abstract class InfoDialogBase extends ModalDialogBase {

	protected InfoDialogBase(final String title) {
		this(title, "");
	}

	/** @param msg Message text, it would be html escaped. */
	protected InfoDialogBase(final String title, final String msg) {
		super(title);
		final IGwtPanel panel = new GwtPanel();
		setWidget(panel.asWidget());
		populateContent(panel, msg);
	}

	protected void populateContent(final IGwtPanel panel, final String msg) {
		new GwtBuilder() {
			void build() {
				panel.istyle("min-width: 240px; margin: 8px;").c(label(msg), createCloseButton());
			}
		}.build();
	}
}
