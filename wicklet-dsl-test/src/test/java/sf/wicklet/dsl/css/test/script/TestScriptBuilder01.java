/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.test.script;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Test;
import sf.blacksun.util.FileUtil;
import sf.wicklet.dsl.script.impl.ScriptBuilder;

public class TestScriptBuilder01 {

	static final boolean DEBUG = true;

	@Test
	public void test01() throws IOException {
		final String id = "testPanel";
		final String ret = new ScriptBuilder() {
			public String build() {
				return script(
					fmt("$('#%s > *:nth-child(odd)').click(", id).blk(
						stm("function() {").blk(
							stm("var content = $(this).next();"),
							stm("var d = content.css('display');"),
							stm("var save = content.attr('wicklet-save-display');"),
							stm(
								"if (d && d.toLowerCase() == 'none') {",
								"    content.css('display', save);",
								"    content.removeAttr('wicklet-save-display');"),
							stm(
								"} else {",
								"    content.css('display', 'none');",
								"    content.attr('wicklet-save-display', d);"),
							stm("}});"))));
			}
		}.build();
		if (DEBUG) {
			System.out.println("|" + ret + "|");
		}
		assertEquals(
			FileUtil.getResourceAsString("/sf/wicklet/dsl/css/test/script/TestScriptBuilder01Test01.txt"),
			ret);
	}

	@Test
	public void testIndent01() throws IOException {
		final String id = "testPanel";
		final String ret = new ScriptBuilder("  ", "    ") {
			public String build() {
				return script(
					fmt("$('#%s > *:nth-child(odd)').click(", id).blk(
						stm("function() {").blk(
							stm("var content = $(this).next();"),
							stm("var d = content.css('display');"),
							stm("var save = content.attr('wicklet-save-display');"),
							stm("if (d && d.toLowerCase() == 'none') {").blk(
								stm("content.css('display', save);"),
								stm("content.removeAttr('wicklet-save-display');")),
							stm("} else {").blk(
								stm("content.css('display', 'none');"),
								stm("content.attr('wicklet-save-display', d);")),
							stm("}});"))));
			}
		}.build();
		if (DEBUG) {
			System.out.println("|" + ret + "|");
		}
		assertEquals(
			FileUtil.getResourceAsString(
				"/sf/wicklet/dsl/css/test/script/TestScriptBuilder01TestIndent01.txt"),
			ret);
	}

	@Test
	public void testFunction01() throws IOException {
		final String id = "testPanel";
		final String ret = new ScriptBuilder("  ", "    ") {
			public String build() {
				return script(
					fmt("$('#%s > *:nth-child(odd)').click(", id).blk(
						function().blk(
							stm("var content = $(this).next();"),
							stm("var d = content.css('display');"),
							stm("var save = content.attr('wicklet-save-display');"),
							stm("if (d && d.toLowerCase() == 'none') {"),
							stm("    content.css('display', save);"),
							stm("    content.removeAttr('wicklet-save-display');"),
							stm("} else {"),
							stm("    content.css('display', 'none');"),
							stm("    content.attr('wicklet-save-display', d);"),
							stm("}});"))));
			}
		}.build();
		if (DEBUG) {
			System.out.println("|" + ret + "|");
		}
		assertEquals(
			FileUtil.getResourceAsString(
				"/sf/wicklet/dsl/css/test/script/TestScriptBuilder01TestIndent01.txt"),
			ret);
	}
}
