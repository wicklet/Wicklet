/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.impl;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.MarkupParser;
import org.apache.wicket.markup.WicketTag;
import org.apache.wicket.markup.parser.XmlTag.TagType;
import org.apache.wicket.util.value.IValueMap;
import sf.blacksun.util.io.StringPrintWriter;
import sf.wicklet.dsl.html.api.IAttribute;
import sf.wicklet.dsl.html.api.IChild;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.api.ITop;
import sf.wicklet.dsl.html.api.IWicketComponent;
import sf.wicklet.dsl.html.api.IWicketElement;
import sf.wicklet.wicketext.markup.impl.WicketWriter;

/**
 * Create an IRootMarkup from an XHtml DSL DOM.
 *
 */
public class WicketSerializer {

	//////////////////////////////////////////////////////////////////////

	public static IRootMarkup serialize(final IElement e) {
		return serialize("", "", e);
	}

	public static IRootMarkup serialize(final String tab, final boolean compact, final IElement e) {
		return serialize("", tab, e);
	}

	public static IRootMarkup serialize(final String indent, final String tab, final IElement e) {
		final Serializer visitor = new Serializer(indent, tab);
		final StringPrintWriter data = new StringPrintWriter();
		e.accept(visitor, data);
		visitor.flush(data);
		return visitor.writer.build();
	}

	//////////////////////////////////////////////////////////////////////

	private WicketSerializer() {
	}

	//////////////////////////////////////////////////////////////////////

	// FIXME As of Wicket 6.0.0, it seems to have problem if components tags are emtpy tags.
	// So for now empty component tags are rendered as &lt;tag ...>&lt;/tag> instead of <tag .. />.
	public static class Serializer extends HtmlSerializer<StringPrintWriter> {

		protected MyWicketWriter writer;

		Serializer(final String indent, final String tab) {
			this(indent, tab, new MyWicketWriter(indent, tab));
		}

		Serializer(final String indent, final String tab, final MyWicketWriter w) {
			super(tab);
			writer = w;
			indent(indent);
			noEmptyTag(true);
		}

		@Override
		public void visit(final ITop node, final StringPrintWriter data) {
			super.visit(node, data);
			flush(data);
		}

		@Override
		public void visit(final IWicketComponent node, final StringPrintWriter data) {
			flush(data);
			ComponentTag close;
			if (node.getWicketId() == null) {
				close = writer.autoComp(node);
			} else {
				close = writer.startComp(node);
			}
			visitchildren(node, data);
			flush(data);
			writer.myclose(close);
			writer.end();
		}

		@Override
		public void visit(final IWicketElement node, final StringPrintWriter data) {
			flush(data);
			ComponentTag close;
			close = writer.startWicket(node);
			visitchildren(node, data);
			flush(data);
			writer.myclose(close);
			writer.end();
		}

		@Override
		protected void flush(final StringPrintWriter data) {
			super.flush(data);
			final StringBuffer buf = data.getBuffer();
			if (buf.length() > 0) {
				writer.raw(buf.toString());
				buf.setLength(0);
		}}

		private void visitchildren(final IElement node, final StringPrintWriter data) {
			indent();
			for (final IChild c: node.children()) {
				c.accept(this, data);
			}
			unindent();
		}
	}

	public static class MyWicketWriter extends WicketWriter {
		public MyWicketWriter(final String indent, final String tab) {
			super(indent, tab);
		}

		public ComponentTag startComp(final IWicketComponent node) {
			final String tag = node.tag();
			final String id = node.getWicketId();
			final ComponentTag stag = new ComponentTag(tag, TagType.OPEN);
			stag.setFlag(node.getFlags(), true);
			final Iterable<IAttribute> attrs = node.attributes();
			return mystart(stag, id, new ComponentTag(tag, TagType.CLOSE), attrs);
		}

		public ComponentTag emptyComp(final IWicketComponent node) {
			final String tag = node.tag();
			final String id = node.getWicketId();
			final ComponentTag stag = new ComponentTag(tag, TagType.OPEN_CLOSE);
			stag.setFlag(node.getFlags(), true);
			final Iterable<IAttribute> attrs = node.attributes();
			return mystart(stag, id, null, attrs);
		}

		/** Create an auto ComponentTag without wicket:id attribute, eg. head, link, ...etc. */
		public ComponentTag autoComp(final IWicketComponent node) {
			final String tag = node.tag();
			final String id = node.getWicketId();
			final Iterable<IAttribute> attrs = node.attributes();
			final ComponentTag stag = new ComponentTag(tag, TagType.OPEN);
			stag.setFlag(node.getFlags(), true);
			stag.setAutoComponentTag(true);
			return mystart(stag, id, new ComponentTag(tag, TagType.CLOSE), attrs);
		}

		/** Create an auto open-close ComponentTag without wicket:id attribute, eg. head, link, ...etc. */
		public ComponentTag emptyAutoComp(final IWicketComponent node) {
			final String tag = node.tag();
			final String id = node.getWicketId();
			final Iterable<IAttribute> attrs = node.attributes();
			final ComponentTag stag = new ComponentTag(tag, TagType.OPEN_CLOSE);
			stag.setFlag(node.getFlags(), true);
			stag.setAutoComponentTag(true);
			return mystart(stag, id, null, attrs);
		}

		public ComponentTag startWicket(final IWicketElement node) {
			final String tag = trimWicket(node.tag());
			final ComponentTag stag = new WicketTag(createXmlTag(MarkupParser.WICKET, tag, TagType.OPEN));
			final Iterable<IAttribute> attrs = node.attributes();
			return mystart(
				stag, null, new WicketTag(createXmlTag(MarkupParser.WICKET, tag, TagType.CLOSE)), attrs);
		}

		public ComponentTag emptyWicket(final IWicketElement node) {
			final String tag = trimWicket(node.tag());
			final Iterable<IAttribute> attrs = node.attributes();
			return mystart(
				new WicketTag(createXmlTag(MarkupParser.WICKET, tag, TagType.OPEN_CLOSE)),
				null,
				null,
				attrs);
		}

		protected ComponentTag mystart(
			final ComponentTag open,
			final String wicketid,
			final ComponentTag close,
			final Iterable<IAttribute> attrs) {
			raw(xhtml);
			if (wicketid != null) {
				open.setId(wicketid);
			}
			myaddattributes(open.getAttributes(), wicketid, attrs);
			if (close != null) {
				close.setOpenTag(open);
			} else {
				open.setOpenTag(open);
			}
			open.makeImmutable();
			add(open);
			return close;
		}

		protected void myclose(final ComponentTag close) {
			if (close != null) {
				close.makeImmutable();
				tagStack.push(close);
		}}

		protected void myaddattributes(
			final IValueMap a, final String wicketid, final Iterable<IAttribute> attrs) {
			if (wicketid != null) {
				a.put("wicket:id", wicketid);
			}
			for (final IAttribute attr: attrs) {
				a.put(attr.aname(), attr.avalue());
			}
			a.makeImmutable();
		}
	}

	//////////////////////////////////////////////////////////////////////
}
