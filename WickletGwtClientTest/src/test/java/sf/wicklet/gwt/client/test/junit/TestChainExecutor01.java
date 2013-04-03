/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.test.junit;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import sf.wicklet.gwt.client.util.ChainExecutor;
import sf.wicklet.gwt.client.util.ChainExecutor.IChainable;
import sf.wicklet.gwt.client.util.ChainExecutor.IStartable;

public class TestChainExecutor01 {

	static final boolean DEBUG = true;

	@Test
	public void test() {
		final ChainExecutor e = new ChainExecutor();
		final List<Integer> ret = new ArrayList<Integer>();
		for (int i = 0; i < 10; ++i) {
			final Integer v = i;
			e.queue(
				new IChainable() {
					@Override
					public void run(final IStartable next) {
						ret.add(v);
						if (next != null) {
							next.start();
					}}
				});
		}
		e.start();
		if (DEBUG) {
			for (final Integer v: ret) {
				System.out.println(v);
		}}
		assertEquals(10, ret.size());
		for (int i = 0; i < ret.size(); ++i) {
			assertEquals(Integer.valueOf(i), ret.get(i));
	}}
}
