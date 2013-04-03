/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.ext.components.datatable.IPropertyTextProvider;
import sf.wicklet.gwt.server.UserInfo.Property;

public class UserInfo implements Serializable, IPropertyTextProvider<Property> {
	public enum Property {
		user, roles, passwd 
	}
	private static final long serialVersionUID = 1L;
	private final String username;
	private final String roles;
	private final Timestamp lastPasswordChange;
	private final Timestamp lastLogin;
	private final Byte loginFailures;
	public UserInfo(final String username, final String roles) {
		this.username = username;
		this.roles = roles;
		lastPasswordChange = null;
		lastLogin = null;
		loginFailures = null;
	}
	public UserInfo(
		final String username,
		final String roles,
		final Timestamp lastpasswordchange,
		final Timestamp lastlogin,
		final Byte loginfailures) {
		this.username = username;
		this.roles = roles;
		lastPasswordChange = lastpasswordchange;
		lastLogin = lastlogin;
		loginFailures = loginfailures;
	}
	public String getUsername() {
		return username;
	}
	public String getRoles() {
		return roles;
	}
	public Timestamp getLastPasswordChange() {
		return lastPasswordChange;
	}
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	public Byte getLoginFailures() {
		return loginFailures;
	}
	@Override
	public IModel<String> getLabel(final Property property) {
		switch (property) {
		case user:
			return Model.of(getUsername());
		case roles:
			return Model.of(getRoles());
		default :
			return null;
	}}
	public static Comparator<UserInfo> nameSorter = new Comparator<UserInfo>() {
		@Override
		public int compare(final UserInfo o1, final UserInfo o2) {
			return TextUtil.compare(o1.getUsername(), o2.getUsername());
		}
	};
	public static Comparator<UserInfo> rolesSorter = new Comparator<UserInfo>() {
		@Override
		public int compare(final UserInfo o1, final UserInfo o2) {
			return TextUtil.compare(o1.getRoles(), o2.getRoles());
		}
	};
}
