/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Text padded at end to reach the given length.
 *
 * <p>There is thread safe.
 *
 * @since 0.32
 */
public final class PaddedEnd extends TextEnvelope {

    /**
     * Ctor.
     * @param text The text
     * @param length The minimum length of the resulting string
     * @param symbol The padding symbol
     */
    public PaddedEnd(
        final Text text, final int length, final char symbol) {
        super(
            new Flattened(
                () -> {
                    final String original = text.asString();
                    return new Concatenated(
                        new TextOf(original),
                        new Repeated(
                            new TextOf(symbol), length - original.length()
                        )
                    );
                }
            )
        );
    }
}
