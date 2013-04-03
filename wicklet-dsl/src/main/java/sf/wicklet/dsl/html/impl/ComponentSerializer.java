/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.impl;

import org.apache.wicket.markup.IRootMarkup;
import sf.blacksun.util.io.StringPrintWriter;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.wicketext.markup.api.IXHTMLFragmentWriter;
import sf.wicklet.wicketext.markup.impl.WicketWriter;

/**
 * Create an IMarkup from an XHtml DSL DOM.
 *
 */
public class ComponentSerializer {

	//////////////////////////////////////////////////////////////////////

	public static IRootMarkup serialize(final IElement e) {
		return serialize("", "", e);
	}

	public static IRootMarkup serialize(final String tab, final boolean compact, final IElement e) {
		return serialize("", tab, e);
	}

	public static IRootMarkup serialize(final String indent, final String tab, final IElement e) {
		final Serializer visitor = new Serializer(indent, tab);
		e.accept(visitor, new StringPrintWriter());
		return visitor.writer.build();
	}

	//////////////////////////////////////////////////////////////////////

	private ComponentSerializer() {
	}

	//////////////////////////////////////////////////////////////////////

	public static class Serializer extends WicketSerializer.Serializer {
		protected Serializer(final String indent, final String tab) {
			super(indent, tab, new MyWicketWriter(indent, tab));
		}
	}

	public static class MyWicketWriter extends WicketSerializer.MyWicketWriter {

		public MyWicketWriter(final String indent, final String tab) {
			super(indent, tab);
		}

		@Override
		public WicketWriter raw(final IXHTMLFragmentWriter w) {
			w.take();
			return this;
		}

		@Override
		public WicketWriter raw(final CharSequence content) {
			return this;
		}
	}

	//////////////////////////////////////////////////////////////////////
}
