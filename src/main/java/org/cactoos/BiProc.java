/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

import org.cactoos.proc.IoCheckedBiProc;
import org.cactoos.proc.UncheckedBiProc;

/**
 * Proc that accepts two arguments.
 *
 * <p>If you don't want to have any checked exceptions being thrown
 * out of your {@link BiProc}, you can use
 * {@link UncheckedBiProc} decorator. Also
 * you may try {@link IoCheckedBiProc}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @since 0.20
 */
@FunctionalInterface
public interface BiProc<X, Y> {

    /**
     * Execute it.
     * @param first The first argument
     * @param second The second argument
     * @throws Exception If fails
     */
    void exec(X first, Y second) throws Exception;
}
