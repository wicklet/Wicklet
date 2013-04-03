/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.test.firefox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.List;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import sf.arquillianext.tomcat.ArquillianTomcatUtil;
import sf.arquillianext.tomcat.ArquillianTomcatUtil.Debug;
import sf.blacksun.util.StepWatch;
import sf.wicklet.gwt.site.test.support.FirefoxTestBase;
import sf.wicklet.test.support.SeleniumTestUtil.TestFirefoxDriver;

public class TestFirefox01 extends FirefoxTestBase {

	////////////////////////////////////////////////////////////////////////

	static final Debug DEBUG = Debug.debug;

	@BeforeClass
	public static void setup() throws IOException, LifecycleException {
		ArquillianTomcatUtil.setup(DEBUG, tomcatHome, WEBAPP, war);
	}

	@AfterClass
	public static void teardown() throws LifecycleException {
		ArquillianTomcatUtil.teardown(DEBUG);
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	protected Debug debug() {
		return DEBUG;
	}

	////////////////////////////////////////////////////////////////////////

	@Test
	public void testHome01() throws IOException {
		final StepWatch timer = new StepWatch(true);
		final FirefoxProfile profile = new FirefoxProfile(firefoxProfileDir);
		profile.setPreference("network.dns.disableIPv6", true);
		final TestFirefoxDriver driver = new TestFirefoxDriver(profile);
		try {
			debugprint(timer, "# Client start");
			driver.get(BASEURL);
			checkTitle(driver, "Wicklet");
			takeSnapshot(driver, htmlfile("TestHome01"), pngfile("TestHome01"));
			findAndCount(driver, By.cssSelector("#topPanel #searchForm #searchText"), 1);
			findAndCount(driver, By.cssSelector("#topPanel .loginPanel #topUser"), 1);
		} finally {
			debugprint(timer, "# Client done");
			if (DEBUG.isDebugServer()) {
				System.in.read();
			} else {
				driver.quit();
	}}}

	@Test
	public void testWikiComment01() throws IOException {
		final StepWatch timer = new StepWatch(true);
		final FirefoxProfile profile = new FirefoxProfile(firefoxProfileDir);
		profile.setPreference("network.dns.disableIPv6", true);
		final TestFirefoxDriver driver = new TestFirefoxDriver(profile);
		try {
			debugprint(timer, "# Client start");
			driver.get(BASEURL);
			checkTitle(driver, "Wicklet");
			findAndClick(driver, By.linkText("Comment"));
			waitAndCount(driver, By.name("commentCaptchaText"), 1);
			takeSnapshot(driver, htmlfile("TestWikiComment01"), pngfile("TestWikiComment01"));
			enterText(driver, "commentSubject", "");
			enterText(driver, "commentText", "");
			enterText(driver, "commentCaptchaText", "");
			findAndClick(driver, By.name("commentSubmit"));
			final List<WebElement> errors = waitAndCount(
				driver, By.cssSelector("#commentFeedback ul li"), 2);
			for (final WebElement e: errors) {
				assertTrue(e.getText().indexOf("ERROR") >= 0);
			}
			enterText(driver, "commentSubject", "test");
			findAndClick(driver, By.name("commentSubmit"));
			waitAndCount(driver, By.cssSelector("#commentFeedback ul li"), 1);
			enterText(driver, "commentCaptchaText", "test");
			findAndClick(driver, By.name("commentSubmit"));
			assertTrue(
				waitAndCount(driver, By.cssSelector("#commentFeedback ul li"), 1).get(0).getText()
					.indexOf("Captcha not match") > 0);
			enterText(driver, "commentSubject", "");
			enterText(driver, "commentCaptchaText", CAPTCHA);
			findAndClick(driver, By.name("commentSubmit"));
			waitAndCount(driver, By.cssSelector("#commentFeedback ul li"), 1);
			enterText(driver, "commentSubject", "test");
			findAndClick(driver, By.name("commentSubmit"));
			assertEquals(
				"You comment is accepted, thank you",
				waitAndCount(driver, By.cssSelector("h3+p"), 1).get(0).getText());
		} finally {
			debugprint(timer, "# Client done");
			if (DEBUG.isDebugServer()) {
				System.in.read();
			} else {
				driver.quit();
	}}}

	@Test
	public void testAdmin01() throws IOException {
		final StepWatch timer = new StepWatch(true);
		final FirefoxProfile profile = new FirefoxProfile(firefoxProfileDir);
		profile.setPreference("network.dns.disableIPv6", true);
		final TestFirefoxDriver driver = new TestFirefoxDriver(profile);
		try {
			debugprint(timer, "# Client start");
			driver.get(BASEURL);
			final String title = driver.getTitle();
			debugprint(timer, "# Page title is: " + title);
			assertEquals("Wicklet", title);
			findAndClick(driver, By.linkText("Login"));
			takeSnapshot(driver, htmlfile("TestLogin01"), pngfile("TestLogin01"));
			findAndCount(driver, By.cssSelector("#topPanel #searchForm #searchText"), 1);
			findAndCount(driver, By.cssSelector("#topPanel .loginPanel #topUser"), 1);
			findAndCount(driver, By.cssSelector("#signinPanel input"), 6);
			login(driver, "admin", "tomcat6");
			waitAndCount(driver, By.cssSelector("table.zebraTable"), 1);
			findAndCount(driver, By.cssSelector(".zebraTable tr.even"), 2);
			findAndCount(driver, By.cssSelector(".zebraTable tr.odd"), 2);
			takeSnapshot(driver, htmlfile("TestAdmin01"), pngfile("TestAdmin01"));
		} finally {
			debugprint(timer, "# Client done");
			if (DEBUG.isDebugServer()) {
				System.in.read();
			} else {
				driver.quit();
	}}}

	@Test
	public void testWiki01() throws IOException {
		final StepWatch timer = new StepWatch(true);
		final FirefoxProfile profile = new FirefoxProfile(firefoxProfileDir);
		profile.setPreference("network.dns.disableIPv6", true);
		final FirefoxDriver driver = new FirefoxDriver(profile);
		// driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
		try {
			debugprint(timer, "# Client start");
			driver.get(BASEURL);
			final String title = driver.getTitle();
			debugprint(timer, "# Page title is: " + title);
			assertEquals("Wicklet", title);
			findAndClick(driver.findElementById("leftTopPanel"), By.linkText("Projects"));
			// No edit link without login
			findAndCount(driver.findElementById("rightPanel"), By.linkText("Edit"), 0);
			// login
			findAndClick(driver, By.linkText("Login"));
			login(driver, "writer", "writer");
			//
			findAndClick(driver.findElementById("rightPanel"), By.linkText("Edit"));
			waitAndCount(driver, By.cssSelector(".gwt-RichTextToolbar"), 1);
			findAndCount(driver, By.cssSelector("iframe.gwt-RichTextArea"), 1);
			takeSnapshot(driver, htmlfile("TestWiki01"), pngfile("TestWiki01"));
			final String top = driver.getWindowHandle();
			driver.switchTo().frame("richtextarea");
			waitAndCount(driver, By.tagName("h1"), 1);
			driver.switchTo().window(top);
			findAndClick(driver, By.linkText("Projects"));
			waitAndClick(driver.findElementById("rightPanel"), By.linkText("Edit"));
			waitAndCount(driver, By.cssSelector("iframe.gwt-RichTextArea"), 1);
			driver.switchTo().frame("richtextarea");
			final List<WebElement> h3s = waitAndCount(driver, By.tagName("h3"), 7);
			takeSnapshot(driver, htmlfile("TestWiki01RichTextArea"), pngfile("TestWiki01RichTextArea"));
			final String text = h3s.get(4).getText();
			assertEquals("WickletGwtServer", text);
		} finally {
			debugprint(timer, "# Client done");
			if (DEBUG.isDebugServer()) {
				System.in.read();
			} else {
				driver.quit();
	}}}

	////////////////////////////////////////////////////////////////////////
}
