/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.BiFunc;
import org.cactoos.BiProc;
import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.Scalar;

/**
 * Represents many possible inputs as {@link BiFunc}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @param <Z> Type of output
 * @since 0.20
 */
public final class BiFuncOf<X, Y, Z> extends BiFuncEnvelope<X, Y, Z> {

    /**
     * Ctor.
     * @param scalar The scalar
     */
    public BiFuncOf(final Scalar<Z> scalar) {
        this((first, second) -> scalar.value());
    }

    /**
     * Ctor.
     * @param fnc The func
     */
    public BiFuncOf(final Func<X, Z> fnc) {
        this((first, second) -> fnc.apply(first));
    }

    /**
     * Ctor.
     * @param proc The proc
     * @param result Result to return
     */
    public BiFuncOf(final Proc<X> proc, final Z result) {
        this(
            (first, second) -> {
                proc.exec(first);
                return result;
            }
        );
    }

    /**
     * Ctor.
     * @param proc The proc
     * @param result Result to return
     */
    public BiFuncOf(final BiProc<X, Y> proc, final Z result) {
        this(
            (first, second) -> {
                proc.exec(first, second);
                return result;
            }
        );
    }

    /**
     * Ctor.
     * @param fnc The func
     */
    public BiFuncOf(final BiFunc<X, Y, Z> fnc) {
        super(
            fnc::apply
        );
    }
}
