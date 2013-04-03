/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.shared;

public enum CV {
	TopBG("#cd5555"), TopFG("#fff"), TopEm("#000"), TopA("#fff"), //
	HeadFG("#4169e1"), CodeFG("#770077"),
	SearchInput("#aaa"), SearchInputFocus("black"), SearchButtonBG("#aaa"), //
	LeftBG("#ffa500"),
	AccordionHeaderBG("#000"), AccordionHeaderFG("#fff"),
	IndexBG("#9acd32"),
	WikiBG("#9acd32"), NewsBG("#aaa"), ForumBG("#c1cdc1"), BugsBG("#ff6f6f"),
	AdminBG("#ff6f6f"), UserBG("#c1cdc1"),
	RightBG("#fff"), ToolbarBG("#ccc"), ToolbarFG("#000"),
	BottomBG("#eee"),
	SigninBG("#9acd32"),
	ZebraHeadBG("#000"), ZebraHeadFG("#fff"), ZebraHeadBorder("#000"),
	ZebraEvenBG("#eee"), ZebraOddBG("#ddd"),
	UiDialogBG("#9acd32"), UiDialogButtonPaneBG("#d0e000"),
	ErrorFG("#c00000"), WarningFG("#808000"),
	//
	;
	private String value;
	private CV(final String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}
}
