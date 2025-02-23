/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.bytes;

import org.cactoos.Bytes;

/**
 * Bytes check for no nulls.
 *
 * @since 0.11
 */
public final class NoNulls implements Bytes {
    /**
     * The input.
     */
    private final Bytes origin;

    /**
     * Ctor.
     * @param bytes The input
     */
    public NoNulls(final Bytes bytes) {
        this.origin = bytes;
    }

    @Override
    public byte[] asBytes() throws Exception {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL instead of a valid bytes"
            );
        }
        final byte[] bytes = this.origin.asBytes();
        if (bytes == null) {
            throw new IllegalStateException(
                "NULL instead of a valid byte array"
            );
        }
        return bytes;
    }
}
