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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Text;
import org.cactoos.text.StringAsText;

/**
 * Bytes of text, array and input.
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
     * @param input The input
     * @param max Max length of the buffer for reading
     */
    public BytesOf(final Input input, final int max) {
        this(new InputAsBytes(input, max));
    }

    /**
     * Ctor.
     *
     * @param text The source
     */
    public BytesOf(final String text) {
        this(new StringAsText(text));
    }

    /**
     * Ctor.
     *
     * @param text The source
     */
    public BytesOf(final Text text) {
        this(text, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param text The source
     * @param cset The charset
     */
    public BytesOf(final Text text, final Charset cset) {
        this(() -> text.asString().getBytes(cset));
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
