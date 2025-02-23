/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import org.cactoos.iterable.HeadOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Reversed;

/**
 * Tail portion of the iterator.
 *
 * <p>
 * There is no thread-safety guarantee.
 * </p>
 * @param <T> Element type
 * @since 0.31
 */
public final class TailOf<T> extends IteratorEnvelope<T> {
    /**
     * Ctor.
     * @param num Number of tail elements
     * @param iterator Decorated iterator
     */
    @SuppressWarnings({ "unchecked", "cast" })
    public TailOf(final int num, final Iterator<? extends T> iterator) {
        super(
            (Iterator<T>) new Reversed<>(
                new HeadOf<>(
                    num,
                    new Reversed<>(
                        new IterableOf<>(iterator)
                    )
                )
            ).iterator()
        );
    }
}
