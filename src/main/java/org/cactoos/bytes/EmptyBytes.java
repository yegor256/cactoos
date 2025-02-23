/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import org.cactoos.Bytes;

/**
 * Bytes with no data.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.2
 */
public final class EmptyBytes implements Bytes {

    /**
     * Empty array of bytes.
     */
    private static final byte[] EMPTY = {};

    @Override
    public byte[] asBytes() {
        return EmptyBytes.EMPTY;
    }

}
