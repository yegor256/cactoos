/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;
import org.cactoos.Input;

/**
 * Input check for no nulls.
 *
 * @since 0.10
 */
public final class InputNoNulls implements Input {
    /**
     * The input.
     */
    private final Input origin;

    /**
     * Ctor.
     * @param input The input
     */
    public InputNoNulls(final Input input) {
        this.origin = input;
    }

    @Override
    public InputStream stream() throws Exception {
        if (this.origin == null) {
            throw new IOException("NULL instead of a valid input");
        }
        final InputStream stream = this.origin.stream();
        if (stream == null) {
            throw new IOException("NULL instead of a valid stream");
        }
        return stream;
    }
}
