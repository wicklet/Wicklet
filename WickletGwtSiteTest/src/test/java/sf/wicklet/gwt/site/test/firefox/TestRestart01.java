/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.test.firefox;

import java.io.File;
import java.io.IOException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import sf.arquillianext.tomcat.ArquillianTomcatUtil;
import sf.arquillianext.tomcat.ArquillianTomcatUtil.Debug;
import sf.blacksun.util.FileUtil;
import sf.blacksun.util.StepWatch;
import sf.wicklet.gwt.site.test.support.HtmlUnitTestBase;

public class TestRestart01 extends HtmlUnitTestBase {

	////////////////////////////////////////////////////////////////////////

	static final Debug DEBUG = Debug.debug;
	static final File testHome01Png = new File(HtmlUnitTestBase.logDir, "TestRestart01TestHome01.png");
	static final File testHome01Html = new File(HtmlUnitTestBase.logDir, "TestRestart01TestHome01.html");

	////////////////////////////////////////////////////////////////////////

	@Override
	protected Debug debug() {
		return TestRestart01.DEBUG;
	}

	////////////////////////////////////////////////////////////////////////

	/* Make sure it still works on tomcat restarted. */
	@Test
	public void testHome01() throws IOException, LifecycleException {
		ArquillianTomcatUtil.setup(DEBUG, tomcatHome, "wicklet-site", war);
		try {
			test();
		} finally {
			ArquillianTomcatUtil.teardown(TestRestart01.DEBUG);
		}
		// Do it again
		ArquillianTomcatUtil.setup(DEBUG.isDebug(), DEBUG.isDebugServer(), tomcatHome);
		try {
			test();
		} finally {
			ArquillianTomcatUtil.teardown(TestRestart01.DEBUG);
	}}

	private void test() throws IOException {
		final StepWatch timer = new StepWatch(true);
		final FirefoxProfile profile = new FirefoxProfile(new File("../opt/firefox/7x16slsr.default"));
		profile.setPreference("network.dns.disableIPv6", true);
		final FirefoxDriver driver = new FirefoxDriver(profile);
		try {
			if (TestRestart01.DEBUG.isDebug()) {
				System.out.println(timer.toString("# Client start"));
			}
			driver.get("http://localhost:8080/wicklet-site/");
			final String title = driver.getTitle();
			if (TestRestart01.DEBUG.isDebug()) {
				System.out.println(timer.toString("Page title is: " + title));
			}
			Assert.assertEquals("Wicklet", driver.getTitle());
		} finally {
			if (TestRestart01.DEBUG.isDebug()) {
				final String text = driver.getPageSource();
				System.out.println(text);
				FileUtil.writeFile(TestRestart01.testHome01Html, false, text);
				takeScreenshot(driver, TestRestart01.testHome01Png);
				System.out.println(timer.toString("# Client done"));
			}
			if (TestRestart01.DEBUG.isDebugServer()) {
				System.in.read();
			} else {
				driver.quit();
	}}}

	////////////////////////////////////////////////////////////////////////
}
