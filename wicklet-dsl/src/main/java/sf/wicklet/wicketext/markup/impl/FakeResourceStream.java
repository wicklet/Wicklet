/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.markup.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;

public class FakeResourceStream implements IResourceStream {
	private static final long serialVersionUID = 1L;
	@Override
	public Time lastModifiedTime() {
		throw new UnsupportedOperationException();
	}
	@Override
	public String getContentType() {
		throw new UnsupportedOperationException();
	}
	@Override
	public Bytes length() {
		throw new UnsupportedOperationException();
	}
	@Override
	public InputStream getInputStream() throws ResourceStreamNotFoundException {
		throw new UnsupportedOperationException();
	}
	@Override
	public void close() throws IOException {
		throw new UnsupportedOperationException();
	}
	@Override
	public Locale getLocale() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void setLocale(Locale locale) {
		throw new UnsupportedOperationException();
	}
	@Override
	public String getStyle() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void setStyle(String style) {
		throw new UnsupportedOperationException();
	}
	@Override
	public String getVariation() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void setVariation(String variation) {
		throw new UnsupportedOperationException();
	}
}
