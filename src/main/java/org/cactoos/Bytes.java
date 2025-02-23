/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

/**
 * Bytes.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @see org.cactoos.bytes.BytesOf
 * @since 0.1
 */
@FunctionalInterface
public interface Bytes {

    /**
     * Convert it to the byte array.
     * @return The byte array
     * @throws Exception If fails
     */
    byte[] asBytes() throws Exception;

}
