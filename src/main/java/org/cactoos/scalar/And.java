/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Logical conjunction.
 * This class performs short-circuit evaluation in which arguments are
 * executed only if the preceding argument does not suffice to determine
 * the value of the expression.
 *
 * <p>This class could be also used for matching multiple boolean
 * expressions:</p>
 *
 * {@code
 * new And(
 *    new True(),
 *    new True(),
 *    new True()
 * ).value(); // the result is true
 *
 * new And(
 *    new True(),
 *    new False(),
 *    new True()
 * ).value(); // the result is false
 * }
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @see Unchecked
 * @see IoChecked
 * @since 0.8
 */
public final class And implements Scalar<Boolean> {

    /**
     * The iterator.
     */
    private final Iterable<? extends Scalar<Boolean>> origin;

    /**
     * Ctor.
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> And(final Func<? super X, Boolean> func, final X... src) {
        this(func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> And(
        final Func<? super X, Boolean> func,
        final Iterable<? extends X> src
    ) {
        this(
            new Mapped<>(
                item -> new ScalarOf<>(() -> func.apply(item)),
                src
            )
        );
    }

    /**
     * Ctor.
     * @param subject The subject
     * @param conditions Funcs to map
     * @param <X> Type of items in the iterable
     * @since 0.34
     */
    @SafeVarargs
    public <X> And(final X subject, final Func<? super X, Boolean>... conditions) {
        this(subject, new IterableOf<>(conditions));
    }

    /**
     * Ctor.
     * @param subject The subject
     * @param conditions Funcs to map
     * @param <X> Type of items in the iterable
     * @since 0.49
     */
    public <X> And(final X subject, final Iterable<? extends Func<? super X, Boolean>> conditions) {
        this(
            new Mapped<>(
                item -> new ScalarOf<>(() -> item.apply(subject)),
                conditions
            )
        );
    }

    /**
     * Ctor.
     * @param scalar The Scalar.
     */
    @SafeVarargs
    public And(final Scalar<Boolean>... scalar) {
        this(new IterableOf<>(scalar));
    }

    /**
     * Ctor.
     * @param iterable The iterable.
     */
    public And(final Iterable<? extends Scalar<Boolean>> iterable) {
        this.origin = iterable;
    }

    @Override
    public Boolean value() throws Exception {
        boolean result = true;
        for (final Scalar<Boolean> item : this.origin) {
            if (!item.value()) {
                result = false;
                break;
            }
        }
        return result;
    }
}
