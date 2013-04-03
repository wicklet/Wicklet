/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.dsl;

public enum DOCTYPE {
	HTML40(
		"HTML",
		"PUBLIC",
		"\"-//W3C//DTD HTML 4.0 Transitional//EN\"",
		"\"http://www.w3.org/TR/REC-html40/loose.dtd\""),
	HTML401("HTML", "PUBLIC", "\"-//W3C//DTD HTML 4.01//EN\"", "\"http://www.w3.org/TR/html4/strict.dtd\""),
	HTML401Transitional(
		"HTML",
		"PUBLIC",
		"\"-//W3C//DTD HTML 4.01 Transitional//EN\"",
		"\"http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd\""),
	HTML401Frameset(
		"HTML",
		"PUBLIC",
		"\"-//W3C//DTD HTML 4.01 Frameset//EN\"",
		"\"http://www.w3.org/TR/1999/REC-html401-19991224/frameset.dtd\""),
	XHTML10(
		"html",
		"PUBLIC",
		"\"-//W3C//DTD XHTML 1.0 Strict//EN\"",
		"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\""),
	XHTML10Transitional(
		"html",
		"PUBLIC",
		"\"-//W3C//DTD XHTML 1.0 Transitional//EN\"",
		"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\""),
	XHTML10Frameset(
		"html",
		"PUBLIC",
		"\"-//W3C//DTD XHTML 1.0 Frameset//EN\"",
		"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\""),
	XHTML11(
		"html", "PUBLIC", "\"-//W3C//DTD XHTML 1.1 Strict//EN\"", "\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\""),
	HTML5("html");

	private String[] content;
	private DOCTYPE(final String...content) {
		this.content = content;
	}
	public String[] content() {
		return content;
	}
	@Override
	public String toString() {
		final StringBuilder ret = new StringBuilder();
		ret.append("<!DOCTYPE");
		for (final String s: content) {
			ret.append(' ');
			ret.append(s);
		}
		ret.append(">");
		return ret.toString();
	}
}
