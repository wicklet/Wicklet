/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.ui.dialog;

import sf.wicklet.gwt.client.dsl.gwt.GwtBuilder;
import sf.wicklet.gwt.client.dsl.gwt.IGwtBuilder.IGwtWidget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;

public abstract class ModalDialogBase extends DialogBox {

	protected ModalDialogBase(final String title) {
		setAnimationEnabled(true);
		setText(title);
		setModal(true);
		setGlassEnabled(true);
	}

	public IGwtWidget populateButtons(final IGwtWidget...buttons) {
		return new GwtBuilder() {
			IGwtTop build() {
				return top(
					hr().istyle("margin: 0px 0px 8px 0px"),
					hpanel().spacing(8).c(istyle("margin: 0px; float: right; ")).c(buttons));
			}
		}.build();
	}

	public IGwtWidget createCancelAndOKButtons() {
		return new GwtBuilder() {
			IGwtWidget build() {
				return populateButtons(
					button("Cancel").onclick(
						new ClickHandler() {
							@Override
							public void onClick(final ClickEvent event) {
								event.preventDefault();
								onCancel();
							}
						}),
					button("OK").onclick(
						new ClickHandler() {
							@Override
							public void onClick(final ClickEvent event) {
								event.preventDefault();
								onOK();
							}
						}));
			}
		}.build();
	}

	public IGwtWidget createCloseButton() {
		return new GwtBuilder() {
			IGwtWidget build() {
				return populateButtons(
					button("Close").onclick(
						new ClickHandler() {
							@Override
							public void onClick(final ClickEvent event) {
								event.preventDefault();
								onCancel();
							}
						}));
			}
		}.build();
	}

	public void showAtCenter() {
		setPopupPositionAndShow(
			new PositionCallback() {
				@Override
				public void setPosition(final int offsetWidth, final int offsetHeight) {
					setPopupPosition(
						(Window.getClientWidth() - offsetWidth) / 2,
						(Window.getClientHeight() - offsetHeight) / 3);
				}
			});
	}

	protected void onCancel() {
		hide();
	}

	protected void onOK() {
		hide();
	}
}
