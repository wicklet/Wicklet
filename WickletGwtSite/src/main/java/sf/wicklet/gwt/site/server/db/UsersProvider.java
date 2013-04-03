/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.db;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import sf.blacksun.util.struct.ListIterable;
import sf.blacksun.util.struct.ReversedComparator;
import sf.wicklet.gwt.server.UserInfo;
import sf.wicklet.gwt.server.UserInfo.Property;
import sf.wicklet.gwt.site.server.MyAuthenticatedWebSession;

public class UsersProvider implements ISortableDataProvider<UserInfo, UserInfo.Property> {
	private static final long serialVersionUID = 1L;
	private List<UserInfo> users;
	private final SingleSortState<UserInfo.Property> sortState = new SingleSortState<UserInfo.Property>();
	public UsersProvider() {
	}
	@Override
	public void detach() {
		users = null;
	}
	@Override
	public Iterator<? extends UserInfo> iterator(final long first, final long count) {
		return new ListIterable<UserInfo>(sort(getusers()), (int)first, (int)(first + count)).iterator();
	}
	@Override
	public long size() {
		final List<UserInfo> a = getusers();
		return a == null ? 0 : a.size();
	}
	@Override
	public IModel<UserInfo> model(final UserInfo info) {
		return Model.of(info);
	}
	@Override
	public ISortState<UserInfo.Property> getSortState() {
		return sortState;
	}
	private List<UserInfo> sort(final List<UserInfo> users) {
		final SortParam<Property> param = sortState.getSort();
		if (param == null) {
			return users;
		}
		final Property p = param.getProperty();
		if (p == null) {
			return users;
		}
		final SortOrder order = sortState.getPropertySortOrder(p);
		if (order == null || order == SortOrder.NONE) {
			return users;
		}
		Comparator<UserInfo> sorter = null;
		if (p == Property.user) {
			sorter = UserInfo.nameSorter;
		} else if (p == Property.roles) {
			sorter = UserInfo.rolesSorter;
		}
		if (sorter != null) {
			if (order == SortOrder.DESCENDING) {
				sorter = new ReversedComparator<UserInfo>(sorter);
			}
			Collections.sort(users, sorter);
		}
		return users;
	}
	private List<UserInfo> getusers() {
		if (users == null) {
			users = ((MyAuthenticatedWebSession)AuthenticatedWebSession.get()).getUserInfos();
		}
		return users;
	}
}
