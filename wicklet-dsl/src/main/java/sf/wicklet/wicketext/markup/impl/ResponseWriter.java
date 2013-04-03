/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.markup.impl;

import java.io.IOException;
import java.io.Writer;
import org.apache.wicket.request.Response;
import sf.blacksun.util.text.CharRange;

public class ResponseWriter extends Writer {

	private final Response response;

	public ResponseWriter(final Response response) {
		this.response = response;
	}

	@Override
	public void write(final char[] cbuf, final int off, final int len) throws IOException {
		response.write(new CharRange(cbuf, off, off + len));
	}

	@Override
	public void flush() throws IOException {
		// response.flush();
	}

	@Override
	public void close() throws IOException {
		response.close();
	}
}
