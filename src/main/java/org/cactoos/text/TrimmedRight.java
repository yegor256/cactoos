/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Text without control characters (char &lt;= 32) only from right.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.12
 */
public final class TrimmedRight extends TextEnvelope {

    /**
     * Ctor.
     * @param text The text
     */
    public TrimmedRight(final Text text) {
        super(
            new Mapped(
                string -> {
                    int cursor = string.length() - 1;
                    while (cursor >= 0 && Character.isWhitespace(string.charAt(cursor))) {
                        cursor -= 1;
                    }
                    return string.substring(0, cursor + 1);
                },
                text
            )
        );
    }
}
