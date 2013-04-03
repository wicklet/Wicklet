/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.behaviors.border;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

/**
 * This is much like BorderBehavior but skip all the markup crap.
 * As BorderBehavior, the border is re-rendered on each ajax rendering of the component
 * this behavior is attached to and create the border repeatedly. In such case,
 * use a wrapper component.
 * @see IAjaxRegionMarkupIdProvider.
 */
public class DecorBehavior extends Behavior {

	private static final long serialVersionUID = 1L;

	private CharSequence prefix;
	private CharSequence suffix;

	public DecorBehavior() {
	}

	public DecorBehavior(final CharSequence prefix, final CharSequence suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	/** A prefix string to be render before this component if this component is visible. */
	public DecorBehavior setPrefix(final CharSequence s) {
		prefix = s;
		return this;
	}

	/** A suffix string to be render after this component if this component is visible. */
	public DecorBehavior setSuffix(final CharSequence s) {
		suffix = s;
		return this;
	}

	@Override
	public void beforeRender(final Component component) {
		if (prefix != null && component.isVisible()) {
			component.getResponse().write(prefix);
		}
		super.beforeRender(component);
	}

	@Override
	public void afterRender(final Component component) {
		super.afterRender(component);
		if (suffix != null && component.isVisible()) {
			component.getResponse().write(suffix);
	}}
}
