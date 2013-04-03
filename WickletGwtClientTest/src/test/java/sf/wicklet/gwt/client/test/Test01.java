/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.File;
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
import sf.blacksun.util.FileUtil;
import sf.blacksun.util.StepWatch;
import sf.wicklet.gwt.client.test.support.HtmlUnitTestBase;

public class Test01 extends HtmlUnitTestBase {

    ////////////////////////////////////////////////////////////////////////

    static final Debug DEBUG = Debug.debug;
    static final File testHome01Png = new File(logDir, "Test01TestHome01.png");
    static final File testHome01Html = new File(logDir, "Test01TestHome01.html");
    static final File testHome01DialogPng = new File(logDir, "Test01TestHome01Dialog.png");
    static final File testHome01DialogHtml = new File(logDir, "Test01TestHome01Dialog.html");
    static final File war = new File("../WickletGwtClientTestWar/target/WickletGwtClientTestWar-1-SNAPSHOT.war");
    // static final File firefoxBinary = FileUtil.root("/opt/firefox/firefox");

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
        // final FirefoxDriver driver = new FirefoxDriver(new FirefoxBinary(firefoxBinary), profile);
        final FirefoxDriver driver = new FirefoxDriver(profile);
        try {
            if (DEBUG.isDebug()) {
                System.out.println(timer.toString("# Client start"));
            }
            driver.get(BASEURL);
            final String title = driver.getTitle();
            if (DEBUG.isDebug()) {
                System.out.println(timer.toString("Page title is: " + title));
            }
            assertEquals("HomePage", title);
            if (DEBUG.isDebug()) {
                final String text = driver.getPageSource();
                System.out.println(text);
                FileUtil.writeFile(testHome01Html, false, text);
                takeScreenshot(driver, testHome01Png);
            }
            toppanel(driver);
            logindialog(driver);
            menubar(driver);
            stackpanel(driver);
            ajaxpanel(driver);
        } catch (final Throwable e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (DEBUG.isDebug()) {
                System.out.println(timer.toString("# Client done"));
            }
            if (DEBUG.isDebugServer()) {
                System.in.read();
            } else {
                driver.quit();
    }}}

    private void toppanel(final FirefoxDriver driver) {
        findAndCount(driver, By.cssSelector("#topPanel #searchForm .searchText"), 1);
        findAndCount(driver, By.cssSelector("#topPanel .loginPanel #topUser"), 1);
    }

    private void logindialog(final FirefoxDriver driver) {
        findAndClick(driver, By.cssSelector("#topPanel .loginPanel #topLogin"));
        waitAndCount(driver, By.cssSelector("#logindialogCancel"), 1);
        final List<WebElement> buttons = findAndCount(driver, By.cssSelector("button"), 2);
        for ( final WebElement e: buttons) {
            if ("Cancel".equals(e.getText())) {
                e.click();
                break;
        }}
        waitAndCount(driver, By.cssSelector("#logindialogCancel"), 0);
    }

    private void menubar(final FirefoxDriver driver) {
        final List<WebElement> items = findAndCount(driver, By.cssSelector("td.gwt-MenuItem"), 3);
        // Show popup menu
        items.get(0).click();
        waitAndCount(driver, By.cssSelector("td.gwt-MenuItem"), 5);
        // Hide popup menu
        items.get(0).click();
    }

    private void stackpanel(final FirefoxDriver driver) {
        final List<WebElement> items = findAndCount(driver, By.cssSelector("td.gwt-StackPanelContent"), 3);
        int displayed = 0;
        for ( final WebElement e: items) {
            if (e.isDisplayed()) {
                ++displayed;
        }}
        assertEquals(1, displayed);
        final List<WebElement> panels = findAndCount(driver, By.id("stackpanel"), 1);
        findAndCount(panels.get(0), By.cssSelector("a.gwt-Hyperlink"), 6);
        findAndCount(panels.get(0), By.linkText("TestHref"), 0);
        // Open Forum panel.
        findAndCount(panels.get(0), By.cssSelector("td.gwt-StackPanelItem"), 3).get(2).click();
        final List<WebElement> testhrefs = findAndCount(panels.get(0), By.linkText("TestHref"), 2);
        assertTrue(testhrefs.get(0).getAttribute("href"), testhrefs.get(0).getAttribute("href").endsWith("/testhref"));
        assertEquals("javascript:", testhrefs.get(1).getAttribute("href"));
    }

    private void ajaxpanel(final FirefoxDriver driver) {
        findAndClick(driver, By.cssSelector("#ajaxshow"));
        waitAndCount(driver, By.cssSelector("#ajaxcontent button"), 1);
        findAndCount(driver, By.cssSelector("#ajaxcontent input"), 1);
        findAndCount(driver, By.cssSelector("#ajaxname"), 1);
        findAndClick(driver, By.cssSelector("#ajaxcontent button"));
        waitAndCount(driver, By.cssSelector("div.dialogContent button"), 1);
        findAndClick(driver, By.cssSelector("div.dialogContent button"));
        assertEquals("instance", findById(driver, "ajaxname").getText());
        findAndClick(driver, By.cssSelector("#ajaxshowstatic"));
        assertEquals("static", findById(driver, "ajaxname").getText());
        assertFalse(findAndCount(driver, By.cssSelector(".ajaxhiddenbylink"), 1).get(0).isDisplayed());
        assertFalse(findAndCount(driver, By.cssSelector(".ajaxhiddenbystyle"), 1).get(0).isDisplayed());
    }

    ////////////////////////////////////////////////////////////////////////
}
