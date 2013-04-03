/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.api;

import java.util.Collection;
import sf.wicklet.dsl.css.api.support.IRulesetChild;
import sf.wicklet.dsl.css.api.support.IStylesheetChild;

public interface IRuleset extends IStylesheetChild, IRulesetChild {
	Collection<ISelector> getSelectors();
	Collection<IDeclaration> getDeclarations();
	//
	IRuleset add(ISelector...sels);
	IRuleset add(IDeclaration...decls);
	/** Add declarations, but not the selectors, of the given rules to this rule. */
	IRuleset add(IRuleset...rules);
	IRuleset add(IDeclarations...decls);
	//
	IRuleset add(ISelector sel);
	IRuleset add(IDeclaration decl);
	/** Add declarations, but not the selectors, of the given rules to this rule. */
	IRuleset add(IRuleset rule);
	IRuleset add(IDeclarations decls);
}
