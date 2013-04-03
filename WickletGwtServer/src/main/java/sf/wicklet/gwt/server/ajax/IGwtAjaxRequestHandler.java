/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.server.ajax;

import org.apache.wicket.core.request.handler.IPageRequestHandler;
import org.apache.wicket.request.ILoggableRequestHandler;


/**
 * A request target that produces ajax response envelopes used on the client side to update
 * component markup as well as evaluate arbitrary javascript.
 * <p>
 * A component whose markup needs to be updated should be added to this target via
 * IGwtAjaxRequestTarget#add(Component) method. Its body will be rendered and added to the envelope when
 * the target is processed, and refreshed on the client side when the ajax response is received.
 * <p>
 * It is important that the component whose markup needs to be updated contains an id attribute in
 * the generated markup that is equal to the value retrieved from Component#getMarkupId(). This can
 * be accomplished by either setting the id attribute in the html template, or using an attribute
 * modifier that will add the attribute with value Component#getMarkupId() to the tag ( such as
 * MarkupIdSetter )
 * <p>
 * Any javascript that needs to be evaluated on the client side can be added using
 * IGwtAjaxRequestTarget#append/prependJavaScript(String). For example, this feature can be useful when
 * it is desirable to link component update with some javascript effects.
 * <p>
 * The target provides a listener interface {@link IGwtAjaxWickletTarget.IListener} that can be used to
 * add code that responds to various target events by adding listeners via
 * {@link #addListener(IGwtAjaxWickletTarget.IListener)}
 *
 * @since 1.2
 *
 * @author Igor Vaynberg (ivaynberg)
 * @author Eelco Hillenius
 */
public interface IGwtAjaxRequestHandler extends IPageRequestHandler, ILoggableRequestHandler {
	IGwtAjaxTarget getGwtAjaxTarget();
}
