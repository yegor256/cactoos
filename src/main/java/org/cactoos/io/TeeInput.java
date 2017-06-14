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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import org.cactoos.Input;
import org.cactoos.Output;

/**
 * Input to Output copying pipe.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class TeeInput implements Input {

    /**
     * The source.
     */
    private final Input source;

    /**
     * The destination.
     */
    private final Output target;

    /**
     * Ctor.
     * @param input The source
     * @param output The output file
     * @since 0.5
     */
    public TeeInput(final Path input, final Path output) {
        this(new PathAsInput(input), new PathAsOutput(output));
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The output file
     * @since 0.5
     */
    public TeeInput(final Path input, final File output) {
        this(new PathAsInput(input), new FileAsOutput(output));
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The output file
     * @since 0.5
     */
    public TeeInput(final File input, final File output) {
        this(new FileAsInput(input), new FileAsOutput(output));
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The output file
     * @since 0.5
     */
    public TeeInput(final File input, final Path output) {
        this(new FileAsInput(input), new PathAsOutput(output));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @since 0.5
     */
    public TeeInput(final String input, final File file) {
        this(new BytesAsInput(input), new FileAsOutput(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @since 0.5
     */
    public TeeInput(final String input, final Path file) {
        this(new BytesAsInput(input), new PathAsOutput(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @since 0.5
     */
    public TeeInput(final byte[] input, final Path file) {
        this(new BytesAsInput(input), new PathAsOutput(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     * @since 0.5
     */
    public TeeInput(final String input, final Output output) {
        this(new BytesAsInput(input), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     */
    public TeeInput(final Input input, final Output output) {
        this.source = input;
        this.target = output;
    }

    @Override
    public InputStream stream() throws IOException {
        return new TeeInputStream(
            this.source.stream(), this.target.stream()
        );
    }

}
