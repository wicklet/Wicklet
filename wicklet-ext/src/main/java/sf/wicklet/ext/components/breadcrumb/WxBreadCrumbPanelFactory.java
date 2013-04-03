/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.components.breadcrumb;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.util.lang.Args;

/** A BreadCrumbFactory that provide an extra parameter to the panel constructor. */
public class WxBreadCrumbPanelFactory<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Class<? extends BreadCrumbPanel> panelClass;
	private final Class<T> dataClass;

	public WxBreadCrumbPanelFactory(final Class<? extends BreadCrumbPanel> panelclass, final Class<T> dataclass) {
		Args.notNull(panelclass, "panelClass");
		if (!BreadCrumbPanel.class.isAssignableFrom(panelclass)) {
			throw new IllegalArgumentException(
				"argument panelClass ("
					+ panelclass
					+ ") must extend class "
					+ BreadCrumbPanel.class.getName());
		}
		this.panelClass = panelclass;
		this.dataClass = dataclass;
		// Make sure there is a proper constructor
		getConstructor();
	}

	public final BreadCrumbPanel create(
		final String componentId, final IBreadCrumbModel breadCrumbModel, final T data) {
		final Constructor<? extends BreadCrumbPanel> ctor = getConstructor();
		try {
			return ctor.newInstance(componentId, breadCrumbModel, data);
		} catch (final Exception e) {
			throw new RuntimeException(e);
	}}

	private final Constructor<? extends BreadCrumbPanel> getConstructor() {
		try {
			return panelClass.getConstructor(String.class, IBreadCrumbModel.class, dataClass);
		} catch (final SecurityException e) {
			throw new RuntimeException(e);
		} catch (final NoSuchMethodException e) {
			throw new RuntimeException(e);
	}}
}
