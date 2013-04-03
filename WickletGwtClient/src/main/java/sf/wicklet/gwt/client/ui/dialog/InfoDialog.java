/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.ui.dialog;

public class InfoDialog extends InfoDialogBase {

	/** @param msg Message text, it would be html escaped. */
	public InfoDialog(final String msg) {
		this("Info", msg);
	}

	/** @param msg Message text, it would be html escaped. */
	public InfoDialog(final String title, final String msg) {
		super(title, msg);
	}
}
