/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import org.cactoos.Bytes;
import org.cactoos.Fallback;
import org.cactoos.scalar.Unchecked;

/**
 * Bytes that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.3
 */
public final class UncheckedBytes implements Bytes {

    /**
     * Bytes with fallback, unchecked.
     */
    private final Unchecked<byte[]> scalar;

    /**
     * Ctor.
     *
     * @param bts Encapsulated bytes
     */
    public UncheckedBytes(final Bytes bts) {
        this(bts, new Fallback.None<>());
    }

    /**
     * Ctor.
     *
     * @param bts Encapsulated bytes
     * @param fbk Fallback
     * @since 0.5
     */
    public UncheckedBytes(final Bytes bts, final Fallback<byte[]> fbk) {
        this.scalar = new Unchecked<>(new BytesWithFallback(bts, fbk)::asBytes);
    }

    @Override
    public byte[] asBytes() {
        return this.scalar.value();
    }
}
