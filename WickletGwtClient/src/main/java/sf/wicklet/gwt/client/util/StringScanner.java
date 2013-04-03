/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.util;

public class StringScanner {

    private final String source;
    private final int length;
    private int index = 0;

    public StringScanner(final String source) {
        this.source = source;
        length = source.length();
    }

    public int get() {
        if (index >= length) {
            return -1;
        }
        return source.charAt(index++);
    }

    public int peek() {
        if (index >= length) {
            return -1;
        }
        return source.charAt(index);
    }

    public boolean lookahead(final String s) {
        final int len = length - index;
        if (len <= 0) {
            return false;
        }
        final int slen = s.length();
        if (len < slen) {
            return false;
        }
        for (int i = 0; i < slen; ++i) {
            if (source.charAt(index + i) != s.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public void unget() {
        if (index == 0) {
            throw new IndexOutOfBoundsException();
        }
        --index;
    }

    public int index() {
        return index;
    }

    public int remaining() {
        return length - index;
    }

    /** Skip next character if it match the given char. */
    public boolean skip(final char c) {
        if (index < length && source.charAt(index) == c) {
            ++index;
            return true;
        }
        return false;
    }

    /** Check that next character is the given char, throw exception otherwise. */
    public int get(final char c) {
        if (index < length && source.charAt(index) == c) {
            ++index;
            return c;
        }
        throw new IllegalStateException();
    }

    public void consume() {
        if (index < length) {
            ++index;
    }}

    public String substring(final int start) {
        return source.substring(start, index);
    }

    public String substring(final int start, final int end) {
        return source.substring(start, end);
    }
}
