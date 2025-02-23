/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;
import org.cactoos.iterable.Filtered;

/**
 * Find first element in a list that satisfies specified condition.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of result
 * @since 0.32
 */
public final class FirstOf<T> implements Scalar<T> {

    /**
     * Condition for getting the element.
     */
    private final Func<? super T, Boolean> condition;

    /**
     * Source iterable.
     */
    private final Iterable<? extends T> source;

    /**
     * Fallback used if no value matches.
     */
    private final Scalar<? extends T> fallback;

    /**
     * Constructor with default condition (always `true`) and plain fallback.
     * @param src Source iterable
     * @param fbck Fallback used if no value matches
     */
    public FirstOf(final Iterable<? extends T> src, final T fbck) {
        this(
            new FuncOf<>(new True()),
            src,
            () -> fbck
        );
    }

    /**
     * Constructor with default condition (always `true`).
     * @param src Source iterable
     * @param fbck Fallback used if no value matches
     */
    public FirstOf(final Iterable<? extends T> src, final Scalar<? extends T> fbck) {
        this(
            new FuncOf<>(new True()),
            src,
            fbck
        );
    }

    /**
     * Constructor.
     * @param cond Condition for getting the element
     * @param src Source iterable
     * @param fbck Fallback used if no value matches
     */
    public FirstOf(
        final Func<? super T, Boolean> cond,
        final Iterable<? extends T> src,
        final Scalar<? extends T> fbck
    ) {
        this.condition = cond;
        this.source = src;
        this.fallback = fbck;
    }

    @Override
    public T value() throws Exception {
        return new ItemAt<>(
            0,
            new FuncOf<>(this.fallback),
            new Filtered<>(this.condition, this.source)
        ).value();
    }
}
