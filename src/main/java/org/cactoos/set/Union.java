/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
        this(new SetOf<>(first), new SetOf<>(second));
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
            new SetOf<T>(
                new IterableOf<T>(
                    () -> new Unchecked<>(first).value().iterator()
                )
            ),
            new SetOf<T>(
                new IterableOf<T>(
                    () -> new Unchecked<>(second).value().iterator()
                )
            )
        );
    }

    /**
     * Ctor.
     * @param first First set
     * @param second Second set
     */
    public Union(final Set<T> first, final Set<T> second) {
        super(new HashSet<T>(first) {
            private static final long serialVersionUID = 1L;
            {
                this.addAll(second);
            }
        });
    }
}
