/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.test.model;

import org.junit.Assert;
import org.junit.Test;
import sf.wicklet.ext.model.AbstractPropertyProvider;
import sf.wicklet.ext.model.IProperty;
import sf.wicklet.ext.test.model.TestPropertyProvider01.FormModel.FormField;

public class TestPropertyProvider01 {

	static enum WID {
		username, password, rememberMe, captchaText; 
	}
	static class FormModel extends AbstractPropertyProvider {
		static interface FormField<T> {
			IProperty<String> username = new Prop<String>(String.class, WID.username);
			IProperty<String> password = new Prop<String>(String.class, WID.password);
			IProperty<Boolean> rememberMe = new Prop<Boolean>(Boolean.class, WID.rememberMe);
			IProperty<String> captchaText = new Prop<String>(String.class, WID.captchaText);
		}
		private static final long serialVersionUID = 1L;
		public FormModel() {
			super(FormField.username, FormField.password, FormField.rememberMe, FormField.captchaText);
		}
	}

	@Test
	public void test() {
		final FormModel model = new FormModel();
		Assert.assertTrue(model.setValue(model.propertyOf(WID.username), "abcd"));
		Assert.assertFalse(model.setValue(model.propertyOf(WID.username), Boolean.valueOf(true)));
		Assert.assertFalse(model.setValue(model.propertyOf(WID.rememberMe), "abcd"));
		Assert.assertTrue(model.setValue(model.propertyOf(WID.rememberMe), Boolean.valueOf(true)));
		Assert.assertTrue(model.setValue(model.propertyOf(WID.captchaText), "abcd"));
		Assert.assertEquals(null, model.getValue(FormField.username));
		Assert.assertEquals(Boolean.TRUE, model.getValue(FormField.rememberMe));
		Assert.assertEquals(null, model.getValue(FormField.password));
		Assert.assertEquals("abcd", model.getValue(FormField.captchaText));
	}
}
