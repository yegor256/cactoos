/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.Func;
import org.cactoos.scalar.Unchecked;

/**
 * Mapped iterator.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @param <Y> Type of target item
 * @since 0.1
 */
public final class Mapped<Y> extends IteratorEnvelope<Y> {

    /**
     * Ctor.
     * @param func Func
     * @param iterator Source iterator
     * @param <X> Type of item
     * @checkstyle AnonInnerLengthCheck (60 lines)
     */
    public <X> Mapped(
        final Func<? super X, ? extends Y> func,
        final Iterator<? extends X> iterator
    ) {
        super(
            new Iterator<Y>() {

                @Override
                public boolean hasNext() {
                    return iterator.hasNext();
                }

                @Override
                public Y next() {
                    if (this.hasNext()) {
                        return new Unchecked<>(
                            new org.cactoos.scalar.Mapped<>(
                                func, iterator::next
                            )
                        ).value();
                    }
                    throw new NoSuchElementException();
                }

                @Override
                public void remove() {
                    iterator.remove();
                }

            }
        );
    }

}
