/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.func;

import java.util.Collection;
import org.cactoos.TriFunc;
import org.cactoos.list.ListOf;

/**
 * A String splitter preserving all tokens.
 * Unlike regular Split, stores empty "" tokens
 * created by adjacent regex separators.
 *
 * <p>
 *     Examples:
 *     1) text - " hello there ", regex - " "
 *     result: ["", "hello", "there", ""]
 *     2) text - "aaa", regex - "a"
 *     result: ["", "", "", ""]
 * </p>
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
        while (pos >= start) {
            if (lmt > 0 && ret.size() == lmt || regex.isEmpty()) {
                break;
            }
            ret.add(str.substring(start, pos));
            start = pos + regex.length();
            pos = str.indexOf(regex, start);
        }
        if (lmt <= 0 || ret.size() < lmt || regex.isEmpty()) {
            if (start < str.length()) {
                ret.add(str.substring(start));
            } else if (start == str.length()) {
                ret.add("");
            }
        }
        return ret;
    }
}
