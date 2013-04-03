/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.dsl.gwt;

import java.util.ArrayList;
import java.util.List;
import sf.wicklet.gwt.client.HistoryChangeListener;
import sf.wicklet.gwt.client.ajax.WickletAjax;
import sf.wicklet.gwt.client.dsl.IIDProvider;
import sf.wicklet.gwt.client.dsl.gwt.GwtBuilder.GwtPanel.WickletFlowPanel;
import sf.wicklet.gwt.client.dsl.gwt.WickletGwt.WickletTemplateFlowPanel;
import sf.wicklet.gwt.client.dsl.gwt.WickletGwt.WickletTemplateHtmlPanel;
import sf.wicklet.gwt.client.util.GwtTextUtil;
import sf.wicklet.gwt.client.util.IRunnable;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Node;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragEvent;
import com.google.gwt.event.dom.client.DragHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.i18n.shared.DirectionEstimator;
import com.google.gwt.i18n.shared.HasDirectionEstimator;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.AttachDetachException;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.DirectionalTextHelper;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasDirectionalSafeHtml;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListenerWrapper;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.HyperlinkImpl;

public class GwtBuilder implements IGwtBuilder {

	////////////////////////////////////////////////////////////////////////

	@Override
	public Widget serialize(final IGwtWidget e) {
		return e.asWidget();
	}

	@Override
	public void ajax(final Method method, final String url, final String data, final IWickletAjaxCallback callback) {
		WickletAjax.ajax(method, url, data, callback);
	}

	@Override
	public void ajax(final String url, final String data, final IWickletAjaxCallback callback) {
		WickletAjax.ajax(RequestBuilder.POST, url, data, callback);
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public IGwtTop top(final IGwtChild...children) {
		return new GwtTop(children);
	}

	@Override
	public IGwtRoot rootpanel(final IGwtChild...children) {
		return new GwtRoot(null, children);
	}

	@Override
	public IGwtRoot rootpanel(final Object id, final IGwtChild...children) {
		return new GwtRoot(id, children);
	}

	@Override
	public IGwtComplexPanel panel(final IGwtChild...children) {
		return new GwtPanel(children);
	}

	@Override
	public IGwtComplexPanel panel(final Element peer, final IGwtChild...children) {
		return new GwtPanel(peer, children);
	}

	@Override
	public IGwtComplexPanel ipanel(final IGwtChild...children) {
		return new GwtPanel(new WickletFlowPanel(DOM.createSpan()), children);
	}

	@Override
	public IGwtComplexPanel htmlpanel(final String html, final IGwtChild...children) {
		return new GwtPanel(new HTMLPanel(html), children);
	}

	@Override
	public IGwtComplexPanel templatepanel(final Element peer, final IGwtChild...children) {
		final IGwtComplexPanel ret = new GwtPanel(new WickletTemplateFlowPanel(peer));
		ret.c(children);
		return ret;
	}

	@Override
	public IGwtComplexPanel templatepanel(final String html, final IGwtChild...children) {
		final IGwtComplexPanel ret = new GwtPanel(new WickletTemplateHtmlPanel(html));
		ret.c(children);
		return ret;
	}

	@Override
	public IGwtCellPanel hpanel(final IGwtChild...children) {
		return new GwtHorizontalPanel(children);
	}

	@Override
	public IGwtCellPanel vpanel(final IGwtChild...children) {
		return new GwtVerticalPanel(children);
	}

	@Override
	public IGwtGridPanel grid(final int rows, final int cols, final IGwtChild...children) {
		return new GwtGridPanel(rows, cols, children);
	}

	/** @param headersize Header size in px. */
	@Override
	public IGwtStackPanel stackpanel(final IGwtWithHeader...children) {
		return new GwtStackPanel(children);
	}

	@Override
	public IGwtWithHeader header(final String header, final IGwtWidget child) {
		return new GwtWithHeader(header, child);
	}

	@Override
	public IGwtWithHeader header(final String header, final IGwtWidget child, final IRunnable<Element> callback) {
		return new GwtWithHeader(header, child, callback);
	}

	@Override
	public IGwtForm form(final IGwtChild...children) {
		return new GwtForm(children);
	}

	@Override
	public IGwtForm form(final Element peer, final IGwtChild...children) {
		return new GwtForm(peer, children);
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public IGwtMenuBar menubar(final boolean vertical, final IGwtMenuChild...children) {
		return new GwtMenuBar(vertical, children);
	}

	@Override
	public IGwtMenuBar hmenubar(final IGwtMenuChild...children) {
		return new GwtMenuBar(false, children);
	}

	@Override
	public IGwtMenuBar vmenubar(final IGwtMenuChild...children) {
		return new GwtMenuBar(true, children);
	}

	@Override
	public IGwtMenuItem menuitem(final String html, final IGwtMenuBar submenu) {
		return new GwtMenuItem(html, submenu);
	}

	@Override
	public IGwtMenuItem menuitem(final String html, final ScheduledCommand cmd) {
		return new GwtMenuItem(html, cmd);
	}

	@Override
	public IGwtMenuSeparator menusep() {
		return new GwtMenuSeparator();
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public IGwtAnchor anchor(final String text, final IGwtAttr...attrs) {
		final GwtAnchor ret = new GwtAnchor(text, "javascript:");
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtAnchor anchor(final String text, final String href, final IGwtAttr...attrs) {
		final GwtAnchor ret = new GwtAnchor(text, href);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtAnchor anchor(final Element peer, final IGwtAttr...attrs) {
		final GwtAnchor ret = new GwtAnchor(peer);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtButton button(final String text, final IGwtAttr...attrs) {
		final GwtButton ret = new GwtButton(text);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtButton button(final Element peer, final IGwtAttr...attrs) {
		final GwtButton ret = new GwtButton(peer);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtHyperlink hyperlink(final String text, final IGwtAttr...attrs) {
		final GwtHyperLink ret = new GwtHyperLink(text, text);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtHyperlink hyperlink(final String text, final String token, final IGwtAttr...attrs) {
		final GwtHyperLink ret = new GwtHyperLink(text, token);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtHyperlink hyperlink(final Element peer, final IGwtAttr...attrs) {
		final GwtHyperLink ret = new GwtHyperLink(peer);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtImage image(final ImageResource res, final IGwtAttr...attrs) {
		final GwtImage ret = new GwtImage(res);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtImage image(final String url, final IGwtAttr...attrs) {
		final GwtImage ret = new GwtImage(url);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtImage image(final Element peer, final IGwtAttr...attrs) {
		final GwtImage ret = new GwtImage(peer);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtLabel label(final String text, final IGwtAttr...attrs) {
		final GwtLabel ret = new GwtLabel(text);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtLabel label(final Element peer, final IGwtAttr...attrs) {
		final GwtLabel ret = new GwtLabel(peer);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtHtml html(final String text, final IGwtAttr...attrs) {
		final GwtHtml ret = new GwtHtml(text);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtIHtml ihtml(final String text, final IGwtAttr...attrs) {
		final GwtIHtml ret = new GwtIHtml(text);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtTextBox textbox(final String text, final IGwtAttr...attrs) {
		final GwtTextBox ret = new GwtTextBox(text);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtTextBox textbox(final Element peer, final IGwtAttr...attrs) {
		final GwtTextBox ret = new GwtTextBox(peer);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtTextArea textarea(final String text, final IGwtAttr...attrs) {
		final GwtTextArea ret = new GwtTextArea(text);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtTextArea textarea(final Element peer, final IGwtAttr...attrs) {
		final GwtTextArea ret = new GwtTextArea(peer);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtPassword password(final String text, final IGwtAttr...attrs) {
		final GwtPassword ret = new GwtPassword(text);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtPassword password(final Element peer, final IGwtAttr...attrs) {
		final GwtPassword ret = new GwtPassword(peer);
		ret.attr(attrs);
		return ret;
	}

	@Override
	public IGwtWidget hr(final IGwtAttr...attrs) {
		final GwtHr ret = new GwtHr();
		ret.attr(attrs);
		return ret;
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public IGwtAttr attr(final String name, final String value) {
		return new GwtAttr(name, value);
	}

	@Override
	public IGwtAttr id(final String value) {
		return new GwtAttr("id", value);
	}

	@Override
	public IGwtAttr name(final String value) {
		return new GwtAttr("name", value);
	}

	@Override
	public IGwtAttr css(final String value) {
		return new GwtClassAttr(value);
	}

	@Override
	public IGwtAttr istyle(final String value) {
		return new GwtAttr("style", value);
	}

	@Override
	public IGwtAttr attr(final Object name, final Object value) {
		return new GwtAttr(name, value);
	}

	@Override
	public IGwtAttr id(final Object value) {
		return new GwtAttr("id", value);
	}

	@Override
	public IGwtAttr name(final Object value) {
		return new GwtAttr("name", value);
	}

	@Override
	public IGwtAttr css(final Object value) {
		return new GwtClassAttr(value);
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public Element byId(final IIDProvider id) {
		final Element ret = DOM.getElementById(id.toString());
		if (ret == null) {
			throw new AssertionError("ELement not found: id=" + id.toString());
		}
		return ret;
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtAttr implements IGwtAttr {

		private final String name;
		private final String value;

		public GwtAttr(final String name, final String value) {
			this.name = name;
			this.value = value;
		}

		public GwtAttr(final Object name, final Object value) {
			this.name = (name == null ? "" : name.toString());
			this.value = (value == null ? "" : value.toString());
		}

		@Override
		public String aname() {
			return name;
		}

		@Override
		public String avalue() {
			return value;
		}

		@Override
		public void addTo(final IGwtPanel panel) {
			panel.a(this);
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtClassAttr implements IGwtAttr {

		private final String value;

		public GwtClassAttr(final String value) {
			this.value = value;
		}

		public GwtClassAttr(final Object value) {
			this.value = (value == null ? "" : value.toString());
		}

		@Override
		public String aname() {
			return "class";
		}

		@Override
		public String avalue() {
			return value;
		}

		@Override
		public void addTo(final IGwtPanel panel) {
			panel.a(this);
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtHandler<H extends EventHandler> implements IGwtHandler {

		private final GwtEvent.Type<H> type;
		private final H handler;
		private HandlerRegistration registration;

		public GwtHandler(final H handler, final GwtEvent.Type<H> type) {
			this.handler = handler;
			this.type = type;
		}

		public HandlerRegistration getRegistration() {
			return registration;
		}

		@Override
		public void addTo(final IGwtPanel panel) {
			registration = panel.asWidget().addHandler(handler, type);
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtAnchor extends GwtTextWidget implements IGwtAnchor {
		private final Anchor anchor;
		public GwtAnchor(final String text, final String href) {
			anchor = new WickletAnchor(text, href);
		}
		public GwtAnchor(final Element e) {
			anchor = new WickletAnchor(e);
		}
		@Override
		public Widget asWidget() {
			return anchor;
		}
		@Override
		public IGwtAnchor setText(final String text) {
			super.setText(text);
			return this;
		}
		@Override
		public IGwtAnchor setLink(final String url) {
			anchor.setHref(url);
			return this;
		}
		@Override
		public String getLink() {
			return anchor.getHref();
		}
		private static class WickletAnchor extends Anchor {
			public WickletAnchor(final String text, final String href) {
				setText(text);
				setHref(href);
			}
			public WickletAnchor(final Element e) {
				super(e);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtButton extends GwtTextWidget implements IGwtButton {
		private final Button button;
		public GwtButton() {
			button = new WickletButton();
		}
		public GwtButton(final String text) {
			button = new WickletButton(text);
		}
		public GwtButton(final Element e) {
			button = new WickletButton(e);
		}
		@Override
		public Widget asWidget() {
			return button;
		}
		@Override
		public IGwtButton setText(final String text) {
			super.setText(text);
			return this;
		}
		@Override
		public IGwtButton onclick(final ClickHandler handler) {
			super.onclick(handler);
			return this;
		}
		private static class WickletButton extends Button {
			public WickletButton() {
			}
			public WickletButton(final String text) {
				setText(text);
			}
			public WickletButton(final Element e) {
				super(e);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtHyperLink extends GwtTextWidget implements IGwtHyperlink {
		private final WickletHyperlink link;
		public GwtHyperLink(final String text, final String target) {
			link = new WickletHyperlink();
			setText(text);
			setLink(target);
		}
		public GwtHyperLink(final Element e) {
			link = new WickletHyperlink(e);
		}
		@Override
		public Widget asWidget() {
			return link;
		}
		@Override
		public IGwtHyperlink setText(final String text) {
			super.setText(text);
			return this;
		}
		@Override
		public IGwtHyperlink setLink(final String token) {
			link.setTargetHistoryToken(token);
			return this;
		}
		@Override
		public String getLink() {
			return link.getTargetHistoryToken();
		}
		@Override
		public IGwtHyperlink onhistorychange(final ValueChangeHandler<String> h) {
			HistoryChangeListener.get().putHandler(link.getTargetHistoryToken(), h);
			return this;
		}
		@Override
		public IGwtHyperlink onactivate(final IRunnable<String> h) {
			HistoryChangeListener.get().putHandler(
				link.getTargetHistoryToken(),
				new ValueChangeHandler<String>() {
					@Override
					public void onValueChange(final ValueChangeEvent<String> event) {
						h.run(getLink());
					}
				});
			onclick(
				new ClickHandler() {
					@Override
					public void onClick(final ClickEvent event) {
						event.preventDefault();
						final String token = getLink();
						History.newItem(token, false);
						h.run(token);
					}
				});
			return this;
		}
		/**
		 * A slightly hacked version of com.google.gwt.user.client.ui.Hyperlink that
		 * allow using an existing A element instead of creating a new one.
		 * Also it do not automatically create a new history item. Click handlers need to
		 * do that.
		 */
		@SuppressWarnings("deprecation")
		public static class WickletHyperlink
			extends Widget
			implements HasHTML,
				SourcesClickEvents,
				HasClickHandlers,
				HasDirectionEstimator,
				HasDirectionalSafeHtml {

			private static HyperlinkImpl impl = GWT.create(HyperlinkImpl.class);

			protected final DirectionalTextHelper directionalTextHelper;
			private Element anchorElem;
			private String targetHistoryToken;

			public WickletHyperlink() {
				this(null);
			}

			protected WickletHyperlink(final Element elem) {
				if (elem == null) {
					setElement(anchorElem = DOM.createAnchor());
				} else {
					setElement(elem);
					if ("a".equalsIgnoreCase(elem.getTagName())) {
						anchorElem = elem;
					} else {
						DOM.appendChild(getElement(), anchorElem = DOM.createAnchor());
				}}
				sinkEvents(Event.ONCLICK);
				setStyleName("gwt-Hyperlink");
				directionalTextHelper = new DirectionalTextHelper(anchorElem, true);
			}

			/**
			 * @deprecated Use {@link Anchor#addClickHandler} instead and call
			 *     History.newItem from the handler if you need to process the
			 *     click before the history token is set.
			 */
			@Override
			@Deprecated
			public HandlerRegistration addClickHandler(final ClickHandler handler) {
				return addHandler(handler, ClickEvent.getType());
			}

			/**
			 * @deprecated Use {@link Anchor#addClickHandler} instead and call
			 *     History.newItem from the handler if you need to process the
			 *     click before the history token is set.
			 */
			@Override
			@Deprecated
			public void addClickListener(final ClickListener listener) {
				ListenerWrapper.WrappedClickListener.add(this, listener);
			}

			@Override
			public DirectionEstimator getDirectionEstimator() {
				return directionalTextHelper.getDirectionEstimator();
			}

			@Override
			public String getHTML() {
				return directionalTextHelper.getTextOrHtml(true);
			}

			/**
			 * Gets the history token referenced by this hyperlink.
			 *
			 * @return the target history token
			 * @see #setTargetHistoryToken
			 */
			public String getTargetHistoryToken() {
				return targetHistoryToken;
			}

			@Override
			public String getText() {
				return directionalTextHelper.getTextOrHtml(false);
			}

			@Override
			public Direction getTextDirection() {
				return directionalTextHelper.getTextDirection();
			}

			@Override
			public void onBrowserEvent(final Event event) {
				super.onBrowserEvent(event);
				//                if (DOM.eventGetType(event) == Event.ONCLICK && impl.handleAsClick(event)) {
				//                    History.newItem(getTargetHistoryToken());
				//                    DOM.eventPreventDefault(event);
				//                }
			}

			/**
			 * @deprecated Use the {@link HandlerRegistration#removeHandler}
			 * method on the object returned by an add*Handler method instead
			 */
			@Override
			@Deprecated
			public void removeClickListener(final ClickListener listener) {
				ListenerWrapper.WrappedClickListener.remove(this, listener);
			}

			/**
			 * {@inheritDoc}
			 * <p>
			 * See note at {@link #setDirectionEstimator(DirectionEstimator)}.
			 */
			@Override
			public void setDirectionEstimator(final boolean enabled) {
				directionalTextHelper.setDirectionEstimator(enabled);
			}

			/**
			 * {@inheritDoc}
			 * <p>
			 * Note: DirectionEstimator should be set before the widget has any content;
			 * it's highly recommended to set it using a constructor. Reason: if the
			 * widget already has non-empty content, this will update its direction
			 * according to the new estimator's result. This may cause flicker, and thus
			 * should be avoided.
			 */
			@Override
			public void setDirectionEstimator(final DirectionEstimator directionEstimator) {
				directionalTextHelper.setDirectionEstimator(directionEstimator);
			}

			@Override
			public void setHTML(final SafeHtml html) {
				setHTML(html.asString());
			}

			@Override
			public void setHTML(final String html) {
				directionalTextHelper.setTextOrHtml(html, true);
			}

			@Override
			public void setHTML(final SafeHtml html, final Direction dir) {
				directionalTextHelper.setTextOrHtml(html.asString(), dir, true);
			}

			/**
			 * Sets the history token referenced by this hyperlink. This is the history
			 * token that will be passed to {@link History#newItem} when this link is
			 * clicked.
			 *
			 * @param targetHistoryToken the new history token, which may not be null (use
			 *          {@link Anchor} instead if you don't need history processing)
			 */
			public void setTargetHistoryToken(final String targetHistoryToken) {
				assert targetHistoryToken != null :
					"targetHistoryToken must not be null, consider using Anchor instead";
				this.targetHistoryToken = targetHistoryToken;
				final String hash = History.encodeHistoryToken(targetHistoryToken);
				DOM.setElementProperty(anchorElem, "href", "#" + hash);
			}

			@Override
			public void setText(final String text) {
				directionalTextHelper.setTextOrHtml(text, false);
			}

			@Override
			public void setText(final String text, final Direction dir) {
				directionalTextHelper.setTextOrHtml(text, dir, false);
			}

			/**
			 * <b>Affected Elements:</b>
			 * <ul>
			 * <li>-wrapper = the div around the link.</li>
			 * </ul>
			 *
			 * @see UIObject#onEnsureDebugId(String)
			 */
			@Override
			protected void onEnsureDebugId(final String baseID) {
				ensureDebugId(anchorElem, "", baseID);
				ensureDebugId(getElement(), baseID, "wrapper");
			}

			@Override
			protected void onAttach() {
				if (false) {
					new Throwable().printStackTrace(System.out);
				}
				super.onAttach();
			}
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtImage extends GwtWidget implements IGwtImage {
		private final Image image;
		public GwtImage(final ImageResource res) {
			image = new WickletImage(res);
		}
		public GwtImage(final String url) {
			image = new WickletImage(url);
		}
		public GwtImage(final Element e) {
			image = new WickletImage(e);
		}
		@Override
		public Widget asWidget() {
			return image;
		}
		@Override
		public IGwtImage setLink(final String url) {
			image.setUrl(url);
			return this;
		}
		@Override
		public String getLink() {
			return image.getUrl();
		}
		private static class WickletImage extends Image {
			WickletImage(final ImageResource res) {
				super(res);
			}
			WickletImage(final String url) {
				super(url);
			}
			WickletImage(final Element e) {
				super(e);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtLabel extends GwtTextWidget implements IGwtLabel {
		private final Label label;
		public GwtLabel(final String text) {
			label = new WickletLabel(text);
		}
		public GwtLabel(final Element e) {
			label = new WickletLabel(e);
		}
		@Override
		public Widget asWidget() {
			return label;
		}
		@Override
		public IGwtLabel setText(final String text) {
			super.setText(text);
			return this;
		}
		private static class WickletLabel extends InlineLabel {
			public WickletLabel(final String text) {
				super(text);
			}
			public WickletLabel(final Element e) {
				super(e);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtHtml extends GwtTextWidget implements IGwtHtml {
		private final HTML label;
		public GwtHtml(final String text) {
			label = new WickletHtml(text);
		}
		public GwtHtml(final Element e) {
			label = new WickletHtml(e);
		}
		@Override
		public Widget asWidget() {
			return label;
		}
		@Override
		public IGwtHtml setText(final String text) {
			super.setText(text);
			return this;
		}
		private static class WickletHtml extends HTML {
			public WickletHtml(final String text) {
				super(text);
			}
			public WickletHtml(final Element e) {
				super(e);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtIHtml extends GwtTextWidget implements IGwtIHtml {
		private final HTML label;
		public GwtIHtml(final String text) {
			label = new WickletIHtml(text);
		}
		public GwtIHtml(final Element e) {
			label = new WickletIHtml(e);
		}
		@Override
		public Widget asWidget() {
			return label;
		}
		@Override
		public IGwtIHtml setText(final String text) {
			super.setText(text);
			return this;
		}
		private static class WickletIHtml extends InlineHTML {
			public WickletIHtml(final String text) {
				super(text);
			}
			public WickletIHtml(final Element e) {
				super(e);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtHr extends GwtWidget {
		private final HTML widget;
		public GwtHr() {
			widget = new WickletHTML("<hr/>");
		}
		@Override
		public Widget asWidget() {
			return widget;
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtTextBox extends GwtWidget implements IGwtTextBox {
		private final WickletTextBox textbox;
		public GwtTextBox() {
			textbox = new WickletTextBox();
		}
		public GwtTextBox(final String text) {
			textbox = new WickletTextBox(text);
		}
		public GwtTextBox(final Element e) {
			textbox = new WickletTextBox(e);
		}
		@Override
		public Widget asWidget() {
			return textbox;
		}
		@Override
		public String getText() {
			return textbox.getText();
		}
		@Override
		public IGwtTextBox setText(final String text) {
			textbox.setText(text);
			return this;
		}
		private static class WickletTextBox extends TextBox {
			public WickletTextBox() {
			}
			public WickletTextBox(final String text) {
				if (text != null) {
					setText(text);
				}
			}
			public WickletTextBox(final Element e) {
				super(e);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtTextArea extends GwtWidget implements IGwtTextArea {
		private final WickletTextArea textarea;
		public GwtTextArea() {
			textarea = new WickletTextArea();
		}
		public GwtTextArea(final String text) {
			textarea = new WickletTextArea(text);
		}
		public GwtTextArea(final Element e) {
			textarea = new WickletTextArea(e);
		}
		@Override
		public Widget asWidget() {
			return textarea;
		}
		@Override
		public String getText() {
			return textarea.getText();
		}
		@Override
		public IGwtTextArea setText(final String text) {
			textarea.setText(text);
			return this;
		}
		@Override
		public IGwtTextArea setCols(final int cols) {
			textarea.setCharacterWidth(cols);
			return this;
		}
		@Override
		public IGwtTextArea setRows(final int rows) {
			textarea.setVisibleLines(rows);
			return this;
		}
		private static class WickletTextArea extends TextArea {
			public WickletTextArea() {
			}
			public WickletTextArea(final String text) {
				if (text != null) {
					setText(text);
				}
			}
			public WickletTextArea(final Element e) {
				super(e);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtPassword extends GwtWidget implements IGwtPassword {
		private final WickletPassword textbox;
		public GwtPassword() {
			textbox = new WickletPassword();
		}
		public GwtPassword(final String text) {
			textbox = new WickletPassword(text);
		}
		public GwtPassword(final Element e) {
			textbox = new WickletPassword(e);
		}
		@Override
		public Widget asWidget() {
			return textbox;
		}
		@Override
		public String getText() {
			return textbox.getText();
		}
		@Override
		public IGwtPassword setText(final String text) {
			textbox.setText(text);
			return this;
		}
		private static class WickletPassword extends PasswordTextBox {
			public WickletPassword() {
			}
			public WickletPassword(final String text) {
				if (text != null) {
					setText(text);
				}
			}
			public WickletPassword(final Element e) {
				super(e);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtMenuBar extends GwtWidget implements IGwtMenuBar {
		private final MenuBar menubar;
		public GwtMenuBar(final boolean vertical, final IGwtMenuChild...children) {
			menubar = new MenuBar(vertical);
			for (final IGwtMenuChild c: children) {
				c.addTo(this);
			}
		}
		@Override
		public MenuBar asWidget() {
			return menubar;
		}
		@Override
		public IGwtMenuBar item(final String html, final IGwtMenuBar popup) {
			menubar.addItem(new MenuItem(html, popup.asWidget()));
			return this;
		}
		@Override
		public IGwtMenuBar item(final String html, final ScheduledCommand cmd) {
			menubar.addItem(new MenuItem(html, cmd));
			return this;
		}
		@Override
		public IGwtMenuBar separator() {
			menubar.addSeparator();
			return this;
		}
		@Override
		public IGwtMenuBar a(final IGwtMenuItem item) {
			menubar.addItem(item.asMenuItem());
			return this;
		}
		@Override
		public IGwtMenuBar a(final IGwtMenuSeparator sep) {
			menubar.addSeparator(sep.asMenuSeparator());
			return this;
		}
		@Override
		public IGwtMenuBar a(final IGwtMenuChild child) {
			throw new AssertionError("Should not reach here");
		}
	}

	public static class GwtMenuItem implements IGwtMenuItem {
		private final MenuItem item;
		public GwtMenuItem(final String html, final ScheduledCommand cmd) {
			item = new MenuItem(html, cmd);
		}
		public GwtMenuItem(final String html, final IGwtMenuBar submenu) {
			item = new MenuItem(html, submenu.asWidget());
		}
		@Override
		public MenuItem asMenuItem() {
			return item;
		}
		@Override
		public void addTo(final IGwtMenuBar menubar) {
			menubar.a(this);
		}
	}

	public static class GwtMenuSeparator implements IGwtMenuSeparator {
		private final MenuItemSeparator sep;
		public GwtMenuSeparator() {
			sep = new MenuItemSeparator();
		}
		@Override
		public MenuItemSeparator asMenuSeparator() {
			return sep;
		}
		@Override
		public void addTo(final IGwtMenuBar menubar) {
			menubar.a(this);
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtForm extends GwtWidget implements IGwtForm {
		private final WickletFormPanel panel;
		public GwtForm(final IGwtChild...children) {
			panel = new WickletFormPanel();
			c(children);
		}
		protected GwtForm(final Element e, final IGwtChild...children) {
			panel = new WickletFormPanel(e);
			c(children);
		}
		@Override
		public Panel asPanel() {
			return panel;
		}
		@Override
		public Widget asWidget() {
			return panel;
		}
		@Override
		public IGwtForm id(final Object id) {
			super.id(id);
			return this;
		}
		@Override
		public IGwtForm css(final Object cssclass) {
			super.css(cssclass);
			return this;
		}
		@Override
		public IGwtForm istyle(final String value) {
			panel.getElement().setAttribute("style", value);
			return this;
		}
		@Override
		public IGwtForm attr(final Object name, final Object value) {
			panel.getElement().setAttribute(name.toString(), value.toString());
			return this;
		}
		@Override
		public IGwtForm c(final IGwtChild child) {
			child.addTo(this);
			return this;
		}
		@Override
		public IGwtForm c(final IGwtChild...children) {
			for (final IGwtChild c: children) {
				c.addTo(this);
			}
			return this;
		}
		@Override
		public IGwtForm a(final IGwtWidget w) {
			panel.getFormContainer().add(w.asWidget());
			//            final Widget child = w.asWidget();
			//            final ComplexPanel top = panel.getComplexPanel();
			//            final Element elm = top.getElement();
			//            Element parent = elm;
			//            // If child is already attached to a child element of this panel, keep it there.
			//            final Element ce = child.getElement();
			//            if (ce != null) {
			//                final com.google.gwt.dom.client.Element cp = ce.getParentElement();
			//                for (com.google.gwt.dom.client.Element e = cp; e != null; e = e.getParentElement()) {
			//                    if (e == elm) {
			//                        parent = cp.cast();
			//                        break;
			//            }}}
			//            top.add(child, parent);
			return this;
		}
		@Override
		public IGwtForm a(final IGwtAttr attr) {
			panel.getElement().setAttribute(attr.aname(), attr.avalue());
			return null;
		}
		@Override
		public IGwtForm a(final IGwtClassAttr attr) {
			panel.getElement().addClassName(attr.avalue());
			return null;
		}
		@Override
		public IGwtForm a(final IGwtChild c) {
			throw new AssertionError("Should not reach here");
		}
		private static class WickletFormPanel extends FormPanel {
			private final WickletFlowPanel panel = new WickletFlowPanel();
			public WickletFormPanel() {
				setWidget(panel);
			}
			public WickletFormPanel(final Element e) {
				super(e);
				setWidget(panel);
			}
			public ComplexPanel getFormContainer() {
				return panel;
			}
		}
	}

	////////////////////////////////////////////////////////////////////////

	protected static abstract class GwtPanelBase extends GwtWidget implements IGwtBuilder.IGwtPanel {
		@Override
		public IGwtPanel id(final Object id) {
			super.id(id);
			return this;
		}
		@Override
		public IGwtPanel css(final Object cssclass) {
			super.css(cssclass);
			return this;
		}
		@Override
		public IGwtPanel istyle(final String value) {
			asPanel().getElement().setAttribute("style", value);
			return this;
		}
		@Override
		public IGwtPanel attr(final Object name, final Object value) {
			asPanel().getElement().setAttribute(name.toString(), value.toString());
			return this;
		}
		@Override
		public IGwtPanel c(final IGwtChild...children) {
			for (final IGwtChild c: children) {
				c.addTo(this);
			}
			return this;
		}
		@Override
		public IGwtPanel c(final IGwtChild child) {
			child.addTo(this);
			return this;
		}
		@Override
		public IGwtPanel a(final IGwtWidget w) {
			asPanel().add(w.asWidget());
			return this;
		}
		@Override
		public IGwtPanel a(final IGwtAttr attr) {
			asPanel().getElement().setAttribute(attr.aname(), attr.avalue());
			return null;
		}
		@Override
		public IGwtPanel a(final IGwtClassAttr attr) {
			asPanel().getElement().addClassName(attr.avalue());
			return null;
		}
		@Override
		public IGwtPanel a(final IGwtChild c) {
			throw new AssertionError("Should not reach here");
		}
	}

	public static class GwtPanel
		extends GwtPanelBase implements sf.wicklet.gwt.client.dsl.gwt.IGwtBuilder.IGwtComplexPanel {
		private final ComplexPanel panel;
		public GwtPanel() {
			panel = new FlowPanel();
		}
		public GwtPanel(final IGwtChild...children) {
			panel = new FlowPanel();
			c(children);
		}
		public GwtPanel(final Element peer) {
			panel = new WickletFlowPanel(peer);
		}
		public GwtPanel(final ComplexPanel panel) {
			this.panel = panel;
		}
		public GwtPanel(final Element peer, final IGwtChild...children) {
			panel = new WickletFlowPanel(peer);
			c(children);
		}
		public GwtPanel(final ComplexPanel panel, final IGwtChild...children) {
			this.panel = panel;
			c(children);
		}
		@Override
		public ComplexPanel asPanel() {
			return panel;
		}
		@Override
		public ComplexPanel asWidget() {
			return panel;
		}
		/** A hacked version of com.google.gwt.user.client.ui.FlowPanel with constructor that takes an existing element. */
		public static class WickletFlowPanel extends ComplexPanel implements InsertPanel.ForIsWidget {
			private AttachDetachException.Command orphanCommand;
			public WickletFlowPanel() {
				setElement(DOM.createDiv());
			}
			public WickletFlowPanel(final Element e) {
				setElement(e);
			}
			@Override
			public void clear() {
				// Only use one orphan command per panel to avoid object creation.
				if (orphanCommand == null) {
					orphanCommand = new AttachDetachException.Command() {
						@SuppressWarnings("synthetic-access")
						@Override
						public void execute(final Widget w) {
							orphan(w);
						}
					};
				}
				try {
					AttachDetachException.tryCommand(this, orphanCommand);
				} finally {
					super.clear();
					// Remove all existing child nodes.
					Node child = getElement().getFirstChild();
					while (child != null) {
						getElement().removeChild(child);
						child = getElement().getFirstChild();
			}}}
			@Override
			public void add(final Widget child) {
				add(child, getElement());
			}
			@Override
			public void insert(final IsWidget w, final int beforeIndex) {
				insert(asWidgetOrNull(w), beforeIndex);
			}
			@Override
			public void insert(final Widget w, final int beforeIndex) {
				insert(w, getElement(), beforeIndex, true);
			}
		}
	}

	public static class GwtTop extends GwtWidget implements IGwtBuilder.IGwtTop {
		private final List<IGwtChild> children = new ArrayList<IGwtChild>();
		public GwtTop() {
		}
		public GwtTop(final IGwtChild...children) {
			c(children);
		}
		@Override
		public IGwtPanel c(final IGwtChild...children) {
			for (final IGwtChild c: children) {
				this.children.add(c);
			}
			return this;
		}
		@Override
		public IGwtPanel c(final IGwtChild child) {
			child.addTo(this);
			return this;
		}
		@Override
		public IGwtPanel a(final IGwtChild c) {
			children.add(c);
			return this;
		}
		@Override
		public IGwtPanel a(final IGwtWidget w) {
			children.add(w);
			return this;
		}
		@Override
		public IGwtPanel a(final IGwtAttr attr) {
			children.add(attr);
			return this;
		}
		@Override
		public IGwtPanel a(final IGwtClassAttr attr) {
			children.add(attr);
			return this;
		}
		@Override
		public void addTo(final IGwtPanel panel) {
			for (final IGwtChild c: children) {
				c.addTo(panel);
		}}
		@Override
		public IGwtPanel id(final Object cssclass) {
			throw new UnsupportedOperationException();
		}
		@Override
		public IGwtPanel css(final Object cssclass) {
			throw new UnsupportedOperationException();
		}
		@Override
		public IGwtPanel istyle(final String value) {
			throw new UnsupportedOperationException();
		}
		@Override
		public IGwtPanel attr(final IGwtAttr...attrs) {
			throw new UnsupportedOperationException();
		}
		@Override
		public IGwtPanel attr(final Object name, final Object value) {
			throw new UnsupportedOperationException();
		}
		@Override
		public ComplexPanel asPanel() {
			throw new UnsupportedOperationException();
		}
		@Override
		public ComplexPanel asWidget() {
			throw new UnsupportedOperationException();
		}
	}

	protected static abstract class GwtCellPanel extends GwtPanel implements IGwtCellPanel {
		protected GwtCellPanel(final CellPanel panel) {
			super(panel);
		}
		@Override
		public CellPanel asPanel() {
			return (CellPanel)super.asPanel();
		}
		@Override
		public IGwtCellPanel spacing(final int px) {
			asPanel().setSpacing(px);
			return this;
		}
		@Override
		public IGwtCellPanel cellheight(final int index, final String value) {
			final CellPanel p = asPanel();
			p.setCellHeight(p.getWidget(index), value);
			return this;
		}
		@Override
		public IGwtCellPanel cellwidth(final int index, final String value) {
			final CellPanel p = asPanel();
			p.setCellWidth(p.getWidget(index), value);
			return this;
		}
		@Override
		public IGwtCellPanel cellhalign(final int index, final HorizontalAlignmentConstant value) {
			final CellPanel p = asPanel();
			p.setCellHorizontalAlignment(p.getWidget(index), value);
			return this;
		}
		@Override
		public IGwtCellPanel cellvalign(final int index, final VerticalAlignmentConstant value) {
			final CellPanel p = asPanel();
			p.setCellVerticalAlignment(p.getWidget(index), value);
			return this;
		}
	}

	public static class GwtVerticalPanel extends GwtCellPanel {
		public GwtVerticalPanel(final IGwtChild...children) {
			super(new VerticalPanel());
			c(children);
		}
		@Override
		public IGwtPanel a(final IGwtWidget w) {
			asPanel().add(w.asWidget());
			return this;
		}
	}

	public static class GwtHorizontalPanel extends GwtCellPanel {
		public GwtHorizontalPanel(final IGwtChild...children) {
			super(new HorizontalPanel());
			c(children);
		}
		@Override
		public IGwtPanel a(final IGwtWidget w) {
			asPanel().add(w.asWidget());
			return this;
		}
	}

	public static class GwtGridPanel extends GwtPanelBase implements IGwtGridPanel {
		private final Grid grid;
		private final int rows;
		private final int cols;
		private int row = 0;
		private int col = 0;
		public GwtGridPanel(final int rows, final int cols, final IGwtChild...children) {
			grid = new Grid(rows, cols);
			this.rows = rows;
			this.cols = cols;
			c(children);
		}
		@Override
		public Grid asPanel() {
			return grid;
		}
		@Override
		public Widget asWidget() {
			return grid;
		}
		/** Add widgets from left to right and top to bottom */
		@Override
		public IGwtGridPanel a(final IGwtWidget w) {
			if (row >= rows) {
				throw new IllegalArgumentException("Two many widgets for the grid");
			}
			grid.setWidget(row, col, w.asWidget());
			if (++col >= cols) {
				col = 0;
				++row;
			}
			return this;
		}
		@Override
		public IGwtGridPanel widget(final int row, final int col, final IGwtWidget w) {
			grid.setWidget(row, col, w.asWidget());
			return this;
		}
		@Override
		public IGwtGridPanel spacing(final int px) {
			grid.setCellSpacing(px);
			return this;
		}
		@Override
		public IGwtGridPanel padding(final int px) {
			grid.setCellPadding(px);
			return this;
		}
		@Override
		public IGwtGridPanel border(final int px) {
			grid.setBorderWidth(px);
			return this;
		}
		@Override
		public IGwtGridPanel rowstyle(final String style) {
			if (row > 0) {
				grid.getRowFormatter().addStyleName(row - 1, style);
			}
			return this;
		}
		@Override
		public IGwtGridPanel rowattr(final String name, final String value) {
			if (row > 0) {
				grid.getRowFormatter().getElement(row - 1).setAttribute(name, value);
			}
			return this;
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtStackPanel extends GwtWidget implements IGwtStackPanel {
		private final StackPanel stackpanel;
		public GwtStackPanel(final IGwtWithHeader...children) {
			stackpanel = new StackPanel();
			stackpanel.getElement().setAttribute("cellspacing", "1px");
			c(children);
		}
		@Override
		public Widget asWidget() {
			return stackpanel;
		}
		@Override
		public IGwtStackPanel id(final Object id) {
			super.id(id);
			return this;
		}
		@Override
		public IGwtStackPanel css(final Object cssclass) {
			super.css(cssclass);
			return this;
		}
		@Override
		public IGwtStackPanel istyle(final String value) {
			stackpanel.getElement().setAttribute("style", value);
			return this;
		}
		@Override
		public IGwtStackPanel attr(final Object name, final Object value) {
			stackpanel.getElement().setAttribute(name.toString(), value.toString());
			return this;
		}
		@Override
		public IGwtStackPanel c(final IGwtWithHeader child) {
			child.addTo(this);
			return this;
		}
		@Override
		public IGwtStackPanel c(final IGwtWithHeader...children) {
			for (final IGwtWithHeader c: children) {
				c.addTo(this);
			}
			return this;
		}
		@Override
		public IGwtStackPanel a(final IGwtWithHeader child) {
			a(child.getHeader(), child.getWidget());
			final IRunnable<Element> c = child.getCallback();
			if (c != null) {
				c.run(stackpanel.getWidget(stackpanel.getWidgetCount() - 1).getElement());
			}
			return this;
		}
		@Override
		public IGwtStackPanel a(final String header, final IGwtWidget child) {
			// TODO If child widget peer is already child of stackpanel peer, keep it there instead of append to end.
			stackpanel.add(child.asWidget(), header);
			return this;
		}
		@Override
		public IGwtStackPanel a(final IGwtAttr attr) {
			stackpanel.getElement().setAttribute(attr.aname(), attr.avalue());
			return this;
		}
		@Override
		public IGwtStackPanel a(final IGwtClassAttr attr) {
			stackpanel.getElement().addClassName(attr.avalue());
			return this;
		}
		@Override
		public IGwtStackPanel a(final IGwtChild c) {
			throw new AssertionError("Should not reach here");
		}
	}

	public static class GwtWithHeader implements IGwtWithHeader {
		private final String header;
		private final IGwtWidget child;
		private final IRunnable<Element> callback;
		public GwtWithHeader(final String header, final IGwtWidget child) {
			this(header, child, null);
		}
		public GwtWithHeader(final String header, final IGwtWidget child, final IRunnable<Element> callback) {
			this.header = header;
			this.child = child;
			this.callback = callback;
		}
		@Override
		public void addTo(final IGwtPanel panel) {
			throw new UnsupportedOperationException();
		}
		@Override
		public void addTo(final IGwtHeaderPanel panel) {
			panel.a(this);
		}
		@Override
		public String getHeader() {
			return header;
		}
		@Override
		public IGwtWidget getWidget() {
			return child;
		}
		@Override
		public IRunnable<Element> getCallback() {
			return callback;
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class GwtRoot extends GwtPanel implements IGwtRoot {
		public GwtRoot(final Object id, final IGwtChild...children) {
			super(id == null ? RootPanel.get() : RootPanel.get(id.toString()), children);
		}
		@Override
		public RootPanel asRootPanel() {
			return (RootPanel)super.asPanel();
		}
		@Override
		public IGwtRoot c(final IGwtChild...children) {
			super.c(children);
			return this;
		}
		@Override
		public IGwtRoot c(final IGwtChild child) {
			super.c(child);
			return this;
		}
		@Override
		public IGwtRoot id(final Object id) {
			super.id(id);
			return this;
		}
		@Override
		public IGwtRoot css(final Object cssclass) {
			super.css(cssclass);
			return this;
		}
		@Override
		public IGwtRoot istyle(final String value) {
			asPanel().getElement().setAttribute("style", value);
			return this;
		}
	}

	////////////////////////////////////////////////////////////////////////

	protected static abstract class GwtTextWidget extends GwtWidget implements IGwtTextWidget {
		@Override
		public String getText() {
			return ((HasText)asWidget()).getText();
		}
		@Override
		public IGwtTextWidget setText(final String text) {
			((HasText)asWidget()).setText(text);
			return this;
		}
	}

	////////////////////////////////////////////////////////////////////////

	protected static abstract class GwtWidget implements IGwtBuilder.IGwtWidget {
		@Override
		public IGwtWidget sinkEvents(final int events) {
			asWidget().sinkEvents(events);
			return this;
		}

		@Override
		public IGwtWidget attr(final IGwtAttr...attrs) {
			final Element e = asWidget().getElement();
			for (final IGwtAttr attr: attrs) {
				e.setAttribute(attr.toString(), attr.toString());
			}
			return this;
		}

		@Override
		public IGwtWidget attr(final IGwtAttr attr) {
			asWidget().getElement().setAttribute(attr.toString(), attr.toString());
			return this;
		}

		@Override
		public IGwtWidget attr(final String name, final String value) {
			asWidget().getElement().setAttribute(name, value);
			return this;
		}

		@Override
		public IGwtWidget attr(final Object name, final Object value) {
			asWidget().getElement().setAttribute(name.toString(), value.toString());
			return this;
		}

		@Override
		public IGwtWidget id(final String id) {
			asWidget().getElement().setAttribute("id", id);
			return this;
		}

		@Override
		public IGwtWidget id(final Object id) {
			asWidget().getElement().setAttribute("id", id.toString());
			return this;
		}

		@Override
		public IGwtWidget name(final String name) {
			asWidget().getElement().setAttribute("name", name);
			return this;
		}

		@Override
		public IGwtWidget name(final Object name) {
			asWidget().getElement().setAttribute("name", name.toString());
			return this;
		}

		@Override
		public IGwtWidget css(final String cssclass) {
			asWidget().getElement().addClassName(cssclass);
			return this;
		}

		@Override
		public IGwtWidget css(final Object cssclass) {
			asWidget().getElement().addClassName(cssclass.toString());
			return this;
		}

		@Override
		public IGwtWidget istyle(final String value) {
			appendAttribute("style", value);
			return this;
		}

		@Override
		public IGwtWidget hide(final boolean hide) {
			asWidget().setVisible(!hide);
			return this;
		}

		@Override
		public IGwtWidget hide() {
			asWidget().setVisible(false);
			return this;
		}

		@Override
		public IGwtWidget show() {
			asWidget().setVisible(true);
			return this;
		}

		////////////////////////////////////////////////////////////////////////

		@Override
		public IGwtWidget onload(final LoadHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, LoadEvent.getType());
			w.sinkEvents(Event.ONLOAD);
			return this;
		}

		@Override
		public IGwtWidget onchange(final ChangeHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, ChangeEvent.getType());
			w.sinkEvents(Event.ONCHANGE);
			return this;
		}

		@Override
		public IGwtWidget onclick(final ClickHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, ClickEvent.getType());
			w.sinkEvents(Event.ONCLICK);
			return this;
		}

		@Override
		public IGwtWidget ondblclick(final DoubleClickHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, DoubleClickEvent.getType());
			w.sinkEvents(Event.ONDBLCLICK);
			return this;
		}

		@Override
		public IGwtWidget onmousedown(final MouseDownHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, MouseDownEvent.getType());
			w.sinkEvents(Event.ONMOUSEDOWN);
			return this;
		}

		@Override
		public IGwtWidget onmouseup(final MouseUpHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, MouseUpEvent.getType());
			w.sinkEvents(Event.ONMOUSEUP);
			return this;
		}

		@Override
		public IGwtWidget onmouseover(final MouseOverHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, MouseOverEvent.getType());
			w.sinkEvents(Event.ONMOUSEOVER);
			return this;
		}

		@Override
		public IGwtWidget onmousemove(final MouseMoveHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, MouseMoveEvent.getType());
			w.sinkEvents(Event.ONMOUSEMOVE);
			return this;
		}

		@Override
		public IGwtWidget onmouseout(final MouseOutHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, MouseOutEvent.getType());
			w.sinkEvents(Event.ONMOUSEOUT);
			return this;
		}

		@Override
		public IGwtWidget onfocus(final FocusHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, FocusEvent.getType());
			w.sinkEvents(Event.ONFOCUS);
			return this;
		}

		@Override
		public IGwtWidget onblur(final BlurHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, BlurEvent.getType());
			w.sinkEvents(Event.ONBLUR);
			return this;
		}

		@Override
		public IGwtWidget onkeypress(final KeyPressHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, KeyPressEvent.getType());
			w.sinkEvents(Event.ONKEYPRESS);
			return this;
		}

		@Override
		public IGwtWidget onkeydown(final KeyDownHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, KeyDownEvent.getType());
			w.sinkEvents(Event.ONKEYDOWN);
			return this;
		}

		@Override
		public IGwtWidget onkeyup(final KeyUpHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, KeyUpEvent.getType());
			w.sinkEvents(Event.ONKEYUP);
			return this;
		}

		////////////////////////////////////////////////////////////////////////

		@Override
		public IGwtWidget ondrag(final DragHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, DragEvent.getType());
			return this;
		}

		@Override
		public IGwtWidget ondragenter(final DragEnterHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, DragEnterEvent.getType());
			return this;
		}

		@Override
		public IGwtWidget ondragstart(final DragStartHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, DragStartEvent.getType());
			return this;
		}

		@Override
		public IGwtWidget ondragend(final DragEndHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, DragEndEvent.getType());
			return this;
		}

		@Override
		public IGwtWidget ondragover(final DragOverHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, DragOverEvent.getType());
			return this;
		}

		@Override
		public IGwtWidget ondragleave(final DragLeaveHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, DragLeaveEvent.getType());
			return this;
		}

		@Override
		public IGwtWidget ondrop(final DropHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, DropEvent.getType());
			return this;
		}

		@Override
		public IGwtWidget onsubmit(final SubmitHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, SubmitEvent.getType());
			return this;
		}

		@Override
		public IGwtWidget onresize(final ResizeHandler handler) {
			final Widget w = asWidget();
			w.addHandler(handler, ResizeEvent.getType());
			return this;
		}

		@Override
		public <T> IGwtWidget onselect(final SelectionHandler<T> handler) {
			final Widget w = asWidget();
			w.addHandler(handler, SelectionEvent.getType());
			return this;
		}

		////////////////////////////////////////////////////////////////////////

		@Override
		public void addTo(final IGwtPanel panel) {
			panel.a(this);
		}

		////////////////////////////////////////////////////////////////////////

		protected void appendAttribute(final String name, final String value) {
			final Element e = asWidget().getElement();
			final String o = e.getAttribute(name);
			if (!GwtTextUtil.isEmpty(o)) {
				e.setAttribute(name, o + " " + value);
			} else {
				e.setAttribute(name, value);
		}}

		////////////////////////////////////////////////////////////////////////

		protected static class WickletHTML extends HTML {
			public WickletHTML(final String html) {
				super(html);
			}
			public WickletHTML(final Element e) {
				super(e);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////
}
