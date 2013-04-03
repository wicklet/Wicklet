/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.api;

public interface IAttributes extends IAttr, Iterable<IAttribute> {

	public abstract IAttributes lb();

	public abstract IAttributes a(IAttribute attr);
	public abstract IAttributes a(IAttribute...attrs);
	public abstract IAttributes a(IAttributes attrs);
	public abstract IAttributes a(IAttributes...attrs);
	public abstract IAttributes a(IAttr...attrs);
	public abstract IAttributes a(String name, boolean value);
	public abstract IAttributes a(String name, int value);
	public abstract IAttributes a(String name, long value);
	public abstract IAttributes a(String name, float value);
	public abstract IAttributes a(String name, double value);
	public abstract IAttributes a(String name, Object value);
	public abstract IAttributes a(String name, Object...values);
	public abstract IAttributes a(Object name, boolean value);
	public abstract IAttributes a(Object name, int value);
	public abstract IAttributes a(Object name, long value);
	public abstract IAttributes a(Object name, float value);
	public abstract IAttributes a(Object name, double value);
	public abstract IAttributes a(Object name, Object value);
	public abstract IAttributes a(Object name, Object...values);
	public abstract IAttributes xmlns(String url);
	public abstract IAttributes xmlns(String name, String url);
	public abstract IAttributes id(Object id);
	public abstract IAttributes css(Object css);
	public abstract IAttributes css(Object...csss);
	public abstract IAttributes type(Object type);
	public abstract IAttributes name(Object name);
	public abstract IAttributes value(Object value);
	public abstract IAttributes label(Object label);
	public abstract IAttributes width(Object width);
	public abstract IAttributes href(Object url);
	public abstract IAttributes src(Object url);
	public abstract IAttributes style(Object style);
	public abstract IAttributes checked();
	public abstract IAttributes selected();
	// Events
	public abstract IAttributes onload(String script);
	public abstract IAttributes onunload(String script);
	public abstract IAttributes onclick(String script);
	public abstract IAttributes ondblclick(String script);
	public abstract IAttributes onmousedown(String script);
	public abstract IAttributes onmouseup(String script);
	public abstract IAttributes onmouseover(String script);
	public abstract IAttributes onmousemove(String script);
	public abstract IAttributes onmouseout(String script);
	public abstract IAttributes onfocus(String script);
	public abstract IAttributes onblur(String script);
	public abstract IAttributes onkeypress(String script);
	public abstract IAttributes onkeydown(String script);
	public abstract IAttributes onkeyup(String script);
	public abstract IAttributes onsubmit(String script);
	public abstract IAttributes onreset(String script);
	public abstract IAttributes onselect(String script);
	//
	public abstract IAttributes onload(String...scripts);
	public abstract IAttributes onunload(String...scripts);
	public abstract IAttributes onclick(String...scripts);
	public abstract IAttributes ondblclick(String...scripts);
	public abstract IAttributes onmousedown(String...scripts);
	public abstract IAttributes onmouseup(String...scripts);
	public abstract IAttributes onmouseover(String...scripts);
	public abstract IAttributes onmousemove(String...scripts);
	public abstract IAttributes onmouseout(String...scripts);
	public abstract IAttributes onfocus(String...scripts);
	public abstract IAttributes onblur(String...scripts);
	public abstract IAttributes onkeypress(String...scripts);
	public abstract IAttributes onkeydown(String...scripts);
	public abstract IAttributes onkeyup(String...scripts);
	public abstract IAttributes onsubmit(String...scripts);
	public abstract IAttributes onreset(String...scripts);
	public abstract IAttributes onselect(String...scripts);
}
