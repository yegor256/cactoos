/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;

/**
 * Logical exclusive or.

 * Returns {@link True} when an odd number of elements have value true,
 * otherwise return {@link False}.
 *
 * <p>This class could be also used for matching multiple boolean
 * expressions:</p>
 *
 * {@code
 * new Xor(
 *    new True(),
 *    new True(),
 *    new True()
 * ).value(); // the result is true
 *
 * new Xor(
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
 * @since 0.49
 */
public final class Xor extends ScalarEnvelope<Boolean> {

    /**
     * Ctor.
     * @param scalar The Scalar.
     */
    @SafeVarargs
    public Xor(final Scalar<Boolean>... scalar) {
        this(new IterableOf<>(scalar));
    }

    /**
     * Ctor.
     * @param iterable The iterable.
     */
    public Xor(final Iterable<? extends Scalar<Boolean>> iterable) {
        super(new Reduced<>((a, b) -> a ^ b, new Joined<>(new False(), iterable))
        );
    }
}
