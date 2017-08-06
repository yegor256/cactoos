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
package org.cactoos.scalar;

import org.cactoos.Scalar;
import org.cactoos.text.JoinedText;

/**
 * No null.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @param <T> Type of entity to validate
 * @since 0.13.2
 */
public final class NoNull<T> implements Scalar<T> {

    /**
     * The origin scalar.
     */
    private final Scalar<T> origin;

    /**
     * Ctor.
     * @param src The scalar
     */
    public NoNull(final T src) {
        this(() -> src);
    }

    /**
     * Ctor.
     * @param src The encapsulated value type
     */
    public NoNull(final Scalar<T> src) {
        this.origin = src;
    }

    @Override
    public T value() throws Exception {
        final T val = this.origin.value();
        if (val == null) {
            throw new IllegalStateException(
                new JoinedText(
                    "\n",
                    "\nNull is an invalid value in Cactoos:",
                    "    - If required, try a null object instead.\n"
                ).asString()
            );
        }
        return val;
    }
}
