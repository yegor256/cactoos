/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Bytes;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.bytes.BytesOf;
import org.cactoos.bytes.UncheckedBytes;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Iterator that returns a set of bytes.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.34
 */
public final class IteratorOfBytes implements Iterator<Byte> {

    /**
     * The list of items to iterate, deferred.
     */
    private final Unchecked<byte[]> items;

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
        this((Scalar<byte[]>) () -> new UncheckedBytes(bytes).asBytes());
    }

    /**
     * Ctor.
     * @param data Items to iterate
     */
    public IteratorOfBytes(final byte... data) {
        this((Scalar<byte[]>) () -> data);
    }

    /**
     * Ctor.
     * @param data Items to iterate, deferred
     */
    private IteratorOfBytes(final Scalar<byte[]> data) {
        this.items = new Unchecked<>(new Sticky<>(data));
        this.position = new AtomicInteger(0);
    }

    @Override
    public boolean hasNext() {
        return this.position.intValue() < this.items.value().length;
    }

    @Override
    public Byte next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have any more items"
            );
        }
        return this.items.value()[this.position.getAndIncrement()];
    }
}
