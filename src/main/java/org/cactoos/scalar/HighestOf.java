/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Find the highest item.
 *
 * <p>Here is how you can use it to
 * find highest of {@link Comparable} items:</p>
 *
 * <pre>
 * final String highest = new HighestOf&lt;String&gt;(
 *         () -&gt; "Banana", () -&gt; "Apple", () -&gt; "Orange"
 *     ).value();
 * // -&gt; highest == "Orange"
 *
 * final Character highestChar = new HighestOf&lt;&gt;('B', 'U', 'G').value();
 * // -&gt; highestChar == 'U'
 * </pre>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Scalar type
 * @see Unchecked
 * @see IoChecked
 * @since 0.29
 */
public final class HighestOf<T extends Comparable<? super T>> extends ScalarEnvelope<T> {

    /**
     * Ctor.
     * @param items The comparable items
     */
    @SafeVarargs
    public HighestOf(final T... items) {
        this(
            new Mapped<>(
                item -> () -> item,
                items
            )
        );
    }

    /**
     * Ctor.
     * @param scalars The scalars
     */
    @SafeVarargs
    public HighestOf(final Scalar<? extends T>... scalars) {
        this(new IterableOf<>(scalars));
    }

    /**
     * Ctor.
     * @param iterable The items
     */
    public HighestOf(final Iterable<? extends Scalar<? extends T>> iterable) {
        super(
            new Reduced<>(
                (first, second) -> {
                    final T value;
                    if (first.compareTo(second) > 0) {
                        value = first;
                    } else {
                        value = second;
                    }
                    return value;
                },
                iterable
            )
        );
    }
}
