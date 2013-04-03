/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import sf.blacksun.util.FileUtil;
import sf.wicklet.dsl.css.api.IDeclaration;
import sf.wicklet.dsl.css.api.IDeclarations;
import sf.wicklet.dsl.css.api.IFontface;
import sf.wicklet.dsl.css.api.IImport;
import sf.wicklet.dsl.css.api.IMedia;
import sf.wicklet.dsl.css.api.IMedium;
import sf.wicklet.dsl.css.api.IPage;
import sf.wicklet.dsl.css.api.IRuleset;
import sf.wicklet.dsl.css.api.IRulesets;
import sf.wicklet.dsl.css.api.ISelector;
import sf.wicklet.dsl.css.api.IStylesheet;
import sf.wicklet.dsl.css.api.IStylesheetBuilder;
import sf.wicklet.dsl.css.api.PV;
import sf.wicklet.dsl.css.api.support.IRulesetChild;
import sf.wicklet.dsl.css.api.support.IStylesheetChild;

public class StylesheetBuilder implements IStylesheetBuilder {

	////////////////////////////////////////////////////////////////////////

	public static void serialize(final File outfile, final IStylesheet stylesheet) {
		PrintStream out = null;
		try {
			out = new PrintStream(outfile);
			StylesheetSerializer.serialize(out, stylesheet);
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			FileUtil.close(out);
	}}

	////////////////////////////////////////////////////////////////////////

	public class Shortcut {
		public IDeclarations borderless() {
			return declarations(PK.Border.s("none"), PK.Margin.s(0), PK.Padding.s(0));
		}
		public IRuleset alignCenter() {
			return ruleset( //
				PV.TextAlign.Center, //
				PV.VerticalAlign.Middle);
		}
		public IRuleset alignLeftTop() {
			return ruleset( //
				PV.TextAlign.Left, //
				PV.VerticalAlign.Top);
		}
		public IRuleset alignLeftMiddle() {
			return ruleset( //
				PV.TextAlign.Left, //
				PV.VerticalAlign.Middle);
		}
		public IRuleset alignRightMiddle() {
			return ruleset( //
				PV.TextAlign.Left, //
				PV.VerticalAlign.Middle);
		}
		public IRuleset borderRadiusTop(final Object expr) {
			return ruleset(
				PK.BorderTopRightRadius.s(expr),
				PK.BorderTopLeftRadius.s(expr),
				PK.WebkitBorderTopRightRadius.s(expr),
				PK.WebkitBorderTopLeftRadius.s(expr));
		}
		public IRuleset borderRadiusBottom(final Object expr) {
			return ruleset(
				PK.BorderBottomRightRadius.s(expr),
				PK.BorderBottomLeftRadius.s(expr),
				PK.WebkitBorderBottomRightRadius.s(expr),
				PK.WebkitBorderBottomLeftRadius.s(expr));
		}
		public IRuleset borderRadius(final Object expr) {
			return ruleset(
				PK.BorderRadius.s(expr), //
				PK.WebkitBorderRadius.s(expr),
				PX.MozOutlineRadius.s(expr));
		}
	}

	////////////////////////////////////////////////////////////////////////

	public Shortcut f = new Shortcut();

	////////////////////////////////////////////////////////////////////////

	@Override
	public IStylesheet stylesheet(final IStylesheetChild...nodes) {
		final IStylesheet ret = new Stylesheet();
		for (final IStylesheetChild n: nodes) {
			ret.add(n);
		}
		return ret;
	}

	@Override
	public IImport imports(final String uri, final IMedium...mediums) {
		return new Import(uri, mediums);
	}

	@Override
	public IMedia media(final IMedium...mediums) {
		return new Media(mediums);
	}

	@Override
	public IMedia media(final IMedium medium, final IRuleset...rulesets) {
		final IMedia ret = new Media(medium);
		ret.add(rulesets);
		return ret;
	}

	@Override
	public IFontface fontface(final IDeclaration...decls) {
		return new Fontface(decls);
	}

	@Override
	public IPage page(final String name, final IDeclaration...decls) {
		return new Page(name, decls);
	}

	@Override
	public IRulesets rulesets(final IStylesheetChild...children) {
		return new Rulesets(children);
	}

	@Override
	public IDeclarations declarations(final IDeclaration...children) {
		return new Declarations(children);
	}

	@Override
	public IRuleset ruleset(final IRulesetChild...children) {
		return new Ruleset(children);
	}

	@Override
	public IRuleset ruleset(final Object sel, final IRulesetChild...children) {
		return new Ruleset(sel, children);
	}

	@Override
	public IDeclaration rule() {
		return new Declaration();
	}

	@Override
	public IDeclaration rule(final Object property, final Object expr) {
		return new Declaration(property, expr);
	}

	@Override
	public ISelector self(final String format, final Object...args) {
		return new Selector(String.format(format, args));
	}

	@Override
	public ISelector sel() {
		return new Selector();
	}

	@Override
	public ISelector sel(final Object sel) {
		return new Selector(sel);
	}

	@Override
	public ISelector sel(final Object...sels) {
		return new Selector(sels);
	}

	@Override
	public ISelector id(final Object id) {
		return new Selector().id(id);
	}

	@Override
	public ISelector css(final Object css) {
		return new Selector().css(css);
	}

	////////////////////////////////////////////////////////////////////////
}
