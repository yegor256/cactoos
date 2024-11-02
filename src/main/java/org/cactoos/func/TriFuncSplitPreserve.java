/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.cactoos.func;

import java.util.Collection;
import org.cactoos.TriFunc;
import org.cactoos.list.ListOf;
import org.cactoos.text.Split;

/**
 * Splits the given string by given regex.
 * Used for avoiding static methods.
 * Differs from {@link Split} by saving
 * empty tokens between adjacent regex
 * separators.
 *
 * <p> Examples:
 * 1) text - " hello there " regex - " "
 * Result: ["", "hello", "there", ""]
 * 2) text - "aaa" regex - "a"
 * Result: ["", "", "", ""]
 *
 * @since 0.0
 */
public final class TriFuncSplitPreserve
    implements TriFunc
        <String, String, Integer, Collection<String>> {
    @Override
    public Collection<String> apply(
        final String str,
        final String regex,
        final Integer lmt
    ) {
        final ListOf<String> ret = new ListOf<>();
        int start = 0;
        int pos = str.indexOf(regex);
        if (regex.isEmpty()) {
            ret.add("");
        } else {
            while (pos >= start) {
                if (lmt > 0 && ret.size() == lmt) {
                    break;
                }
                ret.add(str.substring(start, pos));
                start = pos + regex.length();
                pos = str.indexOf(regex, start);
            }
            if (lmt <= 0 || ret.size() < lmt) {
                if (start < str.length()) {
                    ret.add(str.substring(start));
                } else if (start == str.length()) {
                    ret.add("");
                }
            }
        }
        return ret;
    }
}
