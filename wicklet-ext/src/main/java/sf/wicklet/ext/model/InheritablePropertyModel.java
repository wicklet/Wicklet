/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.model;

import org.apache.wicket.Component;
import org.apache.wicket.model.IComponentInheritedModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;

public class InheritablePropertyModel<P extends IProperty<?>, V extends IPropertyProvider>
	implements IComponentInheritedModel<V> {

	private static final long serialVersionUID = 1L;

	private V provider;

	public InheritablePropertyModel(final V provider) {
		this.provider = provider;
	}

	public IPropertyProvider getProvider() {
		return provider;
	}

	@Override
	public V getObject() {
		return provider;
	}

	@Override
	public void setObject(final V object) {
		this.provider = object;
	}

	@Override
	public void detach() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public <W> IWrapModel<W> wrapOnInheritance(final Component component) {
		return new PropertyValueModel<W>((IProperty<W>)provider.propertyOf(component.getId()));
	}

	private class PropertyValueModel<X> implements IWrapModel<X> {
		private static final long serialVersionUID = 1L;
		final IProperty<X> property;
		public PropertyValueModel(final IProperty<X> property) {
			this.property = property;
		}
		@Override
		public X getObject() {
			return InheritablePropertyModel.this.getProvider().getValue(property);
		}
		@Override
		public void setObject(final X value) {
			InheritablePropertyModel.this.getProvider().setValue(property, value);
		}
		@Override
		public void detach() {
		}
		@Override
		public IModel<?> getWrappedModel() {
			return this;
		}
	}
}
