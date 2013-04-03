/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.test.junit;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import sf.wicklet.gwt.client.util.GwtTextUtil;

public class TestTextUtil01 {

	static final boolean DEBUG = true;

	@Test
	public void test() {
		final String data = "  a b cde 123 \n\t123\r\u0085a\u2028b ";
		final List<String> ret = GwtTextUtil.tokenize(data);
		if (DEBUG) {
			System.out.println("# size=" + ret.size());
			for (final String s: ret) {
				System.out.println(s);
		}}
		assertEquals(7, ret.size());
	}
}
