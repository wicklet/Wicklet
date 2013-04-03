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
import sf.wicklet.dsl.html.api.IAttr;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.api.INode;
import sf.wicklet.dsl.html.api.IWicketBuilder;
import sf.wicklet.dsl.html.api.IWicketComponent;
import sf.wicklet.dsl.html.api.IWicketElement;
import sf.wicklet.dsl.html.api.IXHtmlBuilder;
import sf.wicklet.wicketext.markup.api.IXmlUtil;

public class WicketBuilder extends XHtmlBuilderBase<IElement> implements IWicketBuilder {

	//////////////////////////////////////////////////////////////////////

	// org.apache.wicket.markup.parser.filter.WicketLinkTagHandler.AUTOLINK_ID
	private int wicketIdCounter = 0;

	//////////////////////////////////////////////////////////////////////

	@Override
	public IElement elm(final String name, final INode...children) {
		return new Element(name, children);
	}

	@Override
	public IElement empty(final String name, final IAttr...attrs) {
		return new EmptyElement(name, attrs);
	}

	@Override
	public IWicketElement wicket(final Object tag, final INode...children) {
		final IWicketElement e = new WicketElement(tag.toString());
		for (final INode child: children) {
			child.addTo(e);
		}
		return e;
	}

	@Override
	public IWicketElement wicketPanel(final INode...children) {
		final IWicketElement e = new WicketElement(Wicket.panel.name());
		for (final INode child: children) {
			child.addTo(e);
		}
		return e;
	}

	@Override
	public IWicketComponent component(final String wicketid, final Object tag, final INode...children) {
		if (wicketid == null) {
			return new WicketComponent(wicketid(), tag, children).setAuto(true);
		}
		return new WicketComponent(wicketid, tag, children);
	}

	@Override
	public IWicketComponent component(final String wicketid, final IElement e) {
		if (wicketid == null) {
			return new WicketComponent(wicketid(), e).setAuto(true);
		}
		return new WicketComponent(wicketid, e);
	}

	@Override
	public IWicketComponent component(final Object wicketid, final Object tag, final INode...children) {
		if (wicketid == null) {
			return new WicketComponent(wicketid(), tag, children).setAuto(true);
		}
		return new WicketComponent(wicketid, tag, children);
	}

	@Override
	public IWicketComponent component(final Object wicketid, final IElement e) {
		if (wicketid == null) {
			return new WicketComponent(wicketid(), e).setAuto(true);
		}
		return new WicketComponent(wicketid, e);
	}

	@Override
	public IXHtmlBuilder<IWicketComponent> component(final Object wicketid) {
		if (wicketid == null) {
			throw new AssertionError("ERROR: wicketid must not be null");
		}
		return new ComponentBuilder(wicketid);
	}

	@Override
	public IRootMarkup serialize(final IElement e) {
		return WicketSerializer.serialize(e);
	}

	//////////////////////////////////////////////////////////////////////

	@Override
	public IElement head(final INode...children) {
		return new WicketComponent(WID.HEADER, "head", children).setAuto(true);
	}

	@Override
	public IElement link(final IAttr...attributes) {
		return new WicketComponent(WID.RELATIVE_PATH, "link", attributes).setAuto(true);
	}

	@Override
	public IElement style(final INode...children) {
		return new WicketComponent(WID.SCRIPT_STYLE, "style", children).setAuto(true).setFlags(
			ComponentTag.RENDER_RAW);
	}

	@Override
	public IElement script(final INode...children) {
		return new WicketComponent(WID.SCRIPT_STYLE, "script", children).setAuto(true).setFlags(
			ComponentTag.RENDER_RAW);
	}

	@Override
	public IElement body(final INode...children) {
		return super.body(children);
	}

	//////////////////////////////////////////////////////////////////////

	public Object wicketid() {
		return WID.of(String.format("autowicketid%04d", (++wicketIdCounter)));
	}

	//////////////////////////////////////////////////////////////////////

	private class ComponentBuilder extends XHtmlBuilderBase<IWicketComponent> {
		private final Object wicketid;
		public ComponentBuilder(final Object wicketid) {
			this.wicketid = wicketid;
		}
		@Override
		public IWicketComponent elm(final String name, final INode...children) {
			if (wicketid == null) {
				return new WicketComponent(wicketid(), name, children).setAuto(true);
			}
			return new WicketComponent(wicketid, name, children);
		}
		@Override
		public IWicketComponent empty(final String name, final IAttr...attributes) {
			if (wicketid == null) {
				return new WicketComponent(wicketid(), name, attributes).setAuto(true);
			}
			return new WicketComponent(wicketid, name, attributes);
		}
		@Override
		protected IXmlUtil getXmlUtil() {
			return WicketBuilder.this.getXmlUtil();
		}
	}
}
