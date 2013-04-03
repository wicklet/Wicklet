/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.api;

import org.apache.wicket.markup.IRootMarkup;

public interface IWicketBuilder extends IXHtmlBuilder<IElement> {
	public abstract IWicketElement wicket(Object name, INode...children);
	public abstract IWicketElement wicketPanel(INode...children);
	public abstract IWicketComponent component(String wicketid, IElement tag);
	public abstract IWicketComponent component(Object wicketid, IElement tag);
	/**
	 * Create IWicketComponent using component(id, tag, children(...)) syntax.
	 * This is more efficient.
	 */
	public abstract IWicketComponent component(String wicketid, Object tagname, INode...children);
	public abstract IWicketComponent component(Object wicketid, Object tagname, INode...children);
	/**
	 * Create IWicketComponent using component(id).tag(...) syntax.
	 * This is less efficient, but has more compact syntax and allow using dsl methods instead of textual tag name.
	 * @param wicketid Auto-generated if null.
	 * @return A custom IXHtmlDocument that generate IWicketComponent instead of IElement.
	 */
	public abstract IXHtmlBuilder<IWicketComponent> component(Object wicketid);
	public abstract IRootMarkup serialize(IElement e);
}
