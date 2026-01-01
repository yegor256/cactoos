/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * A decorator of an {@link Iterator} that returns no NULL.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 0.27
 */
public final class NoNulls<X> implements Iterator<X> {

    /**
     * Iterator.
     */
    private final Iterator<? extends X> iterator;

    /**
     * Position.
     */
    private final AtomicLong pos;

    /**
     * Ctor.
     * @param src Source iterable
     */
    public NoNulls(final Iterator<? extends X> src) {
        this.iterator = src;
        this.pos = new AtomicLong();
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public X next() {
        final X next = this.iterator.next();
        if (next == null) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Item #%d of %s is NULL",
                        this.pos.get(), this.iterator
                    )
                ).asString()
            );
        }
        this.pos.incrementAndGet();
        return next;
    }

    @Override
    public void remove() {
        this.iterator.remove();
    }
}
