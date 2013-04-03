/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server;

import java.nio.charset.Charset;
import java.util.Random;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.slf4j.Logger;
import sf.wicklet.ext.application.IWickletConfiguration;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.ext.util.IFileManager;
import sf.wicklet.gwt.site.server.db.IWikiMetaStore;

public interface IConfig {

	String LOGGED_IN_COOKIE_NAME = "LoggedIn";
	String DEF_CONFIG_PATH = "/WEB-INF/etc/configuration.json";
	Charset CHARSET = Charset.forName("ISO-8859-1");

	// Application configuration
	Class<? extends AbstractAuthenticatedWebSession> getAuthenticatedWebSessionClass();
	Class<? extends WebPage> getSignInPageClass();

	boolean isDebug();
	Logger getLogger();
	IWickletSupport getSupport();
	IWickletConfiguration getConfiguration();
	IFileManager getWikiFileManager();
	IWikiMetaStore getWikiMetaStore();
	/** A default key for symmetric encryption. */
	String getDefaultEncryptionKey();
	Random getSecureRandom();
	String randomString(int minlen, int maxlen);

	void debug(String msg);
	void debug(String msg, Throwable e);
	void info(String msg);
	void info(String msg, Throwable e);
	void warn(String msg);
	void warn(String msg, Throwable e);
	void error(String msg);
	void error(String msg, Throwable e);

	public static interface ServletContextInitParameter {
		String CONFIG_PATH = "WickletConfigurationPath";
	}
}
