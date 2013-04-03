/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.dsl.html;

import java.util.Stack;
import sf.wicklet.gwt.client.dsl.Attribute;
import sf.wicklet.gwt.client.dsl.TAG;
import sf.wicklet.gwt.client.util.GwtTextUtil;
import sf.wicklet.gwt.client.util.IPrintWriter;
import sf.wicklet.gwt.client.util.StringPrintWriter;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

/** Serialize the DOM document as Html/Html5 using explicit line breaks. */
public class HtmlSerializer<T extends IPrintWriter> implements INodeVisitor<T> {

	//////////////////////////////////////////////////////////////////////

	protected String indent = "";
	protected String tab = "";
	protected boolean noEmptyTag;
	StringBuilder line = new StringBuilder();
	int startLevel = 0;
	boolean newline = true;
	boolean preformatted = false;
	Stack<String> stack = new Stack<String>();

	//////////////////////////////////////////////////////////////////////

	public static String serialize(final IElement e) {
		return HtmlSerializer.serialize("", "    ", false, e);
	}

	public static String serialize(final String tab, final IElement e) {
		return HtmlSerializer.serialize("", tab, false, e);
	}

	public static String serialize(final String indent, final String tab, final IElement e) {
		return HtmlSerializer.serialize(indent, tab, false, e);
	}

	public static String serialize(
		final String indent, final String tab, final boolean noemptytag, final IElement e) {
		final StringPrintWriter w = new StringPrintWriter();
		e.accept(new HtmlSerializer<StringPrintWriter>(tab).indent(indent).noEmptyTag(noemptytag), w);
		return w.toString();
	}

	//////////////////////////////////////////////////////////////////////

	public HtmlSerializer() {
	}

	public HtmlSerializer(final String tab) {
		this.tab = tab;
	}

	//////////////////////////////////////////////////////////////////////

	public HtmlSerializer<T> indent(final String indent) {
		this.indent = indent;
		return this;
	}

	public HtmlSerializer<T> tab(final String tab) {
		this.tab = tab;
		return this;
	}

	public HtmlSerializer<T> noEmptyTag(final boolean noemptytag) {
		this.noEmptyTag = noemptytag;
		return this;
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public void visit(final ITop node, final T data) {
		for (final IChild c: node.children()) {
			c.accept(this, data);
		}
		if (!newline || line.length() != 0) {
			lb(data);
	}}

	@Override
	public void visit(final IElement node, final T data) {
		visitElement(node, data);
	}

	@Override
	public void visit(final IText node, final T data) {
		content(node.getContent());
	}

	@Override
	public void visit(final ICData node, final T data) {
		line.append(node.toString());
	}

	@Override
	public void visit(final IComment node, final T data) {
		line.append(node.toString());
	}

	@Override
	public void visit(final ILine node, final T data) {
		line.append(node.toString());
		lb(data);
	}

	@Override
	public void visit(final IPI node, final T data) {
		line.append(node.toString());
		lb(data);
	}

	@Override
	public void visit(final IDeclaration node, final T data) {
		line.append(node.toString());
		lb(data);
	}

	@Override
	public void visit(final IChild node, final T data) {
		throw new AssertionError("Should not reach here");
	}

	//////////////////////////////////////////////////////////////////////

	protected void attributes(final T w, final IElement node) {
		for (final IAttribute a: node.attributes()) {
			if (Attribute.LB == a) {
				lb(w);
			} else {
				line.append(" ");
				line.append(a.aname());
				line.append("=");
				line.append('"');
				line.append(SafeHtmlUtils.htmlEscape(a.avalue()));
				line.append('"');
	}}}

	protected void visitElement(final IElement node, final T data) {
		final String name = node.tag();
		final TAG tag = TAG.get(name);
		if (tag != null && tag.isBlockFormat()) {
			if (!preformatted) {
				lb(data);
			}
			inlineElement(node, data, name, tag);
			if (!preformatted) {
				lb(data);
		}} else {
			inlineElement(node, data, name, tag);
	}}

	private void inlineElement(final IElement node, final T data, final String name, final TAG tag) {
		line.append("<" + name);
		attributes(data, node);
		if (node.childCount() > 0) {
			line.append(">");
			stack.push(name);
			final boolean saved = preformatted;
			final boolean now = tag != null && tag.isPreFormatted();
			if (!saved && now) {
				flush(data);
			}
			preformatted = saved | now;
			for (final IChild c: node.children()) {
				c.accept(this, data);
			}
			stack.pop();
			preformatted = saved;
			line.append("</");
			line.append(name);
			line.append(">");
		} else if (tag.isEmpty()) {
			// For html doctype, browser (eg. firefox) might render <br></br> as <br><br>
			line.append(">");
		} else if (noEmptyTag) {
			line.append("></");
			line.append(name);
			line.append(">");
		} else {
			line.append(" />");
	}}

	protected void content(final String[] content) {
		final int len = content.length;
		if (len == 1) {
			line.append(content[0]);
		} else if (len > 0) {
			line.append(GwtTextUtil.join(getindent(), content));
	}}

	protected void indent() {
		if (tab.length() > 0) {
			indent += tab;
	}}

	protected void unindent() {
		final int tablen = tab.length();
		if (tablen > 0) {
			final int len = indent.length() - tablen;
			if (len >= 0) {
				indent = indent.substring(0, indent.length() - tablen);
	}}}

	protected CharSequence getLineBuffer() {
		return line;
	}

	protected int level() {
		return stack.size();
	}

	protected void lb(final T data) {
		if (newline && line.length() == 0) {
			return;
		}
		if (newline && !preformatted) {
			printIndent(data);
		}
		if (line.length() > 0) {
			data.println(line);
		}
		newline = true;
		line.setLength(0);
		startLevel = level();
	}

	protected void newline(final T data) {
		data.println();
		newline = true;
	}

	protected void flush(final T data) {
		if (line.length() == 0) {
			return;
		}
		if (newline && !preformatted) {
			printIndent(data);
		}
		data.print(line);
		newline = false;
		line.setLength(0);
		startLevel = level();
	}

	protected void printIndent(final T data) {
		data.print(indent);
		if (tab != null) {
			int level = level();
			if (level > startLevel) {
				level = startLevel;
			}
			while (--level >= 0) {
				data.print(tab);
	}}}

	private String getindent() {
		final StringBuilder b = new StringBuilder(GwtTextUtil.getLineSeparator());
		b.append(indent);
		if (tab != null) {
			int level = level();
			if (level > startLevel) {
				level = startLevel;
			}
			while (--level >= 0) {
				b.append(tab);
		}}
		return b.toString();
	}

	protected void setNewline(final boolean b) {
		newline = b;
	}

	//////////////////////////////////////////////////////////////////////
}
