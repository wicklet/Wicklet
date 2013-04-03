/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.test;

import static org.junit.Assert.assertNull;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.Test;
import sf.blacksun.util.FileUtil;
import sf.blacksun.util.struct.IIntList;
import sf.blacksun.util.struct.IntList;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.dsl.css.api.IStylesheet;
import sf.wicklet.dsl.css.impl.StylesheetSerializer;
import sf.wicklet.dsl.css.test.css.DefaultCss;

public class TestBasic01 {

	static final boolean DEBUG = true;
	static String defaultcss = "css/default.css";

	@Test
	public void test() throws IOException {
		final IStylesheet s = new DefaultCss().build();
		String ret = StylesheetSerializer.serialize(s);
		ret = ret.replaceAll("(?msi)/\\*.*?\\*/", "");
		ret = ret.replaceAll("\\b0px\\b", "0");
		if (DEBUG) {
			System.out.println("### Result:");
			System.out.println(ret);
		}
		String expected = FileUtil.asString(
			new InputStreamReader(getClass().getClassLoader().getResourceAsStream(defaultcss)));
		expected = expected.replaceAll("(?msi)/\\*.*?\\*/", "");
		expected = expected.replaceAll("\\b0px\\b", "0");
		final IIntList offsets = new IntList();
		final int[] result = TextUtil.differIgnoringWhitespaces(expected, ret, offsets, false);
		if (DEBUG && result != null) {
			System.out.println("### Expected:");
			System.out.println(expected.substring(0, result[0]));
			System.out.println(">>>>>");
			System.out.println(expected.substring(result[0]));
			System.out.println("### ERROR: Differ at: " + result[0] + ", " + result[1]);
		}
		assertNull(result);
	}
}
