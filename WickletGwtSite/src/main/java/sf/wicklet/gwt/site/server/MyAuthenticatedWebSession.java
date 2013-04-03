/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import sf.wicklet.gwt.server.UserInfo;
import sf.wicklet.gwt.site.server.db.H2UserManager;
import sf.wicklet.gwt.site.server.db.IUserManager;
import sf.wicklet.gwt.site.server.db.IUserManager.PrivateUserInfo;
import sf.wicklet.gwt.site.shared.Role;

public class MyAuthenticatedWebSession extends AuthenticatedWebSession {

	public static final String ATTR_KEY_USER = MyAuthenticatedWebSession.class.getName() + ".user";
	private static final long serialVersionUID = 1L;

	////////////////////////////////////////////////////////////////////////

	private volatile Timestamp loginTime = null;
	private volatile String username = null;
	private volatile String roles = null;
	private final IUserManager userManager = new H2UserManager();

	////////////////////////////////////////////////////////////////////////

	public static MyAuthenticatedWebSession get() {
		return (MyAuthenticatedWebSession)Session.get();
	}

	////////////////////////////////////////////////////////////////////////

	public MyAuthenticatedWebSession(final Request request) {
		super(request);
		Config.get().debug(
			"# DEBUG: session id: "
				+ getId()
				+ ": "
				+ getClass().getName()
				+ " created for request: "
				+ request.getOriginalUrl());
	}

	@Override
	public boolean authenticate(final String username, final String password) {
		final UserInfo ret = userManager.authenticate(username, password);
		if (ret == null) {
			return false;
		}
		if (Config.get().isDebug()) {
			System.out.println("### WebSession:");
			for (final String key: getAttributeNames()) {
				System.out.println("# " + key + ": " + getAttribute(key));
		}}
		this.username = username;
		loginTime = ret.getLastLogin();
		roles = ret.getRoles();
		setAttribute(ATTR_KEY_USER, username);
		return true;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	@Override
	public Roles getRoles() {
		if (isSignedIn()) {
			return new Roles(roles);
		}
		return null;
	}

	@Override
	public void signOut() {
		loginTime = null;
		username = null;
		roles = null;
		super.signOut();
	}

	public String getUsername() {
		return username;
	}

	public boolean isAdmin() {
		return hasRole(Role.admin);
	}

	public boolean hasRole(final Role role) {
		final Roles roles = getRoles();
		return roles != null && roles.hasRole(role.name());
	}

	public boolean hasAllRole(final Role...roles) {
		final Roles a = getRoles();
		if (a == null) {
			return false;
		}
		for (final Role role: roles) {
			if (!a.hasRole(role.name())) {
				return false;
		}}
		return true;
	}

	public boolean hasAnyRole(final Role...roles) {
		final Roles a = getRoles();
		if (a == null) {
			return false;
		}
		for (final Role role: roles) {
			if (a.hasRole(role.name())) {
				return true;
		}}
		return false;
	}

	public List<UserInfo> getUserInfos() {
		final List<UserInfo> ret = new ArrayList<UserInfo>();
		if (isAdmin()) {
			userManager.getUserInfos(ret);
		} else if (username != null) {
			final UserInfo info = userManager.getUserInfo(username);
			if (info != null) {
				ret.add(info);
		}}
		return ret;
	}

	public UserInfo getUserInfo() {
		if (username != null) {
			return userManager.getUserInfo(username);
		}
		return null;
	}

	/** @return error message or null. */
	public String editUser(final UserInfo info) {
		if (!isAdmin() && (username == null || !username.equals(info.getUsername()))) {
			return "ERROR: Permission denied";
		}
		return userManager.editUser(info);
	}

	public String deleteUser(final String user) {
		if (!isAdmin()) {
			return "ERROR: Permission denied";
		}
		return userManager.deleteUser(user);
	}

	public String addUser(final PrivateUserInfo info) {
		if (!isAdmin()) {
			return "ERROR: Permission denied";
		}
		return userManager.addUser(info);
	}

	////////////////////////////////////////////////////////////////////////
}
