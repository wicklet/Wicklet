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
import sf.arquillianext.maven.ArquillianMavenUtil;
import sf.arquillianext.tomcat.ArquillianTomcatUtil;
import sf.arquillianext.tomcat.ArquillianTomcatUtil.Debug;
import sf.blacksun.util.FileUtil;
import sf.blacksun.util.StepWatch;
import sf.wicklet.ext.auth.WxSignInPanel;
import sf.wicklet.ext.test.arquillian.support.HtmlUnitTestBase;
import sf.wicklet.ext.test.arquillian.testauth01.TestApplication;
import sf.wicklet.ext.test.arquillian.testauth01.TestApplication.TestLogoutPage;
import sf.wicklet.ext.test.arquillian.testauth01.TestApplication.TestRememberPage;
import sf.wicklet.ext.test.arquillian.testauth01.TestApplication.TestSecurePage;
import sf.wicklet.ext.test.arquillian.testauth01.TestApplication.TestSigninPage;
import sf.wicklet.test.support.SeleniumTestUtil.TestHtmlUnitDriver;
import com.gargoylesoftware.htmlunit.util.Cookie;

/** A test wrapper of a web application for the test pages that require authentication. */
@RunWith(Arquillian.class)
public class TestAuth01 extends HtmlUnitTestBase {

    ////////////////////////////////////////////////////////////////////////

    static final Debug DEBUG = Debug.debug;
    static final File test01HtmlFile = new File(HtmlUnitTestBase.logDir, "testauth01/TestAuth01.html");

    ////////////////////////////////////////////////////////////////////////

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        // NOTE: This is run before @BeforeClass.
        ArquillianTomcatUtil.cleanTomcatHome(HtmlUnitTestBase.tomcatHome);
        final File[] deps = ArquillianMavenUtil.resolveArtifacts(
            new File("pom.xml"), "sf.wicklet:wicklet-ext:1-SNAPSHOT");
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war").addPackages(
            true, "sf.wicklet.ext.test.arquillian.testauth01").addAsLibraries(deps).setWebXML(
            new File("src/test/java/sf/wicklet/ext/test/arquillian/testauth01/Test.xml"));
        if (TestAuth01.DEBUG != Debug.none) {
            System.out.println("### Artifacts: " + deps.length);
            for ( final File file: deps) {
                System.out.println(file.getAbsolutePath());
            }
            war.as(ZipExporter.class).exportTo(FileUtil.mkparent(new File("trash/test.zip")), true);
        }
        return war;
    }

    ////////////////////////////////////////////////////////////////////////

    @Override
    protected Debug debug() {
        return TestAuth01.DEBUG;
    }

    ////////////////////////////////////////////////////////////////////////

    /* Basic test with login fails. */
    @Test
    public void testWxSignInFail01(@ArquillianResource final URL httpContext) throws Exception {
        final StepWatch timer = new StepWatch(true);
        final TestHtmlUnitDriver driver = new TestHtmlUnitDriver();
        final URL url = new URL(httpContext, TestSigninPage.MNT_PATH);
        // Get the login page.
        getAndRedirect(driver, timer, url);
        final List<WebElement> u = driver.findElementsByName("username");
        final List<WebElement> p = driver.findElementsByName("password");
        final List<WebElement> c = driver.findElementsByName("captchaTextTr:captchaText");
        final List<WebElement> s = driver.findElementsByName("submit");
        Assert.assertEquals(1, u.size());
        Assert.assertEquals(1, p.size());
        Assert.assertEquals(1, c.size());
        Assert.assertEquals(1, s.size());
        // Try login
        u.get(0).sendKeys("writer");
        p.get(0).sendKeys("writer");
        c.get(0).sendKeys("xxx");
        submit(driver, timer, s.get(0));
        printCookies(driver);
        // Expected to fail due to captcha text mismatch.
        checkFeedbacks(driver.findElementsByCssSelector("span.feedbackPanelERROR"), WxSignInPanel.MSG_CAPTCHA_FAILED);
        Assert.assertEquals("writer", driver.findElementsByName("username").get(0).getAttribute("value"));
        Assert.assertEquals("", driver.findElementsByName("password").get(0).getAttribute("value"));
        Assert.assertEquals("xxx", driver.findElementsByName("captchaTextTr:captchaText").get(0).getAttribute("value"));
        // Try login again and expected to fail due to missing password.
        login(driver, timer, "writer", null, TestSigninPage.CAPTCHA_TEXT);
        checkFeedbacks(
            driver.findElementsByCssSelector("span.feedbackPanelERROR"), WxSignInPanel.MSG_PASSWORD_REQUIRED);
        // Try login again and expected to fail due to bad password.
        login(driver, timer, "writer", "xxx", TestSigninPage.CAPTCHA_TEXT);
        checkFeedbacks(
            driver.findElementsByCssSelector("span.feedbackPanelERROR"), WxSignInPanel.MSG_AUTHENTICATION_FAILED);
    }

    ////////////////////////////////////////////////////////////////////////

    /* Basic test with login OK. */
    @Test
    public void testWxSignInOk01(@ArquillianResource final URL httpContext) throws Exception {
        final StepWatch timer = new StepWatch(true);
        final TestHtmlUnitDriver driver = new TestHtmlUnitDriver();
        final URL url = new URL(httpContext, TestSigninPage.MNT_PATH);
        // Login and expected to succeed
        login(driver, timer, url, "writer", "writer", TestSigninPage.CAPTCHA_TEXT);
        final List<WebElement> feedbacks = driver.findElementsByCssSelector("span.feedbackPanelERROR");
        Assert.assertEquals(0, feedbacks.size());
        Assert.assertEquals(TestApplication.HomePage.CONTENT, driver.findElementByTagName("body").getText());
        // Try login again without entering password and make sure it would fail with validation error.
        login(driver, timer, url, "writer", null, TestSigninPage.CAPTCHA_TEXT);
        checkFeedbacks(
            driver.findElementsByCssSelector("span.feedbackPanelERROR"), WxSignInPanel.MSG_PASSWORD_REQUIRED);
    }

    ////////////////////////////////////////////////////////////////////////

    /* Basic test with property type conversion. */
    @Test
    public void testRemember01(@ArquillianResource final URL httpContext) throws Exception {
        final StepWatch timer = new StepWatch(true);
        final TestHtmlUnitDriver driver = new TestHtmlUnitDriver();
        driver.getWebClient().setRedirectEnabled(false);
        final URL url = new URL(httpContext, TestRememberPage.MNT_PATH);
        // Login and expected to succeed
        getAndRedirect(driver, timer, url);
        Assert.assertEquals(1, driver.findElementsByName("rememberTr:rememberMe").size());
        login(driver, timer, "writer", "writer", TestRememberPage.CAPTCHA_TEXT, true);
        final List<WebElement> feedbacks = driver.findElementsByCssSelector("span.feedbackPanelERROR");
        Assert.assertEquals(0, feedbacks.size());
        Assert.assertEquals(TestApplication.HomePage.CONTENT, driver.findElementByTagName("body").getText());
        // Logout but keeping the LoggedIn cookie, emulating session timeout.
        final Cookie c = driver.getWebClient().getCookieManager().getCookie(TestApplication.LOGGED_IN_COOKIE_NAME);
        getAndRedirect(driver, timer, new URL(httpContext, TestLogoutPage.MNT_PATH));
        Assert.assertFalse(hasCookie(driver, TestApplication.LOGGED_IN_COOKIE_NAME));
        driver.getWebClient().getCookieManager().addCookie(c);
        getAndRedirect(driver, timer, new URL(httpContext, TestSecurePage.MNT_PATH));
        checkTitle(driver, TestSecurePage.TITLE);
        Assert.assertTrue(hasCookie(driver, TestApplication.LOGGED_IN_COOKIE_NAME));
    }

    ////////////////////////////////////////////////////////////////////////

    private void login(
        final TestHtmlUnitDriver driver,
        final StepWatch timer,
        final URL url,
        final String username,
        final String password,
        final String captchatext) {
        login(driver, timer, url, username, password, captchatext, null);
    }

    private void login(
        final TestHtmlUnitDriver driver,
        final StepWatch timer,
        final URL url,
        final String username,
        final String password,
        final String captchatext,
        final Boolean remember) {
        getAndRedirect(driver, timer, url);
        login(driver, timer, username, password, captchatext, remember);
    }

    private void login(
        final TestHtmlUnitDriver driver,
        final StepWatch timer,
        final String username,
        final String password,
        final String captchatext) {
        login(driver, timer, username, password, captchatext, null);
    }

    private void login(
        final TestHtmlUnitDriver driver,
        final StepWatch timer,
        final String username,
        final String password,
        final String captchatext,
        final Boolean remember) {
        enterText(driver, "username", username);
        enterText(driver, "password", password);
        if (remember != null) {
            setSelection(driver, "rememberTr:rememberMe", remember);
        }
        enterText(driver, "captchaTextTr:captchaText", captchatext);
        submit(driver, timer, driver.findElementsByName("submit").get(0));
    }

    ////////////////////////////////////////////////////////////////////////
}
