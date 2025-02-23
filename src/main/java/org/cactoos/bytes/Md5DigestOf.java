/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import org.cactoos.Input;

/**
 * MD5 checksum calculation of {@link Input}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.29
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class Md5DigestOf extends DigestEnvelope {
    /**
     * Ctor.
     * @param input The input
     */
    public Md5DigestOf(final Input input) {
        super(input, "MD5");
    }

    /**
     * Ctor.
     * @param input The input
     * @param max Buffer size
     */
    public Md5DigestOf(final Input input, final int max) {
        super(input, max, "MD5");
    }
}
