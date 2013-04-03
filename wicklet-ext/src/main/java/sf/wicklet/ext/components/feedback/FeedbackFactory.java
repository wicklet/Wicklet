/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.components.feedback;

import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessages;

/** A simple wrapper of FeedbackMessages to facilitate creating FeedbackMessage. */
public class FeedbackFactory {

	private final FeedbackMessages feedback;
	private final Component reporter;

	public FeedbackFactory(final FeedbackMessages feedback, final Component reporter) {
		this.feedback = feedback;
		this.reporter = reporter;
	}

	public void error(final String msg) {
		feedback.add(new FeedbackMessage(reporter, msg, FeedbackMessage.ERROR));
	}

	public void warn(final String msg) {
		feedback.add(new FeedbackMessage(reporter, msg, FeedbackMessage.WARNING));
	}

	public void info(final String msg) {
		feedback.add(new FeedbackMessage(reporter, msg, FeedbackMessage.INFO));
	}
}
