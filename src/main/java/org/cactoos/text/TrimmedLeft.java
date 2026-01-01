/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Text without control characters (char &lt;= 32) only from left.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @since 0.12
 */
public final class TrimmedLeft extends TextEnvelope {

    /**
     * Ctor.
     * @param text The text
     */
    public TrimmedLeft(final Text text) {
        super(
            new Mapped(
                string -> {
                    int cursor = 0;
                    while (
                        cursor < string.length() && Character.isWhitespace(
                            string.charAt(cursor)
                        )
                    ) {
                        cursor += 1;
                    }
                    return string.substring(cursor);
                },
                text
            )
        );
    }
}
