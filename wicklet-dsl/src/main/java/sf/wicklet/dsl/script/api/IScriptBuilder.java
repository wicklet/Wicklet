/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.script.api;

/** A very simple DSL for writing scripts. */
public interface IScriptBuilder {
	String script(IScriptChild...children);
	IStmt stm(CharSequence stmt);
	IStmt stm(CharSequence...spans);
	/** Typically use with blk(): function(args).blk(...). */
	IStmt function();
	IStmt function(CharSequence arg);
	IStmt function(CharSequence...args);
	IStmt fmt(String format, Object...args);
	IBlock blk(IScriptChild...children);
}
