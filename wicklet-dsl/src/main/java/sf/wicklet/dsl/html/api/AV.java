/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.api;

public class AV implements IAttribute {

	private static final long serialVersionUID = 1L;
	private final String name;
	private final String value;

	protected AV(final String name, final String value) {
		this.name = name.toString();
		this.value = value;
	}

	@Override
	public String aname() {
		return name;
	}

	@Override
	public String avalue() {
		return value;
	}

	@Override
	public void addTo(final IElement e) {
		e.a(this);
	}

	@Override
	public void addTo(final IAttributes a) {
		a.a(this);
	}

	@Override
	public String toString() {
		return avalue();
	}

	//////////////////////////////////////////////////////////////////////

	public static interface Align {
		String NAME = "align";
		IAttribute Left = new AV(NAME, "left");
		IAttribute Center = new AV(NAME, "center");
		IAttribute Right = new AV(NAME, "right");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface Button {
		public static interface Type {
			String NAME = "type";
			IAttribute Button = new AV(NAME, "button");
			IAttribute Submit = new AV(NAME, "submit");
			IAttribute Reset = new AV(NAME, "reset");
		}
	}

	//////////////////////////////////////////////////////////////////////

	public static interface Cell {
		public static interface Align {
			String NAME = "align";
			IAttribute Top = new AV(NAME, "top");
			IAttribute Middle = new AV(NAME, "middle");
			IAttribute Bottom = new AV(NAME, "bottom");
			IAttribute Baseline = new AV(NAME, "baseline");
		}
		public static interface VAlign {
			String NAME = "valign";
			IAttribute Left = new AV(NAME, "left");
			IAttribute Center = new AV(NAME, "center");
			IAttribute Right = new AV(NAME, "right");
			IAttribute Justify = new AV(NAME, "justify");
			IAttribute Char = new AV(NAME, "char");
		}
	}

	//////////////////////////////////////////////////////////////////////

	public static interface Event {
		String NAME = "event";
		IAttribute Onclick = new AV(NAME, "onclick");
		IAttribute Ondblclick = new AV(NAME, "ondblclick");
		IAttribute Onmousedown = new AV(NAME, "onmousedown");
		IAttribute Onmouseup = new AV(NAME, "onmouseup");
		IAttribute Onmouseover = new AV(NAME, "onmouseover");
		IAttribute Onmousemove = new AV(NAME, "onmousemove");
		IAttribute Onmouseout = new AV(NAME, "onmouseout");
		IAttribute Onkeypress = new AV(NAME, "onkeypress");
		IAttribute Onkeydown = new AV(NAME, "onkeydown");
		IAttribute Onkeyup = new AV(NAME, "onkeyup");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface HttpEquiv {
		String NAME = "http-equiv";
		IAttribute Refresh = new AV(NAME, "refresh");
		IAttribute Defaultstyle = new AV(NAME, "default-style");
		IAttribute Contenttype = new AV(NAME, "content-type");
	}

	//////////////////////////////////////////////////////////////////////

	public static interface Input {
		public static interface Type {
			String NAME = "type";
			IAttribute Text = new AV(NAME, "text");
			IAttribute Password = new AV(NAME, "password");
			IAttribute Checkbox = new AV(NAME, "checkbox");
			IAttribute Radio = new AV(NAME, "radio");
			IAttribute Submit = new AV(NAME, "submit");
			IAttribute Reset = new AV(NAME, "reset");
			IAttribute File = new AV(NAME, "file");
			IAttribute Hidden = new AV(NAME, "hidden");
			IAttribute Image = new AV(NAME, "image");
			IAttribute Button = new AV(NAME, "button");
			// HTML5
			IAttribute Datetime = new AV(NAME, "datetime");
			IAttribute Datetimelocal = new AV(NAME, "datetime-local");
			IAttribute Date = new AV(NAME, "date");
			IAttribute Month = new AV(NAME, "month");
			IAttribute Time = new AV(NAME, "time");
			IAttribute Week = new AV(NAME, "week");
			IAttribute Number = new AV(NAME, "number");
			IAttribute Range = new AV(NAME, "range");
			IAttribute Email = new AV(NAME, "email");
			IAttribute Url = new AV(NAME, "url");
			IAttribute Search = new AV(NAME, "search");
			IAttribute Tel = new AV(NAME, "tel");
			IAttribute Color = new AV(NAME, "color");
		}
	}

	//////////////////////////////////////////////////////////////////////
	// HTML5

	public static interface Command {
		public static interface Type {
			String NAME = "type";
			IAttribute Command = new AV(NAME, "command");
			IAttribute Radio = new AV(NAME, "radio");
			IAttribute Checkbox = new AV(NAME, "checkbox");
		}
	}

	//////////////////////////////////////////////////////////////////////
}
