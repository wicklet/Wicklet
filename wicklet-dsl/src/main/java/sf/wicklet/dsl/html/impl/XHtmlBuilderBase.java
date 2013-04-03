/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.impl;

import sf.blacksun.util.text.DOCTYPE;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.dsl.html.api.Attribute;
import sf.wicklet.dsl.html.api.IAttr;
import sf.wicklet.dsl.html.api.IAttribute;
import sf.wicklet.dsl.html.api.IAttributes;
import sf.wicklet.dsl.html.api.ICData;
import sf.wicklet.dsl.html.api.IComment;
import sf.wicklet.dsl.html.api.IDeclaration;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.api.ILine;
import sf.wicklet.dsl.html.api.INode;
import sf.wicklet.dsl.html.api.IPI;
import sf.wicklet.dsl.html.api.IRaw;
import sf.wicklet.dsl.html.api.IText;
import sf.wicklet.dsl.html.api.ITop;
import sf.wicklet.dsl.html.api.IXHtmlBuilder;
import sf.wicklet.wicketext.markup.api.IXmlUtil;
import sf.wicklet.wicketext.util.XmlUtil;

public abstract class XHtmlBuilderBase<E extends IElement> implements IXHtmlBuilder<E> {

	//////////////////////////////////////////////////////////////////////

	private IXmlUtil xmlUtil;

	//////////////////////////////////////////////////////////////////////

	@Override
	public ITop top(final INode...children) {
		final ITop parent = new Top();
		addAll(parent, children);
		return parent;
	}

	@Override
	public IPI xml() {
		return xml("1.0", "UTF-8");
	}

	@Override
	public IPI xml(final String encoding) {
		return new PI("xml", "encoding=" + getXmlUtil().quoteAttr(encoding));
	}

	@Override
	public IPI xml(final String version, final String encoding) {
		return new PI(
			"xml",
			"version=" + getXmlUtil().quoteAttr(version),
			"encoding=" + getXmlUtil().quoteAttr(encoding));
	}

	@Override
	public IDeclaration declaration(final String name, final String...content) {
		return new Declaration(name, content);
	}

	@Override
	public IDeclaration doctype(final DOCTYPE doctype) {
		return new Declaration("DOCTYPE", doctype.content());
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public E html(final INode...children) {
		return elm("html", children);
	}

	@Override
	public E head(final INode...children) {
		return elm("head", children);
	}

	@Override
	public E title(final String...content) {
		return elm("title", txt(content));
	}

	@Override
	public E meta(final IAttr...attributes) {
		return empty("meta", attributes);
	}

	@Override
	public E link(final IAttr...attributes) {
		return empty("link", attributes);
	}

	@Override
	public E base(final IAttr...attributes) {
		return empty("base", attributes);
	}

	@Override
	public E script(final INode...children) {
		return elm("script", children);
	}

	@Override
	public E style(final INode...children) {
		return elm("style", children);
	}

	@Override
	public E body(final INode...children) {
		return elm("body", children);
	}

	@Override
	public E h1(final INode...children) {
		return elm("h1", children);
	}

	@Override
	public E h2(final INode...children) {
		return elm("h2", children);
	}

	@Override
	public E h3(final INode...children) {
		return elm("h3", children);
	}

	@Override
	public E h4(final INode...children) {
		return elm("h4", children);
	}

	@Override
	public E h5(final INode...children) {
		return elm("h5", children);
	}

	@Override
	public E h6(final INode...children) {
		return elm("h6", children);
	}

	@Override
	public E p(final INode...children) {
		return elm("p", children);
	}

	@Override
	public E blockquote(final INode...children) {
		return elm("blockquote", children);
	}

	@Override
	public E div(final INode...children) {
		return elm("div", children);
	}

	@Override
	public E span(final INode...children) {
		return elm("span", children);
	}

	@Override
	public E a(final INode...children) {
		return elm("a", children);
	}

	@Override
	public E b(final INode...children) {
		return elm("b", children);
	}

	@Override
	public E em(final INode...children) {
		return elm("em", children);
	}

	@Override
	public E code(final INode...children) {
		return elm("code", children);
	}

	@Override
	public E pre(final INode...children) {
		return elm("pre", children);
	}

	@Override
	public E img(final IAttr...attributes) {
		return empty("img", attributes);
	}

	@Override
	public E br(final IAttr...attributes) {
		return empty("br", attributes);
	}

	@Override
	public E hr(final IAttr...attributes) {
		return empty("hr", attributes);
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public E ul(final INode...children) {
		return elm("ul", children);
	}

	@Override
	public E li(final INode...children) {
		return elm("li", children);
	}

	@Override
	public E ol(final INode...children) {
		return elm("ol", children);
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public E table(final INode...children) {
		return elm("table", children);
	}

	@Override
	public E thead(final INode...children) {
		return elm("thead", children);
	}

	@Override
	public E tbody(final INode...children) {
		return elm("tbody", children);
	}

	@Override
	public E tfoot(final INode...children) {
		return elm("tfoot", children);
	}

	@Override
	public E colgroup(final INode...children) {
		return elm("colgroup", children);
	}

	@Override
	public E colgroup(final String width1, final String...widths) {
		final E ret = elm("colgroup");
		ret.a(col(atts("width", width1)));
		for (final String width: widths) {
			ret.a(col(atts("width", width)));
		}
		return ret;
	}

	@Override
	public E col(final IAttr...attributes) {
		return empty("col", attributes);
	}

	@Override
	public E tr(final INode...children) {
		return elm("tr", children);
	}

	@Override
	public E th(final INode...children) {
		return elm("th", children);
	}

	@Override
	public E td(final INode...children) {
		return elm("td", children);
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public E form(final INode...children) {
		return elm("form", children);
	}

	@Override
	public E input(final IAttr...attributes) {
		return empty("input", attributes);
	}

	@Override
	public E button(final INode...children) {
		return elm("button", children);
	}

	@Override
	public E optgroup(final INode...children) {
		return elm("optgroup", children);
	}

	@Override
	public E option(final INode...children) {
		return elm("option", children);
	}

	@Override
	public E select(final INode...children) {
		return elm("select", children);
	}

	@Override
	public E textarea(final INode...children) {
		return elm("textarea", children);
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public E noscript(final INode...children) {
		return elm("noscript", children);
	}

	@Override
	public E iframe(final INode...children) {
		return elm("iframe", children);
	}

	@Override
	public E applet(final INode...children) {
		return elm("applet", children);
	}

	@Override
	public E object(final INode...children) {
		return elm("object", children);
	}

	@Override
	public E embed(final IAttr...attributes) {
		return empty("embed", attributes);
	}

	@Override
	public E style(final String...content) {
		return elm(
			"style",
			new Text(
				getXmlUtil().escText(TextUtil.join(TextUtil.getLineSeparator(), content), true)
					.toString()));
	}

	@Override
	public E script(final String...content) {
		return elm(
			"script",
			new Text(
				getXmlUtil().escText(TextUtil.join(TextUtil.getLineSeparator(), content), true)
					.toString()));
	}

	@Override
	public E pre(final String...content) {
		return elm(
			"pre",
			new Text(
				getXmlUtil().escText(TextUtil.join(TextUtil.getLineSeparator(), content), true)
					.toString()));
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public IRaw raw(final String...content) {
		return new Raw(content);
	}

	@Override
	public IText txt(final String...content) {
		return new Text(content);
	}

	@Override
	public IText esc(final String...content) {
		final int len = content.length;
		final String[] c = new String[content.length];
		for (int i = 0; i < len; ++i) {
			c[i] = getXmlUtil().escText(content[i], true).toString();
		}
		return new Text(c);
	}

	@Override
	public ILine lb() {
		return new Linebreak();
	}

	@Override
	public ICData cdata(final String...content) {
		return new CData(content);
	}

	@Override
	public IComment comment(final String...content) {
		return new Comment(content);
	}

	@Override
	public IPI pi(final String target, final String...content) {
		return new PI(target, TextUtil.join(TextUtil.getLineSeparator(), content));
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public IAttributes atts() {
		return new Attributes();
	}

	@Override
	public IAttributes atts(final IAttribute attr) {
		return new Attributes(attr);
	}

	@Override
	public IAttributes atts(final IAttribute...attrs) {
		return new Attributes(attrs);
	}

	@Override
	public IAttributes atts(final Object name, final boolean value) {
		return new Attributes(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes atts(final Object name, final int value) {
		return new Attributes(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes atts(final Object name, final long value) {
		return new Attributes(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes atts(final Object name, final float value) {
		return new Attributes(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes atts(final Object name, final double value) {
		return new Attributes(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes atts(final Object name, final Object value) {
		return new Attributes(new Attribute(name, value));
	}

	@Override
	public IAttributes atts(final boolean linebreaks) {
		return new Attributes(linebreaks);
	}

	@Override
	public IAttributes atts(final Object...namevalues) {
		return lb(false, namevalues);
	}

	@Override
	public IAttributes lb(final IAttribute...attrs) {
		final IAttributes ret = new Attributes();
		boolean first = true;
		for (final IAttribute attr: attrs) {
			if (first) {
				first = false;
			} else {
				ret.a(Attribute.LB);
			}
			ret.a(attr);
		}
		return ret;
	}

	@Override
	public IAttributes lb(final Object...namevalues) {
		return lb(true, namevalues);
	}

	private IAttributes lb(final boolean linebreak, final Object...namevalues) {
		final int len = namevalues.length;
		if (len % 2 != 0) {
			throw new IllegalArgumentException("ERROR: Expected even number of arguments, actual=" + len);
		}
		final Attributes ret = new Attributes();
		for (int i = 0; i < len; i += 2) {
			if (i > 0 && linebreak) {
				ret.a(Attribute.LB);
			}
			ret.a(namevalues[i], namevalues[i + 1]);
		}
		return ret;
	}

	@Override
	public IAttribute attr(final Object name, final boolean value) {
		return new Attribute(name, String.valueOf(value));
	}

	@Override
	public IAttribute attr(final Object name, final int value) {
		return new Attribute(name, String.valueOf(value));
	}

	@Override
	public IAttribute attr(final Object name, final long value) {
		return new Attribute(name, String.valueOf(value));
	}

	@Override
	public IAttribute attr(final Object name, final float value) {
		return new Attribute(name, String.valueOf(value));
	}

	@Override
	public IAttribute attr(final Object name, final double value) {
		return new Attribute(name, String.valueOf(value));
	}

	@Override
	public IAttribute attr(final Object name, final Object value) {
		return new Attribute(name, value);
	}

	@Override
	public IAttribute attr(final Object name, final Object...values) {
		return new Attribute(name, values);
	}

	@Override
	public IAttribute css(final Object value) {
		return new Attribute("class", value);
	}

	@Override
	public IAttribute css(final Object...values) {
		return new Attribute("class", TextUtil.join(" ", values));
	}

	@Override
	public IAttribute istyle(final Object style) {
		return attr("style", style.toString());
	}

	@Override
	public IAttribute istyle(final Object...styles) {
		return attr("style", TextUtil.join(" ", styles));
	}

	@Override
	public IAttribute href(final Object value) {
		return new Attribute("href", value);
	}

	@Override
	public IAttribute src(final Object value) {
		return new Attribute("src", value);
	}

	@Override
	public IAttribute id(final Object value) {
		return new Attribute("id", value);
	}

	@Override
	public IAttribute type(final Object value) {
		return new Attribute("type", value);
	}

	@Override
	public IAttribute name(final Object value) {
		return new Attribute("name", value);
	}

	@Override
	public IAttribute value(final Object value) {
		return new Attribute("value", value);
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public IAttribute onload(final String script) {
		return new Attribute("onload", script);
	}

	@Override
	public IAttribute onunload(final String script) {
		return new Attribute("onunload", script);
	}

	@Override
	public IAttribute onclick(final String script) {
		return new Attribute("onclick", script);
	}

	@Override
	public IAttribute ondblclick(final String script) {
		return new Attribute("ondblclick", script);
	}

	@Override
	public IAttribute onmousedown(final String script) {
		return new Attribute("onmousedown", script);
	}

	@Override
	public IAttribute onmouseup(final String script) {
		return new Attribute("onmouseup", script);
	}

	@Override
	public IAttribute onmouseover(final String script) {
		return new Attribute("onmouseover", script);
	}

	@Override
	public IAttribute onmousemove(final String script) {
		return new Attribute("onmousemove", script);
	}

	@Override
	public IAttribute onmouseout(final String script) {
		return new Attribute("onmouseout", script);
	}

	@Override
	public IAttribute onfocus(final String script) {
		return new Attribute("onfocus", script);
	}

	@Override
	public IAttribute onblur(final String script) {
		return new Attribute("onblur", script);
	}

	@Override
	public IAttribute onkeypress(final String script) {
		return new Attribute("onkeypress", script);
	}

	@Override
	public IAttribute onkeydown(final String script) {
		return new Attribute("onkeydown", script);
	}

	@Override
	public IAttribute onkeyup(final String script) {
		return new Attribute("onkeyup", script);
	}

	@Override
	public IAttribute onsubmit(final String script) {
		return new Attribute("onsubmit", script);
	}

	@Override
	public IAttribute onreset(final String script) {
		return new Attribute("onreset", script);
	}

	@Override
	public IAttribute onselect(final String script) {
		return new Attribute("onselect", script);
	}

	//////////////////////////////////////////////////////////////////////
	// Shorthand methods

	@Override
	public E contenttype(final Object type) {
		return meta(atts("http-equiv", "Content-Type", "content", type));
	}

	@Override
	public E stylesheet() {
		return link(atts("rel", "stylesheet").type("text/css"));
	}

	@Override
	public E stylesheet(final Object href) {
		return link(atts("rel", "stylesheet").type("text/css").href(href));
	}

	@Override
	public E javascript() {
		return script(atts("language", "javascript").type("text/javascript"));
	}

	@Override
	public E javascript(final Object src) {
		return script(atts("language", "javascript").type("text/javascript").src(src));
	}

	@Override
	public E vbox(final IElement...children) {
		final int len = children.length;
		final IElement[] rows = new IElement[len];
		for (int i = 0; i < len; ++i) {
			rows[i] = tr(td(children[i]));
		}
		return table(rows);
	}

	@Override
	public E hbox(final IElement...children) {
		final int len = children.length;
		final IElement[] cols = new IElement[len];
		for (int i = 0; i < len; ++i) {
			cols[i] = td(children[i]);
		}
		return table(tr(cols));
	}

	//////////////////////////////////////////////////////////////////////

	protected void addAll(final IElement e, final INode...children) {
		for (final INode c: children) {
			c.addTo(e);
	}}

	protected IXmlUtil getXmlUtil() {
		if (xmlUtil == null) {
			xmlUtil = new XmlUtil();
		}
		return xmlUtil;
	}

	//////////////////////////////////////////////////////////////////////
}
