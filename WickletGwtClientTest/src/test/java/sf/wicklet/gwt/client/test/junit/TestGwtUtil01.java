/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.test.junit;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sf.wicklet.gwt.client.util.GwtUrlUtil;

public class TestGwtUtil01 {

	@Test
	public void testGetWebappBaseUrl01() {
		assertEquals(
			"https://localhost:8080/test/", GwtUrlUtil.getWebbappUrl("https://localhost:8080/test", ""));
		assertEquals(
			"https://localhost:8080/test/", GwtUrlUtil.getWebbappUrl("https://localhost:8080/test/", ""));
		assertEquals(
			"https://localhost:8080/test/", GwtUrlUtil.getWebbappUrl("https://localhost:8080/test/a", ""));
		assertEquals(
			"https://localhost:8080/test/", GwtUrlUtil.getWebbappUrl("https://localhost:8080/test/?a", ""));
		assertEquals(
			"https://localhost:8080/test/",
			GwtUrlUtil.getWebbappUrl("https://localhost:8080/test/#123", ""));
		assertEquals(
			"https://localhost:8080/test/a/b",
			GwtUrlUtil.getWebbappUrl("https://localhost:8080/test/#123", "a/b"));
		assertEquals(
			"https://localhost:8080/test/a/b",
			GwtUrlUtil.getWebbappUrl("https://localhost:8080/test", "a/b"));
	}

	@Test
	public void testSetUrlPath01() {
		assertEquals(
			"https://localhost:8080/service",
			GwtUrlUtil.setUrlPath("https://localhost:8080/test/", "/service"));
		assertEquals(
			"https://localhost:8080/service/",
			GwtUrlUtil.setUrlPath("https://localhost:8080/test", "/service/"));
		assertEquals(
			"https://localhost:8080/service?a=b",
			GwtUrlUtil.setUrlPath("https://localhost:8080/test", "/service?a=b"));
		assertEquals(
			"https://localhost:8080/service#a",
			GwtUrlUtil.setUrlPath("https://localhost:8080/test/", "/service#a"));
		assertEquals(
			"https://localhost:8080/service?a=b#a",
			GwtUrlUtil.setUrlPath("https://localhost:8080/test", "/service?a=b#a"));
	}

	@Test
	public void testRemoveQueryFragment() {
		assertEquals(
			"https://localhost:8080/test", GwtUrlUtil.removeQueryFragment("https://localhost:8080/test"));
		assertEquals(
			"https://localhost:8080/service/",
			GwtUrlUtil.removeQueryFragment("https://localhost:8080/service/"));
		assertEquals(
			"https://localhost:8080/test/service",
			GwtUrlUtil.removeQueryFragment("https://localhost:8080/test/service?a=b"));
		assertEquals(
			"https://localhost:8080/test/service",
			GwtUrlUtil.removeQueryFragment("https://localhost:8080/test/service#a"));
		assertEquals(
			"https://localhost:8080/test/service/",
			GwtUrlUtil.removeQueryFragment("https://localhost:8080/test/service/?a=b#a"));
	}
}
