/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import org.cactoos.Bytes;
import org.cactoos.Fallback;
import org.cactoos.scalar.ScalarWithFallback;
import org.cactoos.scalar.Unchecked;

/**
 * Bytes that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.3
 * @todo #1615:30m Extract fallback logic for Bytes
 *  to a separate class in accordance
 *  to other XXXWithFallback classes.
 *  Leave only basic exception handling in this class.
 */
public final class UncheckedBytes implements Bytes {

    /**
     * Scalar with fallback.
     */
    private final Unchecked<byte[]> scalar;

    /**
     * Ctor.
     * @param bts Encapsulated bytes
     */
    public UncheckedBytes(final Bytes bts) {
        this(bts, new Fallback.None<>());
    }

    /**
     * Ctor.
     * @param bts Encapsulated bytes
     * @param fbk Fallback
     * @since 0.5
     */
    @SuppressWarnings("unchecked")
    public UncheckedBytes(final Bytes bts, final Fallback<byte[]> fbk) {
        this.scalar = new Unchecked<>(
            new ScalarWithFallback<>(bts::asBytes, fbk)
        );
    }

    @Override
    public byte[] asBytes() {
        return this.scalar.value();
    }

}
