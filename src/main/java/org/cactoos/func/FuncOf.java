/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.Scalar;

/**
 * Represents many possible inputs as {@link Func}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.12
 */
public final class FuncOf<X, Y> implements Func<X, Y> {

    /**
     * The func.
     */
    private final Func<? super X, ? extends Y> func;

    /**
     * Ctor.
     * @param proc The proc
     * @param result Result to return
     */
    public FuncOf(final Proc<? super X> proc, final Y result) {
        this(
            input -> {
                proc.exec(input);
                return result;
            }
        );
    }

    /**
     * Ctor.
     * @param scalar Origin scalar
     */
    public FuncOf(final Scalar<? extends Y> scalar) {
        this(input -> scalar.value());
    }

    /**
     * Ctor.
     * @param fnc Func
     */
    public FuncOf(final Func<? super X, ? extends Y> fnc) {
        this.func = fnc;
    }

    @Override
    public Y apply(final X input) throws Exception {
        return this.func.apply(input);
    }
}
