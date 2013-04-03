/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.clients;

import sf.wicklet.gwt.client.util.GwtUrlUtil;
import sf.wicklet.gwt.site.shared.Shared.ContextPath;
import com.google.gwt.core.client.GWT;

public class WickletGwtSiteUtil {

	public static String getServiceUrl() {
		return getContextUrl(GwtUrlUtil.getProtocolHostPort(GWT.getHostPageBaseURL()), ContextPath.Service);
	}

	public static String getContextUrl(final String path) {
		return getContextUrl(GwtUrlUtil.getProtocolHostPort(GWT.getHostPageBaseURL()), path);
	}

	public static native String getContextUrl(String protocolhostport, String path)
	/*-{
	    return protocolhostport + $wnd.WickletGwtSite.contextPath + path;
	}-*/
	;

	public static native String getPageToken()
	/*-{
	return $wnd.WickletGwtSite.pageToken;
	}-*/
	;

	public static native String getUsername()
	/*-{
	return $wnd.WickletGwtSite.user ?  $wnd.WickletGwtSite.user.name : null;
	}-*/
	;

	public static native String getRoles()
	/*-{
	return $wnd.WickletGwtSite.user ? $wnd.WickletGwtSite.user.roles : null;
	}-*/
	;

	public static boolean hasRole(final String role) {
		final String roles = getRoles();
		if (roles != null) {
			final String[] a = roles.split("[ \\t\\r\\n,]");
			for (final String s: a) {
				if (role.equals(s)) {
					return true;
		}}}
		return false;
	}
}
