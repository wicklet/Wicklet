/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.test.support;

import java.util.Iterator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import sf.blacksun.util.IPredicate;

public interface ISeleniumFinder {

	public abstract ISeleniumFinder find(final By by);
	public abstract ISeleniumFinder find(final By by, final int expected);
	public abstract ISeleniumFinder wait(final By by, final int expected);
	public abstract ISeleniumFinder wait(int timeout, int step, final By by, final int expected);
	public abstract ISeleniumFinder wait(final IPredicate predicate);
	public abstract ISeleniumFinder wait(int timeout, int step, final IPredicate predicate);
	//
	public abstract ISeleniumFinder count(final int expected);
	public abstract ISeleniumFinder click(final int index);
	public abstract ISeleniumFinder clear(final int index);
	public abstract ISeleniumFinder enterText(final int index, final String text);
	public abstract ISeleniumFinder finder(final int index);
	//
	public abstract ISeleniumFinder filterText(final String regex);
	public abstract ISeleniumFinder filterAttr(final String name, final String regex);
	//
	public abstract WebElement get(final int index);
	public abstract Iterator<WebElement> iterator();
}
