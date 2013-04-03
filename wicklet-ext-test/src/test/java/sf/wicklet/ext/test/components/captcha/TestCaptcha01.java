/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.test.components.captcha;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import sf.blacksun.util.FileUtil;
import sf.wicklet.ext.components.captcha.WxCaptchaResource;

public class TestCaptcha01 {

	protected static final File logDir = FileUtil.mkdirs(
		new File(String.format("logs/%1$tY%1$tm%1$td", System.currentTimeMillis())));
	protected static final File test01png = new File(logDir, "TestCaptcha01Test01.png");

	@Test
	public void test01() throws IOException {
		final byte[] data = new WxCaptchaResource() {
			private static final long serialVersionUID = 1L;
			@Override
			public byte[] render() {
				return super.render();
			}
		}.render();
		FileUtil.writeFile(test01png, false, data);
	}
}
