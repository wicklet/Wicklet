/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.api;

public interface ICSSVisitor<T> {
	void visit(IStylesheet node, T data);
	void visit(INamespace node, T data);
	void visit(IImport node, T data);
	void visit(IMedia node, T data);
	void visit(IFontface node, T data);
	void visit(IPage node, T data);
	void visit(IRuleset node, T data);
	void visit(ISelector node, T data);
	void visit(IDeclaration node, T data);
}
