/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.dsl.gwt;

import sf.wicklet.gwt.client.dsl.IIDProvider;
import sf.wicklet.gwt.client.util.IRunnable;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragHandler;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public interface IGwtBuilder {

	public abstract Widget serialize(final IGwtWidget e);
	public abstract void ajax(
		final RequestBuilder.Method method,
		final String url,
		final String data,
		final IWickletAjaxCallback callback);
	/** Default using POST. */
	public abstract void ajax(final String url, final String data, final IWickletAjaxCallback callback);
	// Attributes
	/** Create a HtmlPanel and add it to the given parent. */
	public abstract IGwtAttr attr(final Object name, final Object value);
	public abstract IGwtAttr attr(final String name, final String value);
	public abstract IGwtAttr id(final Object value);
	public abstract IGwtAttr id(final String value);
	public abstract IGwtAttr name(final Object value);
	public abstract IGwtAttr name(final String value);
	public abstract IGwtAttr css(final Object value);
	public abstract IGwtAttr css(final String value);
	public abstract IGwtAttr istyle(final String value);
	// Panels
	public abstract IGwtTop top(final IGwtChild...children);
	public abstract IGwtRoot rootpanel(final IGwtChild...children);
	public abstract IGwtRoot rootpanel(final Object id, final IGwtChild...children);
	/** A templatepanel is special that it preserve existing elements instead of detach and reattach. */
	public abstract IGwtComplexPanel templatepanel(final Element peer, final IGwtChild...children);
	/** A templatepanel is special that it preserve existing elements instead of detach and reattach. */
	public abstract IGwtComplexPanel templatepanel(final String html, final IGwtChild...children);
	public abstract IGwtComplexPanel htmlpanel(final String html, final IGwtChild...children);
	public abstract IGwtComplexPanel panel(final IGwtChild...children);
	public abstract IGwtComplexPanel panel(final Element peer, final IGwtChild...children);
	public abstract IGwtComplexPanel ipanel(final IGwtChild...children);
	public abstract IGwtCellPanel hpanel(final IGwtChild...children);
	public abstract IGwtCellPanel vpanel(final IGwtChild...children);
	public abstract IGwtGridPanel grid(int rows, int cols, final IGwtChild...children);
	public abstract IGwtStackPanel stackpanel(final IGwtWithHeader...children);
	public abstract IGwtWithHeader header(final String header, final IGwtWidget child);
	public abstract IGwtWithHeader header(final String header, final IGwtWidget child, IRunnable<Element> callback);
	// Containers
	public abstract IGwtMenuBar menubar(final boolean vertical, final IGwtMenuChild...children);
	public abstract IGwtMenuBar hmenubar(final IGwtMenuChild...children);
	public abstract IGwtMenuBar vmenubar(final IGwtMenuChild...children);
	public abstract IGwtMenuItem menuitem(final String html, final ScheduledCommand cmd);
	public abstract IGwtMenuItem menuitem(final String html, final IGwtMenuBar submenu);
	public abstract IGwtMenuSeparator menusep();
	// Widgets
	public abstract IGwtAnchor anchor(final String text, IGwtAttr...attrs);
	public abstract IGwtAnchor anchor(final String text, final String href, IGwtAttr...attrs);
	public abstract IGwtHyperlink hyperlink(final String text, final String token, IGwtAttr...attrs);
	public abstract IGwtHyperlink hyperlink(final String text, IGwtAttr...attrs);
	public abstract IGwtLabel label(final String text, IGwtAttr...attrs);
	public abstract IGwtHtml html(final String html, IGwtAttr...attrs);
	public abstract IGwtIHtml ihtml(final String html, IGwtAttr...attrs);
	public abstract IGwtImage image(final String href, IGwtAttr...attrs);
	public abstract IGwtImage image(final ImageResource res, IGwtAttr...attrs);
	public abstract IGwtButton button(final String text, IGwtAttr...attrs);
	public abstract IGwtWidget hr(IGwtAttr...attrs);
	//
	public abstract IGwtForm form(final IGwtChild...children);
	public abstract IGwtTextBox textbox(final String text, IGwtAttr...attrs);
	public abstract IGwtTextArea textarea(final String text, IGwtAttr...attrs);
	public abstract IGwtPassword password(final String text, IGwtAttr...attrs);
	//
	public abstract IGwtAnchor anchor(final Element peer, IGwtAttr...attrs);
	public abstract IGwtHyperlink hyperlink(final Element peer, IGwtAttr...attrs);
	public abstract IGwtLabel label(final Element peer, IGwtAttr...attrs);
	public abstract IGwtImage image(final Element peer, IGwtAttr...attrs);
	public abstract IGwtButton button(final Element peer, IGwtAttr...attrs);
	public abstract IGwtTextBox textbox(final Element peer, IGwtAttr...attrs);
	public abstract IGwtTextArea textarea(final Element peer, IGwtAttr...attrs);
	public abstract IGwtPassword password(final Element peer, IGwtAttr...attrs);
	public abstract IGwtForm form(final Element peer, final IGwtChild...children);
	// Utils
	public abstract Element byId(final IIDProvider id);

	////////////////////////////////////////////////////////////////////////

	public interface IGwtWidget extends IsWidget, IGwtChild {
		IGwtWidget sinkEvents(final int events);
		//
		IGwtWidget attr(final IGwtAttr attr);
		IGwtWidget attr(final IGwtAttr...attrs);
		IGwtWidget attr(final String name, final String value);
		IGwtWidget attr(final Object name, final Object value);
		IGwtWidget id(final String id);
		IGwtWidget id(final Object id);
		IGwtWidget name(final String id);
		IGwtWidget name(final Object id);
		IGwtWidget css(final String cssclass);
		IGwtWidget css(final Object cssclass);
		IGwtWidget istyle(final String value);
		IGwtWidget hide(boolean b);
		IGwtWidget hide();
		IGwtWidget show();
		//
		IGwtWidget onload(final LoadHandler handler);
		// IGwtWidget onunload(final UnloadHandler handler);
		IGwtWidget onchange(final ChangeHandler handler);
		IGwtWidget onclick(final ClickHandler handler);
		IGwtWidget ondblclick(final DoubleClickHandler handler);
		IGwtWidget ondrag(final DragHandler handler);
		IGwtWidget ondragenter(final DragEnterHandler handler);
		IGwtWidget ondragstart(final DragStartHandler handler);
		IGwtWidget ondragend(final DragEndHandler handler);
		IGwtWidget ondragover(final DragOverHandler handler);
		IGwtWidget ondragleave(final DragLeaveHandler handler);
		IGwtWidget ondrop(final DropHandler handler);
		IGwtWidget onmousedown(final MouseDownHandler handler);
		IGwtWidget onmouseup(final MouseUpHandler handler);
		IGwtWidget onmouseover(final MouseOverHandler handler);
		IGwtWidget onmousemove(final MouseMoveHandler handler);
		IGwtWidget onmouseout(final MouseOutHandler handler);
		IGwtWidget onfocus(final FocusHandler handler);
		IGwtWidget onblur(final BlurHandler handler);
		IGwtWidget onkeypress(final KeyPressHandler handler);
		IGwtWidget onkeydown(final KeyDownHandler handler);
		IGwtWidget onkeyup(final KeyUpHandler handler);
		IGwtWidget onsubmit(final SubmitHandler handler);
		IGwtWidget onresize(final ResizeHandler handler);
		<T> IGwtWidget onselect(final SelectionHandler<T> handler);
	}

	////////////////////////////////////////////////////////////////////////

	public interface IWickletAjaxCallback extends RequestCallback {
		void setupRequest(RequestBuilder request);
		void onError(RequestException e);
	}

	public interface IGwtTextWidget extends IGwtWidget {
		IGwtTextWidget setText(final String text);
		String getText();
	}

	public interface IGwtLinkWidget extends IGwtWidget {
		IGwtLinkWidget setLink(final String url);
		String getLink();
	}

	////////////////////////////////////////////////////////////////////////

	public interface IGwtChild {
		void addTo(final IGwtPanel panel);
	}

	public interface IGwtAttr extends IGwtChild {
		String aname();
		String avalue();
	}

	public interface IGwtClassAttr extends IGwtAttr {
	}

	public interface IGwtHandler extends IGwtChild {
	}

	public interface IGwtWithHeader extends IGwtChild {
		void addTo(final IGwtHeaderPanel panel);
		String getHeader();
		IGwtWidget getWidget();
		/** Callback to be called after it has been added to the stack panel. */
		IRunnable<Element> getCallback();
	}

	////////////////////////////////////////////////////////////////////////

	public interface IGwtLabel extends IGwtTextWidget {
		@Override
		public IGwtLabel setText(String text);
	}

	public interface IGwtHtml extends IGwtTextWidget {
		@Override
		public IGwtHtml setText(String text);
	}

	public interface IGwtIHtml extends IGwtTextWidget {
		@Override
		public IGwtIHtml setText(String text);
	}

	public interface IGwtAnchor extends IGwtTextWidget, IGwtLinkWidget {
		@Override
		public IGwtAnchor setText(String text);
		@Override
		public IGwtAnchor setLink(String url);
	}

	public interface IGwtHyperlink extends IGwtTextWidget, IGwtLinkWidget {
		@Override
		public IGwtHyperlink setText(String text);
		@Override
		public IGwtHyperlink setLink(String url);
		public IGwtHyperlink onhistorychange(ValueChangeHandler<String> handler);
		/** Activation due to onclick or onhistorychange. */
		public IGwtHyperlink onactivate(IRunnable<String> handler);
	}

	public interface IGwtImage extends IGwtLinkWidget {
		@Override
		public IGwtImage setLink(String url);
	}

	public interface IGwtButton extends IGwtTextWidget {
		@Override
		public IGwtButton setText(String text);
		@Override
		public IGwtButton onclick(ClickHandler handler);
	}

	public interface IGwtTextBox extends IGwtTextWidget {
		@Override
		public IGwtTextBox setText(String text);
	}

	public interface IGwtTextArea extends IGwtTextWidget {
		@Override
		public IGwtTextArea setText(String text);
		public IGwtTextArea setCols(int cols);
		public IGwtTextArea setRows(int rows);
	}

	public interface IGwtPassword extends IGwtTextWidget {
		@Override
		public IGwtPassword setText(String text);
	}

	////////////////////////////////////////////////////////////////////////

	public interface IGwtMenuBar extends IGwtWidget {
		@Override
		MenuBar asWidget();
		IGwtMenuBar separator();
		IGwtMenuBar item(final String html, final ScheduledCommand cmd);
		IGwtMenuBar item(final String html, final IGwtMenuBar popup);
		IGwtMenuBar a(final IGwtMenuItem item);
		IGwtMenuBar a(final IGwtMenuSeparator sep);
		IGwtMenuBar a(final IGwtMenuChild child);
	}

	public interface IGwtMenuChild {
		void addTo(final IGwtMenuBar menubar);
	}

	public interface IGwtMenuItem extends IGwtMenuChild {
		MenuItem asMenuItem();
	}

	public interface IGwtMenuSeparator extends IGwtMenuChild {
		MenuItemSeparator asMenuSeparator();
	}

	////////////////////////////////////////////////////////////////////////

	public interface IGwtPanel extends IGwtWidget {
		@Override
		IGwtPanel id(final Object id);
		@Override
		IGwtPanel css(final Object cssclass);
		@Override
		IGwtPanel istyle(final String value);
		@Override
		IGwtPanel attr(final Object name, final Object value);
		IGwtPanel a(final IGwtWidget w);
		IGwtPanel a(final IGwtAttr attr);
		IGwtPanel a(final IGwtClassAttr attr);
		IGwtPanel a(final IGwtChild child);
		IGwtPanel c(final IGwtChild child);
		IGwtPanel c(final IGwtChild...children);
		Panel asPanel();
	}

	/** A fake panel for grouping whose sole purpose is to add its children to another panel. */
	public interface IGwtTop extends IGwtPanel {
	}

	public interface IGwtComplexPanel extends IGwtPanel {
		@Override
		ComplexPanel asPanel();
	}

	public interface IGwtHeaderPanel extends IGwtWidget {
		@Override
		IGwtStackPanel id(final Object id);
		@Override
		IGwtStackPanel css(final Object cssclass);
		@Override
		IGwtStackPanel istyle(final String value);
		@Override
		IGwtStackPanel attr(final Object name, final Object value);
		IGwtStackPanel a(final IGwtAttr attr);
		IGwtStackPanel a(final IGwtClassAttr attr);
		IGwtStackPanel a(final IGwtWithHeader attr);
		IGwtStackPanel a(final IGwtChild c);
		IGwtStackPanel a(final String header, final IGwtWidget child);
		IGwtStackPanel c(final IGwtWithHeader child);
		IGwtStackPanel c(final IGwtWithHeader...children);
	}

	public interface IGwtCellPanel extends IGwtPanel {
		@Override
		CellPanel asPanel();
		IGwtCellPanel spacing(int px);
		IGwtCellPanel cellheight(int index, String value);
		IGwtCellPanel cellwidth(int index, String value);
		IGwtCellPanel cellhalign(int index, HorizontalAlignmentConstant value);
		IGwtCellPanel cellvalign(int index, VerticalAlignmentConstant value);
	}

	public interface IGwtGridPanel extends IGwtPanel {
		@Override
		Grid asPanel();
		IGwtGridPanel spacing(int px);
		IGwtGridPanel padding(int px);
		IGwtGridPanel border(int px);
		/** Add given style to the last completed row of the table. */
		IGwtGridPanel rowstyle(String style);
		/** Set given attribute to the last completed row of the table. */
		IGwtGridPanel rowattr(String name, String value);
		IGwtGridPanel widget(int row, int col, IGwtWidget w);
	}

	public interface IGwtStackPanel extends IGwtHeaderPanel {
	}

	public interface IGwtForm extends IGwtPanel {
	}

	public interface IGwtRoot extends IGwtPanel {
		RootPanel asRootPanel();
		@Override
		public IGwtRoot c(final IGwtChild child);
		@Override
		public IGwtRoot c(final IGwtChild...children);
		@Override
		IGwtRoot id(final Object id);
		@Override
		IGwtRoot css(final Object cssclass);
		@Override
		IGwtRoot istyle(final String value);
	}

	////////////////////////////////////////////////////////////////////////
}
