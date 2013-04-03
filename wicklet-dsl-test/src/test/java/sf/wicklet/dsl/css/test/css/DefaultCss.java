/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.test.css;

import sf.wicklet.dsl.css.api.IRuleset;
import sf.wicklet.dsl.css.api.IStylesheet;
import sf.wicklet.dsl.css.api.PV.BorderStyle;
import sf.wicklet.dsl.css.api.PV.FontWeight;
import sf.wicklet.dsl.css.api.PV.Overflow;
import sf.wicklet.dsl.css.api.PV.TextAlign;
import sf.wicklet.dsl.css.api.PV.TextOverflow;
import sf.wicklet.dsl.css.api.PV.VerticalAlign;
import sf.wicklet.dsl.css.api.PV.WhiteSpace;
import sf.wicklet.dsl.css.impl.Medium;
import sf.wicklet.dsl.css.impl.PX;
import sf.wicklet.dsl.css.impl.PK;
import sf.wicklet.dsl.css.impl.StylesheetBuilder;

public class DefaultCss extends StylesheetBuilder {

	enum CSS {
		top, fixed, box, zebra; 
	}

	private static final String bgTitle = "#9acd32";
	private static final String fgTitle = "#fff";
	private final IRuleset roundcorners = roundcorners("8px");
	private final IRuleset borderless = ruleset(PK.Border.set("none"), PK.Margin.set(0), PK.Padding.set(0));
	private IRuleset tableFixed;

	public IStylesheet build() {
		return stylesheet(
			ruleset(
				sel("div"), //
				rule("margin", "0"),
				rule("padding", "0")),
			ruleset(
				sel("table").css(CSS.top), //
				borderless,
				PK.MarginLeft.set("auto"),
				PK.MarginRight.set("auto"),
				PK.Width.set("90%"),
				PK.TableLayout.set("fixed")),
			tableFixed = ruleset(
				sel("table").css(CSS.fixed), //
				PK.Border.set("none"),
				PK.TableLayout.set("fixed")),
			ruleset(
				sel("table").css(CSS.box), //
				tableFixed,
				borderless),
			ruleset(
				sel("table").css(CSS.zebra).desc("tr:nth-child(even)"),
				PK.BackgroundColor.set("#dde4d9")),
			ruleset("tr"),
			ruleset(
				sel("tr.head"),
				sel("td.head"),
				sel("th.head"),
				sel("th"),
				PK.Height.set("32px"),
				PK.Background.set("#6f900d"),
				PK.Color.set("#fff"),
				PK.Font.set("bold medium sans-serif"),
				PK.Padding.set("4px"),
				PK.PaddingLeft.set("12px"),
				PK.Border.set(0),
				PK.Width.set("100%"),
				PK.TextAlign.set("left"),
				PK.VerticalAlign.set("middle")),
			ruleset(
				sel("td.title", "td.title-tall"),
				PK.Height.set("75px"),
				PK.BackgroundColor.set(bgTitle),
				PK.Color.set(fgTitle),
				PK.Font.set("bold 200% san-serif"),
				PK.TextAlign.set("center"),
				PK.VerticalAlign.set("middle"),
				PK.TextShadow.set("0 0 5px #000"),
				PK.BoxShadow.set("3px 3px 3px #444")),
			ruleset(
				sel("td.navbar-top"),
				PK.TextAlign.set("right"),
				PK.FontWeight.set("bold"),
				PK.Margin.set(0),
				PK.Padding.set("5px 0")),
			ruleset(
				sel("td.navbar-bottom"),
				PK.BackgroundColor.set("#fff"),
				PK.TextAlign.set("right"),
				PK.FontWeight.set("bold"),
				PK.Margin.set(0),
				PK.Padding.set("5px 0")),
			ruleset(sel("td.ruler"), borderless, PK.Height.set("1px"), PK.Background.set("#000")),
			ruleset(
				sel("h3"), PK.FontSize.set("medium"), PK.FontWeight.set("bold"), PK.Margin.set("2px 0")),
			ruleset(
				"h3.shadow", //
				PK.MarginTop.set("4px"),
				PK.MarginBottom.set("4px")),
			ruleset(
				"h3.site",
				PK.Display.set("block"),
				PK.BackgroundColor.set("#c1cdc1"),
				PK.LineHeight.set("24px"),
				PK.VerticalAlign.set("middle"),
				PK.Margin.set("4px 0px"),
				PK.Padding.set("2px 4px")),
			ruleset(
				"ul, ol", //
				PK.Margin.set("4px 0"),
				PK.Padding.set(0)),
			ruleset(
				"ul.indent, ol.indent",
				PK.Margin.set("4px 0"),
				PK.MarginLeft.set("12px"),
				PK.Padding.set(0)),
			ruleset(
				"li",
				PK.ListStyle.set("none"),
				PK.Margin.set("0 0 0 12px"),
				PK.Padding.set(0),
				PK.Font.set("normal medium sans-serif")),
			ruleset(
				"li.head, li.section",
				PK.ListStylePosition.set("outside"),
				PK.Margin.set("1px 4px"),
				PK.Padding.set(0),
				PK.Font.set("bold medium sans-serif"),
				PK.Color.set("#6f8c6f")),
			ruleset(
				"img", //
				borderless,
				PK.VerticalAlign.set("top")),
			ruleset(
				"code", //
				PK.Font.set("bold medium monospace"),
				PK.Color.set("#505050")),
			ruleset("pre", PK.Color.set("#305050")),
			ruleset("b", PK.Color.set("#6f8c6f")),
			ruleset("b.grey", PK.Color.set("#666666")),
			ruleset("hr", PK.Height.set("1px")),
			ruleset("a:link, a:visited, a:active", PK.Color.set("#0000bb")),
			ruleset("a:hover", PK.Color.set("#00b"), PK.BorderBottom.set("1px dotted")),
			ruleset(".invisible", PK.Display.set("none")),
			ruleset(".shadow", PK.TextShadow.set("0.075em 0.075em 2px #333")),
			ruleset("td.content", PK.Padding.set("10px")),
			ruleset("tr.content>td", PK.Padding.set("4px")),
			ruleset(
				"span.top-menu div",
				PK.Display.set("inline-block"),
				PK.BackgroundColor.set("#000"),
				PK.Color.set("#fff")),
			ruleset(
				"td.top-menu, div.top-menu, span.top-menu",
				PK.Height.set("36px"),
				PK.LineHeight.set("32px"),
				PK.BoxShadow.set("3px 3px 3px #444"),
				PK.BackgroundColor.set("#000"),
				PK.Color.set("#ffffff"),
				PK.Width.set("100%")),
			media(
				Medium.screen,
				ruleset("div.top-menu", PK.Position.set("fixed"), PK.Left.set(0), PK.Top.set(0)),
				ruleset("div.top-content", PK.MarginTop.set("40px"))),
			media(Medium.print, ruleset("div.top-menu", PK.Position.set("static"))),
			ruleset(
				"#admin-form",
				PK.Width.set("75%"),
				PK.MarginLeft.set("auto"),
				PK.MarginRight.set("auto"),
				PK.MarginBottom.set("10px")),
			ruleset(
				"table.admin-form",
				PK.TableLayout.set("fixed"),
				PK.Width.set("75%"),
				PK.BorderBottom.set("2px solid black"),
				PK.BorderCollapse.set(true),
				PK.Padding.set(0),
				PK.MarginBottom.set("10px")),
			ruleset(
				"table.admin-form tr>td", //
				PK.Padding.set("4px"),
				PK.BorderBottom.set("1px dotted black")),
			ruleset(
				"table.admin-form tr>th", //
				PK.BorderTop.set("2px solid black"),
				PK.BorderBottom.set("2px solid black")),
			ruleset(
				"a.top-menu", //
				PK.Color.set("#ffffff"),
				PK.FontWeight.set("bold"),
				PK.VerticalAlign.set("middle")),
			ruleset(
				"td.top-menu a, td.top-menu em", //
				PK.Color.set("#ffffff"),
				PK.VerticalAlign.set("middle")),
			ruleset(
				".page-buttons",
				PK.Border.set("0 none"),
				PK.BorderRadius.set("3px"),
				PK.BoxShadow.set("1px 1px 0 rgba(0, 0, 0, 0.25)"),
				PK.Color.set("#333333"),
				PK.Display.set("inline"),
				PK.Font.set("1em italics sans-serif"),
				PK.LetterSpacing.set("0.5px"),
				PK.Padding.set("2px 10px 2px 10px"),
				PK.TextTransform.set("uppercase"),
				PK.TextDecoration.set("none"),
				PK.BackgroundColor.set("#C8E6ED"),
				PK.Cursor.set("pointer"),
				PK.Opacity.set("0.75")),
			ruleset(
				"#search-form",
				PK.MarginRight.set("5px"),
				PK.Display.set("inline"),
				PK.Float.set("right"),
				PK.VerticalAlign.set("middle")),
			ruleset(
				"#search-form>input:focus", //
				PK.Color.set("black")),
			ruleset(
				"#search-text",
				PK.BorderRadius.set("3px"),
				PK.Display.set("inline"),
				PK.Border.set("1px solid #666666"),
				PK.Color.set("#AAAAAA"),
				PK.FontSize.set("0.857em"),
				PK.Margin.set("0 0 0 4px"),
				PK.Padding.set("3px 4px 3px 4px"),
				PK.Width.set("160px"),
				PK.VerticalAlign.set("middle")),
			ruleset(
				"#search-button",
				PK.Border.set("0 none"),
				PK.Background.set(
					"url(\"../img/search-icons.png\") no-repeat scroll 6px -96px rgba(0, 0, 0, 0.9)"),
				PK.Width.set("28px"),
				PK.Height.set("24px"),
				PK.VerticalAlign.set("middle")),
			ruleset(
				"#search-button:hover", //
				PK.Opacity.set("0.75")),
			ruleset(
				"#feedback", //
				PK.Margin.set("10px 4px 10px 4px")),
			ruleset(
				"#search-result", //
				PK.Width.set("80%"),
				PK.Margin.set("10px auto 10px auto")),
			ruleset(
				"#search-result tr.search-result>td",
				PK.BackgroundColor.set("#eef4e9"),
				PK.Color.set("#444"),
				PK.Font.set("bold x-large sans-serif"),
				PK.TextShadow.set("1px 1px 2px #444"),
				PK.Height.set("40px"),
				PK.LineHeight.set("40px"),
				PK.VerticalAlign.set("top")),
			ruleset(
				"#search-result tr.search-result-header>td",
				PK.BorderTop.set("1px dotted #777"),
				PK.BorderBottom.set("1px dotted #777"),
				PK.BorderRadius.set("0 15px 0 0"),
				PK.BackgroundColor.set("#dde4d9"),
				PK.Color.set("#666"),
				PK.FontWeight.set("normal"),
				PK.Height.set("32px"),
				PK.VerticalAlign.set(VerticalAlign.Middle),
				PK.TextAlign.set(TextAlign.Left),
				PK.Padding.set("4px"),
				PK.Overflow.set(Overflow.Hidden),
				PK.WhiteSpace.set(WhiteSpace.Nowrap),
				PK.TextOverflow.set(TextOverflow.Ellipsis)),
			ruleset(
				"#search-result tr.search-result-body>td",
				PK.BackgroundColor.set("#eef4e9"),
				PK.Padding.set("8px 32px")),
			ruleset(
				"#search-result tr.search-result-body em",
				PK.FontStyle.set("normal"),
				PK.BackgroundColor.set("#fff840"),
				PK.Color.set("#444")),
			ruleset(
				"#search-result tr.search-result-body hr",
				PK.Height.set("1px"),
				PK.Width.set("100%"),
				PK.Border.set(BorderStyle.None),
				PK.BorderTop.set("1px dotted #777"),
				PK.Margin.set("4px")),
			ruleset(".warning", PK.Color.set("#804000")),
			ruleset("span.normal", PK.Font.set("normal medium sans-serif")),
			ruleset("div.panels-box", PK.Width.set("100%"), PK.Height.set("100%")),
			ruleset(
				"div.left-panel-box",
				PK.Width.set("100%"),
				PK.Margin.set(0),
				PK.Padding.set("0 0 8px 0")),
			ruleset(
				"table.grid, table.grid>tr, table.grid>tr>td"
					+ ", table.grid>tbody, table.grid>tbody>tr, table.grid>tbody>tr>td"
					+ ", div.grid",
				PK.VerticalAlign.set(VerticalAlign.Top),
				borderless,
				PK.Width.set("100%")),
			ruleset(
				"div.left-panel", //
				PK.VerticalAlign.set(VerticalAlign.Top),
				PK.TextAlign.set(TextAlign.Left)),
			ruleset(
				"div.right-panel",
				PK.VerticalAlign.set(VerticalAlign.Top),
				PK.TextAlign.set(TextAlign.Left),
				PK.Margin.set("0 0 8px 8px"),
				PK.Padding.set("0 0 8px 8px")),
			ruleset("div.main-content", PK.Width.set("100%")),
			ruleset("ul.widgets"),
			ruleset(
				"ul.widgets>li",
				PK.MinHeight.set("32px"),
				PK.Margin.set("8px 8px 8px 0"),
				PK.Padding.set("8px"),
				PK.Background.set("#ccc"),
				PK.BoxShadow.set("3px 3px 3px #444"),
				roundcorners),
			ruleset(".round-corners", roundcorners),
			ruleset(
				"div.box",
				PK.Background.set("#f8f8f8"),
				PK.Padding.set(0),
				PK.Margin.set("10px"),
				PK.BorderRadius.set("5px"),
				PK.BoxShadow.set("3px 3px 3px #777")),
			ruleset("div.padded", PK.Padding.set("10px")),
			ruleset(
				"div.header-box",
				PK.Background.set("#555"),
				borderless,
				PK.Width.set("100%"),
				roundtop("5px")),
			ruleset(
				"div.header",
				PK.Color.set("#fff"),
				PK.FontWeight.set(FontWeight.Bold),
				PK.Padding.set("5px"),
				roundtop("5px")),
			ruleset("div.body", PK.Padding.set("10px"), PK.Width.set("100%"), roundbottom("5px")));
	}
	private IRuleset roundtop(final Object expr) {
		return ruleset(
			PK.BorderTopRightRadius.s(expr),
			PK.BorderTopLeftRadius.s(expr),
			PK.WebkitBorderTopRightRadius.s(expr),
			PK.WebkitBorderTopLeftRadius.s(expr));
	}
	private IRuleset roundbottom(final Object expr) {
		return ruleset(
			PK.BorderBottomRightRadius.s(expr),
			PK.BorderBottomLeftRadius.s(expr),
			PK.WebkitBorderBottomRightRadius.s(expr),
			PK.WebkitBorderBottomLeftRadius.s(expr));
	}
	private IRuleset roundcorners(final Object expr) {
		return ruleset(
			PK.BorderRadius.s(expr), //
			PK.WebkitBorderRadius.s(expr),
			PX.MozOutlineRadius.s(expr));
	}
}
