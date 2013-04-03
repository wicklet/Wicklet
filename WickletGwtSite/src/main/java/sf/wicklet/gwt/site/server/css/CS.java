/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.css;

import java.io.File;
import sf.blacksun.util.FileUtil;
import sf.wicklet.dsl.css.api.IRulesets;
import sf.wicklet.dsl.css.api.ISelector;
import sf.wicklet.dsl.css.api.IStylesProvider;
import sf.wicklet.dsl.css.api.IStylesheet;
import sf.wicklet.dsl.css.api.PV;
import sf.wicklet.dsl.css.impl.Medium;
import sf.wicklet.dsl.css.impl.PK;
import sf.wicklet.dsl.css.impl.StylesheetBuilder;
import sf.wicklet.gwt.site.shared.CV;
import sf.wicklet.gwt.site.shared.ICS;
import sf.wicklet.gwt.site.shared.WID;

public class CS extends StylesheetBuilder {

	private static final File outfile = FileUtil.mkparent(new File("src/main/webapp/assets/default.css"));
	private static final int TOPPANEL_ZINDEX = 100;

	public static void main(final String[] args) {
		serialize(outfile, new CS().build());
		System.out.println("See output at: " + outfile.getAbsolutePath());
	}

	public IStylesheet build() {
		return stylesheet( //
			Defaults.styles(),
			Toppanel.styles(),
			Toolbarpanel.styles(),
			Leftpanel.styles(),
			Rightpanel.styles(),
			Adminpanel.styles(),
			Loginpage.styles(),
			Logoutpage.styles(),
			Wikipage.styles(),
			Overrides.styles());
	}

	public static class Defaults implements IStylesProvider, ICS {
		public static IRulesets styles() {
			return new StylesheetBuilder() {
				IRulesets build() {
					return rulesets(
						ruleset(
							"body", //
							PK.FontFamily.s("verdana, sans-serif"),
							PK.Margin.s(0),
							PK.Padding.s(0)),
						ruleset(
							"div, tr, span", //
							f.borderless()),
						ruleset(
							"h1", //
							PK.Margin.s("10px 0px")),
						ruleset(
							sel("h1").css(title), //
							PK.Margin.s("0px 0px 10px 0px")),
						ruleset(
							"h3", //
							PK.Color.s(CV.HeadFG),
							PK.Margin.s("5px 0px")),
						ruleset(
							"ul", //
							PK.PaddingLeft.s("20px")),
						ruleset(
							"ul.none > li, li.none", //
							PV.ListStyleType.None,
							PK.MarginTop.s("4px"),
							PK.MarginBottom.s("4px")),
						ruleset(
							"pre, code", //
							PK.Color.s(CV.CodeFG)),
						ruleset(
							"a:hover", //
							PK.BorderBottom.s("dotted 1px black"),
							PV.TextDecoration.None),
						ruleset(
							"hr",
							PK.Height.s(0),
							PK.Padding.s(0),
							PK.Margin.s("4px 0px"),
							PK.Border.s("none"),
							PK.BorderBottom.s("1px dotted gray")),
						// threePane
						ruleset(
							self("div .%s", threePane), //
							PK.PaddingTop.px(Toppanel.menuTotal + 7),
							PK.PaddingLeft.px(Leftpanel.totalwidth + Leftpanel.leftGutter),
							PK.MarginTop.s(0),
							PK.MarginLeft.s(0)),
						// twoPane
						ruleset(
							self("div .%s", twoPane), //
							PK.PaddingTop.px(Toppanel.menuTotal + 4),
							PK.MarginTop.s(0)),
						zebraTable(),
						layoutTable(),
						ruleset(
							sel(".gwt-StackPanel .gwt-StackPanelItem"), //
							PK.BorderRadius.s("5px 5px 0 0")));
				}
				public IRulesets zebraTable() {
					return rulesets(
						ruleset(
							css(zebraTable), //
							PK.Border.s("1px solid " + CV.ZebraHeadBorder),
							f.borderRadius("5px")),
						ruleset(
							css(zebraTable).a("tr"), //
							PK.Border.s("none"),
							PK.Padding.s("4px")),
						ruleset(
							css(zebraTable).a("tr.even"), //
							PK.BackgroundColor.s(CV.ZebraEvenBG)),
						ruleset(
							css(zebraTable).a("tr.odd"), //
							PK.BackgroundColor.s(CV.ZebraOddBG)),
						ruleset(
							css(zebraTable).a("tfoot td"),
							PV.TextAlign.Right,
							PK.BorderTop.s("1px dotted " + CV.ZebraHeadBorder)),
						ruleset(
							css(zebraTable).a("tr:first-child"), //
							PK.BackgroundColor.s(CV.ZebraHeadBG),
							PK.Color.s(CV.ZebraHeadFG),
							PV.FontWeight.Bold),
						ruleset(
							self(".%1$s th, .%1$s td", zebraTable),
							PV.TextAlign.Left,
							PK.BorderLeft.s("1px solid " + CV.ZebraHeadBorder),
							PK.Padding.s("4px 10px")),
						ruleset(
							self(
								".%1$s tr>th:first-child, .%1$s tr>td:first-child",
								zebraTable),
							PK.BorderLeft.s("none")),
						ruleset(
							css(zebraTable).a("tbody tr>td:first-child"), //
							PV.FontWeight.Bold),
						ruleset(
							css(zebraTable).a("tr.headers"),
							PK.BorderBottom.s("1px solid " + CV.ZebraHeadBorder),
							PK.BackgroundColor.s(CV.ZebraHeadBG),
							PK.Color.s(CV.ZebraHeadFG),
							PK.Height.s("32px")));
				}
				public IRulesets layoutTable() {
					return rulesets( //
						ruleset(
							sel( //
								css(layoutTable),
								css(layoutTable).a("tr"),
								css(layoutTable).a("th"),
								css(layoutTable).a("td")),
							f.borderless()));
				}
			}.build();
		}
	}

	public static class Overrides implements IStylesProvider, ICS {
		public static IRulesets styles() {
			return new StylesheetBuilder() {
				IRulesets build() {
					return rulesets(
						ruleset(
							".ui-dialog",
							PK.Background.s(CV.UiDialogBG),
							PK.FontSize.s("medium")),
						ruleset(
							".ui-dialog-buttonpane",
							PK.Background.s(CV.UiDialogButtonPaneBG)),
						ruleset("#editEditor_tbl", PK.Height.s("100%")),
						ruleset("span.feedbackPanelFATAL", PK.Color.s(CV.ErrorFG)),
						ruleset("span.feedbackPanelERROR", PK.Color.s(CV.ErrorFG)),
						ruleset("span.feedbackPanelWARNING", PK.Color.s(CV.WarningFG)),
						ruleset(".gwt-PopupPanelGlass", PK.ZIndex.s(TOPPANEL_ZINDEX + 1)));
				}
			}.build();
		}
	}

	public static class Toppanel implements IStylesProvider, ICS {
		public static final int menuHeight = 28;
		public static final int menuPadding = 2;
		public static final int menuPaddingH = 5;
		public static final int menuTotal = menuHeight + menuPadding * 2;
		public static IRulesets styles() {
			return new StylesheetBuilder() {
				IRulesets build() {
					return rulesets(
						ruleset(
							id(WID.topPanel), //
							PK.Position.s(PV.Position.Static),
							PK.BackgroundColor.s(CV.TopBG),
							PK.Width.s("100%")),
						media(
							Medium.screen,
							ruleset(
								id(WID.topPanel), //
								PK.Position.s(PV.Position.Fixed),
								PK.Left.px(0),
								PK.Top.px(0),
								PK.ZIndex.s(TOPPANEL_ZINDEX))),
						// Menu
						ruleset(
							id(WID.topPanel).a(css(topMenu)), //
							PK.BackgroundColor.s(CV.TopBG),
							PK.Color.s(CV.TopFG),
							PK.Height.px(menuTotal),
							PK.Padding.px(menuPadding, menuPaddingH),
							PV.VerticalAlign.Middle),
						ruleset(
							id(WID.topPanel).a(css(topMenu), "*"), //
							PV.VerticalAlign.Middle),
						ruleset(
							id(WID.topPanel).a(css(topMenu)).child("div"), //
							PK.LineHeight.px(menuHeight),
							PV.Display.InlineBlock),
						ruleset(
							sel(
								id(WID.topPanel).a(css(topMenu)).child("form"),
								id(WID.topPanel).a(css(topMenu)).child("form", "div")), //
							PK.LineHeight.px(menuHeight - 4),
							PV.Display.InlineBlock),
						ruleset(
							id(WID.topPanel).a(css(topMenu), "em"), //
							PK.Color.s(CV.TopEm)),
						ruleset(
							id(WID.topPanel).a(css(topMenu), "a"), //
							PK.Color.s(CV.TopA),
							PK.BorderBottom.s("1px dotted #fff"),
							PV.TextDecoration.None),
						ruleset(
							id(WID.topPanel).a(css(topMenu), "a:hover"), //
							PK.Color.s("#ffff00")),
						ruleset(
							id(WID.topPanel).a(css(searchPanel)), //
							PV.Float.Right),
						ruleset(
							id(WID.topPanel).a(css(loginPanel)), //
							PV.Float.Right),
						ruleset(
							id(WID.topPanel).a(css(searchForm)), //
							PV.Display.Inline,
							PV.Float.Right,
							PK.MarginRight.s("5px")),
						ruleset(
							id(WID.topPanel).a(css(searchForm)).child("input:focus"), //
							PK.Color.s(CV.SearchInputFocus)),
						ruleset(
							id(WID.topPanel).a(css(searchText)), //
							PV.Display.Inline,
							PK.Margin.s("0 0 0 4px"),
							PK.Padding.s("3px 4px"),
							PK.Width.s("160px"),
							PK.Color.s(CV.SearchInput),
							PK.Border.s("1px solid #666"),
							f.borderRadius("3px")),
						ruleset(
							id(WID.topPanel).a(css(searchButton)), //
							PV.BorderStyle.None,
							PK.Width.s("28px"),
							PK.Height.s("24px"),
							PK.BackgroundColor.s(CV.SearchButtonBG),
							PK.Background.s(
								"url(\"search-icon.png\") no-repeat scroll 6px -96px")),
						ruleset(
							id(WID.topPanel).a(css(searchButton)).psc("hover"), //
							PK.Opacity.s(0.75)));
				}
			}.build();
		}
	}

	public static class Toolbarpanel implements IStylesProvider, ICS {
		public static final int toolbarHeight = 32;
		public static final int toolbarPadding = 2;
		public static final int toolbarPaddingH = 5;
		public static final int toolbarTotal = toolbarHeight + toolbarPadding * 2;
		public static IRulesets styles() {
			return new StylesheetBuilder() {
				IRulesets build() {
					final ISelector tb = id(WID.toolbarPanel);
					return rulesets(
						ruleset(
							tb, //
							PK.Position.s(PV.Position.Static),
							PK.BackgroundColor.s(CV.ToolbarBG),
							PK.Color.s(CV.ToolbarFG),
							PK.Height.px(toolbarTotal),
							PK.LineHeight.px(toolbarTotal),
							PK.Width.s("100%"),
							PV.TextAlign.Right,
							PV.VerticalAlign.Middle),
						media(
							Medium.screen,
							ruleset(
								tb, //
								PK.Position.s(PV.Position.Fixed),
								PK.Left.px(0),
								PK.Top.px(CS.Toppanel.menuTotal),
								PK.ZIndex.s(TOPPANEL_ZINDEX - 2))),
						ruleset(
							sel(tb).child("div"),
							PK.Height.px(toolbarHeight),
							PK.LineHeight.px(toolbarHeight),
							PV.VerticalAlign.Middle,
							PK.Padding.px(toolbarPadding, toolbarPaddingH),
							PK.PaddingLeft.px(Leftpanel.totalwidth + toolbarPaddingH),
							PK.PaddingRight.px(8)));
				}
			}.build();
		}
	}

	// Implemented CSS class as a class instead of enum.
	public static class Leftpanel implements IStylesProvider, ICS {
		public static final int width = 160;
		public static final int padding = 10;
		public static final int totalwidth = width + 2 * padding;
		public static final int leftGutter = 0;
		public static IRulesets styles() {
			return new StylesheetBuilder() {
				IRulesets build() {
					return rulesets( //
						leftpanel(),
						leftaccordion(),
						ruleset(
							sel(id(WID.indexPanel), css(ICS.indexPanel)), //
							PK.BackgroundColor.s(CV.IndexBG),
							PK.Padding.s("5px"),
							f.borderRadiusBottom("5px")),
						ruleset(
							sel(id(WID.newsPanel), css(ICS.newsPanel)), //
							PK.BackgroundColor.s(CV.NewsBG),
							PK.Padding.s("5px"),
							f.borderRadiusBottom("5px")),
						ruleset(
							sel(id(WID.wikiPanel), css(ICS.wikiPanel)), //
							PK.BackgroundColor.s(CV.WikiBG),
							PK.Padding.s("5px"),
							f.borderRadiusBottom("5px")),
						ruleset(
							sel(id(WID.forumPanel), css(ICS.forumPanel)), //
							PK.BackgroundColor.s(CV.ForumBG),
							PK.Padding.s("5px"),
							f.borderRadiusBottom("5px")),
						ruleset(
							sel(id(WID.bugsPanel), css(ICS.bugsPanel)), //
							PK.BackgroundColor.s(CV.BugsBG),
							PK.Padding.s("5px"),
							f.borderRadiusBottom("5px")),
						ruleset(
							sel(id(WID.adminPanel), css(ICS.adminPanel)), //
							PK.BackgroundColor.s(CV.AdminBG),
							PK.Padding.s("5px"),
							f.borderRadiusBottom("5px")),
						ruleset(
							sel(id(WID.userPanel), css(ICS.userPanel)), //
							PK.BackgroundColor.s(CV.UserBG),
							PK.Padding.s("5px"),
							f.borderRadiusBottom("5px")));
				}
				private IRulesets leftpanel() {
					return rulesets(
						media(
							Medium.all, //
							ruleset( //
								id(WID.leftPanel),
								PK.Position.s(PV.Position.Static),
								PK.BackgroundColor.s(CV.LeftBG),
								PK.Padding.px(padding),
								PK.Height.s("100%"),
								PK.Width.px(width),
								PV.Overflow.Auto)),
						media(
							Medium.screen,
							ruleset( //
								id(WID.leftPanel),
								PK.Position.s(PV.Position.Fixed),
								PK.Left.s(0),
								PK.Top.s(0),
								PK.Width.px(width),
								PK.PaddingTop.px(CS.Toppanel.menuTotal + 14),
								PK.ZIndex.s(TOPPANEL_ZINDEX - 1))),
						ruleset(
							id(WID.leftPanel).desc("ul"), //
							PK.Margin.s(0),
							PK.Padding.s("0px")),
						ruleset(
							id(WID.leftTopPanel), //
							PK.Height.s("100%"),
							PV.Overflow.Auto),
						ruleset(
							id(WID.leftPanel).desc("li"), //
							PV.ListStyleType.None,
							PK.Margin.s("2px"),
							PK.Padding.s("0")));
				}
				private IRulesets leftaccordion() {
					return rulesets(
						ruleset(
							id(WID.leftAccordion), //
							PK.Padding.s(0)),
						ruleset(
							id(WID.leftAccordion).a(css(accordionHeader)),
							PK.BackgroundColor.s(CV.AccordionHeaderBG),
							PK.Color.s(CV.AccordionHeaderFG),
							PK.MarginTop.s("10px"),
							PK.Padding.s("6px 4px"),
							PV.FontWeight.Bold,
							f.borderRadiusTop("5px")),
						ruleset(
							id(WID.leftAccordion).a(css(accordionHeader)).a("a"),
							PK.Color.s(CV.AccordionHeaderFG),
							PV.TextDecoration.None));
				}
			}.build();
		}
	}

	public static class Rightpanel implements IStylesProvider, ICS {
		public static IRulesets styles() {
			return new StylesheetBuilder() {
				IRulesets build() {
					return rulesets( //
						ruleset(
							id(WID.rightPanel), //
							PK.BackgroundColor.s(CV.RightBG)));
				}
			}.build();
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class Adminpanel implements IStylesProvider, ICS {
		public static IRulesets styles() {
			return new StylesheetBuilder() {
				IRulesets build() {
					return rulesets(
						ruleset(
							id(WID.usersTable).a("th", "a"), //
							PK.Color.s("#fff")),
						ruleset(
							css(adminDialog), //
							PK.FontSize.s("medium")),
						ruleset(
							css(adminDialog).a("table"), //
							PK.TableLayout.s("fixed")),
						ruleset(
							css(adminDialog).a("table", "tr"), //
							PK.Height.s("24px")),
						ruleset(
							css(adminDialog).a("table", "td", "input[type='text']"), //
							PK.Padding.s("2px 4px")));
				}
			}.build();
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class Wikipage implements IStylesProvider, ICS {
		public static IRulesets styles() {
			return new StylesheetBuilder() {
				IRulesets build() {
					return rulesets( //
						ruleset(
							css(wikiEditorHeader),
							PK.BackgroundColor.s(CV.ToolbarBG),
							PK.Padding.s("8px 4px"),
							PK.MarginBottom.s("2px"),
							PK.BorderRadius.s("5px 5px 0px 0px")));
				}
			}.build();
		}
	}

	public static class Loginpage implements IStylesProvider, ICS {
		public static IRulesets styles() {
			return new StylesheetBuilder() {
				IRulesets build() {
					return rulesets(
						ruleset(
							id(WID.bottomPanel),
							PK.BackgroundColor.s(CV.BottomBG),
							PV.TextAlign.Center,
							PV.VerticalAlign.Middle),
						ruleset(
							id(WID.signinPanel),
							PK.BackgroundColor.s(CV.SigninBG),
							PV.Display.InlineBlock,
							PK.Margin.s("20px"),
							PK.Padding.s("20px"),
							f.borderRadius("5px")),
						ruleset(
							id(WID.signinPanel).desc(".feedbackPanelERROR"), //
							PK.Color.s(CV.ErrorFG)));
				}
			}.build();
		}
	}

	public static class Logoutpage implements IStylesProvider, ICS {
		public static IRulesets styles() {
			return new StylesheetBuilder() {
				IRulesets build() {
					return rulesets( //
						ruleset(
							id(WID.bottomPanel).a(css(feedback)),
							PK.BackgroundColor.s(CV.SigninBG),
							PV.Display.InlineBlock,
							PK.Margin.s("20px"),
							PK.Padding.s("20px"),
							f.borderRadius("5px")));
				}
			}.build();
		}
	}
}
