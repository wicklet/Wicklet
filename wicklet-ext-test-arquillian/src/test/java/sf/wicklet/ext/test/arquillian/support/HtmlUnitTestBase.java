/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.test.arquillian.support;

import java.io.File;
import sf.blacksun.util.FileUtil;
import sf.wicklet.test.support.SeleniumTestBase;

public abstract class HtmlUnitTestBase extends SeleniumTestBase {

	////////////////////////////////////////////////////////////////////////

	protected static final File tomcatHome = new File("../opt/tomcat7");
	protected static final File tomcatWebappsDir = new File(tomcatHome, "webapps");
	protected static final File logDir = FileUtil.mkdirs(
		new File(String.format("logs/%1$tY%1$tm%1$td", System.currentTimeMillis())));

	////////////////////////////////////////////////////////////////////////
}
