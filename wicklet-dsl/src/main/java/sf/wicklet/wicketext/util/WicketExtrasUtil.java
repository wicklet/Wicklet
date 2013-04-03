/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IMarkupElement;
import org.apache.wicket.markup.RawMarkup;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import sf.wicklet.wicketext.util.WicketExtrasUtil.MarkupDumper.MarkupStat;

public class WicketExtrasUtil {

	private WicketExtrasUtil() {
	}

	public static MarkupStat dump(
		final PrintStream out, final boolean debug, final Iterable<IMarkupElement> markup) {
		return MarkupDumper.dump(out, debug, markup);
	}

	public static Collection<Component> getComponents(final Page page) {
		final List<Component> data = new ArrayList<Component>();
		if (page == null) {
			return data;
		}
		page.visitChildren(
			new IVisitor<Component, Void>() {
				@Override
				public void component(final Component component, final IVisit<Void> visit) {
					data.add(component);
				}
			});
		return data;
	}

	public static class MarkupDumper {
		public static class MarkupStat {
			public int count = 0;
			public int ctags = 0;
			public int rtags = 0;
			public int others = 0;
		}
		private MarkupDumper() {
		}
		public static MarkupStat dump(
			final PrintStream out, final boolean debug, final Iterable<IMarkupElement> markup) {
			final MarkupStat stat = new MarkupStat();
			for (final IMarkupElement e: markup) {
				final Class<?> c = e.getClass();
				if (e instanceof ComponentTag) {
					++stat.ctags;
				} else if (e instanceof RawMarkup) {
					++stat.rtags;
				} else {
					++stat.others;
				}
				if (debug) {
					System.out.println("# " + (++stat.count) + ": " + c.getName());
					System.out.println(e.toString());
			}}
			if (debug) {
				System.out.println("# ctags: " + stat.ctags);
				System.out.println("# rtags: " + stat.rtags);
				System.out.println("# others: " + stat.others);
			}
			return stat;
		}
	}
}
