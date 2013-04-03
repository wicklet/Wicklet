/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.util;

import java.io.Serializable;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

/** A model that don't allow setObject(). */
public class ReadonlyModel<T extends Serializable> implements IModel<T> {
	public static <T extends Serializable> IModel<T> of(final T object) {
		return new ReadonlyModel<T>(object);
	}
	private static final long serialVersionUID = 1L;
	private final T object;
	public ReadonlyModel(final T object) {
		this.object = object;
	}
	@Override
	public T getObject() {
		return object;
	}
	@Override
	public void setObject(final T object) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void detach() {
		if (object instanceof IDetachable) {
			((IDetachable)object).detach();
	}}
}
