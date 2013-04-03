/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.components.link;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;

public abstract class WixAjaxLink<T> extends AjaxLink<T> {

	private static final long serialVersionUID = 1L;
	private String href = "#";

	public WixAjaxLink(final Object wid) {
		super(wid.toString());
	}

	/**
	 * Use a more descriptive link rather than "#".
	 * Note that the href attribute of an AjaxLink has no effect anyway.
	 */
	public WixAjaxLink<T> href(final String href) {
		this.href = href;
		return this;
	}

	@Override
	protected void onComponentTag(final ComponentTag tag) {
		super.onComponentTag(tag);
		if (isLinkEnabled()) {
			tag.put("href", href);
	}}
}
