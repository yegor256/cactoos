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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Text;

/**
 * A {@link Bytes} that encapsulates other sources of data.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.12
 */
public final class BytesOf implements Bytes {

    /**
     * The bytes.
     */
    private final Bytes origin;

    /**
     * Ctor.
     * @param input The input
     */
    public BytesOf(final Input input) {
        this(new InputAsBytes(input));
    }

    /**
     * Ctor.
     * @param file The input
     * @since 0.13
     */
    public BytesOf(final File file) {
        this(new InputOf(file));
    }

    /**
     * Ctor.
     * @param path The input
     * @since 0.13
     */
    public BytesOf(final Path path) {
        this(new InputOf(path));
    }

    /**
     * Ctor.
     * @param input The input
     * @param max Max length of the buffer for reading
     */
    public BytesOf(final Input input, final int max) {
        this(new InputAsBytes(input, max));
    }

    /**
     * Ctor.
     * @param rdr Reader
     */
    public BytesOf(final Reader rdr) {
        this(new ReaderAsBytes(rdr));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     */
    public BytesOf(final Reader rdr, final Charset charset) {
        this(new ReaderAsBytes(rdr, charset));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     */
    public BytesOf(final Reader rdr, final CharSequence charset) {
        this(new ReaderAsBytes(rdr, charset));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     * @param max Buffer size
     */
    public BytesOf(final Reader rdr, final Charset charset, final int max) {
        this(new ReaderAsBytes(rdr, charset, max));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param max Buffer size
     * @since 0.13.3
     */
    public BytesOf(final Reader rdr, final int max) {
        this(new ReaderAsBytes(rdr, max));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     * @param max Buffer size
     */
    public BytesOf(final Reader rdr, final CharSequence charset,
        final int max) {
        this(new ReaderAsBytes(rdr, charset, max));
    }

    /**
     * Ctor.
     *
     * @param input The source
     */
    public BytesOf(final CharSequence input) {
        this(input, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param input The source
     * @param charset The charset
     */
    public BytesOf(final CharSequence input, final Charset charset) {
        this(() -> input.toString().getBytes(charset));
    }

    /**
     * Ctor.
     *
     * @param input The source
     * @param charset The charset
     */
    public BytesOf(final CharSequence input, final CharSequence charset) {
        this(() -> input.toString().getBytes(charset.toString()));
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     */
    public BytesOf(final char... chars) {
        this(chars, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     * @param charset The charset
     */
    public BytesOf(final char[] chars, final Charset charset) {
        this(new String(chars), charset);
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     * @param charset The charset
     */
    public BytesOf(final char[] chars, final CharSequence charset) {
        this(new String(chars), charset);
    }

    /**
     * Ctor.
     * @param text The source
     */
    public BytesOf(final Text text) {
        this(text, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param text The source
     * @param charset The charset
     */
    public BytesOf(final Text text, final Charset charset) {
        this(() -> text.asString().getBytes(charset));
    }

    /**
     * Ctor.
     * @param text The source
     * @param charset The charset
     */
    public BytesOf(final Text text, final CharSequence charset) {
        this(() -> text.asString().getBytes(charset.toString()));
    }

    /**
     * Ctor.
     * @param error The exception to serialize
     */
    public BytesOf(final Throwable error) {
        this(error, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param error The exception to serialize
     * @param charset Charset
     */
    public BytesOf(final Throwable error, final Charset charset) {
        this(error, charset.name());
    }

    /**
     * Ctor.
     * @param error The exception to serialize
     * @param charset Charset
     */
    public BytesOf(final Throwable error, final CharSequence charset) {
        this(
            () -> {
                try (
                    final ByteArrayOutputStream baos =
                        new ByteArrayOutputStream()
                ) {
                    error.printStackTrace(
                        new PrintStream(baos, true, charset.toString())
                    );
                    return baos.toByteArray();
                }
            }
        );
    }

    /**
     * Ctor.
     *
     * @param bytes Bytes to encapsulate
     */
    public BytesOf(final byte... bytes) {
        this(() -> bytes);
    }

    /**
     * Ctor.
     *
     * @param bytes Bytes to encapsulate
     */
    private BytesOf(final Bytes bytes) {
        this.origin = bytes;
    }

    @Override
    public byte[] asBytes() throws IOException {
        return this.origin.asBytes();
    }
}
