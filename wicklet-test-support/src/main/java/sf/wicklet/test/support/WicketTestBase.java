/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.test.support;

import org.apache.wicket.ThreadContext;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;

public class WicketTestBase {

	////////////////////////////////////////////////////////////////////////

	protected WicketTester tester;

	////////////////////////////////////////////////////////////////////////

	@Before
	public void setup() {
		// make sure no leaked threadlocals are present
		ThreadContext.detach();
		final WebApplication application = new MockApplication();
		tester = new WicketTester(application);
	}

	@After
	public void teardown() {
		tester.destroy();
	}

	////////////////////////////////////////////////////////////////////////
}
