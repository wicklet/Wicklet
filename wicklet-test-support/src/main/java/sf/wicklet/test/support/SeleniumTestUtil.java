/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.test.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.Response;
import sf.blacksun.util.FileUtil;
import sf.blacksun.util.IPredicate;
import sf.blacksun.util.StepWatch;
import sf.blacksun.util.struct.StructUtil;
import test.util.TestUtil;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.google.common.collect.ImmutableMap;

public class SeleniumTestUtil {

	public static void redirects(final TestHtmlUnitDriver driver, final boolean debug, final StepWatch timer) {
		for (String location; (location = driver.getWebResponse().getResponseHeaderValue("Location")) != null;) {
			if (debug) {
				System.out.println("## Redirect: " + location);
			}
			get(driver, debug, timer, location);
	}}

	public static void get(
		final TestHtmlUnitDriver driver, final boolean debug, final StepWatch timer, final String url) {
		driver.get(url);
		debugprint(driver, debug, timer, url);
	}

	public static void get(
		final TestHtmlUnitDriver driver, final boolean debug, final StepWatch timer, final URL url) {
		final String u = url.toString();
		driver.get(u);
		debugprint(driver, debug, timer, u);
	}

	public static void getAndRedirect(
		final TestHtmlUnitDriver driver, final boolean debug, final StepWatch timer, final String url) {
		get(driver, debug, timer, url);
		redirects(driver, debug, timer);
	}

	public static void getAndRedirect(
		final TestHtmlUnitDriver driver, final boolean debug, final StepWatch timer, final URL url) {
		get(driver, debug, timer, url);
		redirects(driver, debug, timer);
	}

	public static void submit(
		final TestHtmlUnitDriver driver, final boolean debug, final StepWatch timer, final WebElement button) {
		button.submit();
		if (debug) {
			debugprint(driver, debug, timer);
		}
		redirects(driver, debug, timer);
	}

	public static void debugprint(final boolean debug, final String...msgs) {
		if (debug) {
			for (final String msg: msgs) {
				System.out.println(msg);
	}}}

	public static void debugprint(final boolean debug, final StepWatch timer, final String msg) {
		if (debug) {
			System.out.println(timer.toString(msg));
	}}

	public static void debugprint(
		final TestHtmlUnitDriver driver, final boolean debug, final StepWatch timer, final String url) {
		if (debug) {
			System.out.println("###");
			System.out.println("### " + url);
			System.out.println("###");
			System.out.println(timer.toString("Page title is: " + driver.getTitle()));
			printResponse(driver, debug);
			printRequestHeaders(driver, debug);
			printResponseHeaders(driver, debug);
			printCookies(driver, debug);
	}}

	public static void debugprint(final TestHtmlUnitDriver driver, final boolean debug, final StepWatch timer) {
		debugprint(driver, debug, timer, driver.getCurrentUrl());
	}

	public static void printResponse(final TestHtmlUnitDriver driver, final boolean debug) {
		if (debug) {
			System.out.println(driver.getWebResponse().getContentAsString());
	}}

	public static void printRequestHeaders(final TestHtmlUnitDriver driver, final boolean debug) {
		if (debug) {
			final Map<String, String> headers = driver.getWebResponse().getWebRequest()
				.getAdditionalHeaders();
			System.out.println("### Request headers: " + headers.size());
			for (final Map.Entry<String, String> e: headers.entrySet()) {
				System.out.println(e.getKey() + "=" + e.getValue());
	}}}

	public static void printResponseHeaders(final TestHtmlUnitDriver driver, final boolean debug) {
		if (debug) {
			final List<NameValuePair> headers = driver.getWebResponse().getResponseHeaders();
			System.out.println("### Response headers: " + headers.size());
			for (final NameValuePair h: headers) {
				System.out.println(h.toString());
	}}}

	public static void printCookies(final TestHtmlUnitDriver driver, final boolean debug) {
		if (debug) {
			final Set<Cookie> cookies = driver.getWebClient().getCookieManager().getCookies();
			System.out.println("### Cookies: " + cookies.size());
			for (final Cookie c: cookies) {
				System.out.println(c.toString());
	}}}

	public static boolean hasCookie(final TestHtmlUnitDriver driver, final String name) {
		final Set<Cookie> cookies = driver.getWebClient().getCookieManager().getCookies();
		for (final Cookie c: cookies) {
			if (name.equals(c.getName())) {
				return true;
		}}
		return false;
	}

	public static Cookie getCookie(final TestHtmlUnitDriver driver, final String name) {
		return driver.getWebClient().getCookieManager().getCookie(name);
	}

	public static String getCookieValue(final TestHtmlUnitDriver driver, final String name) {
		final Cookie c = driver.getWebClient().getCookieManager().getCookie(name);
		return c == null ? null : c.getValue();
	}

	////////////////////////////////////////////////////////////////////////

	public static Response get(
		final TestFirefoxDriver driver, final boolean debug, final StepWatch timer, final String url) {
		final Response ret = driver.doGet(url);
		debugprint(driver, debug, timer, url, ret);
		return ret;
	}

	public static Response get(
		final TestFirefoxDriver driver, final boolean debug, final StepWatch timer, final URL url) {
		return get(driver, debug, timer, url.toString());
	}

	public static Response submit(
		final TestFirefoxDriver driver,
		final boolean debug,
		final StepWatch timer,
		final RemoteWebElement button) {
		final Response ret = driver.doSubmit(button);
		if (debug) {
			debugprint(driver, debug, timer, driver.getCurrentUrl(), ret);
		}
		return ret;
	}

	public static void takeScreenshot(final TakesScreenshot driver, final File outfile) throws IOException {
		final byte[] data = driver.getScreenshotAs(OutputType.BYTES);
		FileUtil.mkparent(outfile);
		FileUtil.writeFile(outfile, false, data);
	}

	public static void takeSnapshot(
		final boolean debug, final WebDriver driver, final File htmlfile, final File pngfile)
		throws IOException {
		if (debug) {
			final String text = driver.getPageSource();
			System.out.println(text);
			if (htmlfile != null) {
				FileUtil.writeFile(htmlfile, false, text);
			}
			if (pngfile != null) {
				takeScreenshot((TakesScreenshot)driver, pngfile);
	}}}

	public static void debugprint(
		final FirefoxDriver driver,
		final boolean debug,
		final StepWatch timer,
		final String url,
		final Response response) {
		if (debug) {
			System.out.println("###");
			System.out.println("### " + url);
			System.out.println("###");
			System.out.println(timer.toString("Page title is: " + driver.getTitle()));
			System.out.println(response.getValue());
			System.out.println("# sessionId: " + response.getSessionId());
			System.out.println("# status: " + response.getStatus());
	}}

	////////////////////////////////////////////////////////////////////////

	public static WebElement enterText(final SearchContext driver, final String fieldname, final String text) {
		final WebElement e = findAndCount(driver, By.name(fieldname), 1).get(0);
		if (text != null) {
			e.clear();
			e.sendKeys(text);
		}
		return e;
	}

	public static WebElement setSelection(
		final SearchContext driver, final String fieldname, final Boolean selected) {
		final WebElement e = findAndCount(driver, By.name(fieldname), 1).get(0);
		if (selected != null && e.isSelected() != selected.booleanValue()) {
			e.click();
		}
		return e;
	}

	////////////////////////////////////////////////////////////////////////

	public static WebElement findById(final SearchContext driver, final String id) {
		return findAndCount(driver, By.id(id), 1).get(0);
	}

	public static WebElement waitById(final SearchContext driver, final String id) {
		return waitAndCount(TestUtil.TIMEOUT, TestUtil.STEP, driver, By.id(id), 1).get(0);
	}

	public static WebElement waitById(
		final int timeout, final int step, final SearchContext driver, final String id) {
		return waitAndCount(timeout, step, driver, By.id(id), 1).get(0);
	}

	////////////////////////////////////////////////////////////////////////

	public static void findAndClick(final SearchContext context, final By by) {
		findAndCount(context, by, 1).get(0).click();
	}

	public static void waitAndClick(final SearchContext context, final By by) {
		waitAndCount(TestUtil.TIMEOUT, TestUtil.STEP, context, by, 1).get(0).click();
	}

	public static void waitAndClick(final int timeout, final int step, final SearchContext context, final By by) {
		waitAndCount(timeout, step, context, by, 1).get(0).click();
	}

	////////////////////////////////////////////////////////////////////////

	public static void findAndClick(
		final SearchContext context, final By by, final int expectedcount, final int clickindex) {
		findAndCount(context, by, expectedcount).get(clickindex).click();
	}

	public static void waitAndClick(
		final SearchContext context, final By by, final int expectedcount, final int clickindex) {
		waitAndCount(TestUtil.TIMEOUT, TestUtil.STEP, context, by, expectedcount).get(clickindex).click();
	}

	public static void waitAndClick(
		final int timeout,
		final int step,
		final SearchContext context,
		final By by,
		final int expectedcount,
		final int clickindex) {
		waitAndCount(timeout, step, context, by, expectedcount).get(clickindex).click();
	}

	////////////////////////////////////////////////////////////////////////

	public static List<WebElement> findAndCount(final SearchContext context, final By by, final int expectedcount) {
		final List<WebElement> ret = context.findElements(by);
		assertEquals(expectedcount, ret.size());
		return ret;
	}

	public static List<WebElement> waitAndCount(final SearchContext context, final By by, final int expectedcount) {
		return waitAndCount(TestUtil.TIMEOUT, TestUtil.STEP, context, by, expectedcount);
	}

	public static List<WebElement> waitAndCount(
		final int timeout, final int step, final SearchContext context, final By by, final int expectedcount) {
		TestUtil.assertWithinTimeout(
			timeout,
			step,
			new IPredicate() {
				@Override
				public boolean ok() {
					return context.findElements(by).size() == expectedcount;
				}
			});
		final List<WebElement> a = context.findElements(by);
		assertEquals(expectedcount, a.size());
		return a;
	}

	////////////////////////////////////////////////////////////////////////

	/** @return Elements with text that match the given java regex. */
	public static List<WebElement> filterByText(final List<WebElement> input, final String includes) {
		final List<WebElement> ret = new ArrayList<WebElement>();
		final Pattern pat = Pattern.compile(includes);
		for (final Iterator<WebElement> it = input.iterator(); it.hasNext();) {
			final WebElement e = it.next();
			final String text = e.getText();
			if (text != null && pat.matcher(text).matches()) {
				ret.add(e);
		}}
		return ret;
	}

	/** @return Elements with text that match the given java regex. */
	public static List<WebElement> filterByAttr(
		final List<WebElement> input, final String name, final String includes) {
		final List<WebElement> ret = new ArrayList<WebElement>();
		final Pattern pat = Pattern.compile(includes);
		for (final Iterator<WebElement> it = input.iterator(); it.hasNext();) {
			final WebElement e = it.next();
			final String text = e.getAttribute(name);
			if (text != null && pat.matcher(text).matches()) {
				ret.add(e);
		}}
		return ret;
	}

	////////////////////////////////////////////////////////////////////////

	public static void checkTitle(final WebDriver driver, final String expected) {
		assertEquals(expected, driver.getTitle());
	}

	public static void checkTitle(final boolean debug, final WebDriver driver, final String expected) {
		final String title = driver.getTitle();
		if (debug) {
			System.out.println("DEBUB: Page title is: " + title);
		}
		assertEquals(expected, title);
	}

	public static void checkFeedbacks(
		final boolean debug, final List<WebElement> feedbacks, final String...expects) {
		if (debug) {
			System.out.println("### Feedbacks: " + feedbacks.size());
			for (final WebElement e: feedbacks) {
				System.out.println(e.getText());
		}}
		final Set<String> expected = StructUtil.toTreeSet(expects);
		for (final WebElement e: feedbacks) {
			final String text = e.getText();
			assertTrue(text, expected.remove(text));
		}
		if (debug) {
			for (final String s: expected) {
				System.out.println(s);
		}}
		assertEquals(0, expected.size());
	}

	public static WebElement setSelection(
		final TestFirefoxDriver driver, final String fieldname, final Boolean selected) {
		final WebElement e = driver.findElementsByName(fieldname).get(0);
		if (selected != null && e.isSelected() != selected.booleanValue()) {
			e.click();
		}
		return e;
	}

	public static WebElement enterText(final WebElement e, final String text) {
		if (text != null) {
			e.clear();
			e.sendKeys(text);
		}
		return e;
	}

	public static Cookie getCookie(final Set<Cookie> cookies, final String name) {
		for (final Cookie c: cookies) {
			if (name.equals(c.getName())) {
				return c;
		}}
		return null;
	}

	public static String getCookieValue(final Set<Cookie> cookies, final String name) {
		for (final Cookie c: cookies) {
			if (name.equals(c.getName())) {
				return c.getValue();
		}}
		return null;
	}

	////////////////////////////////////////////////////////////////////////

	public static class TestHtmlUnitDriver extends HtmlUnitDriver {
		@Override
		public WebClient getWebClient() {
			return super.getWebClient();
		}
		public WebResponse getWebResponse() {
			return getWebClient().getCurrentWindow().getEnclosedPage().getWebResponse();
		}
		@Override
		public void get(final URL url) {
			super.get(url);
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class TestFirefoxDriver extends FirefoxDriver {
		public TestFirefoxDriver(final FirefoxProfile profile) {
			super(profile);
		}
		public Response doGet(final String url) {
			return execute(DriverCommand.GET, ImmutableMap.of("url", url));
		}
		public Response doSubmit(final RemoteWebElement e) {
			return execute(DriverCommand.SUBMIT_ELEMENT, ImmutableMap.of("id", e.getId()));
		}
	}

	////////////////////////////////////////////////////////////////////////
}
