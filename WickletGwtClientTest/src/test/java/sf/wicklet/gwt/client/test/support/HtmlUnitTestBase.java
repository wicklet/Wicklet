/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.test.support;

import java.io.File;
import java.util.List;
import org.openqa.selenium.WebElement;
import sf.blacksun.util.FileUtil;
import sf.wicklet.test.support.SeleniumTestBase;

public abstract class HtmlUnitTestBase extends SeleniumTestBase {

	////////////////////////////////////////////////////////////////////////

	public static final String VERSION = "1-SNAPSHOT";
	public static final String WEBAPP = "ROOT";
	public static final String BASEURL = "http://localhost:8080/";
	// public static final String BASEURL = "http://localhost:8080/" + WEBAPP + "/";
	protected static final File optDir = FileUtil.afile("../opt");
	protected static final File firefoxProfileDir = new File(optDir, "firefox/7x16slsr.default");
	protected static final File tomcatHome = new File(optDir, "tomcat7");
	protected static final File tomcatWebappsDir = new File(tomcatHome, "webapps");
	protected static final File logDir = FileUtil.mkdirs(
		FileUtil.aformat("logs/%1$tY%1$tm%1$td", System.currentTimeMillis()));

	////////////////////////////////////////////////////////////////////////

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
