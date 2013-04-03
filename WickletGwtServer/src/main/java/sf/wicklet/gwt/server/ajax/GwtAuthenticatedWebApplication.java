/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.ajax;

import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.protocol.http.WebApplication;
import sf.wicklet.ext.application.IWickletApplication;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.ext.application.WickletSupport;
import sf.wicklet.gwt.server.ajax.impl.GwtAjaxSupport;

/** Standard authenticated web application with IWickletApplication and IGwtAjaxSupportProvider. */
public abstract class GwtAuthenticatedWebApplication
	extends AuthenticatedWebApplication implements IWickletApplication, IGwtAjaxSupportProvider {
	public static GwtAuthenticatedWebApplication get() {
		return (GwtAuthenticatedWebApplication)WebApplication.get();
	}
	private IWickletSupport support;
	@Override
	protected void init() {
		super.init();
		support = new WickletSupport();
	}
	@Override
	public IGwtAjaxSupport getGwtAjaxSupport() {
		return new GwtAjaxSupport();
	}
	@Override
	public IWickletSupport getWickletSupport() {
		return support;
	}
}
