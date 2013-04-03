/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.panels;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessages;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import sf.blacksun.util.text.XMLStringWriter;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.ext.components.captcha.CaptchaTextField;
import sf.wicklet.ext.components.captcha.WxCaptchaResource;
import sf.wicklet.ext.model.IProperty;
import sf.wicklet.ext.model.InheritablePropertyModel;
import sf.wicklet.ext.ui.panels.WickletPanel;
import sf.wicklet.gwt.server.ajax.GwtAuthenticatedWebApplication;
import sf.wicklet.gwt.server.ajax.IGwtAjaxRequestHandler;
import sf.wicklet.gwt.server.ajax.IGwtAjaxXmlTarget;
import sf.wicklet.gwt.server.ajax.impl.GwtAjaxXmlTarget;
import sf.wicklet.gwt.site.server.MyAuthenticatedWebSession;
import sf.wicklet.gwt.site.server.db.WikiCommentInfo;
import sf.wicklet.gwt.site.server.panels.WikiCommentPanel.FormModel.FormField;
import sf.wicklet.gwt.site.shared.WID;

/*
 * NOTE: Intentionally using wicket form processing here.
 * But for simple forms like this, the ajax response can process the for directly instead.
 */
public class WikiCommentPanel extends WickletPanel {

    private static final long serialVersionUID = 1L;
    private static final String PARAM_HREF = "href";
    private static final int ROWS = 16;
    private static final IRootMarkup MARKUP = new WicketBuilder() {
        IRootMarkup build() {
            return serialize(
                wicketPanel(
                    id(WID.commentPanel),
                    h1(txt("Comment")),
                    div(txt("Wiki: "), component(id(WID.commentPath)).span()),
                    component(id(WID.commentForm)).form(
                        vbox(
                            div(
                                istyle("width: 100%"),
                                txt("Subject: "),
                                component(WID.commentSubject).input(
                                    name(WID.commentSubject),
                                    type("text"),
                                    attr("size", "64"),
                                    istyle("margin: 10px 0px 4px 4px"))),
                            div(
                                component(WID.commentText).textarea(
                                    name(WID.commentText), //
                                    attr("rows", ROWS),
                                    istyle("width: -moz-available")))),
                        div(
                            istyle("margin: 10px 10px 10px 3px"),
                            div(
                                component(id(WID.commentCaptchaImage)).img(
                                    istyle("display: block; margin-right: 20px; margin-bottom: 10px")),
                                txt("Enter captcha text here:"),
                                br(),
                                component(WID.commentCaptchaText).input(
                                    istyle("margin: 4px 0px"), //
                                    type("text"),
                                    name(WID.commentCaptchaText))),
                            div(
                                button(
                                    id(WID.commentSubmit), //
                                    type("submit"),
                                    name(WID.commentSubmit),
                                    value("Submit"),
                                    txt("Submit"))))),
                    component(id(WID.commentFeedback)).div()));
        }
    }.build();

    public static class FormModel extends sf.wicklet.ext.model.AbstractPropertyProvider {
        private static final long serialVersionUID = 1L;
        public interface FormField {
            IProperty<String> subject = new Prop<String>(String.class, WID.commentSubject);
            IProperty<String> captcha = new Prop<String>(String.class, WID.commentCaptchaText);
            IProperty<String> text = new Prop<String>(String.class, WID.commentText);
        }
        public FormModel() {
            super(FormField.subject, FormField.captcha, FormField.text);
        }
    }

    private final String href;
    final WxCaptchaResource captcha = new WxCaptchaResource(challengetext());
    final FormModel formModel = new FormModel();

    public WikiCommentPanel(final Object id, final IRequestParameters p) {
        super(id);
        href = p.getParameterValue(PARAM_HREF).toString();
        final Form<FormModel> form = new Form<FormModel>(WID.commentForm.toString()) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void onSubmit() {
                processSubmit();
            }
            @Override
            protected void onError() {
                processError(this);
            }
        };
        add(new Label(WID.commentPath.toString(), href));
        add(form);
        add(new FeedbackPanel(WID.commentFeedback.toString()));
        form.setModel(new InheritablePropertyModel<FormModel.Prop<?>, FormModel>(formModel));
        form.add(new RequiredTextField<String>(WID.commentSubject.toString()));
        form.add(new TextArea<String>(WID.commentText.toString()));
        form.add(new Image(WID.commentCaptchaImage.toString(), captcha).setOutputMarkupId(true));
        form.add(new CaptchaTextField(WID.commentCaptchaText.toString(), captcha.getChallengeId()));
    }

    @Override
    public IRootMarkup getAssociatedMarkup() {
        return MARKUP;
    }

    protected String challengetext() {
        return null;
    }

    void processSubmit() {
        final IGwtAjaxXmlTarget target = getAjaxXmlTarget();
        final MyAuthenticatedWebSession s = MyAuthenticatedWebSession.get();
        final String user = s.getUsername();
        if (new WikiCommentInfo(user, href, formModel.getValue(FormField.subject), formModel.getValue(FormField.text))
            .persist()) {
            getPage().replace(new Label(WID.commentPanel.toString(), ""));
            onOK(target);
            return;
        }
        onFail(target, "ERROR: Failed to save comment, please try again later");
    }

    void processError(final Form<FormModel> form) {
        final IGwtAjaxXmlTarget target = getAjaxXmlTarget();
        target.write(
            new XMLStringWriter() {
                String build() {
                    final FeedbackMessages msgs = form.getFeedbackMessages();
                    final XMLStringWriter w = new XMLStringWriter();
                    w.start("ul", "style", "color: red;");
                    for ( final FeedbackMessage msg: msgs) {
                        w.element("li", escXml(msg.getLevelAsString() + ": " + msg.getMessage()));
                    }
                    form.visitFormComponents(
                        new IVisitor<FormComponent<?>, Void>() {
                            @Override
                            public void component(final FormComponent<?> c, final IVisit<Void> visit) {
                                for ( final FeedbackMessage msg: c.getFeedbackMessages()) {
                                    w.element("li", escXml(msg.getLevelAsString() + ": " + msg.getMessage()));
                            }}
                        });
                    return "<feedback>" + escXml(w.endAll().toString()) + "</feedback>";
                }
            }.build());
    }

    void onFail(final IGwtAjaxXmlTarget target, final String msg) {
        target.write(
            new XMLStringWriter() {
                String build() {
                    return xmlHeader().element(
                        "feedback", escXml("<ul style=\"color: red;\"><li>" + msg + "</li></ul>")).toString();
                }
            }.build());
    }

    void onOK(final IGwtAjaxXmlTarget target) {
        target.write(
            new XMLStringWriter() {
                String build() {
                    return xmlHeader().element(
                        "success",
                        escXml(
                            "<div style=\"margin: 10px;\">"
                                + "<h3>Success</h3>"
                                + "<p>You comment is accepted, thank you</p>"
                                + "</div>")).toString();
                }
            }.build());
    }

    IGwtAjaxXmlTarget getAjaxXmlTarget() {
        final IGwtAjaxXmlTarget target = new GwtAjaxXmlTarget(getPage());
        final IGwtAjaxRequestHandler handler = GwtAuthenticatedWebApplication.get().getGwtAjaxSupport()
            .createGwtAjaxRequestHandler(getPage(), target);
        RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);
        return target;
    }
}
