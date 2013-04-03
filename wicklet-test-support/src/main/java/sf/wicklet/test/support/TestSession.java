/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.test.support;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import sf.blacksun.util.text.TextUtil;

public class TestSession extends AuthenticatedWebSession {
	private static final long serialVersionUID = 1L;
	private String user;

	public TestSession(final Request request) {
		super(request);
	}

	@Override
	public Roles getRoles() {
		return (user != null) ? new Roles(user) : null;
	}

	@Override
	public boolean authenticate(final String username, final String password) {
		if (TextUtil.equals(username, password)) {
			user = username;
			return true;
		}
		return false;
	}
}
