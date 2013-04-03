/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.application;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;

public interface IWickletSupport {

	ServletContext getServletContext();
	String getContextPath();
	/**
	 * Shortcut for getContextPath()+path.
	 * @param path Absolute path relative to context root, eg. /wiki/home.html.
	 * @return getContextPath() + path;
	 */
	String getContextPath(String path);
	/**
	 * @param path Absolute path relative to context, eg. /WEB-INF/web.xml.
	 * @return Cannonical local file referenced by the given context relative path,
	 * null if result path is not under context root.
	 */
	File getContextFile(String path);
	IWickletConfiguration getConfiguration(String contextparamkey, String defpath) throws IOException;
	String getHttpsUrl(String path);
	int getHttpPort();
	int getHttpsPort();
}
