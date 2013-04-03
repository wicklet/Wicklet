/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.api;

import sf.wicklet.dsl.css.api.support.IRulesetChild;
import sf.wicklet.dsl.css.api.support.IStylesheetChild;

public interface IStylesheetBuilder {
	IStylesheet stylesheet(IStylesheetChild...nodes);
	IImport imports(String uri, IMedium...mediums);
	IMedia media(IMedium...mediums);
	IMedia media(IMedium medium, IRuleset...rulesets);
	IFontface fontface(IDeclaration...decls);
	IPage page(String name, IDeclaration...decls);
	IRulesets rulesets(IStylesheetChild...children);
	IDeclarations declarations(IDeclaration...children);
	IRuleset ruleset(IRulesetChild...children);
	IRuleset ruleset(Object sel, IRulesetChild...children);
	IDeclaration rule();
	IDeclaration rule(Object property, Object value);
	ISelector self(String format, Object...args);
	ISelector sel();
	ISelector sel(Object sel);
	ISelector sel(Object...sels);
	ISelector id(Object id);
	ISelector css(Object css);
}
