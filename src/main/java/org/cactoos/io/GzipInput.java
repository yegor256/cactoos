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

import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import org.cactoos.Input;

/**
 * Input that reads compressed data from the GZIP file format.
 *
 * @since 0.29
 */
public final class GzipInput implements Input {

    /**
     * The input.
     */
    private final Input origin;

    /**
     * The buffer size.
     */
    private final int size;

    /**
     * Ctor.
     * @param input The input.
     */
    public GzipInput(final Input input) {
        // @checkstyle MagicNumberCheck (1 line)
        this(input, 16 << 10);
    }

    /**
     * Ctor.
     * @param input The input
     * @param max Max length of the buffer
     */
    public GzipInput(final Input input, final int max) {
        this.origin = input;
        this.size = max;
    }

    @Override
    public InputStream stream() throws Exception {
        return new GZIPInputStream(
            this.origin.stream(),
            this.size
        );
    }
}
