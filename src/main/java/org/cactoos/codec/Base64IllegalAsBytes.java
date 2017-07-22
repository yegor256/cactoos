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
package org.cactoos.codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.cactoos.Bytes;

/**
 * Illegal Characters.
 * <p>
 * <p>The class is immutable and thread-safe.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.12
 */
public final class Base64IllegalAsBytes implements Bytes {

    /**
     * All legal Base64 chars.
     */
    private static final String BASE64CHARS =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    /**
     * The input.
     */
    private final byte[] source;

    /**
     * Ctor.
     *
     * @param bts Source
     */
    public Base64IllegalAsBytes(final byte[] bts) {
        this.source = bts.clone();
    }

    @Override
    public byte[] asBytes() throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int pos = 0; pos < this.source.length; ++pos) {
            if (BASE64CHARS.indexOf(this.source[pos]) < 0) {
                out.write(this.source[pos]);
            }
        }
        return out.toByteArray();
    }
}
