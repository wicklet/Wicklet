/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.ajax;

import sf.wicklet.gwt.client.dsl.gwt.IGwtBuilder.IWickletAjaxCallback;
import sf.wicklet.gwt.client.ui.dialog.ErrorDialog;
import sf.wicklet.gwt.client.util.ChainExecutor;
import sf.wicklet.gwt.client.util.ChainExecutor.IChainable;
import sf.wicklet.gwt.client.util.ChainExecutor.IStartable;
import sf.wicklet.gwt.client.util.GwtDomUtil;
import sf.wicklet.gwt.client.util.GwtTextUtil;
import sf.wicklet.gwt.client.util.GwtXmlUtil;
import sf.wicklet.gwt.shared.GwtHttp;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.DOM;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;

public class WickletAjax {

	////////////////////////////////////////////////////////////////////////

	public static final boolean DEBUG = false;
	public static final RequestBuilder.Method POST = RequestBuilder.POST;
	public static final RequestBuilder.Method GET = RequestBuilder.GET;

	////////////////////////////////////////////////////////////////////////

	public static void ajax(final String url, final String data, final IWickletAjaxCallback callback) {
		ajax(RequestBuilder.POST, url, data, callback);
	}

	public static void ajax(
		final Method method, final String url, final String data, final IWickletAjaxCallback callback) {
		try {
			final RequestBuilder b = new RequestBuilder(method, url);
			//            b.setHeader(GwtHttp.Wicket.Ajax, "true");
			//            b.setHeader(GwtHttp.Wicket.AjaxBaseURL, GwtUrlUtil.getWicketBaseUrl());
			callback.setupRequest(b);
			b.sendRequest(data, callback);
		} catch (final RequestException e) {
			callback.onError(e);
	}}

	public static HeadElement getHead() {
		final com.google.gwt.dom.client.Element elt = com.google.gwt.dom.client.Document.get()
			.getElementsByTagName("head").getItem(0);
		if (elt == null) {
			throw new AssertionError("The host HTML page does not have a <head> element");
		}
		return HeadElement.as(elt);
	}

	////////////////////////////////////////////////////////////////////////

	public static abstract class WickletAjaxCallback implements IWickletAjaxCallback {
		/** Default to use content-type: application/x-www-form-urlencoded. */
		@Override
		public void setupRequest(final RequestBuilder request) {
			request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		}
		@Override
		public void onError(final RequestException e) {
			onError(null, e);
		}
	}

	////////////////////////////////////////////////////////////////////////

	/** Callback with default to accept simple xml response instead of wicket style xml ajax-response. */
	public static abstract class XmlWickletAjaxCallback extends WickletAjax.WickletAjaxCallback {
		@Override
		public void setupRequest(final RequestBuilder request) {
			super.setupRequest(request);
			request.setHeader(GwtHttp.Wicklet.Accept, "text/xml");
		}
	}

	////////////////////////////////////////////////////////////////////////

	/** A standard handler for wicket style ajax response. */
	public static class DefaultWickletAjaxCallback extends WickletAjax.WickletAjaxCallback {
		private final ChainExecutor executor = new ChainExecutor();
		@Override
		public void onResponseReceived(final Request request, final Response response) {
			if (WickletAjax.DEBUG) {
				new ErrorDialog(response.getText()).showAtCenter();
			}
			final int code = response.getStatusCode();
			if (GwtHttp.Status.isOK(code)) {
				final Document xmldoc;
				try {
					xmldoc = XMLParser.parse(response.getText());
				} catch (final DOMParseException e) {
					if (WickletAjax.DEBUG) {
						onError(
							request,
							new Exception(
								"ERROR: Invalid ajax response: " + response.getText(),
								e));
					}
					onError(request, e);
					return;
				}
				final Element top = xmldoc.getDocumentElement();
				if (!"ajax-response".equals(top.getTagName())) {
					onError(
						request,
						new AssertionError("Invalid ajax response: " + response.getText()));
					return;
				}
				process(top);
			} else if (GwtHttp.Status.isRedirect(code)) {
				final String url = response.getHeader("Ajax-Location");
				if (!GwtTextUtil.isEmpty(url)) {
					// TODO Redirect
				}
				todo("TODO: Ajax redirect action is not yet implemented");
		}}
		/** @param top The ajax-response element. */
		protected void process(final Element top) {
			// Execute priority commands
			for (Node child = top.getFirstChild(); child != null; child = child.getNextSibling()) {
				if (child.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				final Element e = (Element)child;
				final String tagname = e.getTagName();
				if ("header-contribution".equals(tagname)) {
					processHeaderContribution(e);
				} else if ("priority-evaluate".equals(tagname)) {
					processEvaluation(e);
			}}
			// Process other commands
			for (Node child = top.getFirstChild(); child != null; child = child.getNextSibling()) {
				if (child.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				final Element e = (Element)child;
				final String tagname = e.getTagName();
				if ("component".equals(tagname)) {
					processComponent(e);
				} else if ("evaluate".equals(tagname)) {
					processEvaluation(e);
			}}
			executor.start();
		}
		private void processHeaderContribution(final Element e) {
			if (!"escaped-xml".equals(e.getAttribute("encoding"))) {
				GWT.log(
					"ERROR: Only escaped-xml encoding is currently supported for header-contribution");
				return;
			}
			final Document xmldoc;
			final String content = GwtXmlUtil.getTextUnder(e);
			try {
				xmldoc = XMLParser.parse(GwtXmlUtil.unescXml(content));
			} catch (final DOMParseException ex) {
				final String msg = "ERROR: Invalid ajax header-contribution response: " + content;
				GWT.log(msg, ex);
				return;
			}
			for (Node n = xmldoc.getDocumentElement().getFirstChild(); n != null; n = n.getNextSibling()) {
				if (n.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				final String tagname = n.getNodeName().toLowerCase();
				if ("script".equals(tagname)) {
					processScript((Element)n);
				} else if ("style".equals(tagname)) {
					processStyle((Element)n);
				} else if ("link".equals(tagname)) {
					processLink((Element)n);
		}}}
		/** Inject script element, either using src reference or inline, into head section. */
		private void processScript(final Element n) {
			executor.queue(
				new IChainable() {
					@Override
					public void run(final IStartable next) {
						final String srcurl = n.getAttribute("src");
						final String id = n.getAttribute("id");
						final boolean hasid = !GwtTextUtil.isEmpty(id);
						if (hasid && DOM.getElementById(id) != null) {
							if (next != null) {
								next.start();
							}
							return;
						}
						if (srcurl != null) {
							// TODO May check for duplicate using srcurl instead.
							ScriptInjector.fromUrl(srcurl).setCallback(
								new Callback<Void, Exception>() {
									@Override
									public void onSuccess(final Void result) {
										createId(id);
										if (next != null) {
											next.start();
									}}
									@Override
									public void onFailure(final Exception reason) {
										GWT.log(
											"ERROR: processScript failed",
											reason);
										if (next != null) {
											next.start();
									}}
								}).inject();
						} else {
							final String content = GwtXmlUtil.getTextUnder(n);
							ScriptInjector.fromString(content).setRemoveTag(false).inject();
							createId(id);
							if (next != null) {
								next.start();
					}}}
					void createId(final String id) {
						if (!GwtTextUtil.isEmpty(id)) {
							final com.google.gwt.dom.client.Element e
								= com.google.gwt.dom.client.Document.get()
									.createScriptElement();
							e.setPropertyString("type", "text/javascript");
							e.setId(id);
							getHead().appendChild(e);
					}}
				});
		}
		/** Inject inline style element into the head section. */
		private void processStyle(final Element n) {
			executor.queue(
				new IChainable() {
					@Override
					public void run(final IStartable next) {
						final String id = n.getAttribute("id");
						if (!GwtTextUtil.isEmpty(id) && DOM.getElementById(id) != null) {
							if (next != null) {
								next.start();
							}
							return;
						}
						StyleInjector.inject(GwtXmlUtil.getTextUnder(n));
						createId(id);
						if (next != null) {
							next.start();
					}}
					void createId(final String id) {
						if (!GwtTextUtil.isEmpty(id)) {
							// FIXME Only fake if brower is IE.
							// Since GWT cannot reliably return the injected style element. create one as a place holder for the id.
							final com.google.gwt.dom.client.Document d
								= com.google.gwt.dom.client.Document.get();
							final StyleElement style = d.createStyleElement();
							style.setPropertyString("language", "text/css");
							style.setId(id);
							getHead().appendChild(style);
					}}
				});
		}
		/** Inject link element, eg. stylesheet reference, into head section. */
		private void processLink(final Element n) {
			executor.queue(
				new IChainable() {
					@Override
					public void run(final IStartable next) {
						final String href = n.getAttribute("href");
						final com.google.gwt.dom.client.Document d
							= com.google.gwt.dom.client.Document.get();
						if (!GwtTextUtil.isEmpty(href)
							&& GwtDomUtil.hasElementWithAttributeValue(
								d, "link", "href", href)) {
							if (next != null) {
								next.start();
							}
							return;
						}
						final LinkElement link = d.createLinkElement();
						final NamedNodeMap attrs = n.getAttributes();
						for (int i = 0, len = attrs.getLength(); i < len; ++i) {
							final Node attr = attrs.item(i);
							link.setAttribute(attr.getNodeName(), attr.getNodeValue());
						}
						final NodeList<com.google.gwt.dom.client.Element> a
							= d.getElementsByTagName("head");
						if (a.getLength() > 0) {
							a.getItem(0).appendChild(link);
							if (!GwtTextUtil.isEmpty(href)) {
								asyncwait(next, href);
					}}}
				});
		}
		/** Replace element with given id by the given html content. */
		private void processComponent(final Element e) {
			executor.queue(
				new IChainable() {
					@Override
					public void run(final IStartable next) {
						final String id = e.getAttribute("id");
						DONE: {
							if (!"escaped-xml".equals(e.getAttribute("encoding"))) {
								GWT.log(
									"ERROR: Only escaped-xml encoding is currently supported for component update");
								break DONE;
							}
							if (GwtTextUtil.isEmpty(id)) {
								GWT.log(
									"ERROR: Ajax component update must specify an id",
									new Throwable());
								break DONE;
							}
							final com.google.gwt.dom.client.Document doc
								= com.google.gwt.dom.client.Document.get();
							final com.google.gwt.dom.client.Element o
								= doc.getElementById(id);
							if (o == null) {
								GWT.log(
									"ERROR: Ajax target component not found: id="
										+ id);
								break DONE;
							}
							final com.google.gwt.dom.client.Element n
								= doc.createDivElement();
							final String text = GwtXmlUtil.getTextUnder(e);
							n.setInnerHTML(GwtXmlUtil.unescXml(text));
							GwtDomUtil.replace(o, n.getFirstChildElement());
						}
						if (next != null) {
							next.start();
						}
						return;
					}
				});
		}
		/** Inject javascript, evaluate it and remove it. */
		private void processEvaluation(final Element e) {
			executor.queue(
				new IChainable() {
					@Override
					public void run(final IStartable next) {
						ScriptInjector.fromString(GwtXmlUtil.getTextUnder(e)).setRemoveTag(
							!DEBUG).inject();
						if (next != null) {
							next.start();
					}}
				});
		}
		// From wicket:
		// cross browser way to check when the css is loaded
		// taked from http://www.backalleycoder.com/2011/03/20/link-tag-css-stylesheet-load-event/
		// this makes a second GET request to the css but it gets it either from the cache or
		// downloads just the first several bytes and realizes that the MIME is wrong and ignores the rest
		native void asyncwait(final IStartable next, final String href)
		/*-{
		var img = document.createElement('img');
		var notifyCalled = false;
		img.onerror = function () {
		if (!notifyCalled) {
		notifyCalled = true;
		next.@sf.wicklet.gwt.client.util.ChainExecutor.IStartable::start()();
		}
		};
		img.src = href;
		if (img.complete) {
		if (!notifyCalled) {
		notifyCalled = true;
		next.@sf.wicklet.gwt.client.util.ChainExecutor.IStartable::start()();
		}
		}
		}-*/
		;
		private void todo(final String msg) {
			GWT.log(msg);
			new ErrorDialog(msg).showAtCenter();
		}
		@Override
		public void onError(final Request request, final Throwable e) {
			onError(request, "ERROR: ", e);
		}
		protected void onError(final Request request, final String msg, final Throwable e) {
			if (WickletAjax.DEBUG) {
				final StringBuilder b = new StringBuilder(msg);
				if (e != null) {
					for (final StackTraceElement s: e.getStackTrace()) {
						b.append(s.toString());
				}}
				new ErrorDialog(b.toString()).showAtCenter();
			} else {
				GWT.log(msg + (e != null ? e.getMessage() : ""));
		}}
	}

	////////////////////////////////////////////////////////////////////////
}
