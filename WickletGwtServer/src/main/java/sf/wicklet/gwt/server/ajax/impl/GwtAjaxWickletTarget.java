/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.ajax.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.core.request.handler.logger.PageLogData;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.response.StringResponse;
import org.apache.wicket.response.filter.IResponseFilter;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import sf.wicklet.gwt.server.ajax.IGwtAjaxWickletTarget;

/** A wicket like ajax xml response target. */
public class GwtAjaxWickletTarget extends AbstractGwtAjaxTarget implements IGwtAjaxWickletTarget {

	/**
	 * A POJO-like that collects the data for the Ajax response written to the client and serializes
	 * it to specific String-based format (XML, JSON, ...).
	 */
	private final AbstractGwtAjaxResponse responseObject;
	List<IGwtAjaxWickletTarget.IListener> listeners = null;
	private final Set<ITargetRespondListener> respondListeners = new HashSet<ITargetRespondListener>();
	/** see https://issues.apache.org/jira/browse/WICKET-3564 */
	protected transient boolean respondersFrozen;
	protected transient boolean listenersFrozen;
	/** The associated Page */
	private final Page page;
	private PageLogData logData;

	/**
	 * @param page The currently active page
	 */
	public GwtAjaxWickletTarget(final Page page) {
		this.page = Args.notNull(page, "page");
		responseObject = new GwtAjaxWickletResponse(page) {
			/**
			 * Freezes the {@link AjaxRequestHandler#listeners}, and does not un-freeze them as the
			 * events will have been fired by now.
			 */
			@Override
			protected void fireOnAfterRespondListeners(final Response response) {
				listenersFrozen = true;
				// invoke onafterresponse event on listeners
				if (listeners != null) {
					final Map<String, Component> components
						= Collections.unmodifiableMap(markupIdToComponent);
					// create response that will be used by listeners to append javascript
					final IGwtAjaxWickletTarget.IJavaScriptResponse jsresponse
						= new IGwtAjaxWickletTarget.IJavaScriptResponse() {
							@Override
							public void addJavaScript(final String script) {
								writeNormalEvaluations(
									response,
									Collections.<CharSequence>singleton(script));
							}
						};
					for (final IGwtAjaxWickletTarget.IListener listener: listeners) {
						listener.onAfterRespond(components, jsresponse);
			}}}
			/**
			 * Freezes the {@link AjaxRequestHandler#listeners} before firing the event and
			 * un-freezes them afterwards to allow components to add more
			 * {@link IGwtAjaxWickletTarget.IListener}s for the second event.
			 */
			@Override
			protected void fireOnBeforeRespondListeners() {
				listenersFrozen = true;
				if (listeners != null) {
					final Map<String, Component> components
						= Collections.unmodifiableMap(markupIdToComponent);
					for (final IGwtAjaxWickletTarget.IListener listener: listeners) {
						listener.onBeforeRespond(components, GwtAjaxWickletTarget.this);
				}}
				listenersFrozen = false;
			}
		};
	}

	/**
	 * @see org.apache.wicket.core.request.handler.IPageRequestHandler#getPage()
	 */
	@Override
	public Page getPage() {
		return page;
	}

	@Override
	public void putComponentXml(final Object id, final String xml) {
		responseObject.putComponentXml(id, xml);
	}

	@Override
	public void putHeaderXml(final Object id, final String xml) {
		responseObject.putHeaderXml(id, xml);
	}

	@Override
	public void addListener(final IGwtAjaxWickletTarget.IListener listener) throws IllegalStateException {
		Args.notNull(listener, "listener");
		assertListenersNotFrozen();
		if (listeners == null) {
			listeners = new LinkedList<IGwtAjaxWickletTarget.IListener>();
		}
		if (!listeners.contains(listener)) {
			listeners.add(listener);
	}}

	@Override
	public final void addChildren(final MarkupContainer parent, final Class<?> childCriteria) {
		Args.notNull(parent, "parent");
		Args.notNull(childCriteria, "childCriteria");
		parent.visitChildren(
			childCriteria,
			new IVisitor<Component, Void>() {
				@Override
				public void component(final Component component, final IVisit<Void> visit) {
					add(component);
					visit.dontGoDeeper();
				}
			});
	}

	@Override
	public void add(final Component...components) {
		for (final Component component: components) {
			Args.notNull(component, "component");
			if (component.getOutputMarkupId() == false && !(component instanceof Page)) {
				throw new IllegalArgumentException(
					"cannot update component that does not have setOutputMarkupId property set to true. Component: "
						+ component.toString());
			}
			add(component, component.getMarkupId());
	}}

	@Override
	public void add(final Component component, final String markupId) {
		responseObject.add(component, markupId);
	}

	@Override
	public final Collection<? extends Component> getComponents() {
		return responseObject.getComponents();
	}

	@Override
	public final void setFocus(final String id) {
		appendJavaScript("Wicket.Focus.setFocusOnId(" + id + ");");
	}

	@Override
	public final void appendJavaScript(final CharSequence javascript) {
		responseObject.appendJavaScript(javascript);
	}

	/**
	 * @see org.apache.wicket.core.request.handler.IPageRequestHandler#detach(org.apache.wicket.request.IRequestCycle)
	 */
	@Override
	public void detach(final IRequestCycle requestCycle) {
		if (logData == null) {
			logData = new PageLogData(page);
		}
		responseObject.detach(requestCycle);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof GwtAjaxWickletTarget) {
			final GwtAjaxWickletTarget that = (GwtAjaxWickletTarget)obj;
			return responseObject.equals(that.responseObject);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = "AjaxRequestHandler".hashCode();
		result += responseObject.hashCode() * 17;
		return result;
	}

	@Override
	public final void prependJavaScript(final CharSequence javascript) {
		responseObject.prependJavaScript(javascript);
	}

	@Override
	public void registerRespondListener(final ITargetRespondListener listener) {
		assertRespondersNotFrozen();
		respondListeners.add(listener);
	}

	/**
	 * @see org.apache.wicket.core.request.handler.IPageRequestHandler#respond(org.apache.wicket.request.IRequestCycle)
	 */
	@Override
	public final void respond(final IRequestCycle requestCycle) {
		final RequestCycle rc = (RequestCycle)requestCycle;
		final WebResponse response = (WebResponse)requestCycle.getResponse();
		if (errorResponse(response)) {
			return;
		}
		if (responseObject.containsPage()) {
			// the page itself has been added to the request target, we simply issue a redirect
			// back to the page
			final IRequestHandler handler = new RenderPageRequestHandler(new PageProvider(page));
			final String url = rc.urlFor(handler).toString();
			response.sendRedirect(url);
			return;
		}
		respondersFrozen = true;
		for (final ITargetRespondListener listener: respondListeners) {
			listener.onTargetRespond(this);
		}
		final Application app = page.getApplication();
		page.send(app, Broadcast.BREADTH, this);
		// Determine encoding
		final String encoding = app.getRequestCycleSettings().getResponseRequestEncoding();
		// Set content type based on markup type for page
		responseObject.setContentType(response, encoding);
		// Make sure it is not cached by a client
		response.disableCaching();
		try {
			final StringResponse bodyResponse = new StringResponse();
			responseObject.writeTo(bodyResponse, encoding);
			final CharSequence filteredResponse = invokeResponseFilters(bodyResponse);
			response.write(filteredResponse);
		} finally {
			// restore the original response
			rc.setResponse(response);
	}}

	/**
	 * Runs the configured {@link IResponseFilter}s over the constructed Ajax response
	 *
	 * @param contentResponse
	 *            the Ajax {@link Response} body
	 * @return filtered response
	 */
	private AppendingStringBuffer invokeResponseFilters(final StringResponse contentResponse) {
		AppendingStringBuffer responseBuffer = new AppendingStringBuffer(contentResponse.getBuffer());
		final List<IResponseFilter> responseFilters = Application.get().getRequestCycleSettings()
			.getResponseFilters();
		if (responseFilters != null) {
			for (final IResponseFilter filter: responseFilters) {
				responseBuffer = filter.filter(responseBuffer);
		}}
		return responseBuffer;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[AjaxRequestHandler@" + hashCode() + " responseObject [" + responseObject + "]";
	}

	@Override
	public IHeaderResponse getHeaderResponse() {
		return responseObject.getHeaderResponse();
	}

	/**
	 * @return the markup id of the focused element in the browser
	 */
	@Override
	public String getFocus() {
		final WebRequest request = (WebRequest)page.getRequest();
		final String id = request.getHeader("Wicket-FocusedElementId");
		return Strings.isEmpty(id) ? null : id;
	}

	private void assertNotFrozen(final boolean frozen, final Class<?> clazz) {
		if (frozen) {
			throw new IllegalStateException(Classes.simpleName(clazz) + "s can no longer be added");
	}}

	private void assertRespondersNotFrozen() {
		assertNotFrozen(respondersFrozen, IGwtAjaxWickletTarget.ITargetRespondListener.class);
	}

	private void assertListenersNotFrozen() {
		assertNotFrozen(listenersFrozen, IGwtAjaxWickletTarget.IListener.class);
	}
}
