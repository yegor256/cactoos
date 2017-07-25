/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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

/**
 * Bytes.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @see org.cactoos.io.BytesOf
 * @since 0.1
 */
public interface Bytes {

    /**
     * Convert it to the byte array.
     * @return The byte array
     * @throws IOException If fails
     */
    byte[] asBytes() throws IOException;

    /**
     * Bytes check for no nulls.
     *
     * @author Fabricio Cabral (fabriciofx@gmail.com)
     * @version $Id$
     * @since 0.11
     */
    final class NoNulls implements Bytes {
        /**
         * The input.
         */
        private final Bytes origin;
        /**
         * Ctor.
         * @param bytes The input
         */
        public NoNulls(final Bytes bytes) {
            this.origin = bytes;
        }
        @Override
        public byte[] asBytes() throws IOException {
            if (this.origin == null) {
                throw new IllegalArgumentException(
                    "NULL instead of a valid bytes"
                );
            }
            final byte[] bytes = this.origin.asBytes();
            if (bytes == null) {
                throw new IllegalStateException(
                    "NULL instead of a valid byte array"
                );
            }
            return bytes;
        }
    }
}
