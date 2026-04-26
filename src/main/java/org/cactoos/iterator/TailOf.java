/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;

/**
 * Tail portion of the iterator.
 *
 * <p>
 * There is no thread-safety guarantee.
 * </p>
 *
 * @param <T> Element type
 * @since 0.31
 */
public final class TailOf<T> extends IteratorEnvelope<T> {

    /**
     * Ctor.
     * @param num Number of tail elements
     * @param iterator Decorated iterator
     */
    public TailOf(final int num, final Iterator<? extends T> iterator) {
        super(
            new Reversed<>(
                new HeadOf<>(
                    num,
                    new Reversed<>(iterator)
                )
            )
        );
    }
}
