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
import java.sql.Timestamp;

public class Schema {
	public static interface IColumn<T> {
		T from(ResultSet rs) throws SQLException;
	}
	public static class Column<T> implements IColumn<T> {
		private final String name;
		private final Class<T> type;
		Column(final String name, final Class<T> type) {
			this.name = name;
			this.type = type;
		}
		public String name() {
			return name;
		}
		public Class<T> type() {
			return type;
		}
		@SuppressWarnings("unchecked")
		@Override
		public T from(final ResultSet rs) throws SQLException {
			return (T)rs.getObject(name);
		}
		@Override
		public String toString() {
			return name;
		}
	}
	public interface User {
		Column<Long> Id = new Column<Long>("id", Long.class);
		Column<String> Name = new Column<String>("name", String.class);
		Column<String> Pass = new Column<String>("pass", String.class);
		Column<String> Roles = new Column<String>("roles", String.class);
		Column<Timestamp> LastPasswordChange = new Column<Timestamp>("lastPasswordChange", Timestamp.class);
		Column<Timestamp> LastLogin = new Column<Timestamp>("lastLogin", Timestamp.class);
		Column<Boolean> LastLoginOk = new Column<Boolean>("lastLoginOk", Boolean.class);
		Column<Byte> LoginFailureCount = new Column<Byte>("loginFailureCount", Byte.class);
	}
	public interface Wiki {
		Column<Long> Id = new Column<Long>("id", Long.class);
		Column<String> Owner = new Column<String>("owner", String.class);
		Column<String> Roles = new Column<String>("roles", String.class);
		Column<String> Path = new Column<String>("path", String.class);
		Column<Timestamp> LastModified = new Column<Timestamp>("lastModified", Timestamp.class);
		Column<String> LastModifiedUser = new Column<String>("lastModifiedUser", String.class);
	}
}
