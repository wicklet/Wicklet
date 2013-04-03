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
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import sf.wicklet.gwt.server.UserInfo;
import sf.wicklet.gwt.site.server.Config;
import sf.wicklet.gwt.site.server.db.H2Configurator.PscFactory;

public class H2UserManager implements IUserManager {

	////////////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 1L;

	private static class UserDao extends JdbcDaoSupport {
		static final String ETCDIR = "/WEB-INF/etc/user/";
		static final String DBPATH = ETCDIR + "db/user";
		static final String SCHEMA_DDL = ETCDIR + "user-schema.ddl";
		static final String INIT_DDL = ETCDIR + "user-init.ddl";
		private static final byte[] DEF_SALT = "xxxasdf09we87r-21263sdflxcmn29473456y^)^&%sefaskdfsjgfd61031lsdhfasdxxx"
			.getBytes();
		private static final PscFactory QUERY_USER = new PscFactory(
			"SELECT * FROM User WHERE name = ?", Types.VARCHAR);
		private static final PscFactory ADD_USER = new PscFactory(
			"INSERT INTO User (name, roles, pass) values (?, ?, ?)",
			Types.VARCHAR,
			Types.VARCHAR,
			Types.VARCHAR);
		private static final PscFactory DELETE_USER = new PscFactory(
			"DELETE FROM User WHERE name = ?", Types.VARCHAR);
		private static final PscFactory EDIT_USER = new PscFactory(
			"UPDATE User set roles = ? WHERE name = ?", Types.VARCHAR, Types.VARCHAR);

		////////////////////////////////////////////////////////////////////////

		static class Lazy {
			@SuppressWarnings("synthetic-access")
			static final UserDao singleton = new UserDao(createdb());
			private static EmbeddedDatabase createdb() {
				try {
					return H2Configurator.createDatabase(DBPATH, SCHEMA_DDL, INIT_DDL);
				} catch (final ClassNotFoundException e) {
					Config.get().error("ERROR: dpath=" + DBPATH, e);
					return null;
			}}
		}

		////////////////////////////////////////////////////////////////////////

		private byte[] salt;

		////////////////////////////////////////////////////////////////////////

		static UserDao get() {
			return Lazy.singleton;
		}

		private UserDao(final EmbeddedDatabase db) {
			setDataSource(db);
			H2Configurator.addApplicationListener(db, DBPATH);
			init();
		}

		private void init() {
			byte[] b = null;
			try {
				b = getJdbcTemplate().queryForObject("SELECT salt FROM Salt", String.class).getBytes();
			} catch (final DataAccessException e) {
				Config.get().error("ERROR: Reading salt from database", e);
			} finally {
				if (b == null) {
					b = DEF_SALT;
				}
				salt = b;
		}}

		byte[] getSalt() {
			return salt;
		}

		////////////////////////////////////////////////////////////////////////

		synchronized PrivateUserInfo authenticate(final String username, final String password) {
			final PrivateUserInfo ret = getprivateinfo(username);
			if (ret != null && Util.authenticate(ret, getSalt(), username, password)) {
				return ret;
			}
			return null;
		}

		synchronized PrivateUserInfo getprivateinfo(final String username) {
			return getJdbcTemplate().query(
				QUERY_USER.newPreparedStatementCreator(new Object[] { username }),
				new ResultSetExtractor<PrivateUserInfo>() {
					@Override
					public PrivateUserInfo extractData(final ResultSet rs)
						throws SQLException, DataAccessException {
						if (rs.next()) {
							return createprivateinfo(rs);
						}
						return null;
					}
				});
		}

		synchronized UserInfo getpubinfo(final String username) {
			return getJdbcTemplate().query(
				QUERY_USER.newPreparedStatementCreator(new Object[] { username }),
				new ResultSetExtractor<UserInfo>() {
					@Override
					public UserInfo extractData(final ResultSet rs)
						throws SQLException, DataAccessException {
						if (rs.next()) {
							return createpubinfo(rs);
						}
						return null;
					}
				});
		}

		synchronized void getpubinfos(final List<UserInfo> ret) {
			getJdbcTemplate().query(
				"SELECT * FROM User",
				new ResultSetExtractor<Void>() {
					@Override
					public Void extractData(final ResultSet rs)
						throws SQLException, DataAccessException {
						while (rs.next()) {
							ret.add(createpubinfo(rs));
						}
						return null;
					}
				});
		}

		synchronized String adduser(final PrivateUserInfo info) {
			final int rows = getJdbcTemplate().update(
				ADD_USER.newPreparedStatementCreator(
					new String[] { info.getUsername(), info.getRoles(), info.getPass() }));
			return (rows == 1 ? null : "Add user failed: " + info.getUsername());
		}

		synchronized String deleteuser(final String user) {
			if ("admin".equals(user)) {
				return "Admin user cannot be deleted";
			}
			final int rows = getJdbcTemplate().update(
				DELETE_USER.newPreparedStatementCreator(new String[] { user }));
			return (rows == 1 ? null : "Delete user failed: " + user);
		}

		synchronized String edituser(final UserInfo info) {
			final int rows = getJdbcTemplate().update(
				EDIT_USER.newPreparedStatementCreator(
					new String[] { info.getRoles(), info.getUsername() }));
			return (rows == 1 ? null : "Edit user failed: " + info.getUsername());
		}

		PrivateUserInfo createprivateinfo(final ResultSet rs) throws SQLException {
			return new PrivateUserInfo(
				Schema.User.Name.from(rs),
				Schema.User.Pass.from(rs),
				Schema.User.Roles.from(rs),
				Schema.User.LastPasswordChange.from(rs),
				Schema.User.LastLogin.from(rs),
				Schema.User.LoginFailureCount.from(rs));
		}
		UserInfo createpubinfo(final ResultSet rs) throws SQLException {
			return new UserInfo(
				Schema.User.Name.from(rs),
				Schema.User.Roles.from(rs),
				Schema.User.LastPasswordChange.from(rs),
				Schema.User.LastLogin.from(rs),
				Schema.User.LoginFailureCount.from(rs));
		}
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public UserInfo authenticate(final String username, final String password) {
		final PrivateUserInfo ret = UserDao.get().authenticate(username, password);
		if (ret != null) {
			return PrivateUserInfo.toPublic(ret);
		}
		return null;
	}

	@Override
	public void getUserInfos(final List<UserInfo> ret) {
		UserDao.get().getpubinfos(ret);
	}

	@Override
	public UserInfo getUserInfo(final String username) {
		return UserDao.get().getpubinfo(username);
	}

	@Override
	public String addUser(final PrivateUserInfo info) {
		return UserDao.get().adduser(info);
	}

	@Override
	public String deleteUser(final String user) {
		return UserDao.get().deleteuser(user);
	}

	@Override
	public String editUser(final UserInfo info) {
		return UserDao.get().edituser(info);
	}

	////////////////////////////////////////////////////////////////////////
}
