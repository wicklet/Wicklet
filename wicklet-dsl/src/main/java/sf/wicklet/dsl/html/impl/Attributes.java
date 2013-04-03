/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.dsl.html.api.Attribute;
import sf.wicklet.dsl.html.api.IAttr;
import sf.wicklet.dsl.html.api.IAttribute;
import sf.wicklet.dsl.html.api.IAttributes;
import sf.wicklet.dsl.html.api.IElement;

public class Attributes implements IAttributes {

	//////////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 1L;
	private boolean linebreaks;
	private boolean broken = true;
	private final List<IAttribute> attributes = new ArrayList<IAttribute>(4);

	public Attributes() {
	}

	public Attributes(final boolean linebreaks) {
		this.linebreaks = linebreaks;
	}

	public Attributes(final IAttribute attr) {
		a1(attr);
	}

	public Attributes(final IAttribute...attrs) {
		aa(attrs);
	}

	public Attributes(final boolean linebreaks, final IAttribute attr) {
		this.linebreaks = linebreaks;
		a1(attr);
	}

	public Attributes(final boolean linebreaks, final IAttribute...attrs) {
		this.linebreaks = linebreaks;
		aa(attrs);
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public IAttributes lb() {
		if (!broken) {
			attributes.add(Attribute.LB);
			broken = true;
		}
		return this;
	}

	@Override
	public IAttributes a(final IAttribute attr) {
		return a1(attr);
	}

	@Override
	public IAttributes a(final IAttribute...attrs) {
		for (final IAttribute attr: attrs) {
			a1(attr);
		}
		return this;
	}

	@Override
	public IAttributes a(final IAttributes attrs) {
		for (final IAttribute attr: attrs) {
			a1(attr);
		}
		return this;
	}

	@Override
	public IAttributes a(final IAttributes...attrs) {
		for (final IAttributes a: attrs) {
			for (final IAttribute attr: a) {
				a1(attr);
		}}
		return this;
	}

	@Override
	public IAttributes a(final IAttr...attrs) {
		for (final IAttr n: attrs) {
			n.addTo(this);
		}
		return this;
	}

	@Override
	public IAttributes a(final Object name, final boolean value) {
		return a1(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes a(final Object name, final int value) {
		return a1(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes a(final Object name, final long value) {
		return a1(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes a(final Object name, final float value) {
		return a1(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes a(final Object name, final double value) {
		return a1(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes a(final Object name, final Object value) {
		return a1(new Attribute(name, value));
	}

	@Override
	public IAttributes a(final Object name, final Object...value) {
		return a1(new Attribute(name, value));
	}

	@Override
	public IAttributes a(final String name, final boolean value) {
		return a1(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes a(final String name, final int value) {
		return a1(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes a(final String name, final long value) {
		return a1(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes a(final String name, final float value) {
		return a1(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes a(final String name, final double value) {
		return a1(new Attribute(name, String.valueOf(value)));
	}

	@Override
	public IAttributes a(final String name, final Object value) {
		return a1(new Attribute(name, value));
	}

	@Override
	public IAttributes a(final String name, final Object...value) {
		return a1(new Attribute(name, value));
	}

	@Override
	public IAttributes xmlns(final String url) {
		return a1(new Xmlns("xmlns", url));
	}

	@Override
	public IAttributes xmlns(final String name, final String url) {
		return a1(new Xmlns("xmlns:" + name, url));
	}

	@Override
	public IAttributes id(final Object id) {
		return a1(new Attribute("id", id));
	}

	@Override
	public IAttributes css(final Object css) {
		return a1(new Attribute("class", css));
	}

	@Override
	public IAttributes css(final Object...classes) {
		return a1(new Attribute("class", TextUtil.join(" ", classes)));
	}

	@Override
	public IAttributes type(final Object type) {
		return a1(new Attribute("type", type));
	}

	@Override
	public IAttributes name(final Object name) {
		return a1(new Attribute("name", name));
	}

	@Override
	public IAttributes value(final Object value) {
		return a1(new Attribute("value", value));
	}

	@Override
	public IAttributes label(final Object label) {
		return a1(new Attribute("label", label));
	}

	@Override
	public IAttributes checked() {
		return a1(new Attribute("checked", "true"));
	}

	@Override
	public IAttributes selected() {
		return a1(new Attribute("selected", "true"));
	}

	@Override
	public IAttributes width(final Object width) {
		return a1(new Attribute("width", width));
	}

	@Override
	public IAttributes href(final Object url) {
		return a1(new Attribute("href", url));
	}

	@Override
	public IAttributes src(final Object url) {
		return a1(new Attribute("src", url));
	}

	@Override
	public IAttributes style(final Object style) {
		return a1(new Attribute("style", style));
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public IAttributes onload(final String script) {
		return a1(new Attribute("onload", script));
	}

	@Override
	public IAttributes onunload(final String script) {
		return a1(new Attribute("onunload", script));
	}

	@Override
	public IAttributes onclick(final String script) {
		return a1(new Attribute("onclick", script));
	}

	@Override
	public IAttributes ondblclick(final String script) {
		return a1(new Attribute("ondblclick", script));
	}

	@Override
	public IAttributes onmousedown(final String script) {
		return a1(new Attribute("onmousedown", script));
	}

	@Override
	public IAttributes onmouseup(final String script) {
		return a1(new Attribute("onmouseup", script));
	}

	@Override
	public IAttributes onmouseover(final String script) {
		return a1(new Attribute("onmouseover", script));
	}

	@Override
	public IAttributes onmousemove(final String script) {
		return a1(new Attribute("onmousemove", script));
	}

	@Override
	public IAttributes onmouseout(final String script) {
		return a1(new Attribute("onmouseout", script));
	}

	@Override
	public IAttributes onfocus(final String script) {
		return a1(new Attribute("onfocus", script));
	}

	@Override
	public IAttributes onblur(final String script) {
		return a1(new Attribute("onblur", script));
	}

	@Override
	public IAttributes onkeypress(final String script) {
		return a1(new Attribute("onkeypress", script));
	}

	@Override
	public IAttributes onkeydown(final String script) {
		return a1(new Attribute("onkeydown", script));
	}

	@Override
	public IAttributes onkeyup(final String script) {
		return a1(new Attribute("onkeyup", script));
	}

	@Override
	public IAttributes onsubmit(final String script) {
		return a1(new Attribute("onsubmit", script));
	}

	@Override
	public IAttributes onreset(final String script) {
		return a1(new Attribute("onreset", script));
	}

	@Override
	public IAttributes onselect(final String script) {
		return a1(new Attribute("onselect", script));
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public IAttributes onload(final String...scripts) {
		return a1(new Attribute("onload", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onunload(final String...scripts) {
		return a1(new Attribute("onunload", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onclick(final String...scripts) {
		return a1(new Attribute("onclick", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes ondblclick(final String...scripts) {
		return a1(new Attribute("ondblclick", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onmousedown(final String...scripts) {
		return a1(new Attribute("onmousedown", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onmouseup(final String...scripts) {
		return a1(new Attribute("onmouseup", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onmouseover(final String...scripts) {
		return a1(new Attribute("onmouseover", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onmousemove(final String...scripts) {
		return a1(new Attribute("onmousemove", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onmouseout(final String...scripts) {
		return a1(new Attribute("onmouseout", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onfocus(final String...scripts) {
		return a1(new Attribute("onfocus", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onblur(final String...scripts) {
		return a1(new Attribute("onblur", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onkeypress(final String...scripts) {
		return a1(new Attribute("onkeypress", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onkeydown(final String...scripts) {
		return a1(new Attribute("onkeydown", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onkeyup(final String...scripts) {
		return a1(new Attribute("onkeyup", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onsubmit(final String...scripts) {
		return a1(new Attribute("onsubmit", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onreset(final String...scripts) {
		return a1(new Attribute("onreset", TextUtil.join(" ", scripts)));
	}

	@Override
	public IAttributes onselect(final String...scripts) {
		return a1(new Attribute("onselect", TextUtil.join(" ", scripts)));
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public void addTo(final IAttributes attrs) {
		for (final IAttribute a: attributes) {
			attrs.a(a);
	}}

	@Override
	public void addTo(final IElement e) {
		for (final IAttribute a: attributes) {
			e.a(a);
	}}

	//////////////////////////////////////////////////////////////////////

	@Override
	public Iterator<IAttribute> iterator() {
		return attributes.iterator();
	}

	//////////////////////////////////////////////////////////////////////

	private void aa(final IAttribute...attrs) {
		for (final IAttribute attr: attrs) {
			a1(attr);
	}}

	private IAttributes a1(final IAttribute attr) {
		if (linebreaks && !broken) {
			attributes.add(Attribute.LB);
		}
		attributes.add(attr);
		broken = false;
		return this;
	}

	//////////////////////////////////////////////////////////////////////
}
