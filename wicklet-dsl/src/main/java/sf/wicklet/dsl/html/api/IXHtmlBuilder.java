/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.api;

import sf.blacksun.util.text.DOCTYPE;

public interface IXHtmlBuilder<E extends IElement> {

	public abstract E elm(String name, INode...children);
	public abstract E empty(String name, IAttr...attrs);
	public abstract ITop top(INode...children);

	public abstract IPI xml();
	public abstract IPI xml(String encoding);
	public abstract IPI xml(String version, String encoding);
	public abstract IDeclaration declaration(String name, String...content);
	public abstract IDeclaration doctype(DOCTYPE doctype);

	public abstract E html(INode...children);
	public abstract E head(INode...children);
	public abstract E title(String...content);
	public abstract E meta(IAttr...attributes);
	public abstract E link(IAttr...attributes);
	public abstract E base(IAttr...attributes);
	public abstract E style(INode...children);
	public abstract E script(INode...children);
	public abstract E body(INode...children);
	public abstract E div(INode...children);
	public abstract E span(INode...children);
	public abstract E h1(INode...children);
	public abstract E h2(INode...children);
	public abstract E h3(INode...children);
	public abstract E h4(INode...children);
	public abstract E h5(INode...children);
	public abstract E h6(INode...children);
	public abstract E b(INode...children);
	public abstract E em(INode...children);
	public abstract E code(INode...children);
	public abstract E pre(INode...children);
	public abstract E a(INode...children);
	public abstract E img(IAttr...children);
	public abstract E br(IAttr...attributes);
	public abstract E hr(IAttr...attributes);
	public abstract E p(INode...children);
	public abstract E blockquote(INode...children);
	// List elements
	public abstract E ul(INode...children);
	public abstract E li(INode...children);
	public abstract E ol(INode...children);
	// Table elements
	public abstract E table(INode...children);
	public abstract E thead(INode...children);
	public abstract E tbody(INode...children);
	public abstract E tfoot(INode...children);
	public abstract E colgroup(INode...children);
	public abstract E colgroup(String width1, String...widths);
	public abstract E col(IAttr...attributes);
	public abstract E tr(INode...children);
	public abstract E th(INode...children);
	public abstract E td(INode...children);
	// Form elements
	public abstract E form(INode...children);
	public abstract E input(IAttr...attributes);
	public abstract E button(INode...children);
	public abstract E optgroup(INode...children);
	public abstract E option(INode...children);
	public abstract E select(INode...children);
	public abstract E textarea(INode...children);
	//
	public abstract E noscript(INode...children);
	public abstract E iframe(INode...children);
	public abstract E applet(INode...children);
	public abstract E object(INode...children);
	public abstract E embed(IAttr...attributes);
	public abstract E style(String...content);
	public abstract E script(String...content);
	public abstract E pre(String...children);
	// Text
	/** Emit content as is, ie. even if it contains tags. */
	public abstract IText raw(String...content);
	/** Emit content as is, same as raw() but with intention that content don't contain tags. */
	public abstract IText txt(String...content);
	/** Escape content and emit as literal text. */
	public abstract IText esc(String...content);
	public abstract ICData cdata(String...content);
	public abstract IComment comment(String...content);
	public abstract IPI pi(String target, String...content);
	//
	// Attributes
	public abstract IAttributes atts();
	public abstract IAttributes atts(Object name, boolean value);
	public abstract IAttributes atts(Object name, int value);
	public abstract IAttributes atts(Object name, long value);
	public abstract IAttributes atts(Object name, float value);
	public abstract IAttributes atts(Object name, double value);
	public abstract IAttributes atts(Object name, Object value);
	public abstract IAttributes atts(IAttribute attr);
	public abstract IAttributes atts(IAttribute...attrs);
	public abstract IAttributes atts(Object...namevalues);
	public abstract IAttributes atts(boolean linebreaks);
	public abstract IAttributes lb(Object...namevalues);
	public abstract IAttributes lb(IAttribute...attrs);
	public abstract IAttribute attr(Object name, boolean value);
	public abstract IAttribute attr(Object name, int value);
	public abstract IAttribute attr(Object name, long value);
	public abstract IAttribute attr(Object name, float value);
	public abstract IAttribute attr(Object name, double value);
	public abstract IAttribute attr(Object name, Object value);
	public abstract IAttribute attr(Object name, Object...values);
	// Attribute shortcut methods
	public abstract IAttribute id(Object value);
	public abstract IAttribute type(Object value);
	public abstract IAttribute name(Object value);
	public abstract IAttribute value(Object value);
	public abstract IAttribute href(Object value);
	public abstract IAttribute src(Object value);
	public abstract IAttribute css(Object value);
	public abstract IAttribute css(Object...values);
	/** Inline style specification. */
	public abstract IAttribute istyle(Object style);
	public abstract IAttribute istyle(Object...style);
	//
	public abstract IAttribute onload(String script);
	public abstract IAttribute onunload(String script);
	public abstract IAttribute onclick(String script);
	public abstract IAttribute ondblclick(String script);
	public abstract IAttribute onmousedown(String script);
	public abstract IAttribute onmouseup(String script);
	public abstract IAttribute onmouseover(String script);
	public abstract IAttribute onmousemove(String script);
	public abstract IAttribute onmouseout(String script);
	public abstract IAttribute onfocus(String script);
	public abstract IAttribute onblur(String script);
	public abstract IAttribute onkeypress(String script);
	public abstract IAttribute onkeydown(String script);
	public abstract IAttribute onkeyup(String script);
	public abstract IAttribute onsubmit(String script);
	public abstract IAttribute onreset(String script);
	public abstract IAttribute onselect(String script);
	// Element shortcut methods
	public abstract E contenttype(Object type);
	public abstract E stylesheet(Object href);
	public abstract E javascript(Object src);
	public abstract E stylesheet();
	public abstract E javascript();
	public abstract E vbox(IElement...children);
	public abstract E hbox(IElement...children);
	//
	/** Force a line break at the output. */
	public abstract IText lb();
}
