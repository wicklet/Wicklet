/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.script.impl;

import sf.wicklet.dsl.script.api.IBlock;
import sf.wicklet.dsl.script.api.IScriptChild;
import sf.wicklet.dsl.script.api.IStmt;

public class Stmt implements IStmt {
	private static final long serialVersionUID = 1L;
	private final CharSequence[] stmts;
	public Stmt(final CharSequence...a) {
		stmts = a;
	}
	@Override
	public IBlock blk(final IScriptChild...children) {
		return new Block(this, children);
	}
	@Override
	public void renderTo(final StringBuilder ret, final String indent, final String tab) {
		for (final CharSequence s: stmts) {
			if (s.length() > 0) {
				ret.append(indent);
			}
			ret.append(s);
			ret.append(SEP);
	}}
}
