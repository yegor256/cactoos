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

package org.cactoos.scalar;

import java.io.InputStream;
import java.util.Iterator;
import org.cactoos.Input;
import org.cactoos.Scalar;

/**
 * Length.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 */
public final class LengthOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -7351954368806143451L;

    /**
     * Ctor.
     * @param items The array
     * @param <T> The type of items
     */
    public <T> LengthOf(final Iterable<T> items) {
        this(() -> {
            final Iterator<T> iterator = items.iterator();
            int size = 0;
            while (iterator.hasNext()) {
                iterator.next();
                ++size;
            }
            return (double) size;
        });
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
    @SuppressWarnings(
        {
            "PMD.CallSuperInConstructor",
            "PMD.ConstructorOnlyInitializesOrCallOtherConstructors"
        }
    )
    public LengthOf(final Input input, final int max) {
        this(() -> {
            if (max == 0) {
                throw new IllegalArgumentException(
                    "Cannot use a buffer limited to zero size"
                );
            }
            try (final InputStream stream = input.stream()) {
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
                return (double) length;
            }
        });
    }

    /**
     * Ctor.
     * @param dnm Double number.
     */
    private LengthOf(final Scalar<Double> dnm) {
        super(dnm);
    }

}
