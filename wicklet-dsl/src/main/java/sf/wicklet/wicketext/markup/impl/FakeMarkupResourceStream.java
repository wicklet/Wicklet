/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.markup.impl;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ContainerInfo;
import org.apache.wicket.markup.IMarkupResourceStream;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.MarkupParser;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.time.Time;

public class FakeMarkupResourceStream extends FakeResourceStream implements IMarkupResourceStream {
	private static final long serialVersionUID = 1L;
	public FakeMarkupResourceStream() {
		// super(new FakeResourceStream());
	}
	@Override
	public String getWicketNamespace() {
		return MarkupParser.WICKET;
	}
	@Override
	public String locationAsString() {
		throw new UnsupportedOperationException();
	}
	@Override
	public Time lastModifiedTime() {
		throw new UnsupportedOperationException();
	}
	@Override
	public Class<? extends Component> getMarkupClass() {
		throw new UnsupportedOperationException();
	}
	@Override
	public ContainerInfo getContainerInfo() {
		throw new UnsupportedOperationException();
	}
	@Override
	public String getCacheKey() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void setCacheKey(final String cacheKey) {
		throw new UnsupportedOperationException();
	}
	@Override
	public IResourceStream getResource() {
		throw new UnsupportedOperationException();
	}
	@Override
	public String getEncoding() {
		throw new UnsupportedOperationException();
	}
	@Override
	public String getWicketId() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void setWicketNamespace(final String wicketNamespace) {
		throw new UnsupportedOperationException();
	}
	@Override
	public IMarkupResourceStream getBaseMarkupResourceStream() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void setBaseMarkup(final IRootMarkup baseMarkup) {
		throw new UnsupportedOperationException();
	}
	@Override
	public IRootMarkup getBaseMarkup() {
		throw new UnsupportedOperationException();
	}
	@Override
	public String getDoctype() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void setDoctype(final CharSequence doctype) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean isHtml5() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void setEncoding(final String encoding) {
		throw new UnsupportedOperationException();
	}
}
