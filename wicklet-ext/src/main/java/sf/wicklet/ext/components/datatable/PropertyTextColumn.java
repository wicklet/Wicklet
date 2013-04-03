/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.components.datatable;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

public class PropertyTextColumn<T extends IPropertyTextProvider<P>, P>
	extends AbstractColumn<T, P> implements IExportableColumn<T, P, String> {

	private static final long serialVersionUID = 1L;
	private final P property;

	public PropertyTextColumn(final IModel<String> displaymodel, final P sortproperty, final P property) {
		super(displaymodel, sortproperty);
		this.property = property;
	}

	@Override
	public void populateItem(final Item<ICellPopulator<T>> item, final String wid, final IModel<T> rowmodel) {
		item.add(new Label(wid, getDataModel(rowmodel)));
	}

	@Override
	public IModel<String> getDataModel(final IModel<T> rowmodel) {
		final T model = rowmodel.getObject();
		return model.getLabel(property);
	}
}
