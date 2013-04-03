/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.site.gwt.test.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.wicket.util.file.File;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import sf.blacksun.util.struct.Pair.StringPair;

public class TestSpringJDBCH201 {

	static final boolean DEBUG = true;

	protected EmbeddedDatabase createDatabase(final File dbfile, final String dbpath) throws ClassNotFoundException {
		final EmbeddedDatabaseFactory f = new EmbeddedDatabaseFactory();
		final ResourceDatabasePopulator p = new ResourceDatabasePopulator();
		final ResourceLoader l = new DefaultResourceLoader();
		if (!dbfile.exists()) {
			final String classname = getClass().getName().replace('.', '/');
			p.addScript(l.getResource(String.format("classpath:/%s-schema.sql", classname)));
			p.addScript(l.getResource(String.format("classpath:/%s-init.sql", classname)));
		}
		f.setDatabasePopulator(p);
		f.setDatabaseConfigurer(sf.wicklet.gwt.site.server.db.H2Configurator.getInstance(dbpath));
		// Database name now a dont' care.
		//        f.setDatabaseName("db01");
		return f.getDatabase();
	}

	public static class DbSupport extends JdbcDaoSupport {
		private final EmbeddedDatabase db;
		public DbSupport(final EmbeddedDatabase db) {
			this.db = db;
			setDataSource(db);
		}
		public void shutdown() {
			if (db != null) {
				db.shutdown();
		}}
	}

	@Test
	public void test01() throws SQLException, ClassNotFoundException {
		final String dbpath1 = new File("trash/TestSpringJDBCH201/db01").getAbsolutePath();
		final File dbfile1 = new File(dbpath1 + ".h2.db");
		dbfile1.delete();
		final DbSupport dao = new DbSupport(createDatabase(dbfile1, dbpath1));
		try {
			final JdbcTemplate t = dao.getJdbcTemplate();
			final List<StringPair> ret = t.query(
				"SELECT * from User, Address where User.addressId = Address.id",
				new RowMapper<StringPair>() {
					@Override
					public StringPair mapRow(final ResultSet rs, final int rowNum)
						throws SQLException {
						return new StringPair(rs.getString("name"), rs.getString("address"));
					}
				});
			if (TestSpringJDBCH201.DEBUG) {
				for (final StringPair p: ret) {
					System.out.println(p.first() + ": " + p.second());
			}}
			Assert.assertEquals(3, ret.size());
			final Map<String, String> map = new TreeMap<String, String>();
			for (final StringPair p: ret) {
				map.put(p.first(), p.second());
			}
			Assert.assertEquals("address3", map.get("user2"));
		} finally {
			dao.shutdown();
			Assert.assertTrue(dbfile1.exists());
	}}

	@Test
	public void test02() throws SQLException, ClassNotFoundException {
		final String dbpath2 = new File("trash/TestSpringJDBCH201/db02").getAbsolutePath();
		final File dbfile2 = new File(dbpath2 + ".h2.db");
		dbfile2.delete();
		Assert.assertFalse(dbfile2.exists());
		DbSupport dao = new DbSupport(createDatabase(dbfile2, dbpath2));
		try {
			final JdbcTemplate t = dao.getJdbcTemplate();
			final List<StringPair> ret = t.query(
				"SELECT * from User, Address where User.addressId = Address.id",
				new RowMapper<StringPair>() {
					@Override
					public StringPair mapRow(final ResultSet rs, final int rowNum)
						throws SQLException {
						return new StringPair(rs.getString("name"), rs.getString("address"));
					}
				});
			Assert.assertEquals(3, ret.size());
		} finally {
			dao.shutdown();
		}
		Assert.assertTrue(dbfile2.exists());
		// Try again with existing dbfile.
		dao = new DbSupport(createDatabase(dbfile2, dbpath2));
		try {
			final JdbcTemplate t = dao.getJdbcTemplate();
			final List<StringPair> ret = t.query(
				"SELECT * from User, Address where User.addressId = Address.id",
				new RowMapper<StringPair>() {
					@Override
					public StringPair mapRow(final ResultSet rs, final int rowNum)
						throws SQLException {
						return new StringPair(rs.getString("name"), rs.getString("address"));
					}
				});
			Assert.assertEquals(3, ret.size());
		} finally {
			dao.shutdown();
	}}
}
