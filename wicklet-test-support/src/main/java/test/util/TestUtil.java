/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package test.util;

import static org.junit.Assert.fail;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import sf.blacksun.util.IPredicate;
import sf.blacksun.util.struct.IIntList;
import sf.blacksun.util.struct.IntList;
import sf.blacksun.util.text.DefaultSpaceUtil;
import sf.blacksun.util.text.JavaNameDetector;
import sf.blacksun.util.text.TextUtil;

public class TestUtil {

	public static final int TIMEOUT = 3000; // ms
	public static final int STEP = 50; // ms

	////////////////////////////////////////////////////////////////////////

	private TestUtil() {
	}

	////////////////////////////////////////////////////////////////////////

	public static void checkIndents(final int[] expects, final String result) {
		final List<String> lines = TextUtil.splitLines(result);
		final IIntList actuals = new IntList();
		for (final String line: lines) {
			actuals.add(TestUtil.indentOf(0, 8, line));
		}
		if (expects.length != lines.size()) {
			throw new AssertionError(
				"Expected=\n"
					+ TextUtil.sprintArray(expects)
					+ "\nActual=\n"
					+ TextUtil.sprintArray(actuals.toArray()));
		}
		for (int i = 0; i < expects.length; ++i) {
			if (expects[i] != actuals.get(i)) {
				throw new AssertionError(
					"ERROR: i=" + i + ", expected=" + expects[i] + ", actual=" + actuals.get(i));
	}}}

	public static void verifyFormatted(final CharSequence source, final CharSequence formatted) {
		final int[] diff = TextUtil.differIgnoringWhitespaces(
			source, formatted, null, true, JavaNameDetector.getSingleton(), DefaultSpaceUtil.getSingleton());
		if (diff != null) {
			throw new AssertionError(
				"Non-whitespace differences at: orignal offset="
					+ diff[0]
					+ ", formatted offset="
					+ diff[1]
					+ ", formatted=\n"
					+ formatted.subSequence(diff[1], Math.min(diff[1] + 100, formatted.length())));
	}}

	public static void checkContainsAll(final String result, final String...strings) {
		for (int i = 0; i < strings.length; ++i) {
			if (result.indexOf(strings[i]) < 0) {
				throw new AssertionError("ERROR: String not found: i=" + i + ", string=" + strings[i]);
	}}}

	////////////////////////////////////////////////////////////////////////

	public static int indentOf(int offset, final int tabwidth, final String text) {
		if (offset < 0) {
			throw new AssertionError("ASSERT: offset >= 0: offset=" + offset);
		}
		final int linestart = TestUtil.findLineStart(text, offset);
		offset = DefaultSpaceUtil.getSingleton().skipWhitespaces(text, linestart, text.length());
		final int ret = TextUtil.columnOf(text.substring(linestart, offset), 0, offset - linestart, tabwidth);
		return ret;
	}

	public static int findLineStart(final String text, int offset) {
		for (; offset > 0; --offset) {
			if (text.charAt(offset - 1) == '\n') {
				return offset;
		}}
		return offset;
	}

	public static void gc() {
		for (int i = 0; i < 5; ++i) {
			System.gc();
			TestUtil.sleep(20);
	}}

	public static void sleep(final long ms) {
		try {
			Thread.sleep(ms);
		} catch (final InterruptedException e) {
	}}

	public static void assertWithinTimeout(final IPredicate predicate) {
		assertWithinTimeout(TIMEOUT, TIMEOUT / 10, predicate);
	}

	public static void assertWithinTimeout(final long timeout, final IPredicate predicate) {
		assertWithinTimeout(timeout, timeout / 10, predicate);
	}

	public static void assertWithinTimeout(final long timeout, final long step, final IPredicate predicate) {
		long ms = timeout;
		do {
			sleep(step);
			ms -= step;
			if (predicate.ok()) {
				return;
		}} while (ms > 0);
		fail("Predicate not true within timeout: " + predicate.toString());
	}

	/**
	 * Try to determine sizeof an object given by 'name'. This is very slow, just for benchmark use.
	 *
	 * @enter the named object must have a default constructor.
	 */
	public static int sizeof(final String name) {
		final int size = 1000;
		final Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		final Object[] a = new Object[size];
		final long used = runtime.totalMemory() - runtime.freeMemory();
		for (int i = 0; i < size; ++i) {
			try {
				a[i] = Class.forName(name).newInstance();
			} catch (final Exception e) {
				throw new RuntimeException("name=" + name, e);
		}}
		final int ret = (int)((runtime.totalMemory() - runtime.freeMemory() - used) / size);
		a[0] = a[1];
		return ret;
	}

	public static int count(final Iterator<?> it) {
		if (it == null) {
			return 0;
		}
		int ret = 0;
		while (it.hasNext()) {
			it.next();
			++ret;
		}
		return ret;
	}

	////////////////////////////////////////////////////////////////////////

	public static <T> void equals(final Collection<T> c, final T...expected) {
		final Iterator<T> it = c.iterator();
		for (int i = 0; i < expected.length; ++i) {
			Assert.assertEquals("i=" + i, expected[i], it.next());
	}}

	public static <T> void equals(final Iterator<T> it, final T...expected) {
		for (int i = 0; i < expected.length; ++i) {
			Assert.assertEquals("i=" + i, expected[i], it.next());
	}}

	public static void equals(final byte[] expected, final byte[] actual) {
		if (expected == null) {
			Assert.assertNull(actual);
			return;
		}
		Assert.assertEquals(expected.length, actual.length);
		for (int i = 0; i < expected.length; ++i) {
			Assert.assertEquals("i=" + i, expected[i], actual[i]);
	}}

	public static void equals(final char[] expected, final char[] actual) {
		if (expected == null) {
			Assert.assertNull(actual);
			return;
		}
		Assert.assertEquals(expected.length, actual.length);
		for (int i = 0; i < expected.length; ++i) {
			Assert.assertEquals("i=" + i, expected[i], actual[i]);
	}}

	////////////////////////////////////////////////////////////////////////

	public static <T> void print(final String sep, final Collection<T> c) {
		System.out.println(TextUtil.join(sep, c));
	}

	public static <T> void println(final Collection<T> c) {
		for (final T e: c) {
			System.out.println(e == null ? "null" : e.toString());
	}}

	////////////////////////////////////////////////////////////////////////
}
