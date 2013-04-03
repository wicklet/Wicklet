/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.ajax.impl;

import java.util.Collection;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import sf.blacksun.util.text.XmlUtil;

/**
 * An AbstractAjaxResponse that serializes itself to XML.
 * <p>
 *     The elements of such response are:
 *     <ul>
 *         <li>priority-evaluate - an item of the prepend JavaScripts</li>
 *         <li>component - the markup of the updated component</li>
 *         <li>evaluate - an item of the onDomReady and append JavaScripts</li>
 *         <li>header-contribution - all HeaderItems which have been contributed in components'
 *         and their behaviors' #renderHead(Component, IHeaderResponse)</li>
 *     </ul>
 * </p>
 */
public abstract class GwtAjaxWickletResponse extends AbstractGwtAjaxResponse {

	GwtAjaxWickletResponse(final Page page) {
		super(page);
	}

	@Override
	public void setContentType(final WebResponse response, final String encoding) {
		response.setContentType("text/xml; charset=" + encoding);
	}

	@Override
	protected void writeHeader(final Response response, final String encoding) {
		response.write("<?xml version=\"1.0\" encoding=\"");
		response.write(encoding);
		response.write("\"?>");
		response.write("<ajax-response>");
	}

	@Override
	protected void writeComponent(
		final Response response, final String markupId, final Component component, final String encoding) {
		if (component.getRenderBodyOnly() == true) {
			throw new IllegalStateException(
				"Ajax render cannot be called on component that has setRenderBodyOnly enabled. Component: "
					+ component.toString());
		}
		component.setOutputMarkupId(true);
		// substitute our encoding response for the real one so we can capture
		// component's markup in a manner safe for transport inside CDATA block
		encodingBodyResponse.reset();
		RequestCycle.get().setResponse(encodingBodyResponse);
		// Initialize temporary variables
		final Page page = component.findParent(Page.class);
		if (page == null) {
			// dont throw an exception but just ignore this component, somehow
			// it got removed from the page.
			AbstractGwtAjaxResponse.LOG.debug(
				"component: "
					+ component
					+ " with markupid: "
					+ markupId
					+ " not rendered because it was already removed from page");
			return;
		}
		page.startComponentRender(component);
		try {
			component.prepareForRender();
			// render any associated headers of the component
			writeHeaderContribution(response, component);
		} catch (final RuntimeException e) {
			try {
				component.afterRender();
			} catch (final RuntimeException e2) {
				// ignore this one could be a result off.
			}
			// Restore original response
			RequestCycle.get().setResponse(response);
			encodingBodyResponse.reset();
			throw e;
		}
		try {
			component.render();
		} catch (final RuntimeException e) {
			RequestCycle.get().setResponse(response);
			encodingBodyResponse.reset();
			throw e;
		}
		page.endComponentRender(component);
		// Restore original response
		RequestCycle.get().setResponse(response);
		response.write("<component id=\"");
		response.write(markupId);
		response.write("\" ");
		//        if (encodingBodyResponse.isContentsEncoded()) {
		//            response.write(" encoding=\"");
		//            response.write(getEncodingName());
		//            response.write("\" ");
		//        }
		response.write(" encoding=\"escaped-xml\" >");
		response.write(XmlUtil.escXml(encodingBodyResponse.getContents()));
		response.write("</component>");
		encodingBodyResponse.reset();
	}

	/**
	 * @param xmlcontent Escaped xml.
	 */
	@Override
	protected void writeHeaderXml(
		final Response response, final String id, final String xmlcontent, final String encoding) {
		response.write("<header-contribution encoding=\"escaped-xml\" id=\"" + id + "\">");
		response.write(xmlcontent);
		response.write("</header-contribution>");
	}

	/**
	 * @param xmlcontent Escaped xmlcontent.
	 */
	@Override
	protected void writeComponentXml(
		final Response response, final String markupId, final String xmlcontent, final String encoding) {
		response.write("<component encoding=\"escaped-xml\" id=\"");
		response.write(markupId);
		response.write("\">");
		response.write(xmlcontent);
		response.write("</component>");
	}

	@Override
	protected void writeFooter(final Response response, final String encoding) {
		response.write("</ajax-response>");
	}

	@Override
	protected void writeHeaderContribution(final Response response) {
		if (encodingHeaderResponse.getContents().length() != 0) {
			response.write("<header-contribution");
			//            if (encodingHeaderResponse.isContentsEncoded()) {
			//                response.write(" encoding=\"");
			//                response.write(getEncodingName());
			//                response.write("\" ");
			//            }
			response.write(" encoding=\"escaped-xml\">");
			// we need to write response as CDATA and parse it on client,
			// because konqueror crashes when there is a <script> element
			response.write(
				XmlUtil.escXml(
					"<head xmlns:wicket=\"http://wicket.apache.org\">"
						+ encodingHeaderResponse.getContents()
						+ "</head>"));
			response.write("</header-contribution>");
	}}

	@Override
	protected void writeNormalEvaluations(final Response response, final Collection<CharSequence> scripts) {
		writeEvaluations(response, "evaluate", scripts);
	}

	@Override
	protected void writePriorityEvaluations(final Response response, final Collection<CharSequence> scripts) {
		writeEvaluations(response, "priority-evaluate", scripts);
	}

	private void writeEvaluations(
		final Response response, final String elementName, final Collection<CharSequence> scripts) {
		for (final CharSequence script: scripts) {
			writeEvaluation(elementName, response, script);
	}}

	/**
	 * @param invocation type of invocation tag, usually {@literal evaluate} or {@literal priority-evaluate}
	 */
	private void writeEvaluation(final String invocation, final Response response, final CharSequence js) {
		final CharSequence javascript = js;
		// final boolean encoded = false;
		// encode the response if needed
		//        if (AbstractGwtAjaxResponse.needsEncoding(js)) {
		//            encoded = true;
		//            javascript = AbstractGwtAjaxResponse.encode(js);
		//        }
		response.write("<");
		response.write(invocation);
		//        if (encoded) {
		//            response.write(" encoding=\"");
		//            response.write(getEncodingName());
		//            response.write("\"");
		//        }
		response.write(" encoding=\"escaped-xml\" >");
		response.write(XmlUtil.escXml(javascript));
		response.write("</");
		response.write(invocation);
		response.write(">");
		encodingBodyResponse.reset();
	}
}
