/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.markup.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IMarkupElement;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.MarkupException;
import org.apache.wicket.markup.MarkupParser;
import org.apache.wicket.markup.RawMarkup;
import org.apache.wicket.markup.WicketTag;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.markup.parser.XmlTag.TagType;
import org.apache.wicket.util.value.IValueMap;
import sf.blacksun.util.struct.IterableWrapper;
import sf.blacksun.util.text.IAttribute;
import sf.wicklet.wicketext.markup.api.IXHTMLFragmentWriter;

/**
 * Build an immutable markup programmatically.
 *
 */
public class WicketWriter {

	public static final String AUTO_ID_HEAD = "_header_";

	////////////////////////////////////////////////////////////////////////

	protected IXHTMLFragmentWriter xhtml;

	protected List<IMarkupElement> markups = new ArrayList<IMarkupElement>();

	protected Stack<IMarkupElement> tagStack = new Stack<IMarkupElement>();

	////////////////////////////////////////////////////////////////////////

	public WicketWriter() {
		xhtml = new XHTMLFragmentWriter("", null);
	}

	public WicketWriter(final String indent, final String tab) {
		xhtml = new XHTMLFragmentWriter(indent, tab);
	}

	public IRootMarkup build() {
		raw(xhtml);
		final ReadOnlyRootMarkup ret = new ReadOnlyRootMarkup(markups);
		if (!tagStack.isEmpty()) {
			throw new MarkupException("Markup is not well formed: " + ret);
		}
		if (xhtml.level() != 0) {
			throw new MarkupException("XHtmlFragment builder is not well-formed: " + xhtml.toString());
		}
		return ret;
	}

	public IAttribute a(final String name, final String value) {
		return xhtml.a(name, value);
	}

	public IAttribute id(final String value) {
		return xhtml.id(value);
	}

	public IAttribute type(final String value) {
		return xhtml.type(value);
	}

	public IAttribute name(final String value) {
		return xhtml.name(value);
	}

	public IAttribute css(final String value) {
		return xhtml.css(value);
	}

	public IAttribute href(final String value) {
		return xhtml.href(value);
	}

	public IAttribute src(final String value) {
		return xhtml.src(value);
	}

	public WicketWriter raw(final CharSequence content) {
		if (content.length() > 0) {
			markups.add(new RawMarkup(content));
		}
		return this;
	}

	//	public WicketWriter raw(IXmlWriter w) {
	//		String content = w.toString();
	//		if (content.length() > 0)
	//			markups.add(new RawMarkup(content));
	//		return this;
	//	}

	public WicketWriter raw(final IXHTMLFragmentWriter w) {
		if (!w.isEmpty()) {
			markups.add(new RawMarkup(w.take()));
		}
		return this;
	}

	/** Similar to raw(), but perform XML escape on the input. */
	public WicketWriter text(final String text) {
		if (text.length() > 0) {
			markups.add(new RawMarkup(xhtml.escText(text, false)));
		}
		return this;
	}

	/** Similar to text(), but perserve spaces. */
	public WicketWriter pre(final String text) {
		if (text.length() > 0) {
			markups.add(new RawMarkup(xhtml.escText(text, true)));
		}
		return this;
	}

	public WicketWriter end() {
		raw(xhtml);
		markups.add(tagStack.pop());
		return this;
	}

	public WicketWriter endAll() {
		raw(xhtml);
		while (!tagStack.isEmpty()) {
			markups.add(tagStack.pop());
		}
		return this;
	}

	////////////////////////////////////////////////////////////////////////

	public WicketWriter startComp(final String tag, final String id) {
		return start(new ComponentTag(tag, TagType.OPEN), id, id, new ComponentTag(tag, TagType.CLOSE));
	}

	public WicketWriter emptyComp(final String tag, final String id) {
		return start(new ComponentTag(tag, TagType.OPEN_CLOSE), id, id, null);
	}

	/** Create ComponentTag without wicket:id attribute, eg. head, link, ...etc. */
	public WicketWriter startAutoComp(final String tag, final String id) {
		final ComponentTag ctag = new ComponentTag(tag, TagType.OPEN);
		ctag.setAutoComponentTag(true);
		return start(ctag, id, id, new ComponentTag(tag, TagType.CLOSE));
	}

	/** Create open-close auto ComponentTag that has an id attribute but no wicket:id attribute, eg. head, link, ...etc. */
	public WicketWriter emptyAutoComp(final String tag, final String id) {
		final ComponentTag ctag = new ComponentTag(tag, TagType.OPEN_CLOSE);
		ctag.setAutoComponentTag(true);
		return start(ctag, id, id, null);
	}

	public WicketWriter startWicket(String tag) {
		tag = trimWicket(tag);
		return start(
			new WicketTag(createXmlTag(MarkupParser.WICKET, tag, TagType.OPEN)),
			null,
			null,
			new WicketTag(createXmlTag(MarkupParser.WICKET, tag, TagType.CLOSE)));
	}

	public WicketWriter emptyWicket(final String tag) {
		return start(new WicketTag(createXmlTag(trimWicket(tag), TagType.OPEN_CLOSE)), null, null, null);
	}

	////////////////////////////////////////////////////////////////////////

	public WicketWriter startComp(final String tag, final String id, final String...attrs) {
		return start(new ComponentTag(tag, TagType.OPEN), id, id, new ComponentTag(tag, TagType.CLOSE), attrs);
	}

	public WicketWriter emptyComp(final String tag, final String id, final String...attrs) {
		return start(new ComponentTag(tag, TagType.OPEN_CLOSE), id, id, null, attrs);
	}

	/** Create ComponentTag without wicket:id attribute, eg. head, link, ...etc. */
	public WicketWriter startAutoComp(final String tag, final String id, final String...attrs) {
		final ComponentTag ctag = new ComponentTag(tag, TagType.OPEN);
		ctag.setAutoComponentTag(true);
		return start(ctag, id, null, new ComponentTag(tag, TagType.CLOSE), attrs);
	}

	/** Create open-close auto ComponentTag that has an id attribute but no wicket:id attribute, eg. head, link, ...etc. */
	public WicketWriter emptyAutoComp(final String tag, final String id, final String...attrs) {
		final ComponentTag ctag = new ComponentTag(tag, TagType.OPEN_CLOSE);
		ctag.setAutoComponentTag(true);
		return start(ctag, id, null, null, attrs);
	}

	public WicketWriter startWicket(String tag, final String...attrs) {
		tag = trimWicket(tag);
		return start(
			new WicketTag(createXmlTag(MarkupParser.WICKET, tag, TagType.OPEN)),
			null,
			null,
			new WicketTag(createXmlTag(MarkupParser.WICKET, tag, TagType.CLOSE)),
			attrs);
	}

	public WicketWriter emptyWicket(final String tag, final String...attrs) {
		return start(new WicketTag(createXmlTag(trimWicket(tag), TagType.OPEN_CLOSE)), null, null, null, attrs);
	}

	////////////////////////////////////////////////////////////////////////

	public WicketWriter startComp(final String tag, final String id, final IAttribute...attrs) {
		return start(
			new ComponentTag(tag, TagType.OPEN),
			id,
			id,
			new ComponentTag(tag, TagType.CLOSE),
			IterableWrapper.wrap(attrs));
	}

	public WicketWriter emptyComp(final String tag, final String id, final IAttribute...attrs) {
		return start(new ComponentTag(tag, TagType.OPEN_CLOSE), id, id, null, IterableWrapper.wrap(attrs));
	}

	/** Create an auto ComponentTag without wicket:id attribute, eg. head, link, ...etc. */
	public WicketWriter startAutoComp(final String tag, final String id, final IAttribute...attrs) {
		final ComponentTag ctag = new ComponentTag(tag, TagType.OPEN);
		ctag.setAutoComponentTag(true);
		return start(ctag, id, null, new ComponentTag(tag, TagType.CLOSE), IterableWrapper.wrap(attrs));
	}

	/** Create an auto open-close ComponentTag without wicket:id attribute, eg. head, link, ...etc. */
	public WicketWriter emptyAutoComp(final String tag, final String id, final IAttribute...attrs) {
		final ComponentTag ctag = new ComponentTag(tag, TagType.OPEN_CLOSE);
		ctag.setAutoComponentTag(true);
		return start(ctag, id, null, null, IterableWrapper.wrap(attrs));
	}

	public WicketWriter emptyWicket(final String tag, final IAttribute...attrs) {
		return start(
			new WicketTag(createXmlTag(trimWicket(tag), TagType.OPEN_CLOSE)),
			null,
			null,
			null,
			IterableWrapper.wrap(attrs));
	}

	public WicketWriter startWicket(String tag, final IAttribute...attrs) {
		tag = trimWicket(tag);
		return start(
			new WicketTag(createXmlTag(MarkupParser.WICKET, tag, TagType.OPEN)),
			null,
			null,
			new WicketTag(createXmlTag(MarkupParser.WICKET, tag, TagType.CLOSE)),
			IterableWrapper.wrap(attrs));
	}

	////////////////////////////////////////////////////////////////////////

	public WicketWriter startComp(final String tag, final String id, final Iterable<IAttribute> attrs) {
		return start(new ComponentTag(tag, TagType.OPEN), id, id, new ComponentTag(tag, TagType.CLOSE), attrs);
	}

	public WicketWriter emptyComp(final String tag, final String id, final Iterable<IAttribute> attrs) {
		return start(new ComponentTag(tag, TagType.OPEN_CLOSE), id, id, null, attrs);
	}

	/** Create an auto ComponentTag without wicket:id attribute, eg. head, link, ...etc. */
	public WicketWriter startAutoComp(final String tag, final String id, final Iterable<IAttribute> attrs) {
		final ComponentTag ctag = new ComponentTag(tag, TagType.OPEN);
		ctag.setAutoComponentTag(true);
		return start(ctag, id, null, new ComponentTag(tag, TagType.CLOSE), attrs);
	}

	/** Create an auto open-close ComponentTag without wicket:id attribute, eg. head, link, ...etc. */
	public WicketWriter emptyAutoComp(final String tag, final String id, final Iterable<IAttribute> attrs) {
		final ComponentTag ctag = new ComponentTag(tag, TagType.OPEN_CLOSE);
		ctag.setAutoComponentTag(true);
		return start(ctag, id, null, null, attrs);
	}

	public WicketWriter emptyWicket(final String tag, final Iterable<IAttribute> attrs) {
		return start(new WicketTag(createXmlTag(trimWicket(tag), TagType.OPEN_CLOSE)), null, null, null, attrs);
	}

	public WicketWriter startWicket(String tag, final Iterable<IAttribute> attrs) {
		tag = trimWicket(tag);
		return start(
			new WicketTag(createXmlTag(MarkupParser.WICKET, tag, TagType.OPEN)),
			null,
			null,
			new WicketTag(createXmlTag(MarkupParser.WICKET, tag, TagType.CLOSE)),
			attrs);
	}

	////////////////////////////////////////////////////////////////////////

	private WicketWriter start(
		final ComponentTag open,
		final String id,
		final String wicketid,
		final ComponentTag close,
		final String...attrs) {
		raw(xhtml);
		if (id != null) {
			open.setId(id);
		}
		addAttributes(open.getAttributes(), wicketid, attrs);
		pushClose(open, close);
		open.makeImmutable();
		markups.add(open);
		return this;
	}

	private WicketWriter start(
		final ComponentTag open,
		final String id,
		final String wicketid,
		final ComponentTag close,
		final Iterable<IAttribute> attrs) {
		raw(xhtml);
		if (id != null) {
			open.setId(id);
		}
		addAttributes(open.getAttributes(), wicketid, attrs);
		pushClose(open, close);
		open.makeImmutable();
		markups.add(open);
		return this;
	}

	protected void pushClose(final ComponentTag open, final ComponentTag close) {
		if (close != null) {
			close.makeImmutable();
			close.setOpenTag(open);
			tagStack.push(close);
		} else {
			open.setOpenTag(open);
	}}

	protected void add(final IMarkupElement e) {
		markups.add(e);
	}

	protected void addAttributes(final IValueMap a, final String wicketid, final String...attrs) {
		if (wicketid != null) {
			a.put("wicket:id", wicketid);
		}
		for (int i = 0, len = attrs.length; i < len; i += 2) {
			a.put(attrs[i], attrs[i + 1]);
		}
		a.makeImmutable();
	}

	protected void addAttributes(final IValueMap a, final String wicketid, final Iterable<IAttribute> attrs) {
		if (wicketid != null) {
			a.put("wicket:id", wicketid);
		}
		for (final IAttribute attr: attrs) {
			a.put(attr.name(), attr.value());
		}
		a.makeImmutable();
	}

	protected XmlTag createXmlTag(final String namespace, final String tag, final TagType type) {
		final XmlTag xmltag = new XmlTag();
		xmltag.setNamespace(namespace);
		xmltag.setName(tag);
		xmltag.setType(type);
		return xmltag;
	}

	protected XmlTag createXmlTag(final String tag, final TagType type) {
		final XmlTag xmltag = new XmlTag();
		xmltag.setName(tag);
		xmltag.setType(type);
		return xmltag;
	}

	protected String trimWicket(final String tag) {
		if (!tag.startsWith("wicket:")) {
			return tag;
		}
		return tag.substring(7);
	}

	////////////////////////////////////////////////////////////////////////
}
