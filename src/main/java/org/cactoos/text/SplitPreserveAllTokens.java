/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.func.TriFuncSplitPreserve;
import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.iterator.IteratorOf;

/**
 * Splits the Text into an array, including empty
 * tokens created by adjacent separators.
 *
 * @since 0.0
 */
public final class SplitPreserveAllTokens extends IterableEnvelope<Text> {
    /**
     * Ctor.
     *
     * @param text The text
     */
    public SplitPreserveAllTokens(final CharSequence text) {
        this(new TextOf(text), new TextOf(" "));
    }

    /**
     * Ctor.
     *
     * @param text The text
     */
    public SplitPreserveAllTokens(final Text text) {
        this(text, new TextOf(" "));
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param lmt The limit
     */
    public SplitPreserveAllTokens(final CharSequence text, final int lmt) {
        this(new TextOf(text), new TextOf(" "), lmt);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param lmt The limit
     */
    public SplitPreserveAllTokens(final Text text, final int lmt) {
        this(text, new TextOf(" "), lmt);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     */
    public SplitPreserveAllTokens(final CharSequence text, final CharSequence rgx) {
        this(new TextOf(text), new TextOf(rgx));
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     */
    public SplitPreserveAllTokens(final CharSequence text, final CharSequence rgx, final int lmt) {
        this(new TextOf(text), new TextOf(rgx), lmt);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     */
    public SplitPreserveAllTokens(final CharSequence text, final Text rgx) {
        this(new TextOf(text), rgx);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     */
    public SplitPreserveAllTokens(final CharSequence text, final Text rgx, final int lmt) {
        this(new TextOf(text), rgx, lmt);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     */
    public SplitPreserveAllTokens(final Text text, final CharSequence rgx) {
        this(text, new TextOf(rgx));
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     */
    public SplitPreserveAllTokens(final Text text, final CharSequence rgx, final int lmt) {
        this(text, new TextOf(rgx), lmt);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     */
    public SplitPreserveAllTokens(final Text text, final Text rgx) {
        this(text, rgx, 0);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     */
    public SplitPreserveAllTokens(
        final Text text,
        final Text rgx,
        final int lmt
    ) {
        super(
            new Mapped<>(
                TextOf::new,
                new IterableOf<>(
                    () -> new IteratorOf<>(
                        new TriFuncSplitPreserve()
                            .apply(
                                text.toString(),
                                rgx.toString(),
                                lmt
                            ).toArray(new String[0])
                    )
                )
            )
        );
    }
}
