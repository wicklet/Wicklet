/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.util;

public interface IPrintWriter {
	public abstract void print(final CharSequence s);
	public abstract void print(final String s);
	public abstract void println();
	public abstract void println(final CharSequence s);
	public abstract void println(final String s);
	public abstract void flush();
	public abstract void close();
}
