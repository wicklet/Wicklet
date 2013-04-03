/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.markup.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IMarkupElement;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.IMarkupResourceStream;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.MarkupException;
import sf.blacksun.util.struct.ListIterable;

public class ReadOnlyRootMarkup implements IRootMarkup, Serializable {
	private static final long serialVersionUID = 1L;

	private static IMarkupResourceStream FAKE = new FakeMarkupResourceStream();

	private final List<IMarkupElement> elements;

	private ReadOnlyRootMarkup() {
		this(new ArrayList<IMarkupElement>());
	}

	public ReadOnlyRootMarkup(final List<IMarkupElement> a) {
		elements = a;
	}

	public ReadOnlyRootMarkup(final IMarkupElement...a) {
		elements = new ArrayList<IMarkupElement>();
		for (final IMarkupElement e: a) {
			elements.add(e);
		}
	}

	@Override
	public Iterator<IMarkupElement> iterator() {
		return elements.iterator();
	}

	@Override
	public IMarkupElement get(final int index) {
		return elements.get(index);
	}

	@Override
	public IMarkupResourceStream getMarkupResourceStream() {
		return FAKE;
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public IMarkupFragment find(final String id) {
		if (id != null) {
			final int size = elements.size();
			for (int i = 0; i < size; ++i) {
				final IMarkupElement e = elements.get(i);
				if (e instanceof ComponentTag) {
					final ComponentTag c = (ComponentTag)e;
					final String eid = c.getId();
					if (eid != null && eid.equals(id)) {
						return createFragment(c, i + 1, size);
		}}}}
		return null;
	}

	private IMarkupFragment createFragment(final ComponentTag c, final int start, final int end) {
		final ReadOnlyRootMarkup ret = new ReadOnlyRootMarkup();
		int level = c.isOpenClose() ? 0 : 1;
		ret.add(c);
		for (int k = start; level > 0 && k < end; ++k) {
			final IMarkupElement e = elements.get(k);
			ret.add(e);
			if (e instanceof ComponentTag) {
				final ComponentTag cc = (ComponentTag)e;
				if (cc.isOpen()) {
					++level;
				} else if (cc.isClose()) {
					--level;
				} else if (!cc.isOpenClose()) {
					assert (false);
		}}}
		if (level != 0) {
			throw new MarkupException("Fragment is not well formed: " + ret);
		}
		return ret;
	}

	@Override
	public String toString(final boolean markupOnly) {
		final StringBuilder buf = new StringBuilder(256);
		for (final IMarkupElement e: elements) {
			if (markupOnly && !(e instanceof ComponentTag)) {
				continue;
			}
			buf.append(e.toString());
		}
		return buf.toString();
	}

	@Override
	public String toString() {
		return toString(false);
	}

	@Override
	public void makeImmutable() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void replace(final int index, final IMarkupElement elem) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addMarkupElement(final IMarkupElement markupElement) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addMarkupElement(final int pos, final IMarkupElement markupElement) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<IMarkupElement> iterator(final int start, final int size) {
		return new ListIterable<IMarkupElement>(elements, start, start + size);
	}

	@Override
	public String locationAsString() {
		return null;
	}

	public IMarkupFragment getParentMarkup() {
		return null;
	}

	private void add(final IMarkupElement a) {
		elements.add(a);
	}
}
