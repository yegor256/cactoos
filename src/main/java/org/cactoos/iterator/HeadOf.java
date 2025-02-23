/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;

/**
 * Head portion of the iterator.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.8
 */
public final class HeadOf<T> extends IteratorEnvelope<T> {

    /**
     * Ctor.
     * @param num Num of head elements
     * @param iterator Decorated iterator
     */
    public HeadOf(final int num, final Iterator<? extends T> iterator) {
        super(new Sliced<>(0, num, iterator));
    }

}
