/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.test;

import static org.junit.Assert.assertEquals;
import org.apache.wicket.core.util.resource.PackageResourceStream;
import org.apache.wicket.markup.IRootMarkup;
import org.apache.wicket.markup.MarkupParser;
import org.apache.wicket.resource.ResourceUtil;
import org.junit.Test;
import sf.wicklet.wicketext.test.utils.WicketTestBase;
import sf.wicklet.wicketext.util.WicketExtrasUtil;
import sf.wicklet.wicketext.util.WicketExtrasUtil.MarkupDumper.MarkupStat;

public class TestMarkupParser01 extends WicketTestBase {

	static final boolean DEBUG = true;

	////////////////////////////////////////////////////////////////////////

	/* Check markup generated by MarkupParser on a simple html page. */
	@Test
	public void test01() throws Exception {
		final String input = ResourceUtil.readString(
			new PackageResourceStream(
				getClass(), "sf/wicklet/wicketext/test/TestMarkupParser01Test01.html"));
		final MarkupParser parser = new MarkupParser(input);
		final IRootMarkup ret = parser.parse();
		final MarkupStat stat = WicketExtrasUtil.dump(System.out, DEBUG, ret);
		assertEquals(17, stat.ctags);
		assertEquals(18, stat.rtags);
		assertEquals(0, stat.others);
	}

	////////////////////////////////////////////////////////////////////////
}
