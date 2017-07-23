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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.func.IoCheckedScalar;
import org.cactoos.func.UncheckedScalar;
import org.cactoos.text.ArrayAsBytes;
import org.cactoos.text.StringAsText;
import org.cactoos.text.TextAsBytes;

/**
 * InputOf
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.11.8
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class InputOf implements Input {

    /**
     * Input.
     */
    private final Input origin;

    /**
     * Ctor.
     *
     * @param file The file
     */
    public InputOf(final File file) {
        this(() -> file);
    }

    /**
     * Ctor.
     *
     * @param scalar The scalar of the file
     */
    public InputOf(final Scalar<File> scalar) {
        this(new UncheckedScalar<>(scalar));
    }

    /**
     * Ctor.
     *
     * @param scalar The unchecked scalar of the file
     */
    public InputOf(final UncheckedScalar<File> scalar) {
        this(() -> new FileInputStream(scalar.value()));
    }

    /**
     * Ctor.
     *
     * @param path The path
     */
    public InputOf(final Path path) {
        this(() -> new FileInputStream(path.toFile()));
    }

    /**
     * Ctor.
     *
     * @param uri The URL
     */
    public InputOf(final URI uri) {
        this(() -> {
            return new IoCheckedScalar<URL>(
                () -> uri.toURL()
            ).value().openStream();
        });
    }

    /**
     * Ctor.
     *
     * @param url The url
     */
    public InputOf(final URL url) {
        this(() -> {
            return new IoCheckedScalar<URL>(
                () -> url
            ).value().openStream();
        });
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     */
    public InputOf(final Reader rdr) {
        this(new ReaderAsInput(rdr));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param cset Charset
     */
    public InputOf(final Reader rdr, final Charset cset) {
        this(new ReaderAsInput(rdr, cset));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param cset Charset
     * @param max Buffer size
     */
    public InputOf(final Reader rdr, final Charset cset, final int max) {
        this(new ReaderAsInput(rdr, cset, max));
    }

    /**
     * Ctor.
     *
     * @param builder The string's builder
     */
    public InputOf(final StringBuilder builder) {
        this(builder, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param builder The string's builder
     * @param cset The charset
     */
    public InputOf(final StringBuilder builder, final Charset cset) {
        this(() -> {
            return new IoCheckedScalar<InputStream>(
                () -> new ByteArrayInputStream(
                    builder.toString().getBytes(cset)
                )
            ).value();
        });
    }

    /**
     * Ctor.
     *
     * @param buffer The string's buffer
     */
    public InputOf(final StringBuffer buffer) {
        this(buffer, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param buffer The string's buffer
     * @param cset The charset
     */
    public InputOf(final StringBuffer buffer, final Charset cset) {
        this(() -> {
            return new IoCheckedScalar<InputStream>(
                () -> new ByteArrayInputStream(
                    buffer.toString().getBytes(cset)
                )
            ).value();
        });
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     */
    public InputOf(final char... chars) {
        this(chars, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param array The chars
     * @param cset The charset
     */
    public InputOf(final char[] array, final Charset cset) {
        this(
            new TextAsBytes(
                new StringAsText(new String(array)),
                cset
            )
        );
    }

    /**
     * Ctor.
     *
     * @param string The string
     */
    public InputOf(final String string) {
        this(new TextAsBytes(string));
    }

    /**
     * Ctor.
     *
     * @param text The text
     */
    public InputOf(final Text text) {
        this(new TextAsBytes(text));
    }

    /**
     * Ctor.
     *
     * @param bytes The bytes
     */
    public InputOf(final byte[] bytes) {
        this(new ArrayAsBytes(bytes));
    }

    /**
     * Ctor.
     *
     * @param src The bytes
     */
    public InputOf(final Bytes src) {
        this(() -> {
            return new IoCheckedScalar<InputStream>(
                () -> new ByteArrayInputStream(src.asBytes())
            ).value();
        });
    }

    /**
     * Ctor.
     *
     * @param stream The stream
     */
    public InputOf(final InputStream stream) {
        this(() -> stream);
    }

    /**
     * Ctor.
     *
     * @param input The input
     */
    private InputOf(final Input input) {
        this.origin = input;
    }

    @Override
    public InputStream stream() throws IOException {
        return this.origin.stream();
    }

}
