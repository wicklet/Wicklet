/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.test.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import sf.blacksun.util.IPredicate;
import test.util.TestUtil;

public class SeleniumFinder implements ISeleniumFinder, Iterable<WebElement> {

	////////////////////////////////////////////////////////////////////////

	final SearchContext context;
	List<WebElement> found;

	public SeleniumFinder(final WebDriver driver) {
		context = driver;
	}

	public SeleniumFinder(final WebElement elm) {
		context = elm;
		found = new ArrayList<WebElement>();
		found.add(elm);
	}

	@Override
	public ISeleniumFinder find(final By by) {
		found = context.findElements(by);
		return this;
	}

	@Override
	public ISeleniumFinder find(final By by, final int expected) {
		find(by).count(expected);
		return this;
	}

	@Override
	public ISeleniumFinder wait(final By by, final int expected) {
		TestUtil.assertWithinTimeout(
			TestUtil.TIMEOUT,
			TestUtil.STEP,
			new IPredicate() {
				@Override
				public boolean ok() {
					return context.findElements(by).size() == expected;
				}
			});
		found = context.findElements(by);
		return this;
	}

	@Override
	public ISeleniumFinder wait(final int timeout, final int step, final By by, final int expected) {
		TestUtil.assertWithinTimeout(
			timeout,
			step,
			new IPredicate() {
				@Override
				public boolean ok() {
					return context.findElements(by).size() == expected;
				}
			});
		found = context.findElements(by);
		return this;
	}

	@Override
	public ISeleniumFinder wait(final IPredicate predicate) {
		TestUtil.assertWithinTimeout(TestUtil.TIMEOUT, TestUtil.STEP, predicate);
		return this;
	}

	@Override
	public ISeleniumFinder wait(final int timeout, final int step, final IPredicate predicate) {
		TestUtil.assertWithinTimeout(timeout, step, predicate);
		return this;
	}

	@Override
	public ISeleniumFinder count(final int expected) {
		assertNotNull(found);
		assertEquals(expected, found.size());
		return this;
	}

	@Override
	public ISeleniumFinder click(final int index) {
		assertTrue(found != null && found.size() > index);
		get(index).click();
		return this;
	}

	@Override
	public ISeleniumFinder clear(final int index) {
		assertTrue(found != null && found.size() > index);
		get(index).clear();
		return this;
	}

	@Override
	public ISeleniumFinder enterText(final int index, final String text) {
		assertTrue(found != null && found.size() > index);
		get(index).clear();
		get(index).sendKeys(text);
		return this;
	}

	@Override
	public ISeleniumFinder finder(final int index) {
		return new SeleniumFinder(get(index));
	}

	@Override
	public ISeleniumFinder filterText(final String regex) {
		assertNotNull(found);
		found = SeleniumTestUtil.filterByText(found, regex);
		return this;
	}

	@Override
	public ISeleniumFinder filterAttr(final String name, final String regex) {
		assertNotNull(found);
		found = SeleniumTestUtil.filterByAttr(found, name, regex);
		return this;
	}

	@Override
	public WebElement get(final int index) {
		if (found != null && found.size() > index) {
			return found.get(index);
		}
		return null;
	}

	@Override
	public Iterator<WebElement> iterator() {
		return found.iterator();
	}

	////////////////////////////////////////////////////////////////////////
}
