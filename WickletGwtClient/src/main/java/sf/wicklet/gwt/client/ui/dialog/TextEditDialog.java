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
import sf.wicklet.gwt.client.dsl.gwt.IGwtBuilder.IGwtTextArea;

public abstract class TextEditDialog extends ModalDialogBase {

	private static final int COLS = 40;
	private static final int ROWS = 10;
	private IGwtTextArea textarea;

	public TextEditDialog(final String title, final String content) {
		this(COLS, ROWS, title, content);
	}

	public TextEditDialog(final int cols, final int rows, final String title, final String content) {
		super(title);
		final IGwtPanel panel = new GwtPanel();
		setWidget(panel.asWidget());
		populateContent(panel, cols, rows, content);
	}

	private void populateContent(final IGwtPanel panel, final int cols, final int rows, final String content) {
		textarea = new GwtBuilder() {
			IGwtTextArea build() {
				final IGwtTextArea textarea = textarea(content).setCols(cols).setRows(rows);
				panel.c(textarea, createCancelAndOKButtons());
				return textarea;
			}
		}.build();
	}

	@Override
	public String getText() {
		return textarea.getText();
	}

	/** Override to do something. */
	@Override
	protected abstract void onOK();
}
