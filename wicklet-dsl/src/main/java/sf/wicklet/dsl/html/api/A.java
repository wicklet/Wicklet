/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.api;

import static sf.wicklet.dsl.html.api.TAG.A;
import static sf.wicklet.dsl.html.api.TAG.ABBR;
import static sf.wicklet.dsl.html.api.TAG.APPLET;
import static sf.wicklet.dsl.html.api.TAG.AREA;
import static sf.wicklet.dsl.html.api.TAG.AUDIO;
import static sf.wicklet.dsl.html.api.TAG.BASE;
import static sf.wicklet.dsl.html.api.TAG.BASEFONT;
import static sf.wicklet.dsl.html.api.TAG.BDO;
import static sf.wicklet.dsl.html.api.TAG.BLOCKQUOTE;
import static sf.wicklet.dsl.html.api.TAG.BODY;
import static sf.wicklet.dsl.html.api.TAG.BR;
import static sf.wicklet.dsl.html.api.TAG.BUTTON;
import static sf.wicklet.dsl.html.api.TAG.CANVAS;
import static sf.wicklet.dsl.html.api.TAG.CAPTION;
import static sf.wicklet.dsl.html.api.TAG.COL;
import static sf.wicklet.dsl.html.api.TAG.COLGROUP;
import static sf.wicklet.dsl.html.api.TAG.COMMAND;
import static sf.wicklet.dsl.html.api.TAG.DEL;
import static sf.wicklet.dsl.html.api.TAG.DETAILS;
import static sf.wicklet.dsl.html.api.TAG.DFN;
import static sf.wicklet.dsl.html.api.TAG.DIR;
import static sf.wicklet.dsl.html.api.TAG.DIV;
import static sf.wicklet.dsl.html.api.TAG.DL;
import static sf.wicklet.dsl.html.api.TAG.EMBED;
import static sf.wicklet.dsl.html.api.TAG.Empty;
import static sf.wicklet.dsl.html.api.TAG.FIELDSET;
import static sf.wicklet.dsl.html.api.TAG.FONT;
import static sf.wicklet.dsl.html.api.TAG.FORM;
import static sf.wicklet.dsl.html.api.TAG.FRAME;
import static sf.wicklet.dsl.html.api.TAG.FRAMESET;
import static sf.wicklet.dsl.html.api.TAG.H1;
import static sf.wicklet.dsl.html.api.TAG.H2;
import static sf.wicklet.dsl.html.api.TAG.H3;
import static sf.wicklet.dsl.html.api.TAG.H4;
import static sf.wicklet.dsl.html.api.TAG.H5;
import static sf.wicklet.dsl.html.api.TAG.H6;
import static sf.wicklet.dsl.html.api.TAG.HEAD;
import static sf.wicklet.dsl.html.api.TAG.HR;
import static sf.wicklet.dsl.html.api.TAG.HTML;
import static sf.wicklet.dsl.html.api.TAG.IFRAME;
import static sf.wicklet.dsl.html.api.TAG.IMG;
import static sf.wicklet.dsl.html.api.TAG.INPUT;
import static sf.wicklet.dsl.html.api.TAG.INS;
import static sf.wicklet.dsl.html.api.TAG.ISINDEX;
import static sf.wicklet.dsl.html.api.TAG.KEYGEN;
import static sf.wicklet.dsl.html.api.TAG.LABEL;
import static sf.wicklet.dsl.html.api.TAG.LEGEND;
import static sf.wicklet.dsl.html.api.TAG.LI;
import static sf.wicklet.dsl.html.api.TAG.LINK;
import static sf.wicklet.dsl.html.api.TAG.MAP;
import static sf.wicklet.dsl.html.api.TAG.MENU;
import static sf.wicklet.dsl.html.api.TAG.META;
import static sf.wicklet.dsl.html.api.TAG.METER;
import static sf.wicklet.dsl.html.api.TAG.OBJECT;
import static sf.wicklet.dsl.html.api.TAG.OL;
import static sf.wicklet.dsl.html.api.TAG.OPTGROUP;
import static sf.wicklet.dsl.html.api.TAG.OPTION;
import static sf.wicklet.dsl.html.api.TAG.OUTPUT;
import static sf.wicklet.dsl.html.api.TAG.P;
import static sf.wicklet.dsl.html.api.TAG.PARAM;
import static sf.wicklet.dsl.html.api.TAG.PRE;
import static sf.wicklet.dsl.html.api.TAG.PROGRESS;
import static sf.wicklet.dsl.html.api.TAG.Q;
import static sf.wicklet.dsl.html.api.TAG.SCRIPT;
import static sf.wicklet.dsl.html.api.TAG.SELECT;
import static sf.wicklet.dsl.html.api.TAG.SOURCE;
import static sf.wicklet.dsl.html.api.TAG.STYLE;
import static sf.wicklet.dsl.html.api.TAG.TABLE;
import static sf.wicklet.dsl.html.api.TAG.TBODY;
import static sf.wicklet.dsl.html.api.TAG.TD;
import static sf.wicklet.dsl.html.api.TAG.TEXTAREA;
import static sf.wicklet.dsl.html.api.TAG.TFOOT;
import static sf.wicklet.dsl.html.api.TAG.TH;
import static sf.wicklet.dsl.html.api.TAG.THEAD;
import static sf.wicklet.dsl.html.api.TAG.TITLE;
import static sf.wicklet.dsl.html.api.TAG.TR;
import static sf.wicklet.dsl.html.api.TAG.TRACK;
import static sf.wicklet.dsl.html.api.TAG.UL;
import static sf.wicklet.dsl.html.api.TAG.VIDEO;
import java.util.Set;
import java.util.TreeSet;

/** TODO: Check the valid element list, in particular HTML5 elements, for the HTML4 attributes. */
public enum A {
	// HTML5
	//Accept("accept", INPUT),
	//AcceptCharset("accept-charset", FORM),
	//Accesskey("accesskey"),
	//Action("action", FORM),
	//Alt("alt", AREA, IMG, INPUT),
	Async("async", SCRIPT),
	Autocomplete("autocomplete", FORM, INPUT),
	Autofocus("autofocus", BUTTON, INPUT, KEYGEN, SELECT, TEXTAREA),
	Autoplay("autoplay", AUDIO, VIDEO),
	//Border("border", TABLE),
	Challenge("challenge", KEYGEN),
	//Charset("charset", META),
	//Charset("charset", SCRIPT),
	//Checked("checked", COMMAND, INPUT),
	//Cite("cite", BLOCKQUOTE, DEL, INS, Q),
	//Class("class"),
	//Cols("cols", TEXTAREA),
	//Colspan("colspan", TD, TH),
	Command("command", COMMAND),
	//Content("content", META),
	Contenteditable("contenteditable"),
	Contextmenu("contextmenu"),
	Controls("controls", AUDIO, VIDEO),
	//Coords("coords", AREA),
	Crossorigin("crossorigin", AUDIO, IMG, VIDEO),
	//Data("data", OBJECT),
	//Datetime("datetime", DEL, INS),
	//Datetime("datetime", TIME),
	Default("default", TRACK),
	//Defer("defer", SCRIPT),
	//Dir("dir"),
	Dirname("dirname", INPUT, TEXTAREA),
	//Disabled("disabled", BUTTON, COMMAND, FIELDSET, INPUT, KEYGEN, OPTGROUP, OPTION, SELECT, TEXTAREA),
	Draggable("draggable"),
	Dropzone("dropzone"),
	//Enctype("enctype", FORM),
	//For("for", LABEL),
	//For("for", OUTPUT),
	Form("form", BUTTON, FIELDSET, INPUT, KEYGEN, LABEL, OBJECT, OUTPUT, SELECT, TEXTAREA),
	Formaction("formaction", BUTTON, INPUT),
	Formenctype("formenctype", BUTTON, INPUT),
	Formmethod("formmethod", BUTTON, INPUT),
	Formnovalidate("formnovalidate", BUTTON, INPUT),
	Formtarget("formtarget", BUTTON, INPUT),
	//Headers("headers", TD, TH),
	//Height("height", CANVAS, EMBED, IFRAME, IMG, INPUT, OBJECT, VIDEO),
	Hidden("hidden"),
	High("high", METER),
	//Href("href", A, AREA),
	//Href("href", LINK),
	//Href("href", BASE),
	//Hreflang("hreflang", A, AREA, LINK),
	//Http-equiv("http-equiv", META),
	Icon("icon", COMMAND),
	//Id("id"),
	//Ismap("ismap", IMG),
	Keytype("keytype", KEYGEN),
	Kind("kind", TRACK),
	//Label("label", COMMAND, MENU, OPTGROUP, OPTION, TRACK),
	//Lang("lang"),
	List("list", INPUT),
	Loop("loop", AUDIO, VIDEO),
	Low("low", METER),
	Manifest("manifest"),
	Max("max", INPUT),
	//Max("max", METER, PROGRESS),
	//Maxlength("maxlength", INPUT, TEXTAREA),
	//Media("media", A, AREA, LINK, SOURCE, STYLE),
	Mediagroup("mediagroup", AUDIO, VIDEO),
	//Method("method", FORM),
	Min("min", INPUT),
	//Min("min", METER),
	//Multiple("multiple", INPUT, SELECT),
	Muted("muted", AUDIO, VIDEO),
	//Name("name", BUTTON, FIELDSET, INPUT, KEYGEN, OUTPUT, SELECT, TEXTAREA),
	//Name("name", FORM),
	//Name("name", IFRAME, OBJECT),
	//Name("name", MAP),
	//Name("name", META),
	//Name("name", PARAM),
	Novalidate("novalidate", FORM),
	Open("open", DETAILS),
	//Open("open", DIALOG),
	Optimum("optimum", METER),
	Pattern("pattern", INPUT),
	Placeholder("placeholder", INPUT, TEXTAREA),
	Poster("poster", VIDEO),
	Preload("preload", AUDIO, VIDEO),
	Radiogroup("radiogroup", COMMAND),
	//Readonly("readonly", INPUT, TEXTAREA),
	//Rel("rel", A, AREA, LINK),
	Required("required", INPUT, SELECT, TEXTAREA),
	Reversed("reversed", OL),
	//Rows("rows", TEXTAREA),
	//Rowspan("rowspan", TD, TH),
	Sandbox("sandbox", IFRAME),
	Spellcheck("spellcheck"),
	//Scope("scope", TH),
	Scoped("scoped", STYLE),
	Seamless("seamless", IFRAME),
	//Selected("selected", OPTION),
	//Shape("shape", AREA),
	//Size("size", INPUT, SELECT),
	Sizes("sizes", LINK),
	//Span("span", COL, COLGROUP),
	//Src("src", AUDIO, EMBED, IFRAME, IMG, INPUT, SCRIPT, SOURCE, TRACK, VIDEO),
	Srcdoc("srcdoc", IFRAME),
	Srclang("srclang", TRACK),
	//Start("start", OL),
	Step("step", INPUT),
	//Style("style"),
	//Tabindex("tabindex"),
	//Target("target", A, AREA),
	//Target("target", BASE),
	//Target("target", FORM),
	//Title("title"),
	//Title("title", ABBR, DFN),
	//Title("title", COMMAND),
	//Title("title", LINK),
	//Title("title", LINK, STYLE),
	Translate("translate"),
	// Type("type", A, AREA, LINK),
	//Type("type", BUTTON),
	//Type("type", COMMAND),
	//Type("type", EMBED, OBJECT, SCRIPT, SOURCE, STYLE),
	//Type("type", INPUT),
	//Type("type", MENU),
	Typemustmatch("typemustmatch", OBJECT),
	//Usemap("usemap", IMG, OBJECT),
	//Value("value", BUTTON, OPTION),
	//Value("value", DATA),
	//Value("value", INPUT),
	//Value("value", LI),
	//Value("value", METER, PROGRESS),
	//Value("value", PARAM),
	//Width("width", CANVAS, EMBED, IFRAME, IMG, INPUT, OBJECT, VIDEO),
	Wrap("wrap", TEXTAREA),
	Onabort("onabort"),
	Onafterprint("onafterprint", BODY),
	Onbeforeprint("onbeforeprint", BODY),
	Onbeforeunload("onbeforeunload", BODY),
	//Onblur("onblur", BODY),
	//Onblur("onblur"),
	Oncancel("oncancel"),
	Oncanplay("oncanplay"),
	Oncanplaythrough("oncanplaythrough"),
	//Onchange("onchange"),
	//Onclick("onclick"),
	Onclose("onclose"),
	Oncontextmenu("oncontextmenu"),
	Oncuechange("oncuechange"),
	//Ondblclick("ondblclick"),
	Ondrag("ondrag"),
	Ondragend("ondragend"),
	Ondragenter("ondragenter"),
	Ondragleave("ondragleave"),
	Ondragover("ondragover"),
	Ondragstart("ondragstart"),
	Ondrop("ondrop"),
	Ondurationchange("ondurationchange"),
	Onemptied("onemptied"),
	Onended("onended"),
	Onerror("onerror", BODY),
	//Onerror("onerror"),
	//Onfocus("onfocus", BODY),
	//Onfocus("onfocus"),
	Onhashchange("onhashchange", BODY),
	Oninput("oninput"),
	Oninvalid("oninvalid"),
	//Onkeydown("onkeydown"),
	//Onkeypress("onkeypress"),
	//Onkeyup("onkeyup"),
	//Onload("onload", BODY),
	//Onload("onload"),
	Onloadeddata("onloadeddata"),
	Onloadedmetadata("onloadedmetadata"),
	Onloadstart("onloadstart"),
	Onmessage("onmessage", BODY),
	//Onmousedown("onmousedown"),
	//Onmousemove("onmousemove"),
	//Onmouseout("onmouseout"),
	//Onmouseover("onmouseover"),
	//Onmouseup("onmouseup"),
	Onmousewheel("onmousewheel"),
	Onoffline("onoffline", BODY),
	Ononline("ononline", BODY),
	Onpagehide("onpagehide", BODY),
	Onpageshow("onpageshow", BODY),
	Onpause("onpause"),
	Onplay("onplay"),
	Onplaying("onplaying"),
	Onpopstate("onpopstate", BODY),
	Onprogress("onprogress"),
	Onratechange("onratechange"),
	//Onreset("onreset"),
	Onresize("onresize", BODY),
	Onscroll("onscroll", BODY),
	//Onscroll("onscroll"),
	Onseeked("onseeked"),
	Onseeking("onseeking"),
	//Onselect("onselect"),
	Onshow("onshow"),
	Onstalled("onstalled"),
	Onstorage("onstorage", BODY),
	//Onsubmit("onsubmit"),
	Onsuspend("onsuspend"),
	Ontimeupdate("ontimeupdate"),
	//Onunload("onunload", BODY),
	Onvolumechange("onvolumechange"),
	Onwaiting("onwaiting"),
	// HTML4
	Abbr("abbr", TD, TH),
	AcceptCharset("accept-charset", FORM),
	Accept("accept", INPUT),
	Accesskey("accesskey", A, AREA, BUTTON, INPUT, LABEL, LEGEND, TEXTAREA),
	Action("action", FORM),
	Align(
		"align",
		CAPTION,
		APPLET,
		IFRAME,
		IMG,
		INPUT,
		OBJECT,
		LEGEND,
		TABLE,
		HR,
		DIV,
		H1,
		H2,
		H3,
		H4,
		H5,
		H6,
		P,
		COL,
		COLGROUP,
		TBODY,
		TD,
		TFOOT,
		TH,
		THEAD,
		TR),
	Alink("alink", BODY),
	Alt("alt", APPLET, AREA, IMG, INPUT),
	Archive("archive", OBJECT, APPLET),
	Axis("axis", TD, TH),
	Background("background", BODY),
	Bgcolor("bgcolor", TABLE, TR, TD, TH, BODY),
	Border("border", IMG, OBJECT, TABLE),
	Cellpadding("cellpadding", TABLE),
	Cellspacing("cellspacing", TABLE),
	Char("char", COL, COLGROUP, TBODY, TD, TFOOT, TH, THEAD, TR),
	Charoff("charoff", COL, COLGROUP, TBODY, TD, TFOOT, TH, THEAD, TR),
	Charset(
		"charset",
		A,
		LINK,
		SCRIPT,
		// HTML5
		META),
	Checked("checked", INPUT),
	Cite("cite", BLOCKQUOTE, Q, DEL, INS),
	Class("class", Empty, BASE, BASEFONT, HEAD, HTML, META, PARAM, SCRIPT, STYLE, TITLE),
	Classid("classid", OBJECT),
	Clear("clear", BR),
	Code("code", APPLET),
	Codebase("codebase", OBJECT, APPLET),
	Codetype("codetype", OBJECT),
	Color("color", BASEFONT, FONT),
	Cols("cols", FRAMESET, TEXTAREA),
	Colspan("colspan", TD, TH),
	Compact("compact", DIR, MENU, DL, OL, UL),
	Content("content", META),
	Coords("coords", AREA, A),
	Data("data", OBJECT),
	Datetime("datetime", DEL, INS),
	Declare("declare", OBJECT),
	Defer("defer", SCRIPT),
	Dir("dir", Empty, APPLET, BASE, BASEFONT, BR, FRAME, FRAMESET, HR, IFRAME, PARAM, SCRIPT),
	Disabled("disabled", BUTTON, INPUT, OPTGROUP, OPTION, SELECT, TEXTAREA),
	Enctype("enctype", FORM),
	Face("face", BASEFONT, FONT),
	For(
		"for",
		LABEL,
		// HTML5
		OUTPUT),
	Frame("frame", TABLE),
	Frameborder("frameborder", FRAME, IFRAME),
	Headers("headers", TD, TH),
	Height(
		"height",
		IFRAME,
		IMG,
		OBJECT,
		APPLET,
		TD,
		TH,
		// HTML5
		EMBED,
		INPUT,
		VIDEO),
	Href("href", A, AREA, LINK, BASE),
	Hreflang("hreflang", A, LINK),
	Hspace("hspace", APPLET, IMG, OBJECT),
	HttpEquiv("http-equiv", META),
	Id("id", Empty, BASE, HEAD, HTML, META, SCRIPT, STYLE, TITLE),
	Ismap("ismap", IMG),
	Label(
		"label",
		OPTION,
		OPTGROUP,
		// HTML5
		COMMAND,
		MENU,
		TRACK),
	Lang("lang", Empty, APPLET, BASE, BASEFONT, BR, FRAME, FRAMESET, HR, IFRAME, PARAM, SCRIPT),
	Language("language", SCRIPT),
	Link("link", BODY),
	Longdesc("longdesc", IMG, FRAME, IFRAME),
	Marginheight("marginheight", FRAME, IFRAME),
	Marginwidth("marginwidth", FRAME, IFRAME),
	Maxlength("maxlength", INPUT),
	Media("media", STYLE, LINK, /* HTML5 */ A, AREA, SOURCE),
	Method("method", FORM),
	Multiple("multiple", SELECT),
	Name(
		"name",
		BUTTON,
		TEXTAREA,
		APPLET,
		SELECT,
		FRAME,
		IFRAME,
		A,
		INPUT,
		OBJECT,
		MAP,
		PARAM,
		META,
		// HTML5
		FIELDSET,
		KEYGEN,
		OUTPUT,
		FORM),
	Nohref("nohref", AREA),
	Noresize("noresize", FRAME),
	Noshade("noshade", HR),
	Nowrap("nowrap", TD, TH),
	Object("object", APPLET),
	Onblur("onblur", A, AREA, BUTTON, INPUT, LABEL, SELECT, TEXTAREA),
	Onchange("onchange", INPUT, SELECT, TEXTAREA),
	Onclick(
		"onclick",
		Empty,
		APPLET,
		BASE,
		BASEFONT,
		BDO,
		BR,
		FONT,
		FRAME,
		FRAMESET,
		HEAD,
		HTML,
		IFRAME,
		ISINDEX,
		META,
		PARAM,
		SCRIPT,
		STYLE,
		TITLE),
	Ondblclick(
		"ondblclick",
		Empty,
		APPLET,
		BASE,
		BASEFONT,
		BDO,
		BR,
		FONT,
		FRAME,
		FRAMESET,
		HEAD,
		HTML,
		IFRAME,
		ISINDEX,
		META,
		PARAM,
		SCRIPT,
		STYLE,
		TITLE),
	Onfocus("onfocus", A, AREA, BUTTON, INPUT, LABEL, SELECT, TEXTAREA),
	Onkeydown(
		"onkeydown",
		Empty,
		APPLET,
		BASE,
		BASEFONT,
		BDO,
		BR,
		FONT,
		FRAME,
		FRAMESET,
		HEAD,
		HTML,
		IFRAME,
		ISINDEX,
		META,
		PARAM,
		SCRIPT,
		STYLE,
		TITLE),
	Onkeypress(
		"onkeypress",
		Empty,
		APPLET,
		BASE,
		BASEFONT,
		BDO,
		BR,
		FONT,
		FRAME,
		FRAMESET,
		HEAD,
		HTML,
		IFRAME,
		ISINDEX,
		META,
		PARAM,
		SCRIPT,
		STYLE,
		TITLE),
	Onkeyup(
		"onkeyup",
		Empty,
		APPLET,
		BASE,
		BASEFONT,
		BDO,
		BR,
		FONT,
		FRAME,
		FRAMESET,
		HEAD,
		HTML,
		IFRAME,
		ISINDEX,
		META,
		PARAM,
		SCRIPT,
		STYLE,
		TITLE),
	Onload("onload", FRAMESET, BODY),
	Onmousedown(
		"onmousedown",
		Empty,
		APPLET,
		BASE,
		BASEFONT,
		BDO,
		BR,
		FONT,
		FRAME,
		FRAMESET,
		HEAD,
		HTML,
		IFRAME,
		ISINDEX,
		META,
		PARAM,
		SCRIPT,
		STYLE,
		TITLE),
	Onmousemove(
		"onmousemove",
		Empty,
		APPLET,
		BASE,
		BASEFONT,
		BDO,
		BR,
		FONT,
		FRAME,
		FRAMESET,
		HEAD,
		HTML,
		IFRAME,
		ISINDEX,
		META,
		PARAM,
		SCRIPT,
		STYLE,
		TITLE),
	Onmouseout(
		"onmouseout",
		Empty,
		APPLET,
		BASE,
		BASEFONT,
		BDO,
		BR,
		FONT,
		FRAME,
		FRAMESET,
		HEAD,
		HTML,
		IFRAME,
		ISINDEX,
		META,
		PARAM,
		SCRIPT,
		STYLE,
		TITLE),
	Onmouseover(
		"onmouseover",
		Empty,
		APPLET,
		BASE,
		BASEFONT,
		BDO,
		BR,
		FONT,
		FRAME,
		FRAMESET,
		HEAD,
		HTML,
		IFRAME,
		ISINDEX,
		META,
		PARAM,
		SCRIPT,
		STYLE,
		TITLE),
	Onmouseup(
		"onmouseup",
		Empty,
		APPLET,
		BASE,
		BASEFONT,
		BDO,
		BR,
		FONT,
		FRAME,
		FRAMESET,
		HEAD,
		HTML,
		IFRAME,
		ISINDEX,
		META,
		PARAM,
		SCRIPT,
		STYLE,
		TITLE),
	Onreset("onreset", FORM),
	Onselect("onselect", INPUT, TEXTAREA),
	Onsubmit("onsubmit", FORM),
	Onunload("onunload", FRAMESET, BODY),
	Profile("profile", HEAD),
	Prompt("prompt", ISINDEX),
	Readonly("readonly", TEXTAREA, INPUT),
	Rel("rel", A, LINK),
	Rev("rev", A, LINK),
	Rows("rows", FRAMESET, TEXTAREA),
	Rowspan("rowspan", TD, TH),
	Rules("rules", TABLE),
	Scheme("scheme", META),
	Scope("scope", TD, TH),
	Scrolling("scrolling", FRAME, IFRAME),
	Selected("selected", OPTION),
	Shape("shape", AREA, A),
	Size("size", HR, FONT, INPUT, BASEFONT, SELECT),
	Span("span", COL, COLGROUP),
	Src("src", SCRIPT, INPUT, FRAME, IFRAME, IMG),
	Standby("standby", OBJECT),
	Start("start", OL),
	Style("style", Empty, BASE, BASEFONT, HEAD, HTML, META, PARAM, SCRIPT, STYLE, TITLE),
	Summary("summary", TABLE),
	Tabindex("tabindex", A, AREA, BUTTON, INPUT, OBJECT, SELECT, TEXTAREA),
	Target("target", A, AREA, BASE, FORM, LINK),
	Text("text", BODY),
	Title(
		"title",
		Empty,
		BASE,
		BASEFONT,
		HEAD,
		HTML,
		META,
		PARAM,
		SCRIPT,
		TITLE,
		// HTML5
		ABBR,
		DFN,
		COMMAND,
		LINK,
		STYLE),
	Type(
		"type",
		A,
		LINK,
		OBJECT,
		PARAM,
		SCRIPT,
		STYLE,
		INPUT,
		LI,
		OL,
		UL,
		BUTTON,
		// HTML5
		AREA,
		COMMAND,
		EMBED,
		SOURCE,
		MENU),
	Usemap("usemap", IMG, INPUT, OBJECT),
	Valign("valign", COL, COLGROUP, TBODY, TD, TFOOT, TH, THEAD, TR),
	Value(
		"value",
		OPTION,
		PARAM,
		INPUT,
		BUTTON,
		LI,
		// HTML5
		// DATA,
		METER,
		PROGRESS),
	Valuetype("valuetype", PARAM),
	Version("version", HTML),
	Vlink("vlink", BODY),
	Vspace("vspace", APPLET, IMG, OBJECT),
	Width(
		"width",
		HR,
		IFRAME,
		IMG,
		OBJECT,
		TABLE,
		APPLET,
		COL,
		COLGROUP,
		TD,
		TH,
		PRE,
		// HTML5
		CANVAS,
		EMBED,
		VIDEO),
	//
	;
	private String text;
	private final Set<TAG> valids = new TreeSet<TAG>();
	private final Set<TAG> invalids = new TreeSet<TAG>();
	private A(final String text, final TAG...valids) {
		this.text = text;
		for (final TAG t: valids) {
			this.valids.add(t);
		}
	}
	private A(final String text, final TAG[] valids, final TAG...invalids) {
		this.text = text;
		for (final TAG t: valids) {
			this.valids.add(t);
		}
		for (final TAG t: invalids) {
			this.invalids.add(t);
		}
	}
	public boolean isValid(final TAG tag) {
		return valids.contains(tag) || !invalids.contains(tag);
	}
	public IAttribute value(final Object value) {
		return new Attribute(toString(), value.toString());
	}
	public IAttribute value(final String value) {
		return new Attribute(toString(), value);
	}
	public IAttribute value(final boolean value) {
		return new Attribute(toString(), String.valueOf(value));
	}
	public IAttribute value(final int value) {
		return new Attribute(toString(), String.valueOf(value));
	}
	public IAttribute value(final double value) {
		return new Attribute(toString(), String.valueOf(value));
	}
	/** Set value, shorthand for value(). */
	public IAttribute s(final Object value) {
		return new Attribute(toString(), value.toString());
	}
	/** Set value, shorthand for value(). */
	public IAttribute s(final String value) {
		return new Attribute(toString(), value);
	}
	/** Set value, shorthand for value(). */
	public IAttribute s(final boolean value) {
		return new Attribute(toString(), String.valueOf(value));
	}
	/** Set value, shorthand for value(). */
	public IAttribute s(final int value) {
		return new Attribute(toString(), String.valueOf(value));
	}
	/** Set value, shorthand for value(). */
	public IAttribute s(final double value) {
		return new Attribute(toString(), String.valueOf(value));
	}
	@Override
	public String toString() {
		return text;
	}
}
