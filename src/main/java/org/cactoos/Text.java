/**
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

/**
 * Text.
 *
 * <p>If you don't want to have any checked exceptions being thrown
 * out of your {@link Text}, you can use
 * {@link UncheckedText} decorator.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @see org.cactoos.text.TextOf
 * @since 0.1
 */
public interface Text {

    /**
     * Convert it to the string.
     * @return The string
     * @throws Exception If fails
     */
    String asString() throws Exception;

    /**
     * Text check for no nulls.
     *
     * <p>There is no thread-safety guarantee.
     *
     * @author Fabricio Cabral (fabriciofx@gmail.com)
     * @version $Id$
     * @since 0.11
     */
    final class NoNulls implements Text {
        /**
         * The origin text.
         */
        private final Text origin;
        /**
         * Ctor.
         * @param text The text
         */
        public NoNulls(final Text text) {
            this.origin = text;
        }
        @Override
        public String asString() throws Exception {
            if (this.origin == null) {
                throw new IllegalArgumentException(
                    "NULL instead of a valid text"
                );
            }
            final String string = this.origin.asString();
            if (string == null) {
                throw new IllegalStateException(
                    "NULL instead of a valid result string"
                );
            }
            return string;
        }
    }

}
