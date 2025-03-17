/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Unchecked;

/**
 * Set intersection.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Type of item
 * @since 0.49
 */
public final class Intersection<T> extends SetEnvelope<T> {

    /**
     * Ctor.
     * @param first First set
     * @param second Second set
     * @since 0.49
     */
    public Intersection(final Iterable<T> first, final Iterable<T> second) {
        super(
            computeIntersection(
                new SetOf<>(first),
                new SetOf<>(second)
            )
        );
    }

    /**
     * Ctor.
     * @param first First iterator
     * @param second Second iterator
     * @since 0.49
     */
    public Intersection(final Iterator<T> first, final Iterator<T> second) {
        this(
            new IterableOf<>(first),
            new IterableOf<>(second)
        );
    }

    /**
     * Ctor.
     * @param first First iterable supplier
     * @param second Second iterable supplier
     * @since 0.49
     */
    public Intersection(
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
     * @since 0.49
     */
    public Intersection(final SetOf<T> first, final SetOf<T> second) {
        super(computeIntersection(first, second));
    }

    /**
     * Compute the intersection between two sets.
     * @param first The first set
     * @param second The second set
     * @param <E> Type of elements
     * @return The intersection set (elements in both first and second)
     * @since 0.49
     */
    private static <E> SetOf<E> computeIntersection(
        final SetOf<E> first,
        final SetOf<E> second
    ) {
        final SetOf<E> result = new SetOf<>(first);
        result.retainAll(second);
        return result;
    }
}
