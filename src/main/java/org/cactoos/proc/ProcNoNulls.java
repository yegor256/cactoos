/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.Proc;

/**
 * Proc check for no nulls.
 *
 * @param <X> Type of input
 * @since 0.11
 */
public final class ProcNoNulls<X> implements Proc<X> {
    /**
     * The procedure.
     */
    private final Proc<? super X> origin;

    /**
     * Ctor.
     * @param proc The procedure
     */
    public ProcNoNulls(final Proc<? super X> proc) {
        this.origin = proc;
    }

    @Override
    public void exec(final X input) throws Exception {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL instead of a valid procedure"
            );
        }
        if (input == null) {
            throw new IllegalArgumentException(
                "NULL instead of a valid input"
            );
        }
        this.origin.exec(input);
    }
}
