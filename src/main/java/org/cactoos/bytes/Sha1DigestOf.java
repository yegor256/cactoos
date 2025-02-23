/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import org.cactoos.Input;

/**
 * SHA-1 checksum calculation of {@link Input}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.29
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class Sha1DigestOf extends DigestEnvelope {
    /**
     * Ctor.
     * @param input The input
     */
    public Sha1DigestOf(final Input input) {
        super(input, "SHA-1");
    }

    /**
     * Ctor.
     * @param input The input
     * @param max Buffer size
     */
    public Sha1DigestOf(final Input input, final int max) {
        super(input, max, "SHA-1");
    }
}
