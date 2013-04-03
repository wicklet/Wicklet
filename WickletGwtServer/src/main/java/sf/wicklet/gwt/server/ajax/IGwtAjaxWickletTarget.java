/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.ajax;

import java.util.Collection;
import java.util.Map;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.markup.head.IHeaderResponse;

public interface IGwtAjaxWickletTarget extends IGwtAjaxTarget {

	/** An {@link IGwtAjaxWickletTarget} listener that can be used to respond to various target-related events */
	interface IListener {
		/** Triggered before ajax request target begins its response cycle */
		void onBeforeRespond(Map<String, Component> map, IGwtAjaxWickletTarget target);
		/**
		 * Triggered after ajax request target is done with its response cycle. At this point only
		 * additional javascript can be output.
		 */
		void onAfterRespond(Map<String, Component> map, IGwtAjaxWickletTarget.IJavaScriptResponse jsresponse);
	}

	/**
	 * Components can implement this interface to get a notification when AjaxRequestTarget begins
	 * to respond. This can be used to postpone adding components to AjaxRequestTarget until the
	 * response begins.
	 *
	 * @author Matej Knopp
	 */
	interface ITargetRespondListener {
		/** Invoked when AjaxRequestTarget is about the respond. */
		void onTargetRespond(IGwtAjaxWickletTarget target);
	}

	/**
	 * An ajax javascript response that allows users to add javascript to be executed on the client side
	 * @author ivaynberg
	 */
	interface IJavaScriptResponse {
		/** Adds more javascript to the ajax response that will be executed on the client side */
		void addJavaScript(String script);
	}

	/**
	 * Adds a listener to this target
	 * @throws IllegalStateException if {@link IGwtAjaxWickletTarget.IListener}'s events are currently being fired
	 * or have both been fired already
	 */
	void addListener(IGwtAjaxWickletTarget.IListener listener);

	/**
	 * @return The header response associated with current IGwtAjaxRequestTarget.
	 *
	 * Beware that only renderOnDomReadyJavaScript and renderOnLoadJavaScript can be called outside
	 * the renderHeader(IHeaderResponse response) method. Calls to other render** methods will
	 * result in the call failing with a debug-level log statement to help you see why it failed.
	 *
	 * @return header response
	 */
	IHeaderResponse getHeaderResponse();

	/**
	 * Adds javascript that will be evaluated on the client side after components are replaced
	 */
	void appendJavaScript(CharSequence javascript);

	/**
	 * Adds javascript that will be evaluated on the client side before components are replaced
	 */
	void prependJavaScript(CharSequence javascript);

	/**
	 * Sets the focus in the browser to the element with the given Id.
	 * If id is null, no change to focus.
	 */
	void setFocus(String id);

	/**
	 * @return The HTML id of the last focused element, null if none.
	 */
	String getFocus();

	/**
	 * @return The page instance. Be aware that the page can be instantiated if this wasn't the case already.
	 */
	@Override
	Page getPage();

	void addChildren(MarkupContainer parent, Class<?> childCriteria);

	void add(Component...components);

	void add(Component component, String markupId);

	Collection<? extends Component> getComponents();

	void registerRespondListener(ITargetRespondListener listener);

	/**
	 * Replace element with given id using the given unescaped xml.
	 * The xml should has a single top level element with the given id.
	 * @param xml Unescaped xml, eg.
	 *     <div id="tochange">...</div>.
	 */
	void putComponentXml(Object id, String xml);

	/**
	 * Add header contribution elements, ie. script, style and link elements, to ajax response.
	 * The xml may contains multiple header elements.
	 * @param xml Unescaped xml, eg.
	 *     <script language="..">...</script>
	 *     <style>...</style>.
	 */
	void putHeaderXml(Object id, String xml);
}
