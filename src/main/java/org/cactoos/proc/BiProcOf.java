/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.BiFunc;
import org.cactoos.BiProc;
import org.cactoos.Func;
import org.cactoos.Proc;

/**
 * Represents many possible inputs as {@link BiProc}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @since 0.50
 */
public final class BiProcOf<X, Y> extends BiProcEnvelope<X, Y> {

    /**
     * Ctor.
     * @param func The function
     */
    public BiProcOf(final Func<? super X, ?> func) {
        this(x -> {
            func.apply(x);
        });
    }

    /**
     * Ctor.
     * @param prc The procedure
     */
    public BiProcOf(final Proc<? super X> prc) {
        this(
            prc,
            yvl -> {
            }
        );
    }

    /**
     * Ctor.
     * @param first The procedure on first arg.
     * @param second The procedure on second arg.
     */
    public BiProcOf(
        final Proc<? super X> first,
        final Proc<? super Y> second
    ) {
        this(
            (xvl, yvl) -> {
                first.exec(xvl);
                second.exec(yvl);
            }
        );
    }

    /**
     * Ctor.
     * @param func The bi function
     */
    public BiProcOf(final BiFunc<? super X, ? super Y, ?> func) {
        this((first, second) -> {
            func.apply(first, second);
        });
    }

    /**
     * Ctor.
     * @param biprc The bi procedure
     */
    public BiProcOf(final BiProc<? super X, ? super Y> biprc) {
        super(biprc);
    }
}
