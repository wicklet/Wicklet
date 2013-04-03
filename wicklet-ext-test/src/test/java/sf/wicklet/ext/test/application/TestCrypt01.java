/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.test.application;

import static org.junit.Assert.assertEquals;
import org.apache.wicket.util.crypt.ICrypt;
import org.junit.Test;
import sf.blacksun.util.RandomUtil;
import sf.wicklet.ext.application.DefaultWickletCrypt;

public class TestCrypt01 {

	static final boolean DEBUG = true;
	static final RandomUtil randUtil = RandomUtil.getSingleton();

	@Test
	public void test01() {
		final int COUNT = 1000;
		final String[] data = new String[COUNT];
		for (int i = 0; i < COUNT; ++i) {
			data[i] = randUtil.getString(0, 40);
		}
		final ICrypt crypt = new DefaultWickletCrypt();
		for (int i = 0; i < COUNT; ++i) {
			final String e = crypt.encryptUrlSafe(data[i]);
			final String ret = crypt.decryptUrlSafe(e);
			if (DEBUG) {
				System.out.println("# " + i);
				System.out.println(data[i]);
				System.out.println(e);
				System.out.println(ret);
			}
			assertEquals(data[i], ret);
	}}
}
