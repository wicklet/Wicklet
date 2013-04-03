/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.ui.panels;

import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class SimpleFeedbackPanel extends FeedbackPanel {
	private static final long serialVersionUID = 1L;
	public SimpleFeedbackPanel(final Object id) {
		super(id.toString());
	}
	public SimpleFeedbackPanel(final Object id, final Component reporter, final String msg, final int level) {
		super(id.toString());
		add(reporter, msg, level);
	}
	public SimpleFeedbackPanel add(final Component reporter, final String msg, final int level) {
		getFeedbackMessages().add(new FeedbackMessage(reporter, msg, level));
		return this;
	}
}
