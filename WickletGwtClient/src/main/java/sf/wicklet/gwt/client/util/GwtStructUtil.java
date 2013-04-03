/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Some static utilities for data structure manipulation.
 */
public class GwtStructUtil {

	////////////////////////////////////////////////////////////////////////

	public static <K, V> boolean addToList(final Map<K, List<V>> map, final K key, final V value) {
		List<V> a = map.get(key);
		if (a == null) {
			map.put(key, a = new ArrayList<V>(4));
		}
		return a.add(value);
	}

	////////////////////////////////////////////////////////////////////////
}
