/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import sf.wicklet.gwt.site.server.Config;
import sf.wicklet.gwt.site.server.db.H2Configurator.PscFactory;

public class H2WikiMetaStore implements IWikiMetaStore {

	////////////////////////////////////////////////////////////////////////

	private static class WikiDao extends JdbcDaoSupport {
		static final String DBPATH = "/WEB-INF/etc/wiki/db/wiki";
		static final String SCHEMA_DDL = "/WEB-INF/etc/wiki/wiki-schema.ddl";
		static final String INIT_DDL = "/WEB-INF/etc/wiki/wiki-init.ddl";
		private static final PscFactory QUERY_USER = new PscFactory(
			"SELECT * FROM Wiki WHERE username = ?", Types.VARCHAR);
		private static final PscFactory QUERY_OWNER = new PscFactory(
			"SELECT * FROM Wiki WHERE username = ? and path = ?", Types.VARCHAR, Types.VARCHAR);

		////////////////////////////////////////////////////////////////////////

		static class Lazy {
			@SuppressWarnings("synthetic-access")
			static final WikiDao singleton = new WikiDao(createdb());
			private static EmbeddedDatabase createdb() {
				try {
					return H2Configurator.createDatabase(DBPATH, SCHEMA_DDL, INIT_DDL);
				} catch (final ClassNotFoundException e) {
					Config.get().error("ERROR: dpath=" + DBPATH, e);
					return null;
			}}
		}

		////////////////////////////////////////////////////////////////////////

		static WikiDao get() {
			return Lazy.singleton;
		}

		private WikiDao(final EmbeddedDatabase db) {
			setDataSource(db);
			H2Configurator.addApplicationListener(db, DBPATH);
		}

		synchronized boolean isOwner(final String username, final String path) {
			return getJdbcTemplate().query(
				QUERY_OWNER.create(username, path),
				new ResultSetExtractor<Boolean>() {
					@Override
					public Boolean extractData(final ResultSet rs)
						throws SQLException, DataAccessException {
						return (rs.next());
					}
				});
		}

		public List<String> getPaths(final String username) {
			return getJdbcTemplate().query(
				QUERY_USER.create(username),
				new RowMapper<String>() {
					@Override
					public String mapRow(final ResultSet rs, final int rown) throws SQLException {
						return Schema.Wiki.Path.from(rs);
					}
				});
		}
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public boolean isOwner(final String username, final String path) {
		return WikiDao.get().isOwner(username, path);
	}

	@Override
	public List<String> getPaths(final String username) {
		return WikiDao.get().getPaths(username);
	}

	////////////////////////////////////////////////////////////////////////
}
