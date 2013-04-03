/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.markup.api;

import java.util.Map;
import sf.blacksun.util.text.DOCTYPE;
import sf.blacksun.util.text.IAttribute;
import sf.blacksun.util.text.IXHTMLWriter;

public interface IXHTMLFragmentWriter extends IXHTMLWriter {
    boolean isEmpty();
    String take();
    CharSequence escAttr(String s);
    CharSequence escText(String s, boolean preservespace);
    @Override
    public abstract IAttribute id(String value);
    @Override
    public abstract IAttribute type(String value);
    @Override
    public abstract IAttribute name(String value);
    @Override
    public abstract IAttribute css(String value);
    @Override
    public abstract IAttribute href(String value);
    @Override
    public abstract IAttribute src(String value);

    @Override
    public abstract IXHTMLFragmentWriter xmlHeader();
    @Override
    public abstract IXHTMLFragmentWriter doctype(DOCTYPE doctype);
    @Override
    public abstract IXHTMLFragmentWriter startXHtmlXmlns(String lang);
    @Override
    public abstract IXHTMLFragmentWriter title(String text);
    @Override
    public abstract IXHTMLFragmentWriter stylesheet(String href);
    @Override
    public abstract IXHTMLFragmentWriter javascript(String...content);
    @Override
    public abstract IXHTMLFragmentWriter style(String...content);
    @Override
    public abstract IXHTMLFragmentWriter contentType(String charset);

    @Override
    public abstract IXHTMLFragmentWriter start(String tag);
    @Override
    public abstract IXHTMLFragmentWriter startAll(String...tags);
    @Override
    public abstract IXHTMLFragmentWriter start(String tag, IAttribute...attrs);
    @Override
    public abstract IXHTMLFragmentWriter start(String tag, String...attrs);
    @Override
    public abstract IXHTMLFragmentWriter start(String tag, Map<String, String> attrs);
    @Override
    public abstract IXHTMLFragmentWriter empty(String tag);
    @Override
    public abstract IXHTMLFragmentWriter empty(String tag, IAttribute...attrs);
    @Override
    public abstract IXHTMLFragmentWriter empty(String tag, String...attrs);
    @Override
    public abstract IXHTMLFragmentWriter empty(String tag, Map<String, String> attrs);
    @Override
    public abstract IXHTMLFragmentWriter raw(String content);
    @Override
    public abstract IXHTMLFragmentWriter raw(String...content);
    @Override
    public abstract IXHTMLFragmentWriter txt(String text);
    @Override
    public abstract IXHTMLFragmentWriter txt(String...text);
    @Override
    public abstract IXHTMLFragmentWriter format(String format, Object...args);
    @Override
    public abstract IXHTMLFragmentWriter comment(String comment);
    @Override
    public abstract IXHTMLFragmentWriter comment(String...comment);
    @Override
    public abstract IXHTMLFragmentWriter cdata(String cdata);
    @Override
    public abstract IXHTMLFragmentWriter cdata(String...cdata);
    @Override
    public abstract IXHTMLFragmentWriter end();
    @Override
    public abstract IXHTMLFragmentWriter end(String...expects);
    @Override
    public abstract IXHTMLFragmentWriter end(int level);
    @Override
    public abstract IXHTMLFragmentWriter endTill(int level);
    @Override
    public abstract IXHTMLFragmentWriter endAll();
    @Override
    public abstract IXHTMLFragmentWriter element(String tag, String content);
    @Override
    public abstract IXHTMLFragmentWriter element(String tag, String content, IAttribute...attrs);
    @Override
    public abstract IXHTMLFragmentWriter element(String tag, String content, String...attrs);
    @Override
    public abstract IXHTMLFragmentWriter element(String tag, String content, Map<String, String> attrs);
    @Override
    public abstract IXHTMLFragmentWriter formatted(String...content);
    @Override
    public abstract IXHTMLFragmentWriter lb();
    @Override
    public abstract IXHTMLFragmentWriter flush();
}
