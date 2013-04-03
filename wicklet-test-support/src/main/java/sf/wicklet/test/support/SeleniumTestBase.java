/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.test.support;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.Response;
import sf.arquillianext.tomcat.ArquillianTomcatUtil.Debug;
import sf.blacksun.util.StepWatch;
import sf.wicklet.test.support.SeleniumTestUtil.TestFirefoxDriver;
import sf.wicklet.test.support.SeleniumTestUtil.TestHtmlUnitDriver;
import com.gargoylesoftware.htmlunit.util.Cookie;

public abstract class SeleniumTestBase {

	protected abstract Debug debug();

	////////////////////////////////////////////////////////////////////////

	protected void redirects(final TestHtmlUnitDriver driver, final StepWatch timer) {
		SeleniumTestUtil.redirects(driver, debug().isDebug(), timer);
	}

	protected void get(final TestHtmlUnitDriver driver, final StepWatch timer, final String url) {
		SeleniumTestUtil.get(driver, debug().isDebug(), timer, url);
	}

	protected void get(final TestHtmlUnitDriver driver, final StepWatch timer, final URL url) {
		SeleniumTestUtil.get(driver, debug().isDebug(), timer, url);
	}

	protected void getAndRedirect(final TestHtmlUnitDriver driver, final StepWatch timer, final String url) {
		SeleniumTestUtil.getAndRedirect(driver, debug().isDebug(), timer, url);
	}

	protected void getAndRedirect(final TestHtmlUnitDriver driver, final StepWatch timer, final URL url) {
		SeleniumTestUtil.getAndRedirect(driver, debug().isDebug(), timer, url);
	}

	protected void submit(final TestHtmlUnitDriver driver, final StepWatch timer, final WebElement button) {
		SeleniumTestUtil.submit(driver, debug().isDebug(), timer, button);
	}

	protected boolean hasCookie(final TestHtmlUnitDriver driver, final String name) {
		return SeleniumTestUtil.hasCookie(driver, name);
	}

	public static Cookie getCookie(final TestHtmlUnitDriver driver, final String name) {
		return SeleniumTestUtil.getCookie(driver, name);
	}

	public static String getCookieValue(final TestHtmlUnitDriver driver, final String name) {
		return SeleniumTestUtil.getCookieValue(driver, name);
	}

	protected void debugprint(final String...msgs) {
		SeleniumTestUtil.debugprint(debug().isDebug(), msgs);
	}

	protected void debugprint(final StepWatch timer, final String msg) {
		SeleniumTestUtil.debugprint(debug().isDebug(), timer, msg);
	}

	protected void debugprint(final TestHtmlUnitDriver driver, final StepWatch timer, final String url) {
		SeleniumTestUtil.debugprint(driver, debug().isDebug(), timer, url);
	}

	protected void debugprint(final TestHtmlUnitDriver driver, final StepWatch timer) {
		SeleniumTestUtil.debugprint(driver, debug().isDebug(), timer);
	}

	protected void printResponse(final TestHtmlUnitDriver driver) {
		SeleniumTestUtil.printResponse(driver, debug().isDebug());
	}

	protected void printRequestHeaders(final TestHtmlUnitDriver driver) {
		SeleniumTestUtil.printRequestHeaders(driver, debug().isDebug());
	}

	protected void printResponseHeaders(final TestHtmlUnitDriver driver) {
		SeleniumTestUtil.printResponseHeaders(driver, debug().isDebug());
	}

	protected void printCookies(final TestHtmlUnitDriver driver) {
		SeleniumTestUtil.printCookies(driver, debug().isDebug());
	}

	////////////////////////////////////////////////////////////////////////

	protected Response get(final TestFirefoxDriver driver, final StepWatch timer, final String url) {
		return SeleniumTestUtil.get(driver, debug().isDebug(), timer, url);
	}

	protected Response get(final TestFirefoxDriver driver, final StepWatch timer, final URL url) {
		return SeleniumTestUtil.get(driver, debug().isDebug(), timer, url);
	}

	protected Response submit(final TestFirefoxDriver driver, final StepWatch timer, final RemoteWebElement button) {
		return SeleniumTestUtil.submit(driver, debug().isDebug(), timer, button);
	}

	protected WebElement setSelection(
		final TestFirefoxDriver driver, final String fieldname, final Boolean selected) {
		return SeleniumTestUtil.setSelection(driver, fieldname, selected);
	}

	protected void debugprint(
		final FirefoxDriver driver, final StepWatch timer, final String url, final Response response) {
		SeleniumTestUtil.debugprint(driver, debug().isDebug(), timer, url, response);
	}

	////////////////////////////////////////////////////////////////////////

	protected WebElement setSelection(
		final SearchContext context, final String fieldname, final Boolean selected) {
		return SeleniumTestUtil.setSelection(context, fieldname, selected);
	}

	protected WebElement enterText(final SearchContext context, final String fieldname, final String text) {
		return SeleniumTestUtil.enterText(context, fieldname, text);
	}

	protected void checkTitle(final WebDriver driver, final String expected) {
		SeleniumTestUtil.checkTitle(driver, expected);
	}

	////////////////////////////////////////////////////////////////////////

	public static WebElement findById(final SearchContext context, final String id) {
		return SeleniumTestUtil.findById(context, id);
	}

	public static WebElement waitById(final SearchContext context, final String id) {
		return SeleniumTestUtil.waitById(context, id);
	}

	public static WebElement waitById(
		final int timeout, final int step, final SearchContext context, final String id) {
		return SeleniumTestUtil.waitById(timeout, step, context, id);
	}

	////////////////////////////////////////////////////////////////////////

	public static void findAndClick(final SearchContext context, final By by) {
		SeleniumTestUtil.findAndClick(context, by);
	}

	public static void waitAndClick(final SearchContext context, final By by) {
		SeleniumTestUtil.waitAndClick(context, by);
	}

	public static void waitAndClick(final int timeout, final int step, final SearchContext context, final By by) {
		SeleniumTestUtil.waitAndClick(timeout, step, context, by);
	}

	////////////////////////////////////////////////////////////////////////

	public static void findAndClick(
		final SearchContext context, final By by, final int expectedcount, final int clickindex) {
		SeleniumTestUtil.findAndClick(context, by, expectedcount, clickindex);
	}

	public static void waitAndClick(
		final SearchContext context, final By by, final int expectedcount, final int clickindex) {
		SeleniumTestUtil.waitAndClick(context, by, expectedcount, clickindex);
	}

	public static void waitAndClick(
		final int timeout,
		final int step,
		final SearchContext context,
		final By by,
		final int expectedcount,
		final int clickindex) {
		SeleniumTestUtil.waitAndClick(timeout, step, context, by, expectedcount, clickindex);
	}

	////////////////////////////////////////////////////////////////////////

	public static List<WebElement> findAndCount(final SearchContext context, final By by, final int expectedcount) {
		return SeleniumTestUtil.findAndCount(context, by, expectedcount);
	}

	public static List<WebElement> waitAndCount(final SearchContext context, final By by, final int expectedcount) {
		return SeleniumTestUtil.waitAndCount(context, by, expectedcount);
	}

	public static List<WebElement> waitAndCount(
		final int timeout, final int step, final SearchContext context, final By by, final int expectedcount) {
		return SeleniumTestUtil.waitAndCount(timeout, step, context, by, expectedcount);
	}

	////////////////////////////////////////////////////////////////////////

	public static List<WebElement> filterByText(final List<WebElement> input, final String regex) {
		return SeleniumTestUtil.filterByText(input, regex);
	}

	public static List<WebElement> filterByAttr(
		final List<WebElement> input, final String name, final String regex) {
		return SeleniumTestUtil.filterByAttr(input, name, regex);
	}

	////////////////////////////////////////////////////////////////////////

	protected void checkFeedbacks(final List<WebElement> feedbacks, final String...expects) {
		SeleniumTestUtil.checkFeedbacks(debug().isDebug(), feedbacks, expects);
	}

	protected WebElement enterText(final WebElement e, final String text) {
		return SeleniumTestUtil.enterText(e, text);
	}

	protected Cookie getCookie(final Set<Cookie> cookies, final String name) {
		return SeleniumTestUtil.getCookie(cookies, name);
	}

	protected String getCookieValue(final Set<Cookie> cookies, final String name) {
		return SeleniumTestUtil.getCookieValue(cookies, name);
	}

	protected void takeScreenshot(final TakesScreenshot driver, final File outfile) throws IOException {
		SeleniumTestUtil.takeScreenshot(driver, outfile);
	}

	protected void takeSnapshot(final WebDriver driver, final File htmlfile, final File pngfile) throws IOException {
		SeleniumTestUtil.takeSnapshot(debug().isDebug(), driver, htmlfile, pngfile);
	}

	////////////////////////////////////////////////////////////////////////

	protected ISeleniumFinder finder(final WebDriver driver) {
		return new SeleniumFinder(driver);
	}

	protected ISeleniumFinder finder(final WebElement elm) {
		return new SeleniumFinder(elm);
	}

	////////////////////////////////////////////////////////////////////////
}
