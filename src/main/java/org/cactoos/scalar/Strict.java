/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;

/**
 * Validates the given {@code origin} against the specified {@code rule}.
 * If the validation fails, the {@code exception} is thrown.
 *
 * @param <T> Element type.
 *
 * @since 0.56
 */
public final class Strict<T> extends ScalarEnvelope<T> {

    /**
     * Ctor.
     *
     * @param origin The value to test againts the {@code rule}.
     * @param rule The rule, that validates {@code origin}.
     * @param exception The exception, that is thrown if validation fails.
     */
    public Strict(
        final Scalar<T> origin,
        final Func<Scalar<T>, Scalar<Boolean>> rule,
        final Func<Scalar<T>, Exception> exception
    ) {
        super(
            new Mapped<>(
                new FuncOf<>(origin),
                new ThrowsOnFalse(
                    new Flattened<>(
                        new ScalarOf<>(rule, origin)
                    ),
                    new ScalarOf<>(exception, origin)
                )
            )
        );
    }
}
