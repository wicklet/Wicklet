/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.ajax;

import java.io.Serializable;
import org.apache.wicket.ajax.AjaxRequestTarget;

public interface IAjaxCallback<T> extends Serializable {
	void ajaxCallback(final AjaxRequestTarget target, T data);
}
