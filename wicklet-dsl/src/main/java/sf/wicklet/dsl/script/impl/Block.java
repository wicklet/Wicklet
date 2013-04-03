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

public class Block implements IBlock {
	private static final long serialVersionUID = 1L;
	private final IStmt stmt;
	private final IScriptChild[] children;
	public Block(final IStmt stmt, final IScriptChild[] children) {
		this.stmt = stmt;
		this.children = children;
	}
	@Override
	public void renderTo(final StringBuilder ret, final String indent, final String tab) {
		if (stmt != null) {
			stmt.renderTo(ret, indent, tab);
		}
		if (children.length > 0) {
			final String dent = indent + tab;
			for (final IScriptChild child: children) {
				child.renderTo(ret, dent, tab);
	}}}
}
