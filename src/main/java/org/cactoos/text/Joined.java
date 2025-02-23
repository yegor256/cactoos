/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.util.StringJoiner;
import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Join a Text.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.9
 */
public final class Joined extends TextEnvelope {

    /**
     * Ctor.
     * @param delimit Delimit among strings
     * @param strs Strings to be joined
     */
    public Joined(final CharSequence delimit, final CharSequence... strs) {
        this(delimit, new IterableOf<>(strs));
    }

    /**
     * Ctor.
     * @param delimit Delimit among strings
     * @param strs Strings to be joined
     */
    public Joined(final CharSequence delimit, final Iterable<? extends CharSequence> strs) {
        this(
            new TextOf(delimit),
            new Mapped<>(TextOf::new, strs)
        );
    }

    /**
     * Ctor.
     * @param delimit Delimit among texts
     * @param txts Texts to be joined
     */
    public Joined(final Text delimit, final Text... txts) {
        this(delimit, new IterableOf<>(txts));
    }

    /**
     * Ctor.
     * @param delimit Delimit among texts
     * @param txts Texts to be joined
     */
    public Joined(final CharSequence delimit, final Text... txts) {
        this(new TextOf(delimit), new IterableOf<>(txts));
    }

    /**
     * Ctor.
     * @param delimit Delimit among texts
     * @param txts Texts to be joined
     */
    public Joined(final Text delimit, final Iterable<? extends Text> txts) {
        super(
            new TextOf(
                () -> {
                    final StringJoiner joint = new StringJoiner(delimit.asString());
                    for (final Text text : txts) {
                        joint.add(text.asString());
                    }
                    return joint.toString();
                }
            )
        );
    }
}
