/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

/**
 * Function that accepts two arguments.
 *
 * <p>If you don't want to have any checked exceptions being thrown
 * out of your {@link BiFunc}, you can use
 * {@link org.cactoos.func.UncheckedBiFunc} decorator. Also
 * you may try {@link org.cactoos.func.IoCheckedBiFunc}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @param <Z> Type of output
 * @since 0.9
 */
@FunctionalInterface
public interface BiFunc<X, Y, Z> {

    /**
     * Apply it.
     * @param first The first argument
     * @param second The second argument
     * @return The result
     * @throws Exception If fails
     */
    Z apply(X first, Y second) throws Exception;
}
