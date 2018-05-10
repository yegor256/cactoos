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
package org.cactoos;

import java.io.IOException;
import java.io.InputStream;

/**
 * Input.
 *
 * <p>Here is for example how {@link Input} can be used
 * in order to read the content of a text file:</p>
 *
 * <pre> String content = new TextOf(
 *   new InputOf(new File("/tmp/names.txt"))
 * ).asString();</pre>
 *
 * <p>Here {@link org.cactoos.io.InputOf} implements
 * {@link Input} and behaves like
 * one, providing read-only access to
 * the encapsulated {@link java.io.File}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @see org.cactoos.io.InputOf
 * @since 0.1
 */
public interface Input {

    /**
     * Get read access to it.
     * @return InputStream to read from
     * @throws Exception If something goes wrong
     */
    InputStream stream() throws Exception;

    /**
     * Input check for no nulls.
     *
     * @since 0.10
     */
    final class NoNulls implements Input {
        /**
         * The input.
         */
        private final Input origin;
        /**
         * Ctor.
         * @param input The input
         */
        public NoNulls(final Input input) {
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
}
