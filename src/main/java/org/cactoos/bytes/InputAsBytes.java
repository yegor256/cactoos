/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.io.OutputTo;
import org.cactoos.io.TeeInput;

/**
 * Input as Byte Array.
 *
 * <p>This class is for internal use only. Use {@link BytesOf} instead.</p>
 *
 * <p>There is no thread-safety guarantee.
 * @since 0.1
 */
public final class InputAsBytes implements Bytes {

    /**
     * The input.
     */
    private final Input source;

    /**
     * The buffer size.
     */
    private final int size;

    /**
     * Ctor.
     * @param input The input
     */
    InputAsBytes(final Input input) {
        this(input, 16 << 10);
    }

    /**
     * Ctor.
     * @param input The input
     * @param max Max length of the buffer for reading
     */
    InputAsBytes(final Input input, final int max) {
        this.source = input;
        this.size = max;
    }

    @Override
    public byte[] asBytes() throws Exception {
        try (
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream stream = new TeeInput(
                this.source,
                new OutputTo(baos)
            ).stream()
        ) {
            final byte[] buf = new byte[this.size];
            while (true) {
                if (stream.read(buf) < 0) {
                    break;
                }
            }
            return baos.toByteArray();
        }
    }
}
