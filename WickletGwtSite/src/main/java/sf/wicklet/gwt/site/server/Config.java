/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sf.blacksun.util.checksum.ChecksumKind;
import sf.blacksun.util.checksum.ChecksumUtil;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.ext.application.IWickletApplication;
import sf.wicklet.ext.application.IWickletConfiguration;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.ext.application.WickletConfiguration;
import sf.wicklet.ext.application.WickletSupport;
import sf.wicklet.ext.util.IFileManager;
import sf.wicklet.ext.util.SimpleFileManager;
import sf.wicklet.gwt.site.server.db.H2WikiMetaStore;
import sf.wicklet.gwt.site.server.db.IWikiMetaStore;
import sf.wicklet.gwt.site.server.pages.p.Login;

public class Config implements IConfig {
	public static final boolean DEBUG = true;
	private static final Config singleton = new Config();
	private static Logger logger = LoggerFactory.getLogger(Config.class.getPackage().getName());
	private static final SecureRandom secureRandom;
	static {
		SecureRandom sr;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
		} catch (final NoSuchAlgorithmException e) {
			sr = new SecureRandom();
		}
		secureRandom = sr;
	}
	private static final String defaultEncryptionKey = WickletSupport.randomString(secureRandom, 16, 16);
	private static class LazyConfig {
		static IWickletConfiguration configuration;
		static {
			IWickletConfiguration ret;
			try {
				ret = Config.get().getSupport().getConfiguration(
					ServletContextInitParameter.CONFIG_PATH, DEF_CONFIG_PATH);
			} catch (final IOException e) {
				Config.get().error("ERROR: Reading configuration file", e);
				ret = new WickletConfiguration();
			}
			configuration = ret;
		}
	}

	public static byte[] salte(final byte[] salt, final byte[] value) {
		final int vlen = value.length;
		final int slen = salt.length;
		final int len = Math.max(vlen, slen);
		final byte[] ret = new byte[len];
		for (int i = 0, j = ((vlen * 31) % slen), k = 0; k < len; ++i, ++j, ++k) {
			if (j >= slen) {
				j = 0;
			}
			if (i >= vlen) {
				i = 0;
			}
			ret[k] = (byte)(value[i] ^ salt[j]);
		}
		return ret;
	}

	public static String hash(final byte[] salt, final String value) {
		final byte[] digest = ChecksumUtil.getDigester(ChecksumKind.SHA512).digest(
			salte(salt, value.getBytes(CHARSET)));
		return TextUtil.toLowerHex(digest).toString();
	}

	private final IFileManager wikiFileManager = new SimpleFileManager();
	private final IWikiMetaStore wikiMetaStore = new H2WikiMetaStore();

	protected Config() {
	}

	public static IConfig get() {
		return singleton;
	}

	@Override
	public Class<? extends AbstractAuthenticatedWebSession> getAuthenticatedWebSessionClass() {
		return MyAuthenticatedWebSession.class;
	}

	@Override
	public Class<? extends WebPage> getSignInPageClass() {
		return Login.class;
	}

	@Override
	public boolean isDebug() {
		return DEBUG;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public IWickletSupport getSupport() {
		return ((IWickletApplication)Application.get()).getWickletSupport();
	}

	@Override
	public IWickletConfiguration getConfiguration() {
		return LazyConfig.configuration;
	}

	@Override
	public IFileManager getWikiFileManager() {
		return wikiFileManager;
	}

	@Override
	public IWikiMetaStore getWikiMetaStore() {
		return wikiMetaStore;
	}

	@Override
	public String getDefaultEncryptionKey() {
		return defaultEncryptionKey;
	}

	@Override
	public Random getSecureRandom() {
		return secureRandom;
	}

	@Override
	public String randomString(final int minlen, final int maxlen) {
		return WickletSupport.randomString(secureRandom, minlen, maxlen);
	}

	@Override
	public void debug(final String msg) {
		if (DEBUG) {
			getLogger().info(msg);
	}}

	@Override
	public void debug(final String msg, final Throwable e) {
		if (DEBUG) {
			getLogger().info(msg, e);
	}}

	@Override
	public void info(final String msg) {
		getLogger().info(msg);
	}

	@Override
	public void info(final String msg, final Throwable e) {
		getLogger().info(msg, e);
	}

	@Override
	public void warn(final String msg) {
		getLogger().warn(msg);
	}

	@Override
	public void warn(final String msg, final Throwable e) {
		getLogger().warn(msg, e);
	}

	@Override
	public void error(final String msg) {
		getLogger().error(msg);
	}

	@Override
	public void error(final String msg, final Throwable e) {
		getLogger().error(msg, e);
	}
}
