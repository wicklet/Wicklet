/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import sf.blacksun.util.text.TextUtil;

public class AbstractPropertyProvider implements IPropertyProvider, Serializable {

	private static final long serialVersionUID = 1L;
	private final Map<String, IProperty<?>> names = new TreeMap<String, IProperty<?>>();
	private final Map<IProperty<?>, Object> values = new HashMap<IProperty<?>, Object>();

	public AbstractPropertyProvider(final IProperty<?>...a) {
		for (final IProperty<?> p: a) {
			names.put(p.name(), p);
			values.put(p, p.def());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getValue(final IProperty<T> property) {
		if (values.containsKey(property)) {
			return (T)values.get(property);
		}
		return null;
	}

	@Override
	public boolean setValue(final IProperty<?> property, final Object value) {
		if (values.containsKey(property) && property.type().isAssignableFrom(value.getClass())) {
			values.put(property, value);
			return true;
		}
		values.put(property, property.def());
		return false;
	}

	@Override
	public IProperty<?> propertyOf(final String name) {
		return names.get(name);
	}

	@Override
	public IProperty<?> propertyOf(final Object name) {
		return names.get(name.toString());
	}

	@Override
	public Iterator<IProperty<?>> iterator() {
		return new ArrayList<IProperty<?>>(values.keySet()).iterator();
	}

	public static class Prop<T> implements IProperty<T> {

		private static final long serialVersionUID = 1L;
		private final Class<T> type;
		private final String name;
		private final T def;

		public Prop(final Class<T> type, final Object name) {
			this(type, name, null);
		}

		public Prop(final Class<T> type, final Object name, final T def) {
			this.name = name.toString();
			this.type = type;
			this.def = def;
		}

		@Override
		public String name() {
			return name;
		}

		@Override
		public Class<T> type() {
			return type;
		}

		@Override
		public T def() {
			return def;
		}

		@Override
		public int hashCode() {
			return name == null ? 0 : name.hashCode();
		}

		@Override
		public boolean equals(final Object a) {
			if (!(a instanceof IProperty)) {
				return false;
			}
			@SuppressWarnings("unchecked")
			final IProperty<T> p = (IProperty<T>)a;
			return TextUtil.equals(name, p.name());
		}
	}
}
