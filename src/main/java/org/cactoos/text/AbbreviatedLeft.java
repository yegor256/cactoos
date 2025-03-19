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
 * Abbreviates a Text using ellipses from the left side.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.58.0
 */
public final class AbbreviatedLeft extends TextEnvelope {

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
    public AbbreviatedLeft(final CharSequence text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     *
     * <p> By default, the max line width is 80 characters.
     *
     * @param text The Text
     */
    public AbbreviatedLeft(final Text text) {
        this(text, AbbreviatedLeft.MAX_WIDTH);
    }

    /**
     * Ctor.
     *
     * @param text A CharSequence
     * @param max Max width of the result string
     */
    public AbbreviatedLeft(final CharSequence text, final int max) {
        this(new TextOf(text), max);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param max Max width of the result string
     */
    public AbbreviatedLeft(final Text text, final int max) {
        super(
            new Flattened(
                new Ternary<>(
                    new ScalarOf<>(() -> new Sticky(text)),
                    (Text t) -> new LengthOf(t).value() <= (long) max,
                    t -> t,
                    t -> new FormattedText(
                        "%s%s",
                        AbbreviatedLeft.ELLIPSES,
                        new Sub(
                            t.asString(),
                            (int) (new LengthOf(t).value().longValue()
                                - max + AbbreviatedLeft.ELLIPSES.length()),
                            (int) new LengthOf(t).value().longValue()
                        )
                    )
                )
            )
        );
    }
}
