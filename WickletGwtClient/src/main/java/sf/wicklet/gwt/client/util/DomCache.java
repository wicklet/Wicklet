/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.google.gwt.user.client.Element;

public class DomCache {

	public static final String TAGS = "#tags";

	private final String[] names;
	private final Map<String, Element> byids = new TreeMap<String, Element>();
	private final Map<String, List<Element>> byclasses = new TreeMap<String, List<Element>>();
	private final Map<String, List<Element>> bytags = new TreeMap<String, List<Element>>();
	private final Map<String, Map<String, List<Element>>> bynames
		= new TreeMap<String, Map<String, List<Element>>>();

	public static Element byId(final Element top, final String id) {
		for (com.google.gwt.dom.client.Element e = top; e != null; e = e.getNextSiblingElement()) {
			final String eid = e.getId();
			if (id.equals(eid)) {
				return e.cast();
		}}
		return null;
	}

	public static List<Element> byTag(final Element top, final String tag) {
		final List<Element> ret = new ArrayList<Element>();
		for (com.google.gwt.dom.client.Element e = top; e != null; e = e.getNextSiblingElement()) {
			if (tag.equals(e.getTagName())) {
				ret.add((Element)e.cast());
		}}
		return ret;
	}

	/**
	 * Create attribute value to elements mappings.
	 * @param names Name of attributes to be indexed.
	 * A special token #tags would create a tag to elements mapping. CSS class attribute value is split
	 * and create the individual CSS class value to element mapping.
	 */
	public DomCache(final Element top, final String...names) {
		this.names = names;
		init(top);
	}

	private void init(final com.google.gwt.dom.client.Element top) {
		for (com.google.gwt.dom.client.Element e = top; e != null; e = e.getNextSiblingElement()) {
			final Element elm = e.cast();
			for (final String name: names) {
				if ("id".equals(name)) {
					final String id = elm.getId();
					if (!GwtTextUtil.isEmpty(id)) {
						byids.put(id, elm);
				}} else if ("class".equals(name)) {
					final String css = elm.getClassName();
					if (!GwtTextUtil.isEmpty(css)) {
						for (final String s: GwtTextUtil.tokenize(css)) {
							GwtStructUtil.addToList(byclasses, s, elm);
				}}} else if (TAGS.equals(name)) {
					GwtStructUtil.addToList(bytags, elm.getTagName(), elm);
				} else {
					final String value = elm.getAttribute(name);
					if (value != null) {
						Map<String, List<Element>> m = bynames.get(name);
						if (m == null) {
							bynames.put(name, m = new TreeMap<String, List<Element>>());
						}
						List<Element> a = m.get(value);
						if (a == null) {
							m.put(value, a = new ArrayList<Element>());
						}
						a.add(elm);
			}}}
			init(e.getFirstChildElement());
	}}

	public Collection<String> byIdKeys() {
		return byids.keySet();
	}

	public Collection<String> byClassKeys() {
		return byclasses.keySet();
	}

	public Collection<String> byTagKeys() {
		return bytags.keySet();
	}

	public Element byId(final Object id) {
		if (id == null) {
			return null;
		}
		return byids.get(id.toString());
	}

	public List<Element> byClass(final Object name) {
		if (name == null) {
			return null;
		}
		return byclasses.get(name.toString());
	}

	public List<Element> byTag(final Object tag) {
		if (tag == null) {
			return null;
		}
		return bytags.get(tag.toString().toUpperCase());
	}

	public List<Element> by(final String name, final Object value) {
		final Map<String, List<Element>> m = bynames.get(name);
		if (m == null) {
			return null;
		}
		return m.get(value.toString());
	}
}
