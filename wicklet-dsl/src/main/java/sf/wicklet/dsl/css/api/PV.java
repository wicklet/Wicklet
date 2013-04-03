/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.api;

public class PV implements IDeclaration {

	private static final long serialVersionUID = 1L;
	private final String property;
	private final String expr;

	protected PV(final String name, final String expr) {
		property = name;
		this.expr = expr;
	}

	@Override
	public String getProperty() {
		return property;
	}

	@Override
	public String getExpr() {
		return expr;
	}

	@Override
	public void addTo(final IRuleset ruleset) {
		ruleset.add(this);
	}

	@Override
	public <T> void accept(final ICSSVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}

	@Override
	public String toString() {
		return getExpr();
	}

	//////////////////////////////////////////////////////////////////////

	public static interface BorderStyle {
		String NAME = "border-style";
		IDeclaration None = new PV(NAME, "none");
		IDeclaration Hidden = new PV(NAME, "hidden");
		IDeclaration Dotted = new PV(NAME, "dotted");
		IDeclaration Dashed = new PV(NAME, "dashed");
		IDeclaration Solid = new PV(NAME, "solid");
		IDeclaration Double = new PV(NAME, "double");
		IDeclaration Groove = new PV(NAME, "groove");
		IDeclaration Ridge = new PV(NAME, "ridge");
		IDeclaration Inset = new PV(NAME, "inset");
		IDeclaration Outset = new PV(NAME, "outset");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface BorderWidth {
		String NAME = "border-width";
		IDeclaration Thin = new PV(NAME, "thin");
		IDeclaration Medium = new PV(NAME, "medium");
		IDeclaration Thick = new PV(NAME, "thick");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface Clear {
		String NAME = "clear";
		IDeclaration Left = new PV(NAME, "left");
		IDeclaration Right = new PV(NAME, "right");
		IDeclaration Both = new PV(NAME, "both");
		IDeclaration None = new PV(NAME, "none");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface Display {
		String NAME = "display";
		IDeclaration None = new PV(NAME, "none");
		IDeclaration Inline = new PV(NAME, "inline");
		IDeclaration Block = new PV(NAME, "block");
		IDeclaration InlineBlock = new PV(NAME, "inline-block");
		IDeclaration ListItem = new PV(NAME, "list-item");
		IDeclaration RunIn = new PV(NAME, "run-in");
		IDeclaration Compact = new PV(NAME, "compact");
		IDeclaration Table = new PV(NAME, "table");
		IDeclaration InlineTable = new PV(NAME, "inline-table");
		IDeclaration TableRowGroup = new PV(NAME, "table-row-group");
		IDeclaration TableHeaderGroup = new PV(NAME, "table-header-group");
		IDeclaration TableFooterGroup = new PV(NAME, "table-footer-group");
		IDeclaration TableRow = new PV(NAME, "table-row");
		IDeclaration TableColumnGroup = new PV(NAME, "table-column-group");
		IDeclaration TableColumn = new PV(NAME, "table-column");
		IDeclaration TableCell = new PV(NAME, "table-cell");
		IDeclaration TableCaption = new PV(NAME, "caption");
		IDeclaration Ruby = new PV(NAME, "ruby");
		IDeclaration RubyBase = new PV(NAME, "ruby-base");
		IDeclaration RubyText = new PV(NAME, "ruby-text");
		IDeclaration RubyBaseGroup = new PV(NAME, "ruby-base-group");
		IDeclaration RubyTextGroup = new PV(NAME, "ruby-text-group");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface Float {
		String NAME = "float";
		IDeclaration Left = new PV(NAME, "left");
		IDeclaration Right = new PV(NAME, "right");
		IDeclaration None = new PV(NAME, "none");
		IDeclaration Inherit = new PV(NAME, "inherit");
	}

	//////////////////////////////////////////////////////////////////////

	/** Generic font familes. */
	public static interface FontFamily {
		String NAME = "font-family";
		IDeclaration Serif = new PV(NAME, "serif");
		IDeclaration SansSerif = new PV(NAME, "sans-serif");
		IDeclaration Cursive = new PV(NAME, "cursive");
		IDeclaration Fantasy = new PV(NAME, "fantasy");
		IDeclaration Monospace = new PV(NAME, "monospace");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface FontWeight {
		String NAME = "font-weight";
		IDeclaration Normal = new PV(NAME, "normal");
		IDeclaration Bold = new PV(NAME, "bold");
		IDeclaration Bolder = new PV(NAME, "bolder");
		IDeclaration Lighter = new PV(NAME, "lighter");
		IDeclaration X100 = new PV(NAME, "100");
		IDeclaration X200 = new PV(NAME, "200");
		IDeclaration X300 = new PV(NAME, "300");
		IDeclaration X400 = new PV(NAME, "400");
		IDeclaration X500 = new PV(NAME, "500");
		IDeclaration X600 = new PV(NAME, "600");
		IDeclaration X700 = new PV(NAME, "700");
		IDeclaration X800 = new PV(NAME, "800");
		IDeclaration X900 = new PV(NAME, "900");
		IDeclaration Inherit = new PV(NAME, "inherit");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface ListStyleType {
		String NAME = "list-style-type";
		IDeclaration None = new PV(NAME, "none");
		IDeclaration Asterisks = new PV(NAME, "asterisks");
		IDeclaration Box = new PV(NAME, "box");
		IDeclaration Check = new PV(NAME, "check");
		IDeclaration Circle = new PV(NAME, "circle");
		IDeclaration Diamond = new PV(NAME, "diamond");
		IDeclaration Disc = new PV(NAME, "disc");
		IDeclaration Hyphen = new PV(NAME, "hyphen");
		IDeclaration Square = new PV(NAME, "square");
		IDeclaration Decimal = new PV(NAME, "decimal");
		IDeclaration DecimalLeadingZero = new PV(NAME, "decimal-leading-zero");
		IDeclaration LowerRoman = new PV(NAME, "lower-roman");
		IDeclaration UpperRoman = new PV(NAME, "upper-roman");
		IDeclaration LowerAlpha = new PV(NAME, "lower-alpha");
		IDeclaration UpPerAlpha = new PV(NAME, "up-per-alpha");
		IDeclaration LowerGreek = new PV(NAME, "lower-greek");
		IDeclaration LowerLatin = new PV(NAME, "lower-latin");
		IDeclaration UpperLatin = new PV(NAME, "upper-latin");
		IDeclaration Hebrew = new PV(NAME, "hebrew");
		IDeclaration Armenian = new PV(NAME, "armenian");
		IDeclaration GeorGian = new PV(NAME, "geor-gian");
		IDeclaration CjkIdeographic = new PV(NAME, "cjk-ideographic");
		IDeclaration Hiragana = new PV(NAME, "hiragana");
		IDeclaration Katakana = new PV(NAME, "katakana");
		IDeclaration HiraGanaIroha = new PV(NAME, "hira-gana-iroha");
		IDeclaration KatakanaIroha = new PV(NAME, "katakana-iroha");
		IDeclaration Footnotes = new PV(NAME, "footnotes");
	}

	//////////////////////////////////////////////////////////////////////

	public static class Outline {
		public static interface Style {
			String NAME = "outline-style";
			IDeclaration None = new PV(NAME, "None");
			IDeclaration Dotted = new PV(NAME, "dotted");
			IDeclaration Dashed = new PV(NAME, "dashed");
			IDeclaration Solid = new PV(NAME, "solid");
			IDeclaration Double = new PV(NAME, "double");
			IDeclaration Groove = new PV(NAME, "groove");
			IDeclaration Ridge = new PV(NAME, "ridge");
			IDeclaration Inset = new PV(NAME, "inset");
			IDeclaration Outset = new PV(NAME, "outset");
		}
	}

	//////////////////////////////////////////////////////////////////////

	public static interface Overflow {
		String NAME = "overflow";
		IDeclaration Visible = new PV(NAME, "visible");
		IDeclaration Hidden = new PV(NAME, "hidden");
		IDeclaration Scroll = new PV(NAME, "scroll");
		IDeclaration Auto = new PV(NAME, "auto");
		IDeclaration NoDisplay = new PV(NAME, "no-display");
		IDeclaration NoContent = new PV(NAME, "no-content");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface Position {
		String NAME = "position";
		IDeclaration Static = new PV(NAME, "static");
		IDeclaration Relative = new PV(NAME, "relative");
		IDeclaration Absolute = new PV(NAME, "absolute");
		IDeclaration Fixed = new PV(NAME, "fixed");
		IDeclaration Inherit = new PV(NAME, "inherit");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface TextAlign {
		String NAME = "text-align";
		IDeclaration Start = new PV(NAME, "start");
		IDeclaration End = new PV(NAME, "end");
		IDeclaration Left = new PV(NAME, "left");
		IDeclaration Right = new PV(NAME, "right");
		IDeclaration Center = new PV(NAME, "center");
		IDeclaration Justify = new PV(NAME, "justify");
	}

	public static interface TextDecoration {
		String NAME = "text-decoration";
		IDeclaration None = new PV(NAME, "none");
		IDeclaration Underline = new PV(NAME, "underline");
		IDeclaration Overline = new PV(NAME, "overline");
		IDeclaration LineThrough = new PV(NAME, "line-through");
		IDeclaration Blink = new PV(NAME, "blink");
		IDeclaration Inherit = new PV(NAME, "inherit");
	}

	public static interface TextOverflow {
		String NAME = "text-overflow";
		IDeclaration Clip = new PV(NAME, "clip");
		IDeclaration Ellipsis = new PV(NAME, "ellipsis");
		IDeclaration Inherit = new PV(NAME, "inherit");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface VerticalAlign {
		String NAME = "vertical-align";
		IDeclaration Baseline = new PV(NAME, "baseline");
		IDeclaration Sub = new PV(NAME, "sub");
		IDeclaration Super = new PV(NAME, "super");
		IDeclaration Top = new PV(NAME, "top");
		IDeclaration TextTop = new PV(NAME, "text-top");
		IDeclaration Middle = new PV(NAME, "middle");
		IDeclaration Bottom = new PV(NAME, "bottom");
		IDeclaration TextBottom = new PV(NAME, "text-bottom");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface Visibility {
		String NAME = "visibility";
		IDeclaration Visible = new PV(NAME, "visible");
		IDeclaration Hidden = new PV(NAME, "hidden");
		IDeclaration Collapse = new PV(NAME, "collapse");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface WhiteSpace {
		String NAME = "white-space";
		IDeclaration Normal = new PV(NAME, "normal");
		IDeclaration Pre = new PV(NAME, "pre");
		IDeclaration Nowrap = new PV(NAME, "nowrap");
		IDeclaration Inherit = new PV(NAME, "inherit");
	}

	//////////////////////////////////////////////////////////////////////
}
