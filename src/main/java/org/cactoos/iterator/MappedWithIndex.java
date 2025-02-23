/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.BiFunc;

/**
 * Mapped with index iterator.
 *
 * <p>
 * There is no thread-safety guarantee.
 * @param <Y> Type of target item
 * @since 1.0.0
 */
public final class MappedWithIndex<Y> extends IteratorEnvelope<Y> {
    /**
     * Ctor.
     * @param func Func
     * @param iterator Source iterator
     * @param <X> Type of item
     */
    public <X> MappedWithIndex(
        final BiFunc<? super X, Integer, ? extends Y> func,
        final Iterator<? extends X> iterator
    ) {
        this(
            new AtomicInteger(-1),
            func,
            iterator
        );
    }

    /**
     * Privated Ctor.
     * @param indexcounter Index Counter
     * @param func Func
     * @param iterator Source iterator
     * @param <X> Type of item
     */
    private <X> MappedWithIndex(
        final AtomicInteger indexcounter,
        final BiFunc<? super X, Integer, ? extends Y> func,
        final Iterator<? extends X> iterator
    ) {
        super(
            new Mapped<>(
                item -> func.apply(item, indexcounter.incrementAndGet()),
                iterator
            )
        );
    }
}
