/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Unchecked;

/**
 * Set union.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Type of item
 * @since 0.58.0
 */
public final class Union<T> extends SetEnvelope<T> {

    /**
     * Ctor.
     * @param first First set
     * @param second Second set
     */
    public Union(final Iterable<T> first, final Iterable<T> second) {
        super(
            computeUnion(
                new SetOf<>(first),
                new SetOf<>(second)
            )
        );
    }

    /**
     * Ctor.
     * @param first First iterator
     * @param second Second iterator
     */
    public Union(final Iterator<T> first, final Iterator<T> second) {
        this(
            new IterableOf<>(first),
            new IterableOf<>(second)
        );
    }

    /**
     * Ctor.
     * @param first First iterable supplier
     * @param second Second iterable supplier
     */
    public Union(
        final Scalar<Iterable<T>> first,
        final Scalar<Iterable<T>> second
    ) {
        this(
            new Unchecked<>(first).value(),
            new Unchecked<>(second).value()
        );
    }

    /**
     * Ctor.
     * @param first First set
     * @param second Second set
     */
    public Union(final SetOf<T> first, final SetOf<T> second) {
        super(computeUnion(first, second));
    }

    /**
     * Compute the union of two sets.
     * @param first The first set
     * @param second The second set
     * @param <E> Type of elements
     * @return The union set (elements in either first or second)
     */
    private static <E> SetOf<E> computeUnion(
        final SetOf<E> first,
        final SetOf<E> second
    ) {
        final SetOf<E> result = new SetOf<>(first);
        result.addAll(second);
        return result;
    }
}
