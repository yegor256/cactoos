/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

/**
 * Function.
 *
 * <p>If you don't want to have any checked exceptions being thrown
 * out of your {@link Func}, you can use
 * {@link org.cactoos.func.UncheckedFunc} decorator. Also
 * you may try {@link org.cactoos.func.IoCheckedFunc}.</p>
 *
 * <p>If you want to cache the result of the {@link Func} and
 * make sure it doesn't calculate anything twice, you can use
 * {@link org.cactoos.func.StickyFunc} decorator.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @see org.cactoos.func.StickyFunc
 * @see org.cactoos.func.UncheckedFunc
 * @see org.cactoos.func.IoCheckedFunc
 * @since 0.1
 */
@FunctionalInterface
public interface Func<X, Y> {

    /**
     * Apply it.
     * @param input The argument
     * @return The result
     * @throws Exception If fails
     */
    Y apply(X input) throws Exception;
}
