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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.cactoos.Bytes;
import org.cactoos.Text;

/**
 * Bytes as Text.
 *
 * <p>This class is doing something similar to what
 * ExceptionUtils are doing from Apache Commons.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.2
 */
public final class BytesAsText implements Text {

    /**
     * The bytes.
     */
    private final Bytes bytes;

    /**
     * The charset.
     */
    private final Charset charset;

    /**
     * Ctor.
     * @param bts Bytes to encapsulate
     */
    public BytesAsText(final byte[] bts) {
        this(bts, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param bts Bytes to encapsulate
     */
    public BytesAsText(final Bytes bts) {
        this(bts, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param bts Bytes to encapsulate
     * @param cset Charset
     */
    public BytesAsText(final byte[] bts, final Charset cset) {
        this(new ArrayAsBytes(bts), cset);
    }

    /**
     * Ctor.
     * @param bts Bytes to encapsulate
     * @param cset Charset
     */
    public BytesAsText(final Bytes bts, final Charset cset) {
        this.bytes = bts;
        this.charset = cset;
    }

    @Override
    public String asString() throws IOException {
        return new String(this.bytes.asBytes(), this.charset);
    }

}
