/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.components.link;

import org.apache.wicket.markup.html.link.Link;

public class JavascriptLink extends Link<Void> {
    private static final long serialVersionUID = 1L;
    private final CharSequence script;
    private String href = "#";
    public JavascriptLink(final Object id, final CharSequence script) {
        super(id.toString());
        this.script = script;
    }
    public JavascriptLink href(final String href) {
        this.href = href;
        return this;
    }
    @Override
    protected CharSequence getOnClickScript(final CharSequence url) {
        return script + "; return false;";
    }
    @Override
    protected CharSequence getURL() {
        return href;
    }
    @Override
    public void onClick() {
    }
}
