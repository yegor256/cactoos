/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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

import java.io.InputStream;
import java.util.Iterator;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Length.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @since 0.1
 */
public final class LengthOf extends ScalarEnvelope<Long> {

    /**
     * Ctor.
     * @param items The array
     * @param <T> The type of items
     */
    public LengthOf(final Iterable<?> items) {
        this(() -> {
            final Iterator<?> iterator = items.iterator();
            long size = 0;
            while (iterator.hasNext()) {
                iterator.next();
                ++size;
            }
            return size;
        });
    }

    /**
     * Ctor.
     * Character-length of Text.
     *
     * @param text The input
     */
    public LengthOf(final Text text) {
        this(() -> (long) text.asString().length());
    }

    /**
     * Ctor.
     * @param input The input
     */
    public LengthOf(final Input input) {
        // @checkstyle MagicNumber (1 line)
        this(input, 16 << 10);
    }

    /**
     * Ctor.
     * @param input The input
     * @param max Buffer size
     */
    public LengthOf(final Input input, final int max) {
        this(() -> {
            if (max == 0) {
                throw new IllegalArgumentException(
                    "Cannot use a buffer limited to zero size"
                );
            }
            try (InputStream stream = input.stream()) {
                final byte[] buf = new byte[max];
                long length = 0L;
                while (true) {
                    final int len = stream.read(buf);
                    if (len > 0) {
                        length += (long) len;
                    }
                    if (len < 0) {
                        break;
                    }
                }
                return length;
            }
        });
    }

    /**
     * Ctor.
     * @param nm number.
     */
    private LengthOf(final Scalar<Long> nm) {
        super(nm);
    }
}
