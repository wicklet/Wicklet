/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.util;

public class StringPrintWriter implements IPrintWriter {

	private static final String SEP = GwtTextUtil.getLineSeparator();
	private final StringBuilder b = new StringBuilder();

	public int length() {
		return b.length();
	}

	public void setLength(final int len) {
		b.setLength(len);
	}

	@Override
	public void print(final CharSequence s) {
		b.append(s);
	}

	@Override
	public void print(final String s) {
		b.append(s);
	}

	@Override
	public void println() {
		b.append(SEP);
	}

	@Override
	public void println(final CharSequence s) {
		b.append(s);
		b.append(SEP);
	}

	@Override
	public void println(final String s) {
		b.append(s);
		b.append(SEP);
	}

	@Override
	public String toString() {
		return b.toString();
	}

	@Override
	public void close() {
	}

	@Override
	public void flush() {
	}
}
