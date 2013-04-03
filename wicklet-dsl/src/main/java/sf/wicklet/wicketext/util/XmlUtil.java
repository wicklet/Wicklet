/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.wicketext.util;

import java.io.IOException;
import org.apache.wicket.util.string.StringEscapeUtils;
import sf.blacksun.util.io.StringPrintWriter;
import sf.wicklet.wicketext.markup.api.IXmlUtil;

public class XmlUtil implements IXmlUtil {
	public XmlUtil() {
	}

	@Override
	public CharSequence escAttr(final String source) {
		return StringEscapeUtils.escapeXml(source);
	}

	@Override
	public CharSequence quoteAttr(final String value) {
		final StringPrintWriter buf = new StringPrintWriter(value.length() * 2);
		buf.append('"');
		try {
			StringEscapeUtils.escapeXml(buf, value);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		buf.append('"');
		return buf.toString();
	}

	@Override
	public CharSequence escText(final String source, final boolean preservespace) {
		return StringEscapeUtils.escapeXml(source);
	}
}
