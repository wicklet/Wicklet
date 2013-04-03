/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.test.request.mapper;

import static org.junit.Assert.assertEquals;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.page.IPageManagerContext;
import org.apache.wicket.request.mapper.info.ComponentInfo;
import org.apache.wicket.request.mapper.info.DefaultPageComponentInfoCodec;
import org.apache.wicket.request.mapper.info.IPageComponentInfoCodec;
import org.apache.wicket.request.mapper.info.PageComponentInfo;
import org.apache.wicket.request.mapper.info.PageInfo;
import org.junit.Test;
import sf.blacksun.util.RandomUtil;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.test.support.WicketTestBase;

public class TestPageComponentInfoCodec01 extends WicketTestBase {

	static final boolean DEBUG = true;

	public static class Test01Page extends WebPage {
		private static final long serialVersionUID = 1L;
		private static IRootMarkup MARKUP = new WicketBuilder() {
			IRootMarkup build() {
				return serialize(
					html(
						head(),
						body(
							form(
								attr("action", "#"),
								component("test").h3(),
								input(type("submit"))))));
			}
		}.build();
		public Test01Page() {
			add(new Label("test", "Test"));
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
	}

	public static class Test02Page extends WebPage {
		private static final long serialVersionUID = 1L;
		private static final String TEXT = "dsfkjal";
		private static IRootMarkup MARKUP = new WicketBuilder() {
			IRootMarkup build() {
				return serialize(
					html(
						head(),
						body(
							component("form").form( //
								component("test").h3(),
								input(type("text"), name("test2"), value(TEXT))))));
			}
		}.build();
		public Test02Page() {
			final Form<Void> form = new Form<Void>("form") {
				private static final long serialVersionUID = 1L;
				@Override
				protected void onSubmit() {
					if (DEBUG) {
						System.out.println(
							"# submit: "
								+ getRequest().getQueryParameters().getParameterValue(
									"test2"));
				}}
			};
			add(form);
			form.add(new Label("test", "Test"));
		}
		@Override
		public IRootMarkup getAssociatedMarkup() {
			return MARKUP;
		}
	}

	@Test
	public void test01() {
		tester.startPage(Test01Page.class);
		final String ret = tester.getLastResponseAsString();
		if (DEBUG) {
			System.out.println(ret);
		}
		assertEquals(
			"<html><body>\n"
				+ "<form action=\"#\"><h3 wicket:id=\"test\">Test</h3><input type=\"submit\">\n"
				+ "</form>\n"
				+ "</body>\n"
				+ "</html>\n",
			ret);
	}

	@Test
	public void test01codec() {
		tester.getApplication().getPageSettings().setPageComponentInfoCodec(
			new DefaultPageComponentInfoCodec(RandomUtil.getSingleton().getString(8, 16)));
		tester.startPage(Test01Page.class);
		final String ret = tester.getLastResponseAsString();
		if (DEBUG) {
			System.out.println(ret);
		}
		assertEquals(
			"<html><body>\n"
				+ "<form action=\"#\"><h3 wicket:id=\"test\">Test</h3><input type=\"submit\">\n"
				+ "</form>\n"
				+ "</body>\n"
				+ "</html>\n",
			ret);
	}

	@Test
	public void test02() {
		tester.startPage(Test02Page.class);
		final String ret = tester.getLastResponseAsString();
		if (DEBUG) {
			System.out.println(ret);
		}
		assertEquals(
			"<html><body><form wicket:id=\"form\" id=\"form1\" method=\"post\" "
				+ "action=\"../page?0-1.IFormSubmitListener-form\">"
				+ "<div style=\"width:0px;height:0px;position:absolute;left:-100px;top:-100px;overflow:hidden\">"
				+ "<input type=\"hidden\" name=\"form1_hf_0\" id=\"form1_hf_0\" />"
				+ "</div><h3 wicket:id=\"test\">Test</h3>"
				+ "<input type=\"text\" name=\"test2\" value=\"dsfkjal\">\n"
				+ "</form></body>\n</html>\n",
			ret);
	}

	@Test
	public void test02codec() {
		tester.getApplication().getPageSettings().setPageComponentInfoCodec(
			new DefaultPageComponentInfoCodec(RandomUtil.getSingleton().getWord(8, 16)));
		tester.startPage(Test02Page.class);
		final String ret = tester.getLastResponseAsString();
		if (DEBUG) {
			System.out.println(ret);
		}
		assertEquals(
			"<html><body><form wicket:id=\"form\" id=\"form1\" method=\"post\" "
				+ "action=\"../page?0-1.IFormSubmitListener-form\">"
				+ "<div style=\"width:0px;height:0px;position:absolute;left:-100px;top:-100px;overflow:hidden\">"
				+ "<input type=\"hidden\" name=\"form1_hf_0\" id=\"form1_hf_0\" />"
				+ "</div><h3 wicket:id=\"test\">Test</h3>"
				+ "<input type=\"text\" name=\"test2\" value=\"dsfkjal\">\n"
				+ "</form></body>\n</html>\n",
			ret);
	}

	@Test
	public void testcodec01() {
		final DefaultPageComponentInfoCodec codec = new DefaultPageComponentInfoCodec("abcd23487");
		final IPageManagerContext context = new IPageManagerContext() {
			private final Map<String, Serializable> map = new TreeMap<String, Serializable>();
			@Override
			public void setSessionAttribute(final String key, final Serializable value) {
				map.put(key, value);
			}
			@Override
			public void setRequestData(final Object data) {
			}
			@Override
			public String getSessionId() {
				return null;
			}
			@Override
			public Serializable getSessionAttribute(final String key) {
				return map.get(key);
			}
			@Override
			public Object getRequestData() {
				return null;
			}
			@Override
			public void bind() {
			}
		};
		test(context, codec, null, new ComponentInfo(123, "testinterace", "a:b:cde", 344));
		test(context, codec, new PageInfo(11), new ComponentInfo(123, "testinterace", "a:b:cde", 344));
		test(context, codec, new PageInfo(11), null);
	}

	private void test(
		final IPageManagerContext context,
		final IPageComponentInfoCodec codec,
		final PageInfo pageinfo,
		final ComponentInfo componentinfo) {
		final String encoded = codec.encode(context, pageinfo, componentinfo);
		final String decoded = codec.decode(context, encoded);
		if (DEBUG) {
			System.out.println("# encoded: " + encoded);
			System.out.println("# decoded: " + decoded);
		}
		assertEquals(PageComponentInfo.toString(pageinfo, componentinfo), decoded);
	}
}
