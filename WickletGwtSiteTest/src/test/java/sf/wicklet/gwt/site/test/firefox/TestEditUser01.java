/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.test.firefox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
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
import sf.blacksun.util.IPredicate;
import sf.blacksun.util.StepWatch;
import sf.wicklet.gwt.site.test.support.FirefoxTestBase;
import test.util.TestUtil;

public class TestEditUser01 extends FirefoxTestBase {

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
    public void testEditUser01() throws LifecycleException, IOException {
        final StepWatch timer = new StepWatch(true);
        final FirefoxProfile profile = new FirefoxProfile(firefoxProfileDir);
        profile.setPreference("network.dns.disableIPv6", true);
        final FirefoxDriver driver = new FirefoxDriver(profile);
        // driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        try {
            // Check that acess to admin page redirect to https connection.
            debugprint(timer, "# Client start");
            driver.get(BASEURL + "p/admin");
            debugprint(timer.toString("# Client start"));
            assertEquals("Login", driver.getTitle());
            login(driver, USER_ADMIN, PASS_ADMIN);
            assertEquals("Admin", driver.getTitle());
            final String url = driver.getCurrentUrl();
            debugprint("# URL=" + url);
            assertTrue(url.startsWith("https"));
            // Test edit roles
            finder(driver).wait(By.cssSelector("table.zebraTable"), 1) //
                .finder(0).find(By.linkText("edit"), 4).click(0);
            final WebElement dialog = finder(driver).find(By.cssSelector(".gwt-DialogBox"), 1).get(0);
            finder(dialog).find(By.name("username"), 1);
            final WebElement roleselm = finder(dialog).find(By.name("roles"), 1).get(0);
            final String roles = roleselm.getAttribute("value");
            debugprint("# unedited roles=" + roles);
            assertFalse(roles, roles.indexOf("editor") >= 0);
            enterText(roleselm, roles + ",editor");
            finder(dialog).find(By.tagName("button"), 2).filterText("^OK$").click(0);
            TestUtil.assertWithinTimeout(
                new IPredicate() {
                    @SuppressWarnings("synthetic-access")
                    @Override
                    public boolean ok() {
                        final String editedroles = finder(driver).wait(
                            By.cssSelector("table.zebraTable tr:first-child+tr td"), 3).get(1).getText();
                        debugprint("# edited roles: " + editedroles);
                        return editedroles.indexOf("editor") >= 0;
                    }
                });
            // Test the delete link
            finder(driver).find(By.cssSelector("table.zebraTable tr")).count(5) //
                .find(By.cssSelector("table.zebraTable"), 1) //
                .finder(0).find(By.linkText("delete"), 4).click(3);
            finder(driver).wait(By.cssSelector(".gwt-DialogBox"), 1) //
                .finder(0).find(By.tagName("button"), 2).filterText("^OK$").click(0);
            finder(driver).wait(By.cssSelector("table.zebraTable tr"), 4);
        } finally {
            if (DEBUG.isDebug()) {
                final String text = driver.getPageSource();
                System.out.println(text);
                FileUtil.writeFile(htmlfile("TestEditUser01"), false, text);
                System.out.println(timer.toString("# Client done"));
            }
            if (DEBUG.isDebugServer()) {
                System.in.read();
            } else {
                driver.quit();
    }}}

    ////////////////////////////////////////////////////////////////////////
}
