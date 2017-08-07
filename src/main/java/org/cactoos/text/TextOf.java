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
package org.cactoos.text;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.io.BytesOf;
import org.cactoos.io.InputOf;
import org.cactoos.scalar.IoCheckedScalar;

/**
 * TextOf
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.12
 */
public final class TextOf implements Text {

    /**
     * The origin.
     */
    private final Scalar<String> origin;

    /**
     * Ctor.
     *
     * @param input The Input
     */
    public TextOf(final Input input) {
        this(new BytesOf(input));
    }

    /**
     * Ctor.
     * @param path The Input
     * @since 0.13
     */
    public TextOf(final Path path) {
        this(new InputOf(path));
    }

    /**
     * Ctor.
     * @param file The Input
     * @since 0.13
     */
    public TextOf(final File file) {
        this(new InputOf(file));
    }

    /**
     * Ctor.
     *
     * @param input The input
     * @param max Max length of the buffer for reading
     */
    public TextOf(final Input input, final int max) {
        this(input, max, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param input The Input
     * @param cset The Charset
     */
    public TextOf(final Input input, final Charset cset) {
        this(new BytesOf(input), cset);
    }

    /**
     * Ctor.
     *
     * @param input The Input
     * @param cset The Charset
     */
    public TextOf(final Input input, final String cset) {
        this(new BytesOf(input), cset);
    }

    /**
     * Ctor.
     *
     * @param input The input
     * @param max Max length of the buffer for reading
     * @param cset The Charset
     */
    public TextOf(final Input input, final int max, final Charset cset) {
        this(new BytesOf(input, max), cset);
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     */
    public TextOf(final Reader rdr) {
        this(new BytesOf(rdr));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param cset Charset
     */
    public TextOf(final Reader rdr, final Charset cset) {
        this(new BytesOf(rdr, cset));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param cset Charset
     * @param max Buffer size
     */
    public TextOf(final Reader rdr, final Charset cset, final int max) {
        this(new BytesOf(rdr, cset, max));
    }

    /**
     * Ctor.
     *
     * @param builder The String builder
     */
    public TextOf(final CharSequence builder) {
        this(new BytesOf(builder));
    }

    /**
     * Ctor.
     *
     * @param builder The String builder
     * @param cset The Charset
     */
    public TextOf(final CharSequence builder, final Charset cset) {
        this(new BytesOf(builder, cset), cset);
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     */
    public TextOf(final char... chars) {
        this(new BytesOf(chars));
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     * @param cset The charset
     */
    public TextOf(final char[] chars, final Charset cset) {
        this(new BytesOf(chars, cset));
    }

    /**
     * Ctor.
     *
     * @param error The exception to serialize
     */
    public TextOf(final Throwable error) {
        this(new BytesOf(error));
    }

    /**
     * Ctor.
     *
     * @param bytes The array of bytes
     */
    public TextOf(final byte... bytes) {
        this(new BytesOf(bytes));
    }

    /**
     * Ctor.
     *
     * @param bytes The Bytes
     */
    public TextOf(final Bytes bytes) {
        this(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param bytes The Bytes
     * @param cset The Charset
     */
    public TextOf(final Bytes bytes, final Charset cset) {
        this(() -> new String(bytes.asBytes(), cset));
    }

    /**
     * Ctor.
     *
     * @param bytes The Bytes
     * @param cset The Charset
     */
    public TextOf(final Bytes bytes, final String cset) {
        this(() -> new String(bytes.asBytes(), cset));
    }

    /**
     * Ctor.
     *
     * @param input The String
     */
    public TextOf(final String input) {
        this(input, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param input The String
     * @param cset The Charset
     */
    public TextOf(final String input, final Charset cset) {
        this(() -> new String(input.getBytes(cset), cset));
    }

    /**
     * Ctor.
     *
     * @param scalar The Scalar of String
     */
    private TextOf(final Scalar<String> scalar) {
        this.origin = scalar;
    }

    @Override
    public String asString() throws IOException {
        return new IoCheckedScalar<>(this.origin).value();
    }

    @Override
    public int compareTo(final Text text) {
        return new UncheckedText(this).compareTo(text);
    }

}
