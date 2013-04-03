/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.behaviors.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.util.string.JavaScriptUtils;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.util.time.Duration;

/**
 * A behavior that generates an AJAX update callback at a regular interval but start as stopped.
 */
public abstract class AjaxRefreshTimerBehavior extends AbstractDefaultAjaxBehavior {
	private static final long serialVersionUID = 1L;
	private static final String TIMERS_ID = AjaxRefreshTimerBehavior.class.getSimpleName() + "-timers";
	private Duration updateInterval;
	private boolean stopped = true;
	private boolean headRendered = false;

	/**
	 * Construct.
	 *
	 * @param updateInterval
	 *            Duration between AJAX callbacks
	 */
	public AjaxRefreshTimerBehavior(final Duration updateInterval) {
		setUpdateInterval(updateInterval);
	}

	/**
	 * Sets the update interval duration. This method should only be called within the
	 * {@link #onTimer(AjaxRequestTarget)} method.
	 *
	 * @param updateInterval
	 */
	protected final void setUpdateInterval(final Duration updateInterval) {
		if (updateInterval == null || updateInterval.getMilliseconds() <= 0) {
			throw new IllegalArgumentException("Invalid update interval");
		}
		this.updateInterval = updateInterval;
	}

	/**
	 * Returns the update interval
	 *
	 * @return The update interval
	 */
	public final Duration getUpdateInterval() {
		return updateInterval;
	}

	@Override
	public void renderHead(final Component component, final IHeaderResponse response) {
		super.renderHead(component, response);
		response.render(
			JavaScriptHeaderItem.forScript(
				"if (typeof(Wicket.TimerHandles) === 'undefined') {Wicket.TimerHandles = {}}",
				TIMERS_ID));
		final WebRequest request = (WebRequest)component.getRequest();
		if (!isStopped() && (!headRendered || !request.isAjax())) {
			headRendered = true;
			response.render(OnLoadHeaderItem.forScript(getJsTimeoutCall(updateInterval)));
	}}

	/**
	 * @param updateInterval
	 *            Duration between AJAX callbacks
	 * @return JS script
	 */
	protected final String getJsTimeoutCall(final Duration updateInterval) {
		CharSequence js = getCallbackScript();
		js = JavaScriptUtils.escapeQuotes(js);
		final String timeoutHandle = getTimeoutHandle();
		// this might look strange, but it is necessary for IE not to leak :(
		return timeoutHandle + " = setTimeout('" + js + "', " + updateInterval.getMilliseconds() + ')';
	}

	/**
	 * @return the name of the handle that is used to stop any scheduled timer
	 */
	private String getTimeoutHandle() {
		return "Wicket.TimerHandles['" + getComponent().getMarkupId() + "']";
	}

	/**
	 *
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(AjaxRequestTarget)
	 */
	@Override
	protected final void respond(final AjaxRequestTarget target) {
		if (!isStopped() && isEnabled(getComponent())) {
			onTimer(target);
			target.getHeaderResponse().render(OnLoadHeaderItem.forScript(getJsTimeoutCall(updateInterval)));
	}}

	/**
	 * Listener method for the AJAX timer event.
	 *
	 * @param target
	 *            The request target
	 */
	protected abstract void onTimer(final AjaxRequestTarget target);

	/**
	 * @return {@code true} if has been stopped via {@link #stop(AjaxRequestTarget)}
	 */
	public final boolean isStopped() {
		return stopped;
	}

	/**
	 * Re-enables the timer if already stopped
	 *
	 * @param target
	 */
	public final void restart(final AjaxRequestTarget target) {
		if (isStopped()) {
			stopped = false;
			headRendered = false;
			target.add(getComponent());
	}}

	/**
	 * Stops the timer
	 */
	public final void stop(final AjaxRequestTarget target) {
		stopped = true;
		final String timeoutHandle = getTimeoutHandle();
		target.prependJavaScript("clearTimeout(" + timeoutHandle + "); delete " + timeoutHandle + ";");
	}
}
