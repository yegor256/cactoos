/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Bytes;
import org.cactoos.Text;
import org.cactoos.bytes.BytesOf;
import org.cactoos.bytes.UncheckedBytes;

/**
 * Iterator that returns a set of bytes.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.34
 */
public final class IteratorOfBytes implements Iterator<Byte> {

    /**
     * The list of items to iterate.
     */
    private final byte[] items;

    /**
     * Current position.
     */
    private final AtomicInteger position;

    /**
     * Ctor.
     * @param txt Text to iterate
     */
    public IteratorOfBytes(final Text txt) {
        this(new BytesOf(txt));
    }

    /**
     * Ctor.
     * @param str String to iterate
     */
    public IteratorOfBytes(final String str) {
        this(new BytesOf(str));
    }

    /**
     * Ctor.
     * @param bytes Bytes to iterate
     */
    public IteratorOfBytes(final Bytes bytes) {
        this(new UncheckedBytes(bytes).asBytes());
    }

    /**
     * Ctor.
     * @param items Items to iterate
     */
    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public IteratorOfBytes(final byte... items) {
        this.items = items;
        this.position = new AtomicInteger(0);
    }

    @Override
    public boolean hasNext() {
        return this.position.intValue() < this.items.length;
    }

    @Override
    public Byte next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have any more items"
            );
        }
        return this.items[this.position.getAndIncrement()];
    }
}
