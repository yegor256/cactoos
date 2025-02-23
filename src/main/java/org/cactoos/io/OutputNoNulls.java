/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.io;

import java.io.IOException;
import java.io.OutputStream;
import org.cactoos.Output;

/**
 * Output check for no nulls.
 *
 * @since 0.10
 */
public final class OutputNoNulls implements Output {
    /**
     * The output.
     */
    private final Output origin;

    /**
     * Ctor.
     * @param output The output
     */
    public OutputNoNulls(final Output output) {
        this.origin = output;
    }

    @Override
    public OutputStream stream() throws Exception {
        if (this.origin == null) {
            throw new IOException("NULL instead of a valid output");
        }
        final OutputStream stream = this.origin.stream();
        if (stream == null) {
            throw new IOException("NULL instead of a valid stream");
        }
        return stream;
    }
}
