/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.test.arquillian.test01;

import org.apache.wicket.Page;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;
import org.apache.wicket.request.mapper.info.DefaultPageComponentInfoCodec;
import sf.blacksun.util.RandomUtil;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.dsl.html.impl.WicketSerializer;
import sf.wicklet.ext.ui.accordion.AccordionBehavior;
import sf.wicklet.ext.ui.pages.SimpleWickletPage;

public class TestApplication extends WebApplication {

	////////////////////////////////////////////////////////////////////////

	@Override
	public Class<? extends Page> getHomePage() {
		return TestApplication.HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		mountPage(TestAccordion01Page.MNT_PATH, TestApplication.TestAccordion01Page.class);
		mountPage(TestCodec01Page.MNT_PATH, TestApplication.TestCodec01Page.class);
		getPageSettings().setPageComponentInfoCodec(
			new DefaultPageComponentInfoCodec(RandomUtil.getSingleton().getString(8, 16)));
	}

	////////////////////////////////////////////////////////////////////////

	public static class HomePage extends SimpleWickletPage {
		private static final long serialVersionUID = 1L;
		public HomePage() {
			add(new Label(WID.content.name(), "Test01"));
		}
	}

	public static class TestAccordion01Page extends WebPage {
		public static final String MNT_PATH = "accordion01";
		private static final long serialVersionUID = 1L;
		private static final IRootMarkup MARKUP = WicketSerializer.serialize(
			new WicketBuilder() {
				public IElement build() {
					return html(
						head(title("Test")),
						body(
							div(
								id("accordion"),
								div(a(href("#"), txt("Panel1"))),
								div(id("content1"), txt("Content1")),
								div(a(href("#"), txt("Panel2"))),
								div(id("content2"), txt("Content2")),
								div(a(href("#"), txt("Panel3"))),
								div(id("content3"), txt("Content3")))));
				}
			}.build());
		public TestAccordion01Page() {
			add(new AccordionBehavior("accordion"));
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class TestCodec01Page extends WebPage {
		public static final String MNT_PATH = "codec01";
		public static final String TEXT = "dsfkjal";
		private static final long serialVersionUID = 1L;
		private static IRootMarkup MARKUP = new WicketBuilder() {
			IRootMarkup build() {
				return serialize(
					html(
						head(),
						body(
							component(id("form")).form( //
								component("test").h3(),
								input(type("text"), name("test2"), value(TEXT)),
								input(type("submit"))))));
			}
		}.build();
		public TestCodec01Page() {
			final Form<Void> form = new Form<Void>("form") {
				private static final long serialVersionUID = 1L;
				@Override
				protected void onSubmit() {
					final String value = getRequest().getRequestParameters().getParameterValue(
						"test2").toString();
					System.out.println("# TestCodec01Page.onSubmit(): " + value);
					RequestCycle.get().scheduleRequestHandlerAfterCurrent(
						new RedirectRequestHandler(TestAccordion01Page.MNT_PATH));
				}
			};
			add(form);
			form.add(new Label("test", "Test"));
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
	}

	////////////////////////////////////////////////////////////////////////
}
