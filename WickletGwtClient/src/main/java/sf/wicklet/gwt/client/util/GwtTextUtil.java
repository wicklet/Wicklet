/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.util;

import java.util.ArrayList;
import java.util.List;

public class GwtTextUtil {

    private static String LINE_SEP = "\n";

    public static String getLineSeparator() {
        return LINE_SEP;
    }

    public static StringBuilder join(final StringBuilder ret, final CharSequence sep, final Iterable<?> a) {
        boolean first = true;
        for ( final Object s: a) {
            if (first) {
                first = false;
            } else {
                ret.append(sep);
            }
            ret.append(s.toString());
        }
        return ret;
    }

    public static StringBuilder join(final StringBuilder ret, final CharSequence sep, final String...a) {
        boolean first = true;
        for ( final String s: a) {
            if (first) {
                first = false;
            } else {
                ret.append(sep);
            }
            ret.append(s);
        }
        return ret;
    }

    public static StringBuilder join(
        final StringBuilder ret, final CharSequence sep, final String[] a, final int start, final int end) {
        for (int i = start; i < end; ++i) {
            if (i != start) {
                ret.append(sep);
            }
            ret.append(a[i]);
        }
        return ret;
    }

    public static String join(final CharSequence sep, final Iterable<?> a) {
        if (a == null) {
            return "";
        }
        final StringBuilder buf = new StringBuilder();
        join(buf, sep, a);
        return buf.toString();
    }

    public static String join(final CharSequence sep, final String...a) {
        return join(sep, a, 0, a.length);
    }

    public static String join(final CharSequence sep, final String[] a, final int start, final int end) {
        final StringBuilder b = new StringBuilder();
        for (int i = start; i < end; ++i) {
            if (i != 0) {
                b.append(sep);
            }
            b.append(a[i]);
        }
        return b.toString();
    }

    public static String joinln(final String...a) {
        return join(getLineSeparator(), a);
    }

    /** Use joins() instead of join() to avoid unintentional conversion to Objects. */
    public static <T> String joins(final CharSequence sep, final T...a) {
        return joins(sep, a, 0, a.length);
    }

    public static <T> String joins(final CharSequence sep, final T[] a, final int start, final int end) {
        final StringBuilder b = new StringBuilder();
        for (int i = start; i < end; ++i) {
            if (i != 0) {
                b.append(sep);
            }
            b.append(a[i].toString());
        }
        return b.toString();
    }

    public static <T> String joinln(final Iterable<T> a) {
        return join(getLineSeparator(), a);
    }

    public static <T> String joinln(final T...a) {
        return joins(getLineSeparator(), a);
    }

    public static boolean isEmpty(final String value) {
        return value == null || value.length() == 0;
    }

    public boolean isSpace(final char c) {
        return (c == ' ' || c == '\t');
    }

    public boolean isWhitespace(final char c) {
        return (c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\u0085' || c == '\u2028');
    }

    public boolean isLineBreak(final char c) {
        return (c == '\n' || c == '\r' || c == '\u0085' || c == '\u2028');
    }

    public static List<String> tokenize(final String s) {
        return tokenize(" \t\n\r\u0085\u2028", s);
    }

    public static List<String> tokenize(final String delims, final String s) {
        final List<String> ret = new ArrayList<String>();
        final int len = s.length();
        int start = -1;
        for (int i = 0; i < len; ++i) {
            final char c = s.charAt(i);
            if (delims.indexOf(c) >= 0) {
                if (start >= 0) {
                    ret.add(s.substring(start, i));
                    start = -1;
                }
                continue;
            }
            if (start < 0) {
                start = i;
        }}
        if (start >= 0) {
            ret.add(s.substring(start, len));
        }
        return ret;
    }

    public static int indexOfAny(final String delimiters, final String str) {
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (delimiters.indexOf(str.charAt(i)) >= 0) {
                return i;
        }}
        return -1;
    }

    public static int lastIndexOfAny(final String delimiters, final String str) {
        for (int i = str.length() - 1; i >= 0; --i) {
            if (delimiters.indexOf(str.charAt(i)) >= 0) {
                return i;
        }}
        return -1;
    }

    public static boolean equals(final String a, final String b) {
        return a == null ? b == null : a.equals(b);
    }

    /**  Remove duplicated /, /./ and /../ */
    public static void cleanupFilePath(final StringBuilder b, final char sep) {
        char c;
        int last = -1;
        int len = 0;
        final String seps = String.valueOf(sep);
        final int max = b.length();
        for (int i = 0; i < max; ++i) {
            c = b.charAt(i);
            if ((last == sep || len == 0) && c == '.' && (((i + 1 < max) && b.charAt(i + 1) == sep) || i + 1 >= max)) {
                // s~^\./~~
                ++i;
                continue;
            }
            if (last == sep && c == sep) {
                // s~//~/~
                continue;
            }
            if (last == sep
                && c == '.'
                && i + 1 < max
                && b.charAt(i + 1) == '.'
                && len >= 2
                && (i + 2 >= max || b.charAt(i + 2) == sep)) {
                final int index = b.lastIndexOf(seps, len - 2);
                if (!"../".equals(b.substring(index + 1, len))) {
                    len = index + 1;
                    ++i;
                    continue;
            }}
            b.setCharAt(len++, c);
            last = c;
        }
        b.setLength(len);
    }
}
