/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.test;

import static org.junit.Assert.fail;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import sf.blacksun.util.FileUtil;
import sf.wicklet.dsl.html.api.IElement;
import sf.wicklet.dsl.html.impl.XHtmlSerializer;
import test.util.TestUtil;

public class DslTestUtil {

	private DslTestUtil() {
	}

	/**
	 * Verify serialized text representation of e is same as the expected resource
	 * loaded from current classloader.
	 * @param compact TODO
	 */
	public static void verify(final boolean debug, final String tab, final String expected, final IElement e) {
		final String ret = XHtmlSerializer.serialize(tab, e);
		verify(debug, expected, ret);
	}

	public static void verify(final boolean debug, final String expected, final String ret) {
		if (debug) {
			System.out.println(ret);
		}
		InputStream is = null;
		try {
			is = DslTestUtil.class.getResourceAsStream(expected);
			TestUtil.verifyFormatted(FileUtil.asString(new InputStreamReader(is)), ret);
		} catch (final IOException e) {
			fail(ret);
		} finally {
			FileUtil.close(is);
	}}
}
