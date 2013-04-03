/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import sf.wicklet.dsl.css.api.ICSSVisitor;
import sf.wicklet.dsl.css.api.IFontface;
import sf.wicklet.dsl.css.api.IImport;
import sf.wicklet.dsl.css.api.IMedia;
import sf.wicklet.dsl.css.api.INamespace;
import sf.wicklet.dsl.css.api.IPage;
import sf.wicklet.dsl.css.api.IRuleset;
import sf.wicklet.dsl.css.api.IRulesets;
import sf.wicklet.dsl.css.api.IStylesheet;
import sf.wicklet.dsl.css.api.support.IStylesheetChild;

public class Stylesheet implements IStylesheet {

	////////////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 1L;
	private final Collection<IStylesheetChild> children = new LinkedList<IStylesheetChild>();
	private final Collection<INamespace> namespaces = new LinkedList<INamespace>();
	private final Collection<IImport> imports = new LinkedList<IImport>();
	private final Collection<IMedia> medias = new LinkedList<IMedia>();
	private final Collection<IPage> pages = new LinkedList<IPage>();
	private final Collection<IFontface> fontfaces = new LinkedList<IFontface>();
	private final Collection<IRuleset> rulesets = new LinkedList<IRuleset>();

	////////////////////////////////////////////////////////////////////////

	@Override
	public Collection<IImport> getImports() {
		return imports;
	}

	@Override
	public Collection<INamespace> getNamespaces() {
		return namespaces;
	}

	@Override
	public Collection<IMedia> getMedias() {
		return medias;
	}

	@Override
	public Collection<IFontface> getFontFaces() {
		return fontfaces;
	}

	@Override
	public Collection<IPage> getPages() {
		return pages;
	}

	@Override
	public Collection<IRuleset> getRulesets() {
		return rulesets;
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public IStylesheet add(final IImport...imports) {
		for (final IImport i: imports) {
			add(i);
		}
		return this;
	}

	@Override
	public IStylesheet add(final INamespace...namespaces) {
		for (final INamespace n: namespaces) {
			add(n);
		}
		return this;
	}

	@Override
	public IStylesheet add(final IMedia...medias) {
		for (final IMedia m: medias) {
			add(m);
		}
		return this;
	}

	@Override
	public IStylesheet add(final IFontface...fontfaces) {
		for (final IFontface f: fontfaces) {
			add(f);
		}
		return null;
	}

	@Override
	public IStylesheet add(final IPage...pages) {
		for (final IPage p: pages) {
			add(p);
		}
		return this;
	}

	@Override
	public IStylesheet add(final IRuleset...rulesets) {
		for (final IRuleset r: rulesets) {
			add(r);
		}
		return this;
	}

	@Override
	public IStylesheet add(final IRulesets...children) {
		for (final IStylesheetChild child: children) {
			child.addTo(this);
		}
		return this;
	}

	@Override
	public IStylesheet add(final IStylesheetChild...children) {
		for (final IStylesheetChild child: children) {
			child.addTo(this);
		}
		return this;
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public IStylesheet add(final IImport iport) {
		children.add(iport);
		imports.add(iport);
		return this;
	}

	@Override
	public IStylesheet add(final INamespace ns) {
		children.add(ns);
		namespaces.add(ns);
		return this;
	}

	@Override
	public IStylesheet add(final IMedia m) {
		children.add(m);
		medias.add(m);
		return this;
	}

	@Override
	public IStylesheet add(final IFontface f) {
		children.add(f);
		fontfaces.add(f);
		return this;
	}

	@Override
	public IStylesheet add(final IPage p) {
		children.add(p);
		pages.add(p);
		return this;
	}

	@Override
	public IStylesheet add(final IRuleset r) {
		children.add(r);
		rulesets.add(r);
		return this;
	}

	@Override
	public IStylesheet add(final IRulesets rset) {
		for (final IStylesheetChild child: rset) {
			child.addTo(this);
		}
		return this;
	}

	@Override
	public IStylesheet add(final IStylesheetChild child) {
		child.addTo(this);
		return this;
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public Iterator<IStylesheetChild> iterator() {
		return children.iterator();
	}

	@Override
	public <T> void accept(final ICSSVisitor<T> visitor, final T data) {
		visitor.visit(this, data);
	}

	////////////////////////////////////////////////////////////////////////
}
