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
package org.cactoos.io;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.cactoos.Output;

/**
 * Output that appends content to a given file.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0
 */
public final class AppendTo implements Output {

    /**
     * Source of the output.
     */
    private final File source;

    /**
     * Ctor.
     * @param path Path as a source of a File.
     */
    public AppendTo(final Path path) {
        this(path.toFile());
    }

    /**
     * Ctor.
     * @param src File to which content will be appended.
     */
    public AppendTo(final File src) {
        this.source = src;
    }

    @Override
    public OutputStream stream() throws Exception {
        return Files.newOutputStream(
            this.source.toPath(), StandardOpenOption.APPEND
        );
    }
}
