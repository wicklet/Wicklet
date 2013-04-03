/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.util;

import com.google.gwt.user.client.DOM;

public class GwtDomUtil {

	public static com.google.gwt.user.client.Element findAndDetach(final Object id) {
		final com.google.gwt.user.client.Element e = DOM.getElementById(id.toString());
		if (e != null) {
			e.removeFromParent();
		}
		return e;
	}

	public static void removeChildren(final com.google.gwt.dom.client.Element e) {
		if (e == null) {
			return;
		}
		for (com.google.gwt.dom.client.Node c; (c = e.getFirstChild()) != null;) {
			e.removeChild(c);
	}}

	public static void clear(final com.google.gwt.dom.client.Element e) {
		if (e == null) {
			return;
		}
		e.setInnerHTML("");
	}

	public static void replace(
		final com.google.gwt.dom.client.Element oelm, final com.google.gwt.dom.client.Element nelm) {
		final com.google.gwt.dom.client.Node parent = oelm.getParentNode();
		final com.google.gwt.dom.client.Node mark = oelm.getNextSibling();
		parent.removeChild(oelm);
		parent.insertBefore(nelm, mark);
	}

	public static boolean hasElementWithAttributeValue(final String tag, final String name, final String value) {
		return hasElementWithAttributeValue(com.google.gwt.dom.client.Document.get(), tag, name, value);
	}

	public static boolean hasElementWithAttributeValue(
		final com.google.gwt.dom.client.Document d, final String tag, final String name, final String value) {
		final com.google.gwt.dom.client.NodeList<com.google.gwt.dom.client.Element> a
			= d.getElementsByTagName(tag);
		for (int i = 0, len = a.getLength(); i < len; ++i) {
			final com.google.gwt.dom.client.Element elm = a.getItem(i);
			if (GwtTextUtil.equals(value, elm.getAttribute(name))) {
				return true;
		}}
		return false;
	}
}
