/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.api;

import java.util.Collection;
import sf.wicklet.dsl.css.api.support.IStylesheetChild;

public interface IStylesheet extends ICSSNode, Iterable<IStylesheetChild> {
	Collection<INamespace> getNamespaces();
	Collection<IImport> getImports();
	Collection<IMedia> getMedias();
	Collection<IFontface> getFontFaces();
	Collection<IPage> getPages();
	Collection<IRuleset> getRulesets();
	//
	IStylesheet add(IImport...imports);
	IStylesheet add(INamespace...namespaces);
	IStylesheet add(IMedia...medias);
	IStylesheet add(IFontface...fontfaces);
	IStylesheet add(IPage...pages);
	IStylesheet add(IRuleset...rulesets);
	IStylesheet add(IRulesets...rulesets);
	IStylesheet add(IStylesheetChild...children);
	//
	IStylesheet add(IImport iport);
	IStylesheet add(INamespace namespace);
	IStylesheet add(IMedia media);
	IStylesheet add(IFontface fontface);
	IStylesheet add(IPage page);
	IStylesheet add(IRuleset ruleset);
	IStylesheet add(IRulesets rulesets);
	IStylesheet add(IStylesheetChild child);
}
