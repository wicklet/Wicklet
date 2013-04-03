/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.util;

import com.google.gwt.core.client.GWT;

public class GwtUrlUtil {

    public static native String getWicketBaseUrl()
    /*-{
        return $wnd.Wicket.Ajax.baseUrl;
    }-*/
    ;

    public static native String getWickletGwtAjaxBehaviorUrl()
    /*-{
        return $wnd.Wicklet.GwtAjaxBehavior.url;
    }-*/
    ;

    /** @return The protocol://host[:port] portion of the given url. */
    public static String getProtocolHostPort(final String url) {
        final UrlParts parts = UrlParts.parse(url);
        parts.setPath(null);
        parts.setQuery(null);
        parts.setFragment(null);
        return parts.toString();
    }

    public static String getWebappUrl(final String rpath) {
        return getWebbappUrl(GWT.getHostPageBaseURL(), rpath);
    }

    /**
     * @return url relative to webapp if input url in form of: scheme://host[:port]/webapp[/...].
     * If input url is in form of scheme://host[:port]/..., return url relative to root.
     */
    public static String getWebbappUrl(final String url, final String rpath) {
        final UrlParts parts = UrlParts.parse(url);
        parts.setFragment(null);
        parts.setQuery(null);
        String path = parts.getPath();
        final int index = (path.length() > 0 && path.charAt(0) == '/') ? 1 : 0;
        final int end = path.indexOf('/', index);
        if (end > 0) {
            path = path.substring(0, end + 1) + rpath;
        } else {
            path = path + '/' + rpath;
        }
        parts.setPath(path);
        return parts.toString();
    }

    public static String setUrlPath(final String url, final String path) {
        final UrlParts parts = UrlParts.parse(url);
        parts.setPath(path);
        return parts.toString();
    }

    public static String removeQueryFragment(final String url) {
        final UrlParts parts = UrlParts.parse(url);
        parts.setQuery(null);
        parts.setFragment(null);
        return parts.toString();
    }

    public static class UrlParts {
        enum State {
            scheme, host, port, user, pass, path, fragment, query;
        }
        public static UrlParts parse(final String url) throws IllegalStateException {
            State state = State.scheme;
            final StringBuilder b = new StringBuilder();
            final UrlParts parts = new UrlParts();
            final StringScanner s = new StringScanner(url);
            DONE: while (true) {
                final int c = s.get();
                switch (state) {
                case scheme:
                    if (c == ':') {
                        s.get('/');
                        s.get('/');
                        parts.scheme = get(b);
                        state = State.host;
                    } else if (c == '/') {
                        if (s.peek() == '/') {
                            s.consume();
                            state = State.host;
                        } else {
                            b.append((char)c);
                            state = State.path;
                    }} else if (c == '?') {
                        parts.path = get(b);
                        state = State.query;
                    } else if (c == '#') {
                        parts.path = get(b);
                        state = State.fragment;
                    } else if (c == -1) {
                        parts.path = get(b);
                        break DONE;
                    } else {
                        b.append((char)c);
                    }
                    break;
                case user:
                    if (c == ':' || c == '@') {
                        parts.user = get(b);
                        state = c == ':' ? State.pass : State.host;
                    } else if (c == '/') {
                        b.append('/');
                        state = State.path;
                    } else if (c == -1) {
                        parts.user = get(b);
                        break DONE;
                    } else {
                        b.append((char)c);
                    }
                    break;
                case pass:
                    if (c == '@') {
                        parts.pass = get(b);
                        state = State.host;
                    } else if (c == '/') {
                        parts.pass = get(b);
                        b.append('/');
                        state = State.path;
                    } else if (c == -1) {
                        parts.pass = get(b);
                        break DONE;
                    } else {
                        b.append((char)c);
                    }
                    break;
                case host:
                    if (c == '@') {
                        parts.user = get(b);
                    } else if (c == ':') {
                        parts.host = get(b);
                        state = State.port;
                    } else if (c == '/') {
                        parts.host = get(b);
                        b.append('/');
                        state = State.path;
                    } else if (c == -1) {
                        parts.host = get(b);
                        break DONE;
                    } else {
                        b.append((char)c);
                    }
                    break;
                case port:
                    if (c == '@') {
                        parts.user = parts.host;
                        parts.pass = get(b);
                        parts.host = null;
                        state = State.host;
                    } else if (c == '/') {
                        parts.port = get(b);
                        b.append('/');
                        state = State.path;
                    } else if (c == -1) {
                        parts.port = get(b);
                        break DONE;
                    } else {
                        b.append((char)c);
                    }
                    break;
                case path:
                    if (c == '?') {
                        parts.path = get(b);
                        state = State.query;
                    } else if (c == '#') {
                        parts.path = get(b);
                        state = State.fragment;
                    } else if (c == -1) {
                        parts.path = get(b);
                        break DONE;
                    } else {
                        b.append((char)c);
                    }
                    break;
                case query:
                    if (c == '#') {
                        parts.query = get(b);
                        state = State.fragment;
                    } else if (c == -1) {
                        parts.query = get(b);
                        break DONE;
                    } else {
                        b.append((char)c);
                    }
                    break;
                case fragment:
                    if (c == -1) {
                        parts.fragment = get(b);
                        break DONE;
                    }
                    b.append((char)c);
                    break;
            }}
            return parts;
        }
        private static String get(final StringBuilder b) {
            if (b.length() == 0) {
                return null;
            }
            final String ret = b.toString();
            b.setLength(0);
            return ret;
        }
        private String scheme;
        private String user;
        private String pass;
        private String host;
        private String port;
        private String path;
        private String fragment;
        private String query;
        public UrlParts(
            final String scheme,
            final String user,
            final String pass,
            final String host,
            final String port,
            final String path,
            final String query,
            final String fragment) {
            this.scheme = scheme;
            this.host = host;
            this.port = port;
            this.user = user;
            this.pass = pass;
            this.path = path;
            this.fragment = fragment;
            this.query = query;
        }
        private UrlParts() {
        }
        public String getScheme() {
            return scheme;
        }
        public String getUser() {
            return user;
        }
        public String getPass() {
            return pass;
        }
        public String getHost() {
            return host;
        }
        public String getPort() {
            return port;
        }
        public String getPath() {
            return path;
        }
        public String getQuery() {
            return query;
        }
        public String getFragment() {
            return fragment;
        }
        public void setScheme(final String scheme) {
            this.scheme = scheme;
        }
        public void setUser(final String user) {
            this.user = user;
        }
        public void setPass(final String pass) {
            this.pass = pass;
        }
        public void setHost(final String host) {
            this.host = host;
        }
        public void setPort(final String port) {
            this.port = port;
        }
        public void setPath(final String path) {
            this.path = path;
        }
        public void setQuery(final String query) {
            this.query = query;
        }
        public void setFragment(final String fragment) {
            this.fragment = fragment;
        }
        /** @return A new instance of UrlParts for the given relative url with this url as base. */
        public UrlParts resolve(final UrlParts relative) {
            final StringBuilder b = new StringBuilder(path);
            final int index = path.lastIndexOf('/');
            if (index > 0 && index != path.length() - 1) {
                b.setLength(index + 1);
            }
            final String rpath = relative.getPath();
            if (!GwtTextUtil.isEmpty(rpath)) {
                b.append(rpath);
                GwtTextUtil.cleanupFilePath(b, '/');
            }
            while (b.indexOf("../") == 0) {
                b.delete(0, 3);
            }
            while (b.indexOf("../") == 1) {
                b.delete(1, 4);
            }
            return new UrlParts(
                scheme, user, pass, host, port, b.toString(), relative.getQuery(), relative.getFragment());
        }
        @Override
        public String toString() {
            final StringBuilder b = new StringBuilder();
            if (scheme != null) {
                b.append(scheme);
                b.append("://");
            }
            if (user != null) {
                if (b.length() == 0) {
                    b.append("//");
                }
                b.append(user);
                if (pass != null) {
                    b.append(':');
                    b.append(pass);
                }
                b.append('@');
            } else if (pass != null) {
                b.append(':');
                b.append(pass);
                b.append('@');
            }
            if (host != null) {
                if (b.length() == 0) {
                    b.append("//");
                }
                b.append(host);
            }
            if (port != null) {
                if (b.length() == 0) {
                    b.append("//");
                }
                b.append(':');
                b.append(port);
            }
            if (path != null) {
                b.append(path);
            }
            if (query != null) {
                b.append('?');
                b.append(query);
            }
            if (fragment != null) {
                b.append('#');
                b.append(fragment);
            }
            return b.toString();
        }
    }
}
