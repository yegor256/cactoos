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
import org.cactoos.Input;
import org.cactoos.scalar.NumberEnvelope;

/**
 * Length of {@link Input}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 */
public final class LengthOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = 512027185282287675L;

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
        super(() -> {
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
                return (double) length;
            }
        });
    }
}
