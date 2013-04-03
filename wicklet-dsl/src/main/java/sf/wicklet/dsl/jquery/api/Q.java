/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.jquery.api;

public class Q {

	public enum Position {
		Bottom("bottom"),
		Center("center"),
		CenterBottom("center bottom"),
		CenterCenter("center center"),
		CenterTop("center top"),
		Left("left"),
		LeftBottom("left bottom"),
		LeftCenter("left center"),
		LeftTop("left top"),
		RightBottom("right bottom"),
		RightCenter("right center"),
		RightTop("right top"),
		Top("top"),
		//
		;
		private final String text;
		Position(final String text) {
			this.text = text;
		}
		@Override
		public String toString() {
			return text;
		}
	}
}
