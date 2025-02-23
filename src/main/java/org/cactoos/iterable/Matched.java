/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.BiFunc;

/**
 * Matched iterable.
 *
 * Iterates over 2 iterables at the same time and verify that elements with the
 * same position has correlation by the function. The function might be equals,
 * endsWith, greaterThen, nonNull, empty, negative, positive, etc.
 *
 * @param <X> Type of item.
 * @since 0.39
 */
public final class Matched<X> extends IterableEnvelope<X> {
    /**
     * Ctor.
     * @param fst The first part of duplex iterator.
     * @param snd The second part of duplex iterator.
     */
    public Matched(
        final Iterable<? extends X> fst,
        final Iterable<? extends X> snd
    ) {
        this(Object::equals, fst, snd);
    }

    /**
     * Ctor.
     * @param fnc The function to detect the correlation between elements.
     * @param fst The first part of duplex iterator.
     * @param snd The second part of duplex iterator.
     * @param <Y> Type of item in snd.
     */
    public <Y> Matched(
        final BiFunc<? super X, ? super Y, Boolean> fnc,
        final Iterable<? extends X> fst,
        final Iterable<? extends Y> snd
    ) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Matched<>(
                    fnc, fst.iterator(), snd.iterator()
                )
            )
        );
    }
}
