/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.scalar.LengthOf;
import org.cactoos.scalar.ScalarOf;
import org.cactoos.scalar.Ternary;

/**
 * Abbreviates a Text using ellipses.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.29
 */
public final class AbbreviatedRight extends TextEnvelope {

    /**
     * The default max line width.
     */
    private static final int MAX_WIDTH = 80;

    /**
     * The ellipses.
     */
    private static final String ELLIPSES = "...";

    /**
     * Ctor.
     *
     * <p> By default, the max line width is 80 characters.
     *
     * @param text The CharSequence
     */
    public AbbreviatedRight(final CharSequence text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     *
     * <p> By default, the max line width is 80 characters.
     *
     * @param text The Text
     */
    public AbbreviatedRight(final Text text) {
        this(text, AbbreviatedRight.MAX_WIDTH);
    }

    /**
     * Ctor.
     *
     * @param text A CharSequence
     * @param max Max width of the result string
     */
    public AbbreviatedRight(final CharSequence text, final int max) {
        this(new TextOf(text), max);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param max Max width of the result string
     */
    public AbbreviatedRight(final Text text, final int max) {
        super(
            new Flattened(
                new Ternary<>(
                    new ScalarOf<>(() -> new Sticky(text)),
                    (Text t) -> new LengthOf(t).value() <= (long) max,
                    t -> t,
                    t -> new FormattedText(
                        "%s%s",
                        new Sub(
                            t,
                            0,
                            max - AbbreviatedRight.ELLIPSES.length()
                        ),
                        AbbreviatedRight.ELLIPSES
                    )
                )
            )
        );
    }
}
