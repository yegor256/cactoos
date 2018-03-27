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

package org.cactoos.bytes;

import java.util.Base64;
import org.cactoos.Bytes;

/**
 * Decodes all origin bytes using the Base64 encoding scheme.
 *
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.20.2
 */
public final class Base64Bytes implements Bytes {

    /**
     * Origin bytes.
     */
    private final Bytes origin;
    /**
     * The decoder.
     */
    private final Base64.Decoder decoder;

    /**
     * Ctor uses a RFC4648 {@link java.util.Base64.Decoder}.
     *
     * @param origin Origin bytes
     */
    public Base64Bytes(final Bytes origin) {
        this(origin, Base64.getDecoder());
    }

    /**
     * Ctor.
     *
     * @param origin Origin bytes.
     * @param dec Decoder to use.
     */
    public Base64Bytes(final Bytes origin, final Base64.Decoder dec) {
        this.origin = origin;
        this.decoder = dec;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.decoder.decode(this.origin.asBytes());
    }
}
