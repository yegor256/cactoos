/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

import org.cactoos.proc.IoCheckedProc;
import org.cactoos.proc.UncheckedProc;

/**
 * Procedure.
 *
 * <p>If you don't want to have any checked exceptions being thrown
 * out of your {@link Proc}, you can use
 * {@link UncheckedProc} decorator. Also
 * you may try {@link IoCheckedProc}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @see org.cactoos.func.FuncOf
 * @since 0.1
 */
@FunctionalInterface
public interface Proc<X> {

    /**
     * Execute it.
     * @param input The argument
     * @throws Exception If fails
     */
    void exec(X input) throws Exception;
}
