/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.db;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.gwt.server.UserInfo;
import sf.wicklet.gwt.site.server.Config;
import sf.wicklet.gwt.site.shared.Role;

public interface IUserManager extends Serializable {

	/** @return roles if success, else null. */
	public abstract UserInfo authenticate(final String username, final String password);
	public abstract void getUserInfos(final List<UserInfo> ret);
	/** @return null if user not exists or access denied. */
	public abstract UserInfo getUserInfo(final String username);
	public abstract String addUser(final PrivateUserInfo info);
	public abstract String deleteUser(final String user);
	public abstract String editUser(final UserInfo info);

	public static class PrivateUserInfo extends UserInfo {
		private static final long serialVersionUID = 1L;
		public static PrivateUserInfo changeRoles(final PrivateUserInfo info, final String roles) {
			return new PrivateUserInfo(
				info.getUsername(),
				info.getPass(),
				roles,
				info.getLastPasswordChange(),
				info.getLastLogin(),
				info.getLoginFailures());
		}
		public static PrivateUserInfo changePass(final PrivateUserInfo info, final String pass) {
			return new PrivateUserInfo(
				info.getUsername(),
				pass,
				info.getRoles(),
				info.getLastPasswordChange(),
				info.getLastLogin(),
				info.getLoginFailures());
		}
		public static UserInfo toPublic(final PrivateUserInfo info) {
			return new UserInfo(
				info.getUsername(),
				info.getRoles(),
				info.getLastPasswordChange(),
				info.getLastLogin(),
				info.getLoginFailures());
		}
		private final String pass;
		public PrivateUserInfo(final String username, final String pass, final String roles) {
			super(username, roles);
			this.pass = pass;
		}
		public PrivateUserInfo(
			final String username,
			final String pass,
			final String roles,
			final Timestamp lastpasswordchange,
			final Timestamp lastlogin,
			final Byte loginfailures) {
			super(username, roles, lastpasswordchange, lastlogin, loginfailures);
			this.pass = pass;
		}
		public String getPass() {
			return pass;
		}
	}

	public static class Util {

		public static String SAFE = ",.-_+=";

		public static String isValidUserInfo(final PrivateUserInfo info) {
			if (info == null) {
				return "ERROR: User info must not be null";
			}
			final String username = info.getUsername();
			final String roles = info.getRoles();
			final String pass = info.getPass();
			if (TextUtil.isEmpty(username)) {
				return "ERROR: User name must not be empty";
			}
			if (roles == null) {
				return "ERROR: Roles must not be null";
			}
			if (!isValidUsername(username)) {
				return "ERROR: Invalid user name";
			}
			if (!TextUtil.isEmpty(pass)) {
				if (!isValidPassword(pass)) {
					return "ERROR: Invalid password";
			}}
			final Roles a = new Roles(roles);
			for (final String r: a) {
				if (Role.get(r) == null) {
					return "ERROR: Invalid role: " + r;
			}}
			return null;
		}

		public static boolean isValid(final String name) {
			for (int i = 0; i < name.length(); ++i) {
				final char c = name.charAt(i);
				if (!Character.isLetterOrDigit(c) && SAFE.indexOf(c) < 0) {
					return false;
			}}
			return true;
		}

		public static boolean isValidUsername(final String username) {
			return isValid(username);
		}

		public static boolean isValidPassword(final String password) {
			return isValid(password);
		}

		public static boolean authenticate(
			final PrivateUserInfo info, final byte[] salt, final String username, final String password) {
			// Should have been checked before here, but do an extra final check.
			if (!isValid(username) || !isValid(password) || isValidUserInfo(info) != null) {
				return false;
			}
			return (username.equals(info.getUsername())
				&& Config.hash(salt, password).equals(info.getPass()));
		}
	}
}
