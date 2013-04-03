/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.auth;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import sf.wicklet.dsl.html.api.AV;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.dsl.html.impl.WicketSerializer;
import sf.wicklet.ext.Messages;
import sf.wicklet.ext.auth.WxSignInPanel.FormModel.FormField;
import sf.wicklet.ext.components.captcha.WxCaptchaResource;
import sf.wicklet.ext.model.IProperty;
import sf.wicklet.ext.model.InheritablePropertyModel;
import sf.wicklet.ext.ui.panels.WickletPanel;

/**
 * A signin panel with captcha.
 */
public class WxSignInPanel extends WickletPanel {
	public static final String MSG_AUTHENTICATION_FAILED
		= Messages.get("authenticationFailed", "ERROR: Authentication failed");
	public static final String MSG_CAPTCHA_FAILED = Messages.get(
		"captchaFailed", "ERROR: Captcha text do not match");
	public static final String MSG_PASSWORD_REQUIRED = Messages.get("passwordRequired", "'password' is required.");
	private static final long serialVersionUID = 1L;
	private static final IRootMarkup MARKUP = WicketSerializer.serialize(
		new WicketBuilder() {
			IElement build() {
				return wicketPanel(
					component(WID.feedback, div(istyle("text-align: left"))),
					component(
						WID.signinForm,
						form(
							table(
								tr(
									td(
										AV.Align.Right,
										txt(Messages.get("Username"))),
									td(
										component(
											WID.username,
											input(AV.Input.Type.Text)))),
								tr(
									td(
										AV.Align.Right,
										txt(Messages.get("Password"))),
									td(
										component(
											WID.password,
											input(AV.Input.Type.Password)))),
								component(
									WID.rememberTr,
									tr(
										td(),
										td(
											txt(
												Messages.get(
													"RememberMe")),
											component(
												WID.rememberMe,
												input(
													AV.Input.Type
														.Checkbox))))),
								component(
									WID.captchaTextTr,
									tr(
										td(
											AV.Align.Right,
											txt(
												Messages.get(
													"Enter captcha text"))),
										td(
											component(
												WID.captchaText,
												input(
													AV.Input.Type
														.Text))))),
								component(
									WID.captchaTr,
									tr(
										td(
											attr("colspan", "2"),
											AV.Align.Right,
											component(WID.captcha, img())))),
								tr(
									td(),
									td(
										AV.Align.Right,
										input(
											type("submit"),
											name("submit"),
											value("Sign In")),
										input(type("reset"), value("Reset"))))))));
			}
		}.build());

	public static class FormModel extends sf.wicklet.ext.model.AbstractPropertyProvider {
		private static final long serialVersionUID = 1L;
		public static interface FormField {
			IProperty<String> username = new Prop<String>(String.class, WID.username);
			IProperty<String> password = new Prop<String>(String.class, WID.password);
			IProperty<Boolean> rememberMe = new Prop<Boolean>(Boolean.class, WID.rememberMe, false);
			IProperty<String> captchaText = new Prop<String>(String.class, WID.captchaText);
		}
		public FormModel() {
			super(FormField.username, FormField.password, FormField.rememberMe, FormField.captchaText);
		}
	}

	//////////////////////////////////////////////////////////////////////

	private boolean includeRememberMe = false;
	private boolean useCaptcha = true;
	private boolean replaceSession = true;
	private boolean resetCaptchaOnRetry = false;
	private final FormModel formModel = new FormModel();
	private WxCaptchaResource captcha;

	//////////////////////////////////////////////////////////////////////

	private enum WID {
		feedback,
		signinForm, username, password,
		rememberTr, rememberMe,
		captchaTextTr, captchaText, captchaTr, captcha;
	}

	//////////////////////////////////////////////////////////////////////

	public WxSignInPanel(final String id) {
		super(id);
		add(new FeedbackPanel(WID.feedback.name()));
		add(new SignInForm(WID.signinForm.name()));
	}

	public WxSignInPanel useRememberMe(final boolean b) {
		includeRememberMe = b;
		return this;
	}

	public WxSignInPanel useCaptcha(final boolean b) {
		useCaptcha = b;
		return this;
	}

	public WxSignInPanel resetCaptchaOnRetry(final boolean b) {
		resetCaptchaOnRetry = b;
		return this;
	}

	public WxSignInPanel replaceSession(final boolean b) {
		replaceSession = b;
		return this;
	}

	@Override
	public IRootMarkup getAssociatedMarkup() {
		return MARKUP;
	}

	//////////////////////////////////////////////////////////////////////

	/** For testing only. Override this to force a challege text. */
	protected String challegetext() {
		return null;
	}

	protected SignInForm getForm() {
		return (SignInForm)get(WID.signinForm.name());
	}

	@Override
	protected void onBeforeRender() {
		if (isSignedIn() == false) {
			final IAuthenticationStrategy authenticationStrategy
				= getApplication().getSecuritySettings().getAuthenticationStrategy();
			final String[] data = authenticationStrategy.load();
			if ((data != null) && (data.length > 1)) {
				if (signIn(data[0], data[1])) {
					continueToOriginalDestination();
					throw new RestartResponseException(
						getSession().getPageFactory().newPage(getApplication().getHomePage()));
				}
				// the loaded credentials are wrong. erase them.
				authenticationStrategy.remove();
		}}
		super.onBeforeRender();
	}

	/** @return true if sign in succeed. */
	private boolean signIn(final String username, final String password) {
		return AuthenticatedWebSession.get().signIn(username, password);
	}

	/**
	 * @return true, if signed in
	 */
	private boolean isSignedIn() {
		return AuthenticatedWebSession.get().isSignedIn();
	}

	protected void onSignInFailed() {
		addStateChange();
	}

	protected void onSignInSucceeded() {
		continueToOriginalDestination();
		setResponsePage(getApplication().getHomePage());
	}

	@SuppressWarnings("synthetic-access")
	public final class SignInForm extends StatelessForm<FormModel> {
		private static final long serialVersionUID = 1L;

		public SignInForm(final String id) {
			super(id);
			setModel(new InheritablePropertyModel<FormModel.Prop<?>, FormModel>(formModel));
			add(new TextField<String>(WID.username.name()));
			add(new PasswordTextField(WID.password.name()));
			add(new WebMarkupContainer(WID.rememberTr.name()).add(new CheckBox(WID.rememberMe.name())));
			if (useCaptcha) {
				captcha = new WxCaptchaResource(challegetext());
				add(
					new WebMarkupContainer(WID.captchaTr.name()).add(
						new Image(WID.captcha.name(), captcha).setOutputMarkupId(true)));
				add(
					new WebMarkupContainer(WID.captchaTextTr.name()).add(
						new TextField<String>(WID.captchaText.name())));
			} else {
				captcha = null;
				add(
					new WebMarkupContainer(WID.captchaTr.name()).add(
						new Label(WID.captchaText.name(), Model.of(""))));
			}
		}

		@Override
		protected void onConfigure() {
			get(WID.rememberTr.name()).setVisible(includeRememberMe);
			get(WID.captchaTextTr.name()).setVisible(useCaptcha);
			get(WID.captchaTr.name()).setVisible(useCaptcha);
			super.onConfigure();
		}

		@Override
		public final void onSubmit() {
			final IAuthenticationStrategy strategy
				= getApplication().getSecuritySettings().getAuthenticationStrategy();
			if (useCaptcha) {
				final String input = formModel.getValue(FormField.captchaText);
				if (captcha == null || !captcha.getChallengeId().equals(input)) {
					getFeedbackMessages().add(
						new FeedbackMessage(this, MSG_CAPTCHA_FAILED, FeedbackMessage.ERROR));
					if (resetCaptchaOnRetry) {
						captcha.invalidate(challegetext());
					}
					onSignInFailed();
					strategy.remove();
					return;
			}}
			final String username = formModel.getValue(FormField.username);
			final String password = formModel.getValue(FormField.password);
			final AuthenticatedWebSession session = AuthenticatedWebSession.get();
			if (replaceSession) {
				session.replaceSession();
			}
			if (signIn(username, password)) {
				if (formModel.getValue(FormField.rememberMe) == true) {
					strategy.save(username, password);
				} else {
					strategy.remove();
				}
				captcha.invalidate(challegetext());
				onSignInSucceeded();
			} else {
				if (resetCaptchaOnRetry) {
					captcha.invalidate(challegetext());
				}
				getFeedbackMessages().add(
					new FeedbackMessage(this, MSG_AUTHENTICATION_FAILED, FeedbackMessage.ERROR));
				onSignInFailed();
				strategy.remove();
		}}
	}

	//////////////////////////////////////////////////////////////////////
}
