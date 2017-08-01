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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import org.cactoos.Output;

/**
 * Output to.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.12
 */
public final class OutputTo implements Output {

    /**
     * The output.
     */
    private final Output origin;

    /**
     * Ctor.
     * @param file The file
     */
    public OutputTo(final File file) {
        this(() -> new FileOutputStream(file));
    }

    /**
     * Ctor.
     * @param path The path
     */
    public OutputTo(final Path path) {
        this(() -> new FileOutputStream(path.toFile()));
    }

    /**
     * Ctor.
     * @param stream The stream
     */
    public OutputTo(final OutputStream stream) {
        this(() -> stream);
    }

    /**
     * Ctor.
     * @param output The output
     */
    private OutputTo(final Output output) {
        this.origin = output;
    }

    @Override
    public OutputStream stream() throws IOException {
        return this.origin.stream();
    }

}
