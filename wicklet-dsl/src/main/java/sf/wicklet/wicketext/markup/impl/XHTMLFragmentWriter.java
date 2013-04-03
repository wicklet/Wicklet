/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.markup.impl;

import java.util.Map;
import sf.blacksun.util.io.StringPrintWriter;
import sf.blacksun.util.text.DOCTYPE;
import sf.blacksun.util.text.IAttribute;
import sf.blacksun.util.text.XHTMLWriter;
import sf.wicklet.wicketext.markup.api.IXHTMLFragmentWriter;
import sf.wicklet.wicketext.markup.api.IXmlUtil;
import sf.wicklet.wicketext.util.XmlUtil;

public class XHTMLFragmentWriter extends XHTMLWriter implements IXHTMLFragmentWriter {

	private final IXmlUtil xmlUtil = new XmlUtil();
	private final StringPrintWriter buf = new StringPrintWriter();
	public XHTMLFragmentWriter(final String indent, final String tab) {
		super(indent, tab);
		setWriter(buf);
	}
	@Override
	public CharSequence escAttr(final String s) {
		return xmlUtil.escAttr(s);
	}
	@Override
	public CharSequence escText(final String s, final boolean preservespace) {
		return xmlUtil.escText(s, preservespace);
	}
	@Override
	public boolean isEmpty() {
		return buf.length() == 0 && getLineBuffer().length() == 0;
	}
	/** @return The content of current buffer. Buffer is cleared before return. */
	@Override
	public String take() {
		flush();
		final String ret = buf.toString();
		buf.getBuffer().setLength(0);
		return ret;
	}
	/** @return The content of current buffer. Buffer is intact after return. */
	@Override
	public String toString() {
		final CharSequence line = getLineBuffer();
		if (line.length() > 0) {
			final int len = buf.length();
			printIndent();
			buf.append(line);
			final String ret = buf.toString();
			buf.getBuffer().setLength(len);
			return ret;
		}
		return buf.toString();
	}

	@Override
	public IXHTMLFragmentWriter doctype(final DOCTYPE doctype) {
		super.doctype(doctype);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter startXHtmlXmlns(final String lang) {
		super.startXHtmlXmlns(lang);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter contentType(final String charset) {
		super.contentType(charset);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter title(final String text) {
		super.title(text);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter stylesheet(final String href) {
		super.stylesheet(href);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter javascript(final String...content) {
		super.javascript(content);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter style(final String...content) {
		super.style(content);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter xmlHeader() {
		super.xmlHeader();
		return this;
	}
	@Override
	public IXHTMLFragmentWriter start(final String tag) {
		super.start(tag);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter startAll(final String...tags) {
		super.startAll(tags);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter start(final String tag, final IAttribute...attrs) {
		super.start(tag, attrs);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter start(final String tag, final String...attrs) {
		super.start(tag, attrs);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter start(final String tag, final Map<String, String> attrs) {
		super.start(tag, attrs);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter empty(final String tag) {
		super.empty(tag);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter empty(final String tag, final IAttribute...attrs) {
		super.empty(tag, attrs);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter empty(final String tag, final String...attrs) {
		super.empty(tag, attrs);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter empty(final String tag, final Map<String, String> attrs) {
		super.empty(tag, attrs);
		return this;
	}
    @Override
    public IXHTMLFragmentWriter raw(final String content) {
        super.raw(content);
        return this;
    }
    @Override
    public IXHTMLFragmentWriter raw(final String...a) {
        super.raw(a);
        return this;
    }
    @Override
    public IXHTMLFragmentWriter txt(final String text) {
        super.txt(text);
        return this;
    }
    @Override
    public IXHTMLFragmentWriter txt(final String...a) {
        super.txt(a);
        return this;
    }
	@Override
	public IXHTMLFragmentWriter format(final String format, final Object...args) {
		super.format(format, args);
		return this;
	}
    @Override
    public IXHTMLFragmentWriter comment(final String content) {
        super.comment(content);
        return this;
    }
    @Override
    public IXHTMLFragmentWriter comment(final String...a) {
        super.comment(a);
        return this;
    }
    @Override
    public IXHTMLFragmentWriter cdata(final String content) {
        super.cdata(content);
        return this;
    }
    @Override
    public IXHTMLFragmentWriter cdata(final String...a) {
        super.cdata(a);
        return this;
    }
	@Override
	public IXHTMLFragmentWriter end() {
		super.end();
		return this;
	}
	@Override
	public IXHTMLFragmentWriter end(final String...expected) {
		super.end(expected);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter end(final int levels) {
		super.end(levels);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter endTill(final int level) {
		super.endTill(level);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter endAll() {
		super.endAll();
		return this;
	}
	@Override
	public IXHTMLFragmentWriter element(final String tag, final String text) {
		super.element(tag, text);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter element(final String tag, final String text, final IAttribute...attrs) {
		super.element(tag, text, attrs);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter element(final String tag, final String text, final String...attrs) {
		super.element(tag, text, attrs);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter element(final String tag, final String text, final Map<String, String> attrs) {
		super.element(tag, text, attrs);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter formatted(final String...a) {
		super.formatted(a);
		return this;
	}
	@Override
	public IXHTMLFragmentWriter lb() {
		super.lb();
		return this;
	}
	@Override
	public IXHTMLFragmentWriter flush() {
		super.flush();
		return this;
	}
}
