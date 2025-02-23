/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.Bytes;
import org.cactoos.Text;
import org.cactoos.bytes.BytesOf;
import org.cactoos.iterator.IteratorOfBytes;

/**
 * Iterable of bytes.
 *
 * @since 1.0
 */
public final class IterableOfBytes extends IterableEnvelope<Byte> {

    /**
     * Ctor.
     * @param txt Text
     */
    public IterableOfBytes(final Text txt) {
        this(new BytesOf(txt));
    }

    /**
     * Ctor.
     * @param str String
     */
    public IterableOfBytes(final String str) {
        this(new BytesOf(str));
    }

    /**
     * Ctor.
     * @param bytes Bytes
     */
    public IterableOfBytes(final byte... bytes) {
        this(new BytesOf(bytes));
    }

    /**
     * Ctor.
     * @param bytes Bytes to iterate
     */
    public IterableOfBytes(final Bytes bytes) {
        super(new IterableOf<>(() -> new IteratorOfBytes(bytes)));
    }
}
