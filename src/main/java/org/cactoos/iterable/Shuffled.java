/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Shuffled iterable.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.20
 */
public final class Shuffled<T> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param src The underlying iterable
     * @since 0.23
     */
    @SafeVarargs
    public Shuffled(final T... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The underlying iterable
     */
    public Shuffled(final Iterable<? extends T> src) {
        this(new SecureRandom(), src);
    }

    /**
     * Ctor.
     * @param rnd Randomizer.
     * @param src The underlying iterable
     */
    public Shuffled(final Random rnd, final Iterable<? extends T> src) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Shuffled<>(
                    rnd,
                    src.iterator()
                )
            )
        );
    }

}
