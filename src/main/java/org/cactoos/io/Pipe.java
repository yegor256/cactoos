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
package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Input to Output copying pipe.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class Pipe {

    /**
     * The source.
     */
    private final Input source;

    /**
     * The destination.
     */
    private final Output target;

    /**
     * Buffer size.
     */
    private final int size;

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     */
    public Pipe(final Input input, final Output output) {
        // @checkstyle MagicNumber (1 line)
        this(input, output, 1024);
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     * @param buf Buffer length
     */
    public Pipe(final Input input, final Output output, final int buf) {
        this.source = input;
        this.target = output;
        this.size = buf;
    }

    /**
     * Copy.
     * @throws IOException If fails
     */
    public void push() throws IOException {
        final byte[] buffer = new byte[this.size];
        try (final InputStream input = this.source.open();
            final OutputStream output = this.target.open()) {
            while (true) {
                final int max = input.read(buffer);
                output.write(buffer, 0, max);
                if (max < buffer.length) {
                    break;
                }
            }
        }
    }

}
