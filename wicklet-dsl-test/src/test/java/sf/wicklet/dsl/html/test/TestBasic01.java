/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.test;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import sf.blacksun.util.io.StringPrintWriter;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.impl.WID;
import sf.wicklet.dsl.html.impl.WicketBuilder;
import sf.wicklet.dsl.html.impl.XHtmlSerializer;

public class TestBasic01 {

	static final boolean DEBUG = true;

	@Test
	public void testBasic01() {
		final IElement e = new WicketBuilder() {
			public IElement create() {
				return html(
					head(
						title("Herbs"),
						meta(
							atts("http-equiv", "content-type").a(
								"content", "text/html; charset=UTF-8")),
						link(
							atts("rel", "stylesheet"),
							atts("type", "text/css"),
							atts().href("../css/herbs.css"))),
					body(
						component(WID.of("top-panel")).div(atts().css("header")),
						component(WID.of("top-menu")).div(atts().css("top-menu")),
						table(
							colgroup(col(atts().width("300px")), col(atts().width("*"))),
							tr(
								td(atts().css("sidePanel").a("min-height", "50%")),
								td(
									component(WID.of("content")).span(
										atts().css("contentPanel"))))),
						div(atts().css("footer"))));
			}
		}.create();
		final StringPrintWriter w = new StringPrintWriter();
		e.accept(new XHtmlSerializer<StringPrintWriter>("  "), w);
		final String ret = w.toString();
		if (TestBasic01.DEBUG) {
			System.out.println(ret);
		}
		Assert.assertTrue(ret.contains("wicket:id=\"top-panel\""));
		Assert.assertTrue(ret.contains("wicket:id=\"top-menu\""));
		// Verify compact format.
		Assert.assertTrue(ret.contains("wicket:id=\"content\" class=\"contentPanel\" />"));
		Assert.assertTrue(ret.contains("<head"));
		Assert.assertTrue(ret.contains("</head"));
		Assert.assertTrue(ret.contains("<link "));
		Assert.assertTrue(ret.contains("footer\" />"));
		Assert.assertTrue(ret.contains("</html>"));
	}
	@Test
	public void testTop01() {
		final IElement e = new WicketBuilder() {
			public IElement create() {
				return top(
					pi("xml", "encoding=\"utf-8\""),
					declaration(
						"DOCTYPE", "html", "PUBLIC", "\"-//W3C//DTD XHTML 1.0 Strict//EN\""),
					html(head1(), body1()));
			}
			private IElement head1() {
				return head(
					title("Herbs"),
					script(
						atts().type("text/javascript").id("wicket-ajax-debug-enable"),
						raw(
							"/*<![CDATA[*/", //
							"wicketAjaxDebugEnable=true;",
							"/*]]>*/")),
					script(
						atts().type("text/javascript").src(
							"../wicket/resource/org.apache.wicket.markup.html.tree.AbstractTree/res/tree-ver-1327223944000.js")));
			}
			private IElement body1() {
				return body(topPanel(), topMenu(), content());
			}
			private IElement topPanel() {
				return component(WID.of("top-panel")).div(
					atts().css("header"), img(atts().src("../images/herbs.png")));
			}
			private IElement topMenu() {
				return component(WID.of("top-menu")).div(
					atts().css("top-menu"),
					div().a("height", "24px").c(
						txt(" | "),
						link1("Home"),
						txt(" | "),
						link1("Manage"),
						txt(" | "),
						link1("About"),
						txt(" | "),
						link1("Login"),
						link1("Logout")));
			}
			private IElement link1(final String name) {
				final String lc = name.toLowerCase();
				return component(WID.of("href-" + lc)).a(atts().href(lc), txt(name));
			}
			private IElement content() {
				return div();
			}
		}.create();
		final StringPrintWriter w = new StringPrintWriter();
		e.accept(new XHtmlSerializer<StringPrintWriter>("  "), w);
		final String ret = w.toString();
		if (TestBasic01.DEBUG) {
			System.out.println(ret);
		}
		Assert.assertTrue(ret.contains("<?xml "));
		Assert.assertTrue(ret.contains("?>"));
		Assert.assertTrue(ret.contains("<!DOCTYPE html"));
		Assert.assertTrue(ret.contains("wicket:id=\"href-home\""));
		Assert.assertTrue(ret.contains("wicket:id=\"href-login\""));
		Assert.assertTrue(ret.contains("/*<![CDATA[*/"));
	}

	@Test
	public void testSchema01() {
		final IElement e = new WicketBuilder() {
			public IElement create() {
				return top(
					xml(),
					elm(
						"beans",
						lb(
							attr("xmlns:http://www.springframework.org/schema/beans"),
							attr("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance"),
							attr("xmlns:aop", "http://www.springframework.org/schema/aop"),
							attr("xmlns:tx", "http://www.springframework.org/schema/tx"),
							attr("xmlns:jee", "http://www.springframework.org/schema/jee"),
							attr(
								"xmlns:context",
								"http://www.springframework.org/schema/context"),
							attr("xmlns:oxm", "http://www.springframework.org/schema/oxm"),
							attr(
								"xmlns:security",
								"http://www.springframework.org/schema/security"),
							attr("xmlns:mvc", "http://www.springframework.org/schema/mvc"),
							attr(
								"xsi:schemaLocation",
								"http://www.springframework.org/schema/beans",
								"http://www.springframework.org/schema/beans/spring-beans-3.0.xsd",
								"http://www.springframework.org/schema/aop",
								"http://www.springframework.org/schema/aop/spring-aop-3.0.xsd",
								"http://www.springframework.org/schema/tx",
								"http://www.springframework.org/schema/tx/spring-tx-3.0.xsd",
								"http://www.springframework.org/schema/jee",
								"http://www.springframework.org/schema/jee/spring-jee-3.0.xsd",
								"http://www.springframework.org/schema/context",
								"http://www.springframework.org/schema/context/spring-context-3.0.xsd",
								"http://www.springframework.org/schema/oxm",
								"http://www.springframework.org/schema/oxm/spring-oxm-3.0.xsd",
								"http://www.springframework.org/schema/security",
								"http://www.springframework.org/schema/security/spring-security-3.0.xsd",
								"http://www.springframework.org/schema/mvc",
								"http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd"))));
			}
		}.create();
		final StringPrintWriter w = new StringPrintWriter();
		e.accept(new XHtmlSerializer<StringPrintWriter>("    "), w);
		final String ret = w.toString();
		if (TestBasic01.DEBUG) {
			System.out.println(ret);
		}
		Assert.assertTrue(ret.contains("<?xml version=\"1.0\" encoding=\"UTF-8\""));
		// Verify non-compact format
		Assert.assertTrue(ret.matches("(?msi).*^\\s+xmlns:context=\"http.*"));
		Assert.assertTrue(ret.contains("xsd\" />"));
	}

	@Test
	public void testSchema02() throws IOException {
		final String expected = "TestBasic01TestSchema02.html";
		final IElement e = new WicketBuilder() {
			public IElement create() {
				return top(
					xml(),
					elm(
						"beans",
						atts(true).xmlns("http://www.springframework.org/schema/beans") 
							//
							.xmlns("xsi", "http://www.w3.org/2001/XMLSchema-instance") 
							//
							.xmlns("aop", "http://www.springframework.org/schema/aop") 
							//
							.xmlns("tx", "http://www.springframework.org/schema/tx") 
							//
							.xmlns("jee", "http://www.springframework.org/schema/jee") 
							//
							.xmlns(
								"context",
								"http://www.springframework.org/schema/context")
							//
							.xmlns("oxm", "http://www.springframework.org/schema/oxm") 
							//
							.xmlns(
								"security",
								"http://www.springframework.org/schema/security")
							//
							.xmlns("mvc", "http://www.springframework.org/schema/mvc") 
							//
							.a(
								"xsi:schemaLocation",
								"http://www.springframework.org/schema/beans",
								"http://www.springframework.org/schema/beans/spring-beans-3.0.xsd",
								"http://www.springframework.org/schema/aop",
								"http://www.springframework.org/schema/aop/spring-aop-3.0.xsd",
								"http://www.springframework.org/schema/tx",
								"http://www.springframework.org/schema/tx/spring-tx-3.0.xsd",
								"http://www.springframework.org/schema/jee",
								"http://www.springframework.org/schema/jee/spring-jee-3.0.xsd",
								"http://www.springframework.org/schema/context",
								"http://www.springframework.org/schema/context/spring-context-3.0.xsd",
								"http://www.springframework.org/schema/oxm",
								"http://www.springframework.org/schema/oxm/spring-oxm-3.0.xsd",
								"http://www.springframework.org/schema/security",
								"http://www.springframework.org/schema/security/spring-security-3.0.xsd",
								"http://www.springframework.org/schema/mvc",
								"http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd")));
			}
		}.create();
		DslTestUtil.verify(TestBasic01.DEBUG, "    ", expected, e);
	}
}
