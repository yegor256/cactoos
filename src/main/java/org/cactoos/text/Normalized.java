/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Normalize (replace sequences of whitespace characters by a single space)
 * a Text.
 *
 * @since 0.9
 */
public final class Normalized extends TextEnvelope {

    /**
     * Ctor.
     * @param text A Text
     */
    public Normalized(final CharSequence text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     * @param text A Text
     */
    public Normalized(final Text text) {
        super(
            new Replaced(
                new Trimmed(text),
                "\\s+",
                " "
            )
        );
    }
}
