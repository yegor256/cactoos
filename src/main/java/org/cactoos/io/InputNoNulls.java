/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
