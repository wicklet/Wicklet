/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.test;

import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.tree.LabelTree;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Test;
import sf.blacksun.util.text.DOCTYPE;
import sf.wicklet.wicketext.markup.impl.WicketWriter;
import sf.wicklet.wicketext.test.utils.WicketTestBase;

public class MarkupBuilder01Test extends WicketTestBase {

	////////////////////////////////////////////////////////////////////////

	private static class SimpleTest01Page extends WebPage {
		private static final long serialVersionUID = 1L;

		private static final IMarkupFragment MARKUP = FACTORY.SINGLETON;

		private static class FACTORY {
			static final IMarkupFragment SINGLETON
				= new WicketWriter().raw("<html><body>").emptyComp("div", "label").raw("</body></html")
					.build();
		}

		public SimpleTest01Page() {
			add(new Label("label", Model.of(SimpleTest01Page.class.getName())));
		}

		@Override
		public IMarkupFragment getMarkup() {
			return SimpleTest01Page.MARKUP;
		}
	}

	@Test
	public void simpletest01() {
		final SimpleTest01Page testpage = new SimpleTest01Page();
		final Page page = tester.startPage(testpage);
		Assert.assertEquals(0, page.getFeedbackMessages().size());
		tester.assertRenderedPage(SimpleTest01Page.class);
		tester.assertLabel("label", SimpleTest01Page.class.getName());
	}

	////////////////////////////////////////////////////////////////////////

	private static abstract class SimpleTest02Page extends WebPage {
		private static final boolean DEBUG = true;

		private static final long serialVersionUID = 1L;

		private static final IMarkupFragment MARKUP = new WicketWriter() {
			public IMarkupFragment create() {
				//	<html>
				//	<head>
				//	<title>Herbs</title>
				//	<meta http-equiv="content-type" content="text/xhtml; charset=UTF-8" />
				//	<link rel="stylesheet" href="css/herbs.css" type="text/css" />
				//	</head>
				//	<body>
				raw(
					xhtml.startAll("html", "head", "title").txt("Herbs").end()
						//
						.empty(
							"meta",
							"http-equiv",
							"content-type",
							"content",
							"text/html; charset=UTF-8") //
						.empty(
							"link",
							"rel",
							"stylesheet",
							"type",
							"text/cass",
							"href",
							"css/herbs.css").end().start("body"));
				startComp("form", "form");
				emptyComp("input", "input", "type", "text");
				emptyComp("input", "submit", "type", "submit");
				end();
				raw(xhtml.endAll());
				final IMarkupFragment ret = build();
				if (SimpleTest02Page.DEBUG) {
					System.out.println(ret);
				}
				return ret;
			}
		}.create();

		protected Form<Void> form;

		@SuppressWarnings("serial")
		public SimpleTest02Page() {
			form = new Form<Void>("form");
			form.add(new TextField<String>("input", Model.of("")));
			form.add(
				new Button("submit", Model.of("Submit")) {
					@Override
					public void onSubmit() {
						SimpleTest02Page.this.onSubmit();
					}
				});
			add(form);
		}

		protected abstract void onSubmit();

		@Override
		public IMarkupFragment getMarkup() {
			return SimpleTest02Page.MARKUP;
		}
	}

	@Test
	public void simpletest02() {
		final int[] count = { 0 };
		@SuppressWarnings("serial")
		final SimpleTest02Page testpage = new SimpleTest02Page() {
			@Override
			protected void onSubmit() {
				++count[0];
				final IRequestParameters params = getRequest().getPostParameters();
				Assert.assertEquals(
					SimpleTest02Page.class.getName(), params.getParameterValue("input").toString());
				@SuppressWarnings("unchecked")
				final FormComponent<String> c = (FormComponent<String>)form.get("input");
				Assert.assertEquals(SimpleTest02Page.class.getName(), c.getValue());
			}
		};
		final Page page = tester.startPage(testpage);
		tester.assertRenderedPage(SimpleTest02Page.class);
		final FormTester formtester = tester.newFormTester("form", false);
		formtester.setValue("input", SimpleTest02Page.class.getName());
		formtester.submit("submit");
		Assert.assertEquals(0, page.getFeedbackMessages().size());
		Assert.assertEquals(1, count[0]);
	}

	////////////////////////////////////////////////////////////////////////

	private static class Finalize01Page extends WebPage {
		static final boolean DEBUG = true;

		private static final long serialVersionUID = 1L;

		private static final IMarkupFragment MARKUP = new WicketWriter("", "") {
			public IMarkupFragment create() {
				raw("<html><body>").emptyComp("div", "label").raw("</body></html>");
				return build();
			}

			@Override
			protected void finalize() throws Throwable {
				if (Finalize01Page.DEBUG) {
					System.out.println("finalize");
				}
				++Finalize01Page.count;
				super.finalize();
			}
		}.create();

		static int count = 0;

		public Finalize01Page() {
			add(new Label("label", Model.of(Finalize01Page.class.getName())));
		}

		@Override
		public IMarkupFragment getMarkup() {
			return Finalize01Page.MARKUP;
		}
	}

	/* Check if MarkupBuilder get GC. */
	@Test
	public void testFinalize01() {
		final int M = 1024 * 1024;
		final Finalize01Page testpage = new Finalize01Page();
		final Page page = tester.startPage(testpage);
		Assert.assertEquals(0, page.getFeedbackMessages().size());
		tester.assertRenderedPage(Finalize01Page.class);
		tester.assertLabel("label", Finalize01Page.class.getName());
		final List<byte[]> garbage = new ArrayList<byte[]>();
		long size = Runtime.getRuntime().freeMemory();
		while (size > -50 * M) {
			garbage.add(new byte[10 * M]);
			size -= 10 * M;
		}
		System.gc();
		try {
			Thread.sleep(10);
		} catch (final InterruptedException e) {
		}
		Assert.assertEquals(1, Finalize01Page.count);
	}

	////////////////////////////////////////////////////////////////////////

	private static class Finalize02Page extends WebPage {
		static final boolean DEBUG = true;

		private static final long serialVersionUID = 1L;

		private static final IMarkupFragment MARKUP = FACTORY.SINGLETON;

		private static class FACTORY {
			static IMarkupFragment SINGLETON = new WicketWriter("", "") {
				public IMarkupFragment create() {
					raw("<html><body>").emptyComp("div", "label").raw("</body></html>");
					return build();
				}

				@Override
				protected void finalize() throws Throwable {
					if (Finalize02Page.DEBUG) {
						System.out.println("finalize");
					}
					++Finalize02Page.count;
					super.finalize();
				}
			}.create();
		}

		static int count = 0;

		public Finalize02Page() {
			add(new Label("label", Model.of(Finalize02Page.class.getName())));
		}

		@Override
		public IMarkupFragment getMarkup() {
			return Finalize02Page.MARKUP;
		}
	}

	/* Check if MarkupBuilder get GC. */
	@Test
	public void testFinalize02() {
		final int M = 1024 * 1024;
		final Finalize02Page testpage = new Finalize02Page();
		final Page page = tester.startPage(testpage);
		Assert.assertEquals(0, page.getFeedbackMessages().size());
		tester.assertRenderedPage(Finalize02Page.class);
		tester.assertLabel("label", Finalize02Page.class.getName());
		final List<byte[]> garbage = new ArrayList<byte[]>();
		long size = Runtime.getRuntime().freeMemory();
		while (size > -50 * M) {
			garbage.add(new byte[10 * M]);
			size -= 10 * M;
		}
		System.gc();
		try {
			Thread.sleep(10);
		} catch (final InterruptedException e) {
		}
		Assert.assertEquals(1, Finalize02Page.count);
	}

	////////////////////////////////////////////////////////////////////////

	private static class TestHead01Page extends WebPage {
		static final boolean DEBUG = true;

		private static final long serialVersionUID = 1L;

		private static final IMarkupFragment MARKUP = new WicketWriter() {
			IMarkupFragment create() {
				raw(xhtml.xmlHeader().doctype(DOCTYPE.XHTML11).start("html"));
				startAutoComp("head", "_header_");
				raw(xhtml.start("title").txt("Herbs").end());
				end();
				raw(xhtml.start("body"));
				emptyComp("div", "tree");
				raw(xhtml.endAll());
				return super.build();
			}
		}.create();

		public TestHead01Page() {
			// Create a tree component, so that wicket has to add some resources to the head element.
			add(new LabelTree("tree", new DefaultTreeModel(new DefaultMutableTreeNode())));
		}

		@Override
		public IMarkupFragment getMarkup() {
			return TestHead01Page.MARKUP;
		}
	}

	@Test
	public void testHead01() {
		final TestHead01Page testpage = new TestHead01Page();
		tester.startPage(testpage);
		tester.assertRenderedPage(TestHead01Page.class);
		Assert.assertEquals(0, tester.getMessages(FeedbackMessage.ERROR).size());
		final String response = tester.getLastResponseAsString();
		if (TestHead01Page.DEBUG) {
			System.out.println(response);
		}
		Assert.assertTrue(response.indexOf("<head>") > 0);
		Assert.assertTrue(response.indexOf("</head>") > 0);
		Assert.assertTrue(response.indexOf("<link ") > 0);
		Assert.assertTrue(response.indexOf("<script ") > 0);
	}

	////////////////////////////////////////////////////////////////////////
}
