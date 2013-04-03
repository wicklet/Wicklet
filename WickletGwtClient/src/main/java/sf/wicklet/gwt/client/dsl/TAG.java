/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.dsl;

import java.util.Map;
import java.util.TreeMap;

public enum TAG {

	// HTML 5
	//	A(), // hyperlink CHANGED
	//	ABBR(), // abbreviation
	//	ADDRESS(), // contact information
	//	AREA(), // image-map hyperlink
	ARTICLE(DTD.HTML5), // article NEW
	ASIDE(DTD.HTML5), // tangential content NEW
	AUDIO(DTD.HTML5), // audio stream NEW
	//	B(), // offset text conventionally styled in bold CHANGED
	//	BASE(), // base URL
	BDI(DTD.HTML5), // BiDi isolate NEW
	//	BDO(), // BiDi override
	//	BLOCKQUOTE(), // block quotation
	//	BODY(), // document body
	//	BR(), // line break
	//	BUTTON(), // button
	//	BUTTON(), // type=submit – submit button
	//	BUTTON(), // type=reset – reset button
	//	BUTTON(), // type=button – button with no additional semantics
	CANVAS(DTD.HTML5), // canvas for dynamic graphics NEW
	//	CAPTION(), // table title
	//	CITE(), // cited title of a work CHANGED
	//	CODE(), // code fragment
	//	COL(), // table column
	//	COLGROUP(), // table column group
	COMMAND(true, DTD.HTML5), // command NEW
	//	COMMAND(), // type=command – command with an associated action NEW
	//	COMMAND(), // type=radio – selection of one item from a list of items NEW
	//	COMMAND(), // type=checkbox – state or option that can be toggled NEW
	DATALIST(DTD.HTML5), // predefined options for other controls NEW
	//	DD(), // description or value
	//	DEL(), // deleted text
	DETAILS(DTD.HTML5), // control for additional on-demand information NEW
	//	DFN(), // defining instance
	//	DIV(), // generic flow container
	//	DL(), // description list
	//	DT(), // term or name
	//	EM(), // emphatic stress
	EMBED(true, DTD.HTML5), // integration point for plugins NEW
	//	FIELDSET(), // set of related form controls
	FIGCAPTION(DTD.HTML5), // figure caption NEW
	FIGURE(DTD.HTML5), // figure with optional caption NEW
	FOOTER(DTD.HTML5), // footer NEW
	//	FORM(), // user-submittable form
	//	H1(), // heading
	//	H2(), // heading
	//	H3(), // heading
	//	H4(), // heading
	//	H5(), // heading
	//	H6(), // heading
	//	HEAD(), // document metadata container
	HEADER(DTD.HTML5), // header NEW
	HGROUP(DTD.HTML5), // heading group NEW
	//	HR(), // thematic break CHANGED
	//	HTML(), // root element
	//	I(), // offset text conventionally styled in italic CHANGED
	//	IFRAME(), // nested browsing context (inline frame)
	//	IMG(), // image
	//	INPUT(), // input control CHANGED
	//	INPUT(), // type=text – text-input field
	//	INPUT(), // type=password – password-input field
	//	INPUT(), // type=checkbox – checkbox
	//	INPUT(), // type=radio – radio button
	//	INPUT(), // type=button – button
	//	INPUT(), // type=submit – submit button
	//	INPUT(), // type=reset – reset button
	//	INPUT(), // type=file – file upload control
	//	INPUT(), // type=hidden – hidden input control
	//	INPUT(), // type=image – image-coordinates input control
	//	INPUT(), // type=datetime – global date-and-time input control NEW
	//	INPUT(), // type=datetime-local – local date-and-time input control NEW
	//	INPUT(), // type=date – date input control NEW
	//	INPUT(), // type=month – year-and-month input control NEW
	//	INPUT(), // type=time – time input control NEW
	//	INPUT(), // type=week – year-and-week input control NEW
	//	INPUT(), // type=number – number input control NEW
	//	INPUT(), // type=range – imprecise number-input control NEW
	//	INPUT(), // type=email – e-mail address input control NEW
	//	INPUT(), // type=url – URL input control NEW
	//	INPUT(), // type=search – search field NEW
	//	INPUT(), // type=tel – telephone-number-input field NEW
	//	INPUT(), // type=color – color-well control NEW
	//	INS(), // inserted text
	//	KBD(), // user input
	KEYGEN(DTD.HTML5), // key-pair generator/input control NEW
	//	LABEL(), // caption for a form control
	//	LEGEND(), // title or explanatory caption
	//	LI(), // list item
	//	LINK(), // inter-document relationship metadata
	//	MAP(), // image-map definition
	MARK(true, DTD.HTML5), // marked (highlighted) text NEW
	//	MENU(), // list of commands CHANGED
	//	META(), // metadata CHANGED
	//	META(), // name – name-value metadata
	//	META(), // http-equiv=refresh – “refresh” pragma directive
	//	META(), // http-equiv=default-style – “preferred stylesheet” pragma directive
	//	META(), // charset – document character-encoding declaration NEW
	//	META(), // http-equiv=content-type – document character-encoding declaration
	METER(DTD.HTML5), // scalar gauge NEW
	NAV(DTD.HTML5), /* group of navigational links NEW */
	//	NOSCRIPT(), // fallback content for script
	//	OBJECT(), // generic external content
	//	OL(), // ordered list
	//	OPTGROUP(), // group of options
	//	OPTION(), // option
	OUTPUT(DTD.HTML5), // result of a calculation in a form NEW
	//	P(), // paragraph
	//	PARAM(), // initialization parameters for plugins
	//	PRE(), // preformatted text
	PROGRESS(DTD.HTML5), // progress indicator NEW
	//	Q(), // quoted text
	RP(DTD.HTML5), // ruby parenthesis NEW
	RT(DTD.HTML5), // ruby text NEW
	RUBY(DTD.HTML5), // ruby annotation NEW
	//	S(), // struck text CHANGED
	//	SAMP(), // (sample) output
	//	SCRIPT(), // embedded script
	SECTION(DTD.HTML5), // section NEW
	//	SELECT(), // option-selection form control
	//	SMALL(), // small print CHANGED
	SOURCE(true, DTD.HTML5), // media source NEW
	//	SPAN(), // generic span
	//	STRONG(), // strong importance
	//	STYLE(), // style (presentation) information
	//	SUB(), // subscript
	SUMMARY(DTD.HTML5), // summary, caption, or legend for a details control NEW
	//	SUP(), // superscript
	//	TABLE(), // table
	//	TBODY(), // table row group
	//	TD(), // table cell
	//	TEXTAREA(), // text input area
	//	TFOOT(), // table footer row group
	//	TH(), // table header cell
	//	THEAD(), // table heading group
	TIME(DTD.HTML5), // date and/or time NEW
	//	TITLE(), // document title
	//	TR(), // table row
	TRACK(true, DTD.HTML5), // supplementary media track NEW
	//	U(), // offset text conventionally styled with an underline CHANGED
	//	UL(), // unordered list
	//	VAR(), // variable or placeholder text
	VIDEO(DTD.HTML5), // video NEW
	WBR(true, DTD.HTML5), // line-break opportunity NEW
	// HTML 4.0
	UNKNOWN(REQ.YES, REQ.YES, false, true, DTD.HTML4),
	DOCTYPE(true),
	A,
	ABBR,
	ACRONYM,
	ADDRESS,
	APPLET(REQ.YES, REQ.YES, false, true, DTD.HTML4),
	AREA(true),
	B,
	BASE(true),
	BASEFONT(REQ.YES, REQ.NO, true, true, DTD.HTML4),
	BDO,
	BIG,
	BLOCKQUOTE,
	BODY(REQ.OPT, REQ.OPT, DTD.HTML4),
	BR(true),
	BUTTON,
	CAPTION,
	CENTER(REQ.YES, REQ.YES, false, true, DTD.HTML4),
	CITE,
	CODE,
	COL(true),
	COLGROUP(REQ.YES, REQ.OPT, DTD.HTML4),
	DD(REQ.YES, REQ.OPT, DTD.HTML4),
	DEL,
	DFN,
	DIR(REQ.YES, REQ.YES, false, true, DTD.HTML4),
	DIV,
	DL,
	DT(REQ.YES, REQ.OPT, DTD.HTML4),
	EM,
	FIELDSET,
	FONT(REQ.YES, REQ.YES, false, true, DTD.HTML4),
	FORM,
	FRAME(true, DTD.HTML4_FRAMESET),
	FRAMESET(DTD.HTML4_FRAMESET),
	H1,
	H2,
	H3,
	H4,
	H5,
	H6,
	HEAD(REQ.OPT, REQ.OPT, DTD.HTML4),
	HR(true),
	HTML(REQ.OPT, REQ.OPT, DTD.HTML4),
	I,
	IFRAME(REQ.YES, REQ.YES, false, false, DTD.HTML4),
	IMG(true),
	INPUT(true),
	INS,
	ISINDEX(REQ.YES, REQ.NO, true, true, DTD.HTML4),
	KBD,
	LABEL,
	LEGEND,
	LI(REQ.YES, REQ.OPT, DTD.HTML4),
	LINK(true),
	MAP,
	MENU(REQ.YES, REQ.YES, false, true, DTD.HTML4),
	META(true),
	NOFRAMES(DTD.HTML4_FRAMESET),
	NOSCRIPT,
	OBJECT,
	OL,
	OPTGROUP,
	OPTION(REQ.YES, REQ.OPT, DTD.HTML4),
	P(REQ.YES, REQ.OPT, DTD.HTML4),
	PARAM(true),
	PRE,
	Q,
	S(REQ.YES, REQ.YES, false, true, DTD.HTML4),
	SAMP,
	SCRIPT,
	SELECT,
	SMALL,
	SPAN,
	STRIKE(REQ.YES, REQ.YES, false, true, DTD.HTML4),
	STRONG,
	STYLE,
	SUB,
	SUP,
	TABLE,
	TBODY(REQ.OPT, REQ.OPT, DTD.HTML4),
	TD(REQ.YES, REQ.OPT, DTD.HTML4),
	TEXTAREA,
	TFOOT(REQ.YES, REQ.OPT, DTD.HTML4),
	TH(REQ.YES, REQ.OPT, DTD.HTML4),
	THEAD(REQ.YES, REQ.OPT, DTD.HTML4),
	TITLE,
	TR(REQ.YES, REQ.OPT, DTD.HTML4),
	TT(),
	U(REQ.YES, REQ.YES, false, true, DTD.HTML4),
	UL,
	VAR,
	// Obsoleted
	XMP(REQ.YES, REQ.YES, false, true, DTD.HTML4),
	PLAINTEXT(REQ.YES, REQ.YES, false, true, DTD.HTML4),
	TERM(REQ.YES, REQ.YES, false, true, DTD.V2),
	// MS extension
	SDFIELD(DTD.MS),
	//	EMBED(REQ.YES, REQ.NO, true, false, DTD.MS),
	// Netvigator extension
	NOBR(REQ.YES, REQ.YES, false, true, DTD.NETVIGATOR),
	//	WBR(REQ.YES, REQ.NO, true, true, DTD.NETVIGATOR),
	SPACER(REQ.YES, REQ.NO, true, true, DTD.NETVIGATOR),
	MULTICOL(REQ.YES, REQ.YES, false, true, DTD.NETVIGATOR),
	LAYER(DTD.NETVIGATOR),
	ILAYER(DTD.NETVIGATOR),
	NOLAYER(DTD.NETVIGATOR),
	//
	;

	//////////////////////////////////////////////////////////////////////

	public enum REQ {
		YES, OPT, NO 
	}

	public enum DTD {
		HTML5, HTML4, HTML4_FRAMESET, MS, NETVIGATOR, V2, X; 
	}

	// Tag type modifiers.
	public static final int NONE = 0x00;
	// Content model modifiers.
	// public static final int C_INLINE = 0x0001;
	// public static final int C_BLOCK = 0x0002;
	// public static final int C_ELEMENT = 0x0004;
	public static final int C_BLOCK_FORMAT = 0x0008;
	public static final int C_PRE_FORMAT = 0x0010;
	public static final Map<String, TAG> lookupTable = new TreeMap<String, TAG>();

	//////////////////////////////////////////////////////////////////////

	static {
		for (final TAG tag:
			new TAG[] {
				UNKNOWN,
				HTML, HEAD, BODY, META, TITLE, LINK, BASE,
				P, DL, DIV, NOSCRIPT, BLOCKQUOTE, HR,
				TABLE, TR, COLGROUP, THEAD, TBODY,
				FORM, INPUT, SELECT, BUTTON, OPTGROUP, OPTION, TEXTAREA, LABEL,
				FIELDSET, ADDRESS,
				H1, H2, H3, H4, H5, H6, // %heading
				UL, OL, LI, // %list;
				PRE, SCRIPT, STYLE, // %preformatted
				// Obsoleted
				XMP, PLAINTEXT, //
				// Netvigator extension
				MULTICOL, LAYER, NOLAYER,
				IFRAME, OBJECT,
			}) {
			tag.setModifier(C_BLOCK_FORMAT);
		}
		for (final TAG tag:
			new TAG[] {
				PRE, SCRIPT, STYLE, // %preformatted
			}) {
			tag.setModifier(C_PRE_FORMAT);
		}
		for (final TAG tag: values()) {
			lookupTable.put(tag.name().toLowerCase(), tag);
	}}

	//////////////////////////////////////////////////////////////////////

	public static TAG[] Empty = new TAG[0];

	int modifier;
	REQ startTag;
	REQ endTag;
	boolean isEmpty;
	boolean isDeprecated;
	DTD variant;

	//////////////////////////////////////////////////////////////////////

	private TAG() {
		this(REQ.YES, REQ.YES, false, false, DTD.HTML4);
	}

	private TAG(final boolean empty) {
		this(REQ.YES, REQ.NO, true, false, DTD.HTML4);
	}

	private TAG(final DTD dtd) {
		this(REQ.YES, REQ.YES, false, false, dtd);
	}

	private TAG(final boolean empty, final DTD dtd) {
		this(REQ.YES, REQ.NO, true, false, dtd);
	}

	private TAG(final REQ start, final REQ end, final DTD dtd) {
		this(start, end, false, false, dtd);
	}

	private TAG(final REQ start, final REQ end, final boolean empty, final boolean deprecated, final DTD dtd) {
		startTag = start;
		endTag = end;
		isEmpty = empty;
		isDeprecated = deprecated;
		variant = dtd;
	}

	//////////////////////////////////////////////////////////////////////

	/** @param name XHtml tag name in lower case. */
	public static TAG get(final String name) {
		return lookupTable.get(name);
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public boolean optionalEndTag() {
		return startTag == REQ.YES && endTag == REQ.OPT;
	}

	public void setModifier(final int flag) {
		modifier |= flag;
	}

	public boolean hasModifier(final int flag) {
		return (modifier & flag) != 0;
	}

	public boolean isBlockFormat() {
		return (modifier & C_BLOCK_FORMAT) != 0;
	}

	public boolean isPreFormatted() {
		return (modifier & C_PRE_FORMAT) != 0;
	}

	//	public boolean isBlock() {
	//		return (modifier & C_BLOCK) != 0;
	//	}
	//
	//	public boolean isInline() {
	//		return (modifier & C_INLINE) != 0;
	//	}

	//////////////////////////////////////////////////////////////////////
}
