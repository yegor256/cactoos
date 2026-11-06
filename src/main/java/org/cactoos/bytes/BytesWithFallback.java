/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import org.cactoos.Bytes;
import org.cactoos.Fallback;
import org.cactoos.scalar.ScalarWithFallback;

/**
 * Bytes with a fallback plan that enables it to recover from errors.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 1.0
 */
public final class BytesWithFallback implements Bytes {

    /**
     * Scalar with fallback.
     */
    private final ScalarWithFallback<byte[]> scalar;

    /**
     * Ctor.
     *
     * @param bytes Encapsulated bytes
     */
    public BytesWithFallback(final Bytes bytes) {
        this(bytes, new Fallback.None<>());
    }

    /**
     * Ctor.
     *
     * @param bytes Encapsulated bytes
     * @param fbk Fallback
     */
    @SuppressWarnings("unchecked")
    public BytesWithFallback(final Bytes bytes, final Fallback<byte[]> fbk) {
        this.scalar = new ScalarWithFallback<>(bytes::asBytes, fbk);
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.scalar.value();
    }
}
