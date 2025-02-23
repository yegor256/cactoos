/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;

/**
 * Ternary operation.
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * <pre>{@code
 * new Ternary<>(
 *     scalar,
 *     value -> value > 3,
 *     value -> true,
 *     value -> false
 * ).value() // will be equal to true
 * }</pre>
 *
 * @param <T> Type of item.
 * @since 0.8
 */
public final class Ternary<T> extends ScalarEnvelope<T> {

    /**
     * Ctor.
     * @param cnd The condition
     * @param cons The consequent
     * @param alter The alternative
     * @since 0.9
     */
    public Ternary(final boolean cnd, final T cons, final T alter) {
        this(() -> cnd, cons, alter);
    }

    /**
     * Ctor.
     * @param cnd The condition
     * @param cons The consequent
     * @param alter The alternative
     */
    public Ternary(final Scalar<Boolean> cnd, final T cons, final T alter) {
        this(cnd, () -> cons, () -> alter);
    }

    /**
     * Ctor.
     * @param cnd The condition
     * @param cons The consequent
     * @param alter The alternative
     * @since 0.9
     */
    public Ternary(
        final boolean cnd,
        final Scalar<? extends T> cons,
        final Scalar<? extends T> alter
    ) {
        this(new Constant<>(cnd), cons, alter);
    }

    /**
     * Ctor.
     * @param cnd The condition
     * @param cons The consequent
     * @param alter The alternative
     * @since 0.9
     */
    public Ternary(
        final Scalar<Boolean> cnd,
        final Scalar<? extends T> cons,
        final Scalar<? extends T> alter
    ) {
        this(
            new Object(),
            new FuncOf<>(cnd),
            new FuncOf<>(cons),
            new FuncOf<>(alter)
        );
    }

    /**
     * Ctor.
     * @param input The input to pass to all of them
     * @param cnd The condition
     * @param cons The consequent
     * @param alter The alternative
     * @param <X> Type of input
     * @since 0.9
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public <X> Ternary(
        final X input,
        final Func<? super X, Boolean> cnd,
        final Func<? super X, ? extends T> cons,
        final Func<? super X, ? extends T> alter
    ) {
        this(new Constant<>(input), cnd, cons, alter);
    }

    /**
     * Ctor.
     * @param input The input to pass to all of them
     * @param cnd The condition
     * @param cons The consequent
     * @param alter The alternative
     * @param <X> Type of input
     * @since 0.9
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public <X> Ternary(
        final Scalar<? extends X> input,
        final Func<? super X, Boolean> cnd,
        final Func<? super X, ? extends T> cons,
        final Func<? super X, ? extends T> alter
    ) {
        super(() -> {
            final X inp = input.value();
            final Func<? super X, ? extends T> result;
            if (cnd.apply(inp)) {
                result = cons;
            } else {
                result = alter;
            }
            return result.apply(inp);
        });
    }
}
