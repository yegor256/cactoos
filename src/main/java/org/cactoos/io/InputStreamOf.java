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
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Wrapper of {@link InputStream}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.13
 */
public final class InputStreamOf extends InputStream {

    /**
     * The source.
     */
    private final UncheckedScalar<InputStream> source;

    /**
     * Ctor.
     * @param path The path
     */
    public InputStreamOf(final Path path) {
        this(new InputOf(path));
    }

    /**
     * Ctor.
     * @param file The file
     */
    public InputStreamOf(final File file) {
        this(new InputOf(file));
    }

    /**
     * Ctor.
     * @param url The URL
     */
    public InputStreamOf(final URL url) {
        this(new InputOf(url));
    }

    /**
     * Ctor.
     * @param uri The URI
     */
    public InputStreamOf(final URI uri) {
        this(new InputOf(uri));
    }

    /**
     * Ctor.
     * @param bytes The text
     */
    public InputStreamOf(final Bytes bytes) {
        this(new InputOf(bytes));
    }

    /**
     * Ctor.
     * @param text The text
     */
    public InputStreamOf(final Text text) {
        this(new InputOf(text));
    }

    /**
     * Ctor.
     * @param text The text
     */
    public InputStreamOf(final String text) {
        this(new InputOf(text));
    }

    /**
     * Ctor.
     * @param rdr The reader
     */
    private InputStreamOf(final Reader rdr) {
        this(new InputOf(rdr));
    }

    /**
     * Ctor.
     * @param input The input
     */
    public InputStreamOf(final Input input) {
        this((Scalar<InputStream>) input::stream);
    }

    /**
     * Ctor.
     * @param src Source
     */
    private InputStreamOf(final Scalar<InputStream> src) {
        super();
        this.source = new UncheckedScalar<>(new StickyScalar<>(src));
    }

    @Override
    public int read() throws IOException {
        return this.source.value().read();
    }

    @Override
    public int read(final byte[] buffer) throws IOException {
        return this.source.value().read(buffer);
    }

    @Override
    public int read(final byte[] buffer, final int offset,
        final int length) throws IOException {
        return this.source.value().read(buffer, offset, length);
    }

    @Override
    public void close() throws IOException {
        this.source.value().close();
    }

}
