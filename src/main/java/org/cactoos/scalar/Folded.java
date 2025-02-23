/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.BiFunc;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;

/**
 * Iterable, which elements are "folded" through the func.
 *
 * @param <X> Type of input and output
 * @param <T> Type of element
 * @since 0.30
 */
public final class Folded<X, T> implements Scalar<X> {

    /**
     * Original iterable.
     */
    private final Iterable<? extends T> iterable;

    /**
     * Input.
     */
    private final X input;

    /**
     * Func.
     */
    private final BiFunc<? super X, ? super T, ? extends X> func;

    /**
     * Ctor.
     * @param ipt Input
     * @param fnc Func original
     * @param list Array of items
     */
    @SafeVarargs
    public Folded(final X ipt, final BiFunc<? super X, ? super T, ? extends X> fnc,
        final T... list) {
        this(ipt, fnc, new IterableOf<>(list));
    }

    /**
     * Ctor.
     * @param ipt Input
     * @param fnc Func original
     * @param list List of items
     */
    public Folded(final X ipt, final BiFunc<? super X, ? super T, ? extends X> fnc,
        final Iterable<? extends T> list) {
        this.iterable = list;
        this.input = ipt;
        this.func = fnc;
    }

    @Override
    public X value() throws Exception {
        X memo = this.input;
        for (final T item : this.iterable) {
            memo = this.func.apply(memo, item);
        }
        return memo;
    }

}
