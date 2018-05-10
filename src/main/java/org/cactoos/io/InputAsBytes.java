/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.cactoos.Bytes;
import org.cactoos.Input;

/**
 * Input as Byte Array.
 *
 * <p>This class is for internal use only. Use {@link BytesOf} instead.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 */
final class InputAsBytes implements Bytes {

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
        // @checkstyle MagicNumber (1 line)
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
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final InputStream stream = new TeeInput(
                this.source, new OutputTo(baos)
            ).stream()) {
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
