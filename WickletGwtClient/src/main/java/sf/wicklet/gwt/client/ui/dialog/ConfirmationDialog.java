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

public abstract class ConfirmationDialog extends ModalDialogBase {

    public ConfirmationDialog(final String content) {
        this("Confirm", content);
    }

    public ConfirmationDialog(final String title, final String content) {
        super(title);
        final IGwtPanel panel = new GwtPanel();
        setWidget(panel.asWidget());
        populateContent(panel, content);
    }

    private void populateContent(final IGwtPanel panel, final String content) {
        new GwtBuilder() {
            void build() {
                panel.c(
                    istyle("margin: 10px"), //
                    label(content),
                    createCancelAndOKButtons());
            }
        }.build();
    }

    @Override
    protected abstract void onOK();
}
