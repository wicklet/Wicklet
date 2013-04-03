/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.dsl.gwt;

import sf.wicklet.gwt.client.dsl.gwt.GwtBuilder.GwtPanel.WickletFlowPanel;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WickletGwt {

	private static final boolean DEBUG = false;

	public static class WickletTemplateHtmlPanel extends HTMLPanel {
		public WickletTemplateHtmlPanel(final String html) {
			super(html);
		}
		@Override
		public void add(final Widget child) {
			final Element elm = getElement();
			Element parent = elm;
			// If child is already attached to a child element of this panel, keep it there.
			final Element ce = child.getElement();
			if (ce != null) {
				final com.google.gwt.dom.client.Element cp = ce.getParentElement();
				for (com.google.gwt.dom.client.Element e = cp; e != null; e = e.getParentElement()) {
					if (e == elm) {
						parent = cp.cast();
						break;
			}}}
			add(child, parent);
		}
		@Override
		public void add(final Widget child, final Element parent) {
			// Logical attach.
			getChildren().add(child);
			final Element ce = child.getElement();
			// Only reattach if not already connected to specified parent.
			if (ce == null || ce.getParentElement() != parent) {
				// Physical attach.
				child.removeFromParent();
				DOM.appendChild(parent, ce);
			}
			// Adopt.
			adopt(child);
		}
		@Override
		protected void onAttach() {
			if (DEBUG) {
				new Throwable().printStackTrace(System.out);
			}
			super.onAttach();
		}
	}

	public static class WickletTemplateFlowPanel extends WickletFlowPanel {
		public WickletTemplateFlowPanel(final Element peer) {
			super(peer);
		}
		@Override
		public void add(final Widget child) {
			final Element elm = getElement();
			Element parent = elm;
			// If child is already attached to a child element of this panel, keep it there.
			final Element ce = child.getElement();
			if (ce != null) {
				final com.google.gwt.dom.client.Element cp = ce.getParentElement();
				for (com.google.gwt.dom.client.Element e = cp; e != null; e = e.getParentElement()) {
					if (e == elm) {
						parent = cp.cast();
						break;
			}}}
			add(child, parent);
		}
		@Override
		public void add(final Widget child, final Element parent) {
			// Logical attach.
			getChildren().add(child);
			final Element ce = child.getElement();
			// Only reattach if not already connected to specified parent.
			if (ce == null || ce.getParentElement() != parent) {
				// Physical attach.
				child.removeFromParent();
				DOM.appendChild(parent, ce);
			}
			// Adopt.
			adopt(child);
		}
		@Override
		protected void onAttach() {
			if (DEBUG) {
				new Throwable().printStackTrace(System.out);
			}
			super.onAttach();
		}
	}

	public static class WickletHorizontalPanel extends HorizontalPanel {
		public WickletHorizontalPanel() {
			super();
		}
		@Override
		public void add(final Widget child) {
			final Element elm = getElement();
			Element parent = elm;
			// If child is already attached to a child element of this panel, keep it there.
			final Element ce = child.getElement();
			if (ce != null) {
				final com.google.gwt.dom.client.Element cp = ce.getParentElement();
				for (com.google.gwt.dom.client.Element e = cp; e != null; e = e.getParentElement()) {
					if (e == elm) {
						parent = cp.cast();
						break;
			}}}
			add(child, parent);
		}
		@Override
		public void add(final Widget child, final Element parent) {
			// Logical attach.
			getChildren().add(child);
			final Element ce = child.getElement();
			// Only reattach if not already connected to specified parent.
			if (ce == null || ce.getParentElement() != parent) {
				// Physical attach.
				child.removeFromParent();
				DOM.appendChild(parent, ce);
			}
			// Adopt.
			adopt(child);
		}
	}

	public static class WickletVerticalPanel extends VerticalPanel {
		public WickletVerticalPanel() {
			super();
		}
		@Override
		public void add(final Widget child) {
			final Element elm = getElement();
			Element parent = elm;
			// If child is already attached to a child element of this panel, keep it there.
			final Element ce = child.getElement();
			if (ce != null) {
				final com.google.gwt.dom.client.Element cp = ce.getParentElement();
				for (com.google.gwt.dom.client.Element e = cp; e != null; e = e.getParentElement()) {
					if (e == elm) {
						parent = cp.cast();
						break;
			}}}
			add(child, parent);
		}
		@Override
		public void add(final Widget child, final Element parent) {
			// Logical attach.
			getChildren().add(child);
			final Element ce = child.getElement();
			// Only reattach if not already connected to specified parent.
			if (ce == null || ce.getParentElement() != parent) {
				// Physical attach.
				child.removeFromParent();
				DOM.appendChild(parent, ce);
			}
			// Adopt.
			adopt(child);
		}
	}

	public static class WickletLabel extends InlineLabel {
		public WickletLabel(final String text) {
			super(text);
		}
		public WickletLabel(final Element e) {
			super(e);
		}
	}

	public static class WickletHyperLink extends Hyperlink {
		public WickletHyperLink(final String text, final String token) {
			super(null);
			setText(text);
			setTargetHistoryToken(token);
		}
		public WickletHyperLink(final SafeHtml html, final String token) {
			this(html.asString(), token);
		}
		public WickletHyperLink(final Element e, final String text, final String token) {
			super(e);
			setText(text);
			setTargetHistoryToken(token);
		}
		public WickletHyperLink(final Element e, final SafeHtml html, final String token) {
			this(e, html.asString(), token);
		}
	}

	public static class WickletAnchor extends Anchor {
		public WickletAnchor(final SafeHtml text) {
			super(text);
		}
		public WickletAnchor(final SafeHtml text, final String href) {
			super(text, href);
		}
		public WickletAnchor(final Element e) {
			super(e);
		}
		public WickletAnchor(final Element e, final String href) {
			super(e);
			setHref(href);
		}
		@Override
		protected void onAttach() {
			if (DEBUG) {
				new Throwable().printStackTrace(System.out);
			}
			super.onAttach();
		}
	}

	public static class WickletFormPanel extends FormPanel {
		public WickletFormPanel() {
		}
		public WickletFormPanel(final Element e) {
			super(e);
		}
	}

	public static class WickletTextBox extends TextBox {
		public WickletTextBox() {
		}
		public WickletTextBox(final String text) {
			if (text != null) {
				setText(text);
			}
		}
		public WickletTextBox(final Element e, final String text) {
			super(e);
			if (text != null) {
				setText(text);
			}
		}
	}

	public static class WickletPassword extends PasswordTextBox {
		public WickletPassword() {
		}
		public WickletPassword(final String text) {
			if (text != null) {
				setText(text);
			}
		}
		public WickletPassword(final Element e, final String text) {
			super(e);
			if (text != null) {
				setText(text);
			}
		}
	}

	public static class WickletButton extends Button {
		public WickletButton() {
		}
		public WickletButton(final String text) {
			if (text != null) {
				setText(text);
			}
		}
		public WickletButton(final Element e, final String text) {
			super(e);
			if (text != null) {
				setText(text);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////
}
