/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.test.htmlunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import sf.arquillianext.tomcat.ArquillianTomcatUtil;
import sf.arquillianext.tomcat.ArquillianTomcatUtil.Debug;
import sf.blacksun.util.FileUtil;
import sf.blacksun.util.StepWatch;
import sf.blacksun.util.text.TextUtil;
import sf.wicklet.gwt.site.test.support.HtmlUnitTestBase;
import sf.wicklet.test.support.SeleniumTestUtil.TestHtmlUnitDriver;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class TestHtmlUnit01 extends HtmlUnitTestBase {

	////////////////////////////////////////////////////////////////////////

	static final Debug DEBUG = Debug.debug;
	static final File testHome01Html = new File(logDir, "TestHtmlUnit01TestHome01.html");
    static final File testLogin10Html = new File(logDir, "TestHtmlUnit01TestLogin01.html");
    static final File testEditUser01Html = new File(logDir, "TestHtmlUnit01TestEditUser01.html");

	@BeforeClass
	public static void setup() throws IOException, LifecycleException {
        final File root = new File(tomcatHome, "webapps/ROOT");
        FileUtil.removeSubTrees(root);
        FileUtil.writeFile(new File(root, "index.html"), false, "testing");
		ArquillianTomcatUtil.setup(DEBUG, tomcatHome, "wicklet-site", war);
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
	public void testHome01() throws LifecycleException, IOException {
		final StepWatch timer = new StepWatch(true);
		final TestHtmlUnitDriver driver = new TestHtmlUnitDriver();
		try {
			if (DEBUG.isDebug()) {
				System.out.println(timer.toString("# Client start"));
			}
			driver.get("http://localhost:8080/wicklet-site/");
			final String resp = driver.getWebResponse().getContentAsString();
			if (DEBUG.isDebug()) {
				System.out.println(timer.toString("Page title is: " + driver.getTitle()));
				System.out.println(resp);
			}
			assertEquals("Wicklet", driver.getTitle());
			finder(driver).find(By.id("wikiPanel"), 1);
			finder(driver).find(By.linkText("Projects"), 1);
		} finally {
			if (DEBUG.isDebug()) {
				final String text = driver.getPageSource();
				System.out.println(text);
				FileUtil.writeFile(testHome01Html, false, text);
				System.out.println(timer.toString("# Client done"));
	}}}

	////////////////////////////////////////////////////////////////////////

    @Test
    public void testLogin01() throws LifecycleException, IOException {
        final StepWatch timer = new StepWatch(true);
        final TestHtmlUnitDriver driver = new TestHtmlUnitDriver();
        try {
            driver.setJavascriptEnabled(true);
            driver.getWebClient().setRedirectEnabled(false);
            if (DEBUG.isDebug()) {
                System.out.println(timer.toString("# Client start"));
            }
            final String url = "http://localhost:8080/wicklet-site/p/admin";
            getAndRedirect(driver, timer, url);
            assertEquals("Login", driver.getTitle());
            loginAndRedirect(driver, timer);
            debugprint(driver, timer, url);
            assertEquals("Admin", driver.getTitle());
        } finally {
            if (DEBUG.isDebug()) {
                final String text = driver.getPageSource();
                System.out.println(text);
                FileUtil.writeFile(testLogin10Html, false, text);
                System.out.println(timer.toString("# Client done"));
    }}}

    ////////////////////////////////////////////////////////////////////////

    @Test
    public void testSessions01() throws LifecycleException, IOException {
        final StepWatch timer = new StepWatch(true);
        try {
            final TestHtmlUnitDriver driver = new TestHtmlUnitDriver();
            driver.setJavascriptEnabled(true);
            driver.getWebClient().setRedirectEnabled(false);
            if (DEBUG.isDebug()) {
                System.out.println(timer.toString("# Client start"));
            }
            get(driver, timer, "http://localhost:8080/");
            assertEquals(0, driver.getWebClient().getCookieManager().getCookies().size());
            get(driver, timer, "https://localhost:8443/");
            assertEquals(0, driver.getWebClient().getCookieManager().getCookies().size());
            get(driver, timer, "http://localhost:8080/wicklet-site/");
            assertEquals(1, driver.getWebClient().getCookieManager().getCookies().size());
            String sid = getCookieValue(driver, "JSESSIONID");
            get(driver, timer, "https://localhost:8443/wicklet-site/");
            assertEquals(1, driver.getWebClient().getCookieManager().getCookies().size());
            // TODO It is possible better to start a new session if switch between http and https.
            assertEquals(sid, getCookieValue(driver, "JSESSIONID"));
            get(driver, timer, "https://localhost:8443/wicklet-site/p/login");
            assertEquals(1, driver.getWebClient().getCookieManager().getCookies().size());
            // There are possible another redirect by wicket to login?n
            redirects(driver, timer);
            // Login should start a new session
            sid = assertNewId(driver, sid);
            loginAdmin(driver);
            debugprint(driver, timer, "https://localhost:8443/wicklet-site/p/login.click");
            final Set<Cookie> cookies = driver.getWebClient().getCookieManager().getCookies();
            assertEquals(1, cookies.size());
            final Cookie cid = getCookie(cookies, "JSESSIONID");
            assertNotNull(cid);
            assertTrue(cid.isSecure());
            // Should start a new session after login.
            sid = assertNewId(driver, sid);
            redirects(driver, timer);
            assertEquals("Admin", driver.getTitle());
            assertEquals(sid, getCookieValue(driver, "JSESSIONID"));
            getAndRedirect(driver, timer, "https://localhost:8443/wicklet-site/");
            get(driver, timer, "http://localhost:8080/wicklet-site/p/admin");
            assertEquals(sid, getCookieValue(driver, "JSESSIONID"));
            redirects(driver, timer);
            assertEquals(sid, getCookieValue(driver, "JSESSIONID"));
            assertEquals("Admin", driver.getTitle());
        } finally {
            if (DEBUG.isDebug()) {
                System.out.println(timer.toString("# Client done"));
    }}}

    private String assertNewId(final TestHtmlUnitDriver driver, final String oldid) {
        final String newid = getCookieValue(driver, "JSESSIONID");
        assertFalse(TextUtil.equals(oldid, newid));
        return newid;
    }

    ////////////////////////////////////////////////////////////////////////
}
