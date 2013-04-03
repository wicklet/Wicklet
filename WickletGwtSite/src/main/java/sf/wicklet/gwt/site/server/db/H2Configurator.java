/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.db;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.wicket.Application;
import org.apache.wicket.IApplicationListener;
import org.h2.Driver;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseConfigurer;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.ClassUtils;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.gwt.site.server.Config;

/** A custom h2database configuration that allow specifying the db location. */
public class H2Configurator implements EmbeddedDatabaseConfigurer {

	/** Simple wrapper that use varargs for ease of use. */
	public static class PscFactory extends PreparedStatementCreatorFactory {
		public PscFactory(final String sql, final int...types) {
			super(sql, types);
		}
		public PreparedStatementCreator create(final Object...params) {
			return newPreparedStatementCreator(params);
		}
	}

	////////////////////////////////////////////////////////////////////////

	private final Class<? extends Driver> driverClass;
	private final String dbPath;

	////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public static synchronized H2Configurator getInstance(final String dbpath) throws ClassNotFoundException {
		final H2Configurator ret = new H2Configurator(
			(Class<? extends Driver>)ClassUtils.forName(
				"org.h2.Driver", H2Configurator.class.getClassLoader()),
			dbpath);
		return ret;
	}

	/** @param dbpath Absolute path relative to web application context root. */
	public static EmbeddedDatabase createDatabase(final String dbpath, final String...initscripts)
		throws ClassNotFoundException {
		final EmbeddedDatabaseFactory f = new EmbeddedDatabaseFactory();
		final ResourceDatabasePopulator p = new ResourceDatabasePopulator();
		final ResourceLoader l = new DefaultResourceLoader();
		final IWickletSupport support = Config.get().getSupport();
		final File dbfile = support.getContextFile(dbpath + ".h2.db");
		if (!dbfile.exists()) {
			for (final String s: initscripts) {
				p.addScript(l.getResource("file:" + support.getContextFile(s).getAbsolutePath()));
		}}
		f.setDatabasePopulator(p);
		f.setDatabaseConfigurer(H2Configurator.getInstance(support.getContextFile(dbpath).getAbsolutePath()));
		// Database name is actually a don't care.
		f.setDatabaseName(TextUtil.fileName(dbpath));
		return f.getDatabase();
	}

	public static void addApplicationListener(final EmbeddedDatabase db, final String path) {
		Application.get().getApplicationListeners().add(
			new IApplicationListener() {
				@Override
				public void onBeforeDestroyed(final Application application) {
					if (Config.DEBUG) {
						System.out.println("# Shutting down database: " + path);
					}
					db.shutdown();
				}
				@Override
				public void onAfterInitialized(final Application application) {
				}
			});
	}

	////////////////////////////////////////////////////////////////////////

	private H2Configurator(final Class<? extends Driver> driverclass, final String dbpath) {
		driverClass = driverclass;
		dbPath = dbpath;
	}

	@Override
	public void configureConnectionProperties(final ConnectionProperties properties, final String dbname) {
		properties.setDriverClass(driverClass);
		properties.setUrl(String.format("jdbc:h2:%s;AUTOCOMMIT=ON", dbPath));
		properties.setUsername("sa");
		properties.setPassword("");
	}

	@Override
	public void shutdown(final DataSource datasource, final String dbname) {
		if (Config.DEBUG) {
			System.out.println("# Shutdown database: " + dbname);
		}
		try {
			final Connection connection = datasource.getConnection();
			final Statement stmt = connection.createStatement();
			stmt.execute("SHUTDOWN");
		} catch (final SQLException ex) {
			Config.get().getLogger().warn("Could not shutdown embedded database", ex);
	}}

	////////////////////////////////////////////////////////////////////////
}
