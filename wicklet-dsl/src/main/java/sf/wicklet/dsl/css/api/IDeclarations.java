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

/** A collection of IDeclarations that expands to individual IDeclaration when added to a ruleset. */
public interface IDeclarations extends IRulesetChild, Iterable<IDeclaration> {
	Collection<IDeclaration> getDeclarations();
	boolean add(Collection<IDeclaration> decls);
	boolean add(IDeclaration decl);
}
