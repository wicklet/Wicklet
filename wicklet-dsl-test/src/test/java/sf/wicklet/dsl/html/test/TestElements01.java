/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.test;

import java.io.IOException;
import org.junit.Test;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.impl.WicketBuilder;

public class TestElements01 {

	static final boolean DEBUG = true;

	/* Simple test on all elements. */
	@Test
	public void test01() throws IOException {
		final String expected = "TestElements01Test01.html";
		final IElement e = new WicketBuilder() {

			public IElement create() {
				return top(
					xml("UTF-8"),
					declaration(
						"DOCTYPE", "html", "PUBLIC", "\"-//W3C//DTD XHTML 1.0 Strict//EN\""),
					html(
						head(
							title("Title"),
							meta(
								atts("http-equiv", "content-type").a(
									"content", "text/html; charset=UTF-8")),
							link(
								atts("rel", "stylesheet").a("type", "text/css").href(
									"../css/herbs.css")),
							base(atts().href("http://localhost/")),
							style(atts().type("css/text"), txt("a { color: #ff0000; }")),
							script(
								atts().type("text/javascript"),
								raw(
									"",
									"(function() {",
									"    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;",
									"    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';",
									"    (",
									"        document.getElementsByTagName('head')[0]",
									"        || document.getElementsByTagName('body')[0]",
									"    ).appendChild(ga);",
									"})();")),
							pre(
								"/*<![CDATA[*/", //
								"wicketAjaxDebugEnable=true;",
								"/*]]>*/")),
						body(atts("background", "white").css("body")).c(
							div(atts().css("toc")),
							span(atts().css("bold")),
							comment("comment"),
							cdata("cdata"),
							pi("php", "echo"),
							raw("<b>bold</b>"),
							h1(txt("head1")),
							h2(txt("head2")),
							h3(txt("head2")),
							h4(txt("head2")),
							h5(txt("head2")),
							h6(txt("head3")),
							p(txt("paragraph"), br(), hr()),
							blockquote(
								pre(
									"",
									"this is the first line",
									"  this is the second line")),
							a(atts().href("http://localhost/"), txt("click here")),
							img(atts().src("/images/logo.png")),
							noscript(txt("noscript")),
							iframe(applet(atts().href("http://test"), txt("A applet"))),
							object(embed(atts().href("/embed"))),
							// List elements
							ul(
								li(txt("First line", "Second line")),
								li(txt("Line")),
								ol(txt("Test"))),
							// Table elements
							table(
								atts("summary", "This is a test"),
								elm("caption", txt("caption text")),
								colgroup(),
								colgroup(atts("span", "3")),
								colgroup(atts("span", "3")),
								thead(
									tr(
										th(atts("rowspan", "2"), txt("Head1")),
										th(atts("rowspan", "2"), txt("Head2")),
										th(
											atts("rowspan", "2"),
											atts("colspan", "3"),
											txt("Head3"))),
									tr(th(), th(), th())),
								tbody( //
									tr(td(), td(txt("col2")), td()), //
									tr(td(), td(), td())),
								tfoot(tr(td(), td(), td()))),
							// Form elements
							form(atts("action", "/form1").a("method", "POST")).c(
								input(atts().type("text").value("input1")),
								button(atts().name("submit").value("confirm")),
								optgroup(
									option(atts().label("option1")),
									option(
										atts().label("option2").a(
											"selected", "true")),
									option(atts().label("option3"))),
								select(atts().name("select1")).c(
									option(atts().label("label1"), txt("option1")),
									option(txt("option2")),
									option(
										atts().label("label3").a(
											"selected", "true"))),
								textarea(atts().name("textarea1").a("cols", "40"))))));
			}
		}.create();
		DslTestUtil.verify(DEBUG, "    ", expected, e);
	}
}
