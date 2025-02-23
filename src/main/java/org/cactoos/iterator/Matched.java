/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import org.cactoos.BiFunc;
import org.cactoos.func.UncheckedBiFunc;
import org.cactoos.text.FormattedText;

/**
 * Matched iterator.
 *
 * Iterates over 2 iterators at the same time and verify that elements with the
 * same position has correlation by the function. The function might be equals,
 * endsWith, greaterThen, nonNull, empty, negative, positive, etc.
 *
 * @param <X> Type of item in first iterator.
 * @param <Y> Type of item in second iterator.
 * @since 0.47
 */
public final class Matched<X, Y> implements Iterator<X> {

    /**
     * The correlation function.
     */
    private final UncheckedBiFunc<? super X, ? super Y, Boolean> func;

    /**
     * First iterator.
     */
    private final Iterator<? extends X> first;

    /**
     * Second iterator.
     */
    private final Iterator<? extends Y> second;

    /**
     * Ctor.
     * @param fnc The function to detect the correlation between elements.
     * @param fst The first part of duplex iterator.
     * @param snd The second part of duplex iterator.
     */
    public Matched(
        final BiFunc<? super X, ? super Y, Boolean> fnc,
        final Iterator<? extends X> fst,
        final Iterator<? extends Y> snd
    ) {
        this.func = new UncheckedBiFunc<>(fnc);
        this.first = fst;
        this.second = snd;
    }

    @Override
    public boolean hasNext() {
        return this.first.hasNext() || this.second.hasNext();
    }

    @Override
    public X next() {
        if (!this.first.hasNext() || !this.second.hasNext()) {
            throw new IllegalStateException("Size mismatch of iterators");
        }
        final X fvl = this.first.next();
        final Y svl = this.second.next();
        if (!this.func.apply(fvl, svl)) {
            throw new IllegalStateException(
                new FormattedText(
                    "There is no correlation between `%s` and `%s`.",
                    fvl, svl
                ).toString()
            );
        }
        return fvl;
    }

}
