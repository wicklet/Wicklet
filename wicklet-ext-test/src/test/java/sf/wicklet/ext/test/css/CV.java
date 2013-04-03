/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.test.css;

public enum CV {
	topBG("#cd5555"), topFG("#fff"), topEm("#fff"), //
	searchInput("#aaa"), searchInputFocus("black"), searchButtonBG("#aaa"), //
	leftBG("#ffa500"),
	accordionHeaderBG("#000"), accordionHeaderFG("#fff"),
	indexBG("#9acd32"), newsBG("#5f9ea0"),
	rightBG("#fff"),
	//
	;
	private String value;
	private CV(final String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}
}
