/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Concatenate a Text.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.47
 */
public final class Concatenated extends TextEnvelope {

    /**
     * Ctor.
     * @param txts Texts to be concatenated
     */
    public Concatenated(final Text... txts) {
        this(new IterableOf<>(txts));
    }

    /**
     * Ctor.
     * @param strs CharSequences to be concatenated
     */
    public Concatenated(final CharSequence... strs) {
        this(
            new Mapped<>(
                TextOf::new,
                new IterableOf<>(strs)
            )
        );
    }

    /**
     * Ctor.
     * @param txts Texts to be concatenated
     */
    public Concatenated(final Iterable<? extends Text> txts) {
        super(new Joined(new TextOf(""), txts));
    }
}
