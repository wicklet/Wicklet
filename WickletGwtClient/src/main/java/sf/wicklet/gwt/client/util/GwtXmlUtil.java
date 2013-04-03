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
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

public class GwtXmlUtil {

	public static Element getFirstChildElement(final Element e) {
		if (e != null) {
			for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					return (Element)n;
		}}}
		return null;
	}

	public static List<Element> getChildElements(final Element e) {
		final List<Element> ret = new ArrayList<Element>();
		if (e != null) {
			for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					ret.add((Element)n);
		}}}
		return ret;
	}

	public static String unescTextUnder(final Node e) {
		return unescXml(getTextUnder(e));
	}

	public static String getTextUnder(final Node e) {
		final StringBuilder b = new StringBuilder();
		getTextUnder(b, e);
		return b.toString();
	}

	public static void getTextUnder(final StringBuilder b, final Node e) {
		if (e != null) {
			for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
				final short type = n.getNodeType();
				if (type == Node.CDATA_SECTION_NODE || type == Node.TEXT_NODE) {
					b.append(n.getNodeValue());
				} else if (type == Node.ELEMENT_NODE) {
					getTextUnder(b, n);
	}}}}

	/**
	 * Escape XML reserved characters in text without checking for xmlEntityRef.
	 */
	public static String escXml(final String value) {
		if (value == null) {
			return "";
		}
		final StringBuilder ret = new StringBuilder();
		for (int i = 0, len = value.length(); i < len; i++) {
			final char c = value.charAt(i);
			switch (c) {
			case '&':
				ret.append("&amp;");
				break;
			case '<':
				ret.append("&lt;");
				break;
			case '>':
				ret.append("&gt;");
				break;
			case '"':
				ret.append("&quot;");
				break;
			case '\'':
				ret.append("&#39;");
				break;
			default :
				ret.append(c);
		}}
		return ret.length() != value.length() ? ret.toString() : value;
	}

	public static String unescXml(final String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		final StringBuilder b = new StringBuilder();
		for (int i = 0, len = s.length(); i < len; ++i) {
			final char c = s.charAt(i);
			if (c == '&') {
				if (match(s, i, "&lt;")) {
					b.append('<');
					i += 3;
				} else if (match(s, i, "&gt;")) {
					b.append('>');
					i += 3;
				} else if (match(s, i, "&amp;")) {
					b.append('&');
					i += 4;
				} else if (match(s, i, "&quot;")) {
					b.append('"');
					i += 5;
				} else if (match(s, i, "&#39;")) {
					b.append('\'');
					i += 4;
				} else {
					b.append(c);
			}} else {
				b.append(c);
		}}
		return (b.length() != s.length()) ? b.toString() : s;
	}

	/**
	 * Escape input for use as string value in XML.
	 */
	public static String escXmlString(final String value) {
		if (value == null) {
			return "";
		}
		final StringBuilder ret = new StringBuilder();
		for (int i = 0, len = value.length(); i < len; i++) {
			final char c = value.charAt(i);
			switch (c) {
			case '&':
				ret.append("&amp;");
				break;
			case '<':
				ret.append("&lt;");
				break;
			case '>':
				ret.append("&gt;");
				break;
			case '"':
				ret.append("&quot;");
				break;
			case '\'':
				ret.append("&#39;");
				break;
			case '\t':
				ret.append(c);
				break;
			default :
				if (c < ' ' || c >= 0x7f) {
					ret.append("&#" + (int)c + ";");
				} else {
					ret.append(c);
		}}}
		return ret.length() != value.length() ? ret.toString() : value;
	}

	public static String unescXmlString(final String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		final StringBuilder b = new StringBuilder();
		for (int i = 0, len = s.length(); i < len; ++i) {
			final char c = s.charAt(i);
			if (c == '&') {
				if (match(s, i, "&lt;")) {
					b.append('<');
					i += 3;
				} else if (match(s, i, "&gt;")) {
					b.append('>');
					i += 3;
				} else if (match(s, i, "&amp;")) {
					b.append('&');
					i += 4;
				} else if (match(s, i, "&quot;")) {
					b.append('"');
					i += 5;
				} else if (match(s, i, "&#39;")) {
					b.append('\'');
					i += 4;
				} else if (match(s, i, "&#")) {
					int code = 0;
					int j = i + 2;
					for (; j < len; ++j) {
						final char cc = s.charAt(j);
						if (cc == ';') {
							break;
						} else if (cc >= '0' && cc <= '9') {
							code = code * 10 + (cc - '0');
					}}
					b.append((char)code);
					i = j;
				} else {
					b.append(c);
			}} else {
				b.append(c);
		}}
		return (b.length() != s.length()) ? b.toString() : s;
	}

	public static boolean match(final String s, final int start, final String needle) {
		for (int i = 0, len = needle.length(); i < len; ++i) {
			if (s.charAt(start + i) != needle.charAt(i)) {
				return false;
		}}
		return true;
	}
}
