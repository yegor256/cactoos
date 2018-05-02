/**
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.IoCheckedScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * An {@link Input} that constraints the data that you get from head.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.29.3
 */
public final class HeadInput implements Input {

    /**
     * Input.
     */
    private final Input origin;

    /**
     * Length.
     */
    private final int length;

    /**
     * Ctor.
     *
     * @param file The file
     * @param length The length
     */
    public HeadInput(final File file, final int length) {
        this(
            () -> new FileInputStream(
                new UncheckedScalar<>(() -> file).value()
            ),
            length
        );
    }

    /**
     * Ctor.
     *
     * @param path The path
     * @param length The length
     */
    public HeadInput(final Path path, final int length) {
        this(() -> new FileInputStream(path.toFile()), length);
    }

    /**
     * Ctor.
     *
     * @param uri The URI
     * @param length The length
     */
    public HeadInput(final URI uri, final int length) {
        this(uri::toURL, length);
    }

    /**
     * Ctor.
     *
     * @param url The URL
     * @param length The length
     */
    public HeadInput(final URL url, final int length) {
        this(() -> url, length);
    }

    /**
     * Ctor.
     *
     * @param scalar The url
     * @param length The length
     */
    public HeadInput(final Scalar<URL> scalar, final int length) {
        this(() -> new IoCheckedScalar<>(scalar).value().openStream(), length);
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     * @param length The length
     */
    public HeadInput(final Reader rdr, final int length) {
        this(new BytesOf(rdr), length);
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     * @param length The length
     */
    public HeadInput(final Reader rdr, final Charset charset,
        final int length) {
        this(new BytesOf(rdr, charset), length);
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     * @param length The length
     */
    public HeadInput(final Reader rdr, final CharSequence charset,
        final int length) {
        this(new BytesOf(rdr, charset), length);
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param max Buffer size
     * @param length The length
     */
    public HeadInput(final Reader rdr, final int max, final int length) {
        this(new BytesOf(rdr, max), length);
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     * @param max Buffer size
     * @param length The length
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public HeadInput(final Reader rdr, final Charset charset, final int max,
        final int length) {
        this(new BytesOf(rdr, charset, max), length);
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     * @param max Buffer size
     * @param length The length
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public HeadInput(final Reader rdr, final CharSequence charset,
        final int max, final int length) {
        this(new BytesOf(rdr, charset, max), length);
    }

    /**
     * Ctor.
     *
     * @param length The length
     * @param chars The chars
     */
    public HeadInput(final int length, final char... chars) {
        this(new BytesOf(chars), length);
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     * @param charset The charset
     * @param length The length
     */
    public HeadInput(final char[] chars, final Charset charset,
        final int length) {
        this(new BytesOf(chars, charset), length);
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     * @param charset The charset
     * @param length The length
     */
    public HeadInput(final char[] chars, final CharSequence charset,
        final int length) {
        this(new BytesOf(chars, charset), length);
    }

    /**
     * Ctor.
     * @param source The string
     * @param length The length
     */
    public HeadInput(final CharSequence source, final int length) {
        this(new BytesOf(source), length);
    }

    /**
     * Ctor.
     * @param source The string
     * @param charset The charset
     * @param length The length
     */
    public HeadInput(final CharSequence source, final Charset charset,
        final int length) {
        this(new BytesOf(source, charset), length);
    }

    /**
     * Ctor.
     * @param source The string
     * @param charset The charset
     * @param length The length
     */
    public HeadInput(final CharSequence source, final CharSequence charset,
        final int length) {
        this(new BytesOf(source, charset), length);
    }

    /**
     * Ctor.
     * @param text The text
     * @param length The length
     */
    public HeadInput(final Text text, final int length) {
        this(new BytesOf(text), length);
    }

    /**
     * Ctor.
     * @param text The text
     * @param charset The charset
     * @param length The length
     */
    public HeadInput(final Text text, final Charset charset, final int length) {
        this(new BytesOf(text, charset), length);
    }

    /**
     * Ctor.
     * @param text The text
     * @param charset The charset
     * @param length The length
     */
    public HeadInput(final Text text, final CharSequence charset,
        final int length) {
        this(new BytesOf(text, charset), length);
    }

    /**
     * Ctor.
     * @param bytes The bytes
     * @param length The length
     */
    public HeadInput(final byte[] bytes, final int length) {
        this(new BytesOf(bytes), length);
    }

    /**
     * Ctor.
     * @param src The bytes
     * @param length The length
     */
    public HeadInput(final Bytes src, final int length) {
        this(
            () -> new IoCheckedScalar<InputStream>(
                () -> new ByteArrayInputStream(src.asBytes())
            ).value(),
            length
        );
    }

    /**
     * Ctor.
     * @param stream The stream
     * @param length The length
     */
    public HeadInput(final InputStream stream, final int length) {
        this(() -> stream, length);
    }

    /**
     * Ctor.
     *
     * @param input The input
     * @param length The length
     */
    private HeadInput(final Input input, final int length) {
        this.origin = input;
        this.length = length;
    }

    @Override
    public InputStream stream() throws IOException {
        return new HeadInputStream(this.origin.stream(), this.length);
    }

}
