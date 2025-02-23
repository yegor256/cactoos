/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Repeat an text count times.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.9
 */
public final class Repeated extends TextEnvelope {

    /**
     * Ctor.
     * @param text A String
     * @param count How many times repeat the Text
     */
    public Repeated(final CharSequence text, final int count) {
        this(new TextOf(text), count);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param count How many times repeat the Text
     */
    public Repeated(final Text text, final int count) {
        super(
            new TextOf(
                () -> {
                    final StringBuilder out = new StringBuilder();
                    for (int cnt = 0; cnt < count; ++cnt) {
                        out.append(text.asString());
                    }
                    return out.toString();
                }
            )
        );
    }
}
