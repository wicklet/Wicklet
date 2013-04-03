/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.test.arquillian;

import java.io.File;
import java.net.URL;
import java.util.List;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import sf.arquillianext.maven.ArquillianMavenUtil;
import sf.arquillianext.tomcat.ArquillianTomcatUtil;
import sf.arquillianext.tomcat.ArquillianTomcatUtil.Debug;
import sf.blacksun.util.FileUtil;
import sf.blacksun.util.StepWatch;
import sf.wicklet.ext.test.arquillian.support.HtmlUnitTestBase;
import sf.wicklet.ext.test.arquillian.test01.TestApplication.TestAccordion01Page;
import sf.wicklet.ext.test.arquillian.test01.TestApplication.TestCodec01Page;
import sf.wicklet.test.support.SeleniumTestUtil;
import sf.wicklet.test.support.SeleniumTestUtil.TestHtmlUnitDriver;

/** A test wrapper of a web application for the test pages that do not require authentication. */
@RunWith(Arquillian.class)
public class Test01 extends HtmlUnitTestBase {

	static final Debug DEBUG = Debug.debug;
	static final File test01Png = new File(HtmlUnitTestBase.logDir, "test01/TestAccordion01Test01.png");
	static final File test01Html = new File(HtmlUnitTestBase.logDir, "test01/TestAccordion01Test01.html");

	////////////////////////////////////////////////////////////////////////

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		// NOTE: This is run before @BeforeClass.
		ArquillianTomcatUtil.cleanWebappsDir(HtmlUnitTestBase.tomcatWebappsDir);
		final File[] deps = ArquillianMavenUtil.resolveArtifacts(
			new File("pom.xml"), "sf.wicklet:wicklet-ext:1-SNAPSHOT", "org.slf4j:slf4j-jdk14:1.6.4");
		if (Test01.DEBUG.isDebug()) {
			System.out.println("# Dependencies: " + deps.length);
			for (final File file: deps) {
				System.out.println(file);
		}}
		final WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war").addPackage(
			"sf.wicklet.ext.test.arquillian.test01").addAsLibraries(deps).setWebXML(
			new File("src/test/java/sf/wicklet/ext/test/arquillian/test01/Test.xml"));
		if (Test01.DEBUG != Debug.none) {
			war.as(ZipExporter.class).exportTo(FileUtil.mkparent(new File("trash/test.zip")), true);
		}
		return war;
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	protected Debug debug() {
		return Test01.DEBUG;
	}

	////////////////////////////////////////////////////////////////////////

	@Test
	public void test01(@ArquillianResource final URL httpContext) throws Exception {
		final StepWatch timer = new StepWatch(true);
		final FirefoxProfile profile = new FirefoxProfile(new File("../opt/firefox/7x16slsr.default"));
		profile.setPreference("network.dns.disableIPv6", true);
		final FirefoxDriver driver = new FirefoxDriver(profile);
		try {
			if (Test01.DEBUG.isDebug()) {
				System.out.println(timer.toString("# Client start"));
			}
			driver.get(new URL(httpContext, TestAccordion01Page.MNT_PATH).toString());
			final String title = driver.getTitle();
			if (Test01.DEBUG.isDebug()) {
				System.out.println(timer.toString("Page title is: " + title));
				System.out.println(driver.getPageSource());
			}
			Assert.assertEquals("Test", driver.getTitle());
			final List<WebElement> p1 = driver.findElementsByLinkText("Panel1");
			final List<WebElement> p2 = driver.findElementsByLinkText("Panel2");
			final List<WebElement> p3 = driver.findElementsByLinkText("Panel3");
			final List<WebElement> c1 = driver.findElementsById("content1");
			final List<WebElement> c2 = driver.findElementsById("content2");
			final List<WebElement> c3 = driver.findElementsById("content3");
			Assert.assertEquals(1, p1.size());
			Assert.assertEquals(1, p2.size());
			Assert.assertEquals(1, p3.size());
			Assert.assertEquals(1, c1.size());
			Assert.assertEquals(1, c2.size());
			Assert.assertEquals(1, c3.size());
			Assert.assertEquals("", c2.get(0).getAttribute("style"));
			//
			p2.get(0).click();
			if (Test01.DEBUG.isDebug()) {
				System.out.println(driver.getPageSource());
			}
			final List<WebElement> cc1 = driver.findElementsById("content1");
			final List<WebElement> cc2 = driver.findElementsById("content2");
			final List<WebElement> cc3 = driver.findElementsById("content3");
			Assert.assertEquals(1, cc1.size());
			Assert.assertEquals(1, cc2.size());
			Assert.assertEquals(1, cc3.size());
			Assert.assertEquals("display: none;", cc2.get(0).getAttribute("style"));
			//
			p2.get(0).click();
			if (Test01.DEBUG.isDebug()) {
				System.out.println(driver.getPageSource());
			}
			final List<WebElement> ccc2 = driver.findElementsById("content2");
			Assert.assertEquals(1, ccc2.size());
			Assert.assertEquals("display: block;", ccc2.get(0).getAttribute("style"));
		} finally {
			if (Test01.DEBUG.isDebug()) {
				final String text = driver.getPageSource();
				FileUtil.writeFile(Test01.test01Html, false, text);
				SeleniumTestUtil.takeScreenshot(driver, Test01.test01Png);
				System.out.println(timer.toString("# Client done"));
			}
			if (Test01.DEBUG.isDebugServer()) {
				System.in.read();
			} else {
				driver.quit();
	}}}

	////////////////////////////////////////////////////////////////////////

	@Test
	public void testCodec01(@ArquillianResource final URL httpContext) throws Exception {
		final StepWatch timer = new StepWatch(true);
		final TestHtmlUnitDriver driver = new TestHtmlUnitDriver();
		final URL url = new URL(httpContext, TestCodec01Page.MNT_PATH);
		// Get the login page.
		getAndRedirect(driver, timer, url);
		final String ret = driver.getWebResponse().getContentAsString();
		if (Test01.DEBUG.isDebug()) {
			System.out.println(ret);
		}
		Assert.assertTrue(ret, ret.matches("(?ms)^.*wIcklEtpcI\\d*-.*$"));
		final WebElement e = driver.findElementById("form");
		e.submit();
		final String title = driver.getTitle();
		if (Test01.DEBUG.isDebug()) {
			System.out.println(timer.toString("Page title is: " + title));
			System.out.println(driver.getPageSource());
		}
		Assert.assertEquals("Test", driver.getTitle());
		final List<WebElement> p1 = driver.findElementsByLinkText("Panel1");
		Assert.assertEquals(1, p1.size());
	}

	////////////////////////////////////////////////////////////////////////
}
