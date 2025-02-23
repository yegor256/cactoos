/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

/**
 * Function that accepts three arguments.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @param <Z> Type of input
 * @param <W> Type of output
 * @since 0.0
 */
@FunctionalInterface
public interface TriFunc<X, Y, Z, W> {

    /**
     * Apply it.
     * @param first The first argument
     * @param second The second argument
     * @param third The third argument
     * @return The result
     * @throws Exception If fails
     */
    W apply(X first, Y second, Z third) throws Exception;
}
