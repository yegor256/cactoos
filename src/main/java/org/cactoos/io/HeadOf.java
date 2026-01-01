/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import org.cactoos.Input;

/**
 * Input that only shows the first N bytes of the original input.
 *
 * @since 0.31
 */
public final class HeadOf implements Input {

    /**
     * The original input.
     */
    private final Input origin;

    /**
     * Limit of bytes that can be read from the beginning.
     */
    private final int length;

    /**
     * Ctor.
     * @param orig The original input.
     * @param len Limit of bytes that can be read from the beginning.
     */
    public HeadOf(final Input orig, final int len) {
        this.origin = orig;
        this.length = len;
    }

    @Override
    public InputStream stream() throws Exception {
        return new HeadInputStream(this.origin.stream(), this.length);
    }
}
