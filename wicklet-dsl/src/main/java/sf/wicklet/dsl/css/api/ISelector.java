/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.css.api;

import sf.wicklet.dsl.css.api.support.IRulesetChild;

public interface ISelector extends IRulesetChild {
	/** Append sel if current selector is empty otherwise &lt;code>, sel</code>*/
	ISelector sel(Object sel);
	/** Append &lt;code>sel, sel, ...</code> */
	ISelector sel(Object...sels);
	/** Append &lt;code>#id</code> */
	ISelector id(Object id);
	/** Append &lt;code>.css</code> */
	ISelector css(Object css);
	/** Append &lt;code>:psc</code> */
	ISelector psc(Object pseudo);
	/** Append &lt;code>::pse</code> */
	ISelector pse(Object pseudo);
	/** Append &lt;code>[expr]</code> */
	ISelector attr(Object expr);
	/** Append &lt;code>[name op "value" ]</code> */
	ISelector attr(Object name, IAOp op, Object value);
	/** Append &lt;code>sel</code> */
	ISelector desc(Object sel);
	/** desc(sel) for each sel */
	ISelector desc(Object...sels);
	/** Append &lt;code>>sel</code> */
	ISelector child(Object sel);
	/** child(sel) for each sel */
	ISelector child(Object...sels);
	/** Append &lt;code>+sel</code> */
	ISelector silbing(Object sel);
	// Shortcuts
	/** Same as child(sel) */
	ISelector c(Object sel);
	/** Same as child(sels) */
	ISelector c(Object...sels);
	/** @return Same as desc(sel) */
	ISelector a(Object sel);
	/** Shortcut for desc(sel) for each sel. */
	ISelector a(Object...sels);
}
