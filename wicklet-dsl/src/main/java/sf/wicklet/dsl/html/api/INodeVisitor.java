/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.api;

public interface INodeVisitor<T> {

	public abstract void visit(ITop node, T data);
	public abstract void visit(IWicketComponent node, T data);
	public abstract void visit(IWicketElement node, T data);
	public abstract void visit(IElement node, T data);
	public abstract void visit(IText node, T data);
	public abstract void visit(ILine node, T data);
	public abstract void visit(ICData node, T data);
	public abstract void visit(IComment node, T data);
	public abstract void visit(IPI node, T data);
	public abstract void visit(IDeclaration node, T data);
	public abstract void visit(IChild node, T data);
}
