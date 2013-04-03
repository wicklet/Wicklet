/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.script.impl;

import sf.blacksun.util.text.TextUtil;
import sf.wicklet.dsl.script.api.IBlock;
import sf.wicklet.dsl.script.api.IScriptBuilder;
import sf.wicklet.dsl.script.api.IScriptChild;
import sf.wicklet.dsl.script.api.IStmt;

public class ScriptBuilder implements IScriptBuilder {
	private final StringBuilder b = new StringBuilder();
	private String indent = "";
	private String tab = "";
	public ScriptBuilder() {
	}
	public ScriptBuilder(final String tab) {
		this.tab = tab;
	}
	public ScriptBuilder(final String indent, final String tab) {
		this.indent = indent;
		this.tab = tab;
	}
	@Override
	public String script(final IScriptChild...children) {
		for (final IScriptChild child: children) {
			child.renderTo(b, indent, tab);
		}
		return b.toString();
	}
	@Override
	public IBlock blk(final IScriptChild...children) {
		return new Block(null, children);
	}
	@Override
	public IStmt stm(final CharSequence clause) {
		return new Stmt(clause);
	}
	@Override
	public IStmt stm(final CharSequence...clauses) {
		return new Stmt(clauses);
	}
	@Override
	public IStmt function() {
		return new Stmt("function() {");
	}
	@Override
	public IStmt function(final CharSequence arg) {
		return new Stmt("function(" + arg + ") {");
	}
	@Override
	public IStmt function(final CharSequence...args) {
		return new Stmt("function(" + TextUtil.join(", ", args) + ") {");
	}
	@Override
	public IStmt fmt(final String format, final Object...args) {
		return new Stmt(String.format(format, args));
	}
}
