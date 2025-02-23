/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.Func;
import org.cactoos.Scalar;

/**
 * Filtered iterable.
 *
 * <p>You can use it in order to create a declarative/lazy
 * version of a filtered collection/iterable. For example,
 * this code will create a list of two strings "hello" and "world":</p>
 *
 * <pre> Iterable&lt;String&gt; list = new Filtered&lt;&gt;(
 *   new ArrayOf&lt;&gt;(
 *     "hey", "hello", "world"
 *   ),
 *   input -&gt; input.length() &gt; 4
 * );
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @see Filtered
 * @since 0.1
 */
public final class Filtered<X> extends IterableEnvelope<X> {

    /**
     * Ctor.
     * @param fnc Predicate
     * @param src Source iterable
     * @since 0.21
     */
    @SafeVarargs
    public Filtered(final Func<? super X, Boolean> fnc, final X... src) {
        this(fnc, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param fnc Predicate
     * @param src Source iterable
     */
    public Filtered(final Func<? super X, Boolean> fnc, final Iterable<? extends X> src) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Filtered<>(fnc, src.iterator())
            )
        );
    }

    /**
     * Ctor.
     * @param src Source iterable
     * @param fnc Predicate
     */
    public Filtered(final Iterable<? extends X> src, final Func<? super X, Scalar<Boolean>> fnc) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Filtered<>(
                    src.iterator(),
                    fnc
                )
            )
        );
    }
}
