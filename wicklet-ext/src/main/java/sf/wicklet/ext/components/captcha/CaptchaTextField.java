/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.components.captcha;

import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;

public class CaptchaTextField extends RequiredTextField<String> {
	private static final long serialVersionUID = 1L;
	private static final String MSG_CAPTCHA_FAILED = "Captcha not match, please try again";
	public CaptchaTextField(final Object wid, final String captchatext) {
		super(wid.toString());
		add(
			new IValidator<String>() {
				private static final long serialVersionUID = 1L;
				@Override
				public void validate(final IValidatable<String> validatable) {
					final String input = validatable.getValue();
					if (captchatext != null && !captchatext.equals(input)) {
						getFeedbackMessages().error(CaptchaTextField.this, MSG_CAPTCHA_FAILED);
				}}
			});
	}
}
