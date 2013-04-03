/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.test.support;

import java.io.File;
import java.util.List;
import org.openqa.selenium.WebElement;
import sf.blacksun.util.FileUtil;
import sf.blacksun.util.StepWatch;
import sf.wicklet.test.support.SeleniumTestBase;
import sf.wicklet.test.support.SeleniumTestUtil.TestFirefoxDriver;
import sf.wicklet.test.support.SeleniumTestUtil.TestHtmlUnitDriver;

public abstract class HtmlUnitTestBase extends SeleniumTestBase {

	////////////////////////////////////////////////////////////////////////

	protected static final File tomcatHome = new File("../opt/tomcat7");
	protected static final File tomcatWebappsDir = new File(tomcatHome, "webapps");
	protected static final File war = new File("../WickletGwtSite/target/WickletGwtSite-1-SNAPSHOT.war");
	protected static final File logDir = FileUtil.mkdirs(
		new File(String.format("logs/%1$tY%1$tm%1$td", System.currentTimeMillis())));

	////////////////////////////////////////////////////////////////////////

	protected static final String ADMIN = "admin";
	protected static final String ADMIN_PASS = "tomcat6";
	protected static final String EDITOR = "editor";
	protected static final String EDITOR_PASS = "editor";
	private static final String CAPTCHA = "gsye7O";

	////////////////////////////////////////////////////////////////////////

	protected void loginAdmin(final TestHtmlUnitDriver driver) {
		enterText(driver.findElementByName("username"), ADMIN);
		enterText(driver.findElementByName("password"), ADMIN_PASS);
		enterText(driver.findElementByName("captchaTextTr:captchaText"), CAPTCHA);
		driver.findElementByName("submit").click();
	}

	protected void loginAndRedirect(final TestHtmlUnitDriver driver, final StepWatch timer) {
		enterText(driver.findElementByName("username"), ADMIN);
		enterText(driver.findElementByName("password"), ADMIN_PASS);
		enterText(driver.findElementByName("captchaTextTr:captchaText"), CAPTCHA);
		driver.findElementByName("submit").click();
		redirects(driver, timer);
	}

	////////////////////////////////////////////////////////////////////////

	protected void loginAdmin(final TestFirefoxDriver driver) {
		enterText(driver.findElementByName("username"), ADMIN);
		enterText(driver.findElementByName("password"), ADMIN_PASS);
		enterText(driver.findElementByName("captchaTextTr:captchaText"), CAPTCHA);
		driver.findElementByName("submit").click();
	}

	protected void loginEditor(final TestFirefoxDriver driver) {
		enterText(driver.findElementByName("username"), EDITOR);
		enterText(driver.findElementByName("password"), EDITOR_PASS);
		enterText(driver.findElementByName("captchaTextTr:captchaText"), CAPTCHA);
		driver.findElementByName("submit").click();
	}

	protected void click(final List<WebElement> a) {
		if (debug().isDebug()) {
			System.out.println("### click(): " + a.size());
			for (final WebElement e: a) {
				System.out.println(e.getTagName() + ": " + e.getLocation() + ": " + getLink(e));
		}}
		if (a.size() > 0) {
			a.get(0).click();
	}}

	protected String getLink(final WebElement e) {
		switch (e.getTagName()) {
		case "img":
			return e.getAttribute("src");
		case "form":
			return e.getAttribute("action");
		case "a":
		default :
			return e.getAttribute("href");
	}}

	////////////////////////////////////////////////////////////////////////
}
