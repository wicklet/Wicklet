/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.impl;

import java.io.PrintStream;
import java.util.Collection;
import sf.blacksun.util.io.StringPrintStream;
import sf.wicklet.dsl.css.api.ICSSVisitor;
import sf.wicklet.dsl.css.api.IDeclaration;
import sf.wicklet.dsl.css.api.IFontface;
import sf.wicklet.dsl.css.api.IImport;
import sf.wicklet.dsl.css.api.IMedia;
import sf.wicklet.dsl.css.api.INamespace;
import sf.wicklet.dsl.css.api.IPage;
import sf.wicklet.dsl.css.api.IRuleset;
import sf.wicklet.dsl.css.api.ISelector;
import sf.wicklet.dsl.css.api.IStylesheet;
import sf.wicklet.dsl.css.api.support.IStylesheetChild;

public class StylesheetSerializer {

	public static String serialize(final IStylesheet s) {
		final StringPrintStream ret = new StringPrintStream();
		serialize(ret, s);
		return ret.toString();
	}

	public static void serialize(final PrintStream out, final IStylesheet s) {
		s.accept(new SerialzeVisitor(), out);
	}

	static class SerialzeVisitor implements ICSSVisitor<PrintStream> {

		private final StringBuilder indent = new StringBuilder();
		private final String tab = "\t";

		@Override
		public void visit(final IStylesheet node, final PrintStream data) {
			data.println("/* Generated content */");
			for (final IStylesheetChild child: node) {
				child.accept(this, data);
		}}

		@Override
		public void visit(final INamespace node, final PrintStream data) {
			data.print(indent);
			data.print("@namespace ");
			data.print(node.getPrefix());
			data.print(' ');
			data.print(quote(node.getURI()));
			data.println(';');
		}

		@Override
		public void visit(final IImport node, final PrintStream data) {
			data.print(indent);
			data.print("@import ");
			final String uri = node.getURI();
			if (uri.startsWith("url(")) {
				data.print(uri);
			} else {
				data.print(quote(uri));
			}
			print(data, " ", node.getMediums());
			data.println(';');
		}

		@Override
		public void visit(final IMedia node, final PrintStream data) {
			data.print(indent);
			data.print("@media");
			print(data, " ", node.getMediums());
			final Collection<IRuleset> rulesets = node.getRulesets();
			if (rulesets.size() > 0) {
				data.println(" {");
				indent();
				for (final IRuleset ruleset: rulesets) {
					ruleset.accept(this, data);
				}
				outdent();
				data.print(indent);
				data.println("}");
		}}

		@Override
		public void visit(final IFontface node, final PrintStream data) {
			data.print(indent);
			data.println("@font-face {");
			indent();
			for (final IDeclaration decl: node.getDeclarations()) {
				decl.accept(this, data);
			}
			outdent();
			data.print("indent");
			data.println("}");
		}

		@Override
		public void visit(final IPage node, final PrintStream data) {
			data.print(indent);
			data.print("@page ");
			data.print(node.getName());
			data.println(" {");
			indent();
			for (final IDeclaration decl: node.getDeclarations()) {
				decl.accept(this, data);
			}
			outdent();
			data.print(indent);
			data.println("}");
		}

		@Override
		public void visit(final IRuleset node, final PrintStream data) {
			final Collection<ISelector> sels = node.getSelectors();
			final Collection<IDeclaration> decls = node.getDeclarations();
			if (!sels.isEmpty() || !decls.isEmpty()) {
				data.print(indent);
				print(data, "", sels);
				data.println(" {");
				indent();
				for (final IDeclaration decl: decls) {
					decl.accept(this, data);
				}
				outdent();
				data.print(indent);
				data.println("}");
		}}

		@Override
		public void visit(final ISelector node, final PrintStream data) {
			throw new AssertionError("Should not reach here");
		}

		@Override
		public void visit(final IDeclaration node, final PrintStream data) {
			data.print(indent);
			data.print(node.getProperty());
			data.print(": ");
			data.print(node.getExpr());
			data.println(";");
		}

		private String quote(final String s) {
			final StringBuilder ret = new StringBuilder();
			ret.append('"');
			for (int i = 0, len = s.length(); i < len; ++i) {
				final char c = s.charAt(i);
				if (c == '"') {
					ret.append("\\\"");
					continue;
				}
				ret.append(c);
			}
			ret.append('"');
			return ret.toString();
		}

		private void print(final PrintStream data, final String initial, final Collection<? extends Object> a) {
			boolean first = true;
			for (final Object m: a) {
				data.print(first ? initial : ", ");
				data.print(m.toString());
				first = false;
		}}

		public void indent() {
			indent.append(tab);
		}
		public void outdent() {
			indent.setLength(indent.length() - tab.length());
		}
	}
}
