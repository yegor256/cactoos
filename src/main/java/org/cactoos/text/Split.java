/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.iterator.IteratorOf;

/**
 * Split the Text.
 *
 * @see String#split(String)
 * @see String#split(String, int)
 * @since 0.9
 */
public final class Split extends IterableEnvelope<Text> {
    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     * @see String#split(String)
     */
    public Split(final CharSequence text, final CharSequence rgx) {
        this(new TextOf(text), new TextOf(rgx));
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     * @see String#split(String, int)
     */
    public Split(final CharSequence text, final CharSequence rgx, final int lmt) {
        this(new TextOf(text), new TextOf(rgx), lmt);
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @see String#split(String)
     */
    public Split(final CharSequence text, final Text rgx) {
        this(new TextOf(text), rgx);
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     * @see String#split(String, int)
     */
    public Split(final CharSequence text, final Text rgx, final int lmt) {
        this(new TextOf(text), rgx, lmt);
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @see String#split(String)
     */
    public Split(final Text text, final CharSequence rgx) {
        this(text, new TextOf(rgx));
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     * @see String#split(String, int)
     */
    public Split(final Text text, final CharSequence rgx, final int lmt) {
        this(text, new TextOf(rgx), lmt);
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @see String#split(String)
     */
    public Split(final Text text, final Text rgx) {
        this(text, rgx, 0);
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     * @see String#split(String, int)
     */
    public Split(final Text text, final Text rgx, final int lmt) {
        super(
            new Mapped<>(
                TextOf::new,
                new IterableOf<>(
                    () -> new IteratorOf<>(text.asString().split(rgx.asString(), lmt))
                )
            )
        );
    }
}
