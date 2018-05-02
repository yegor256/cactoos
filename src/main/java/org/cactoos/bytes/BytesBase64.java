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

import java.io.IOException;
import java.util.Base64;
import org.cactoos.Bytes;

/**
 * Encodes all origin bytes using the Base64 encoding scheme.
 *
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.20.2
 */
public final class BytesBase64 implements Bytes {

    /**
     * Origin bytes.
     */
    private final Bytes origin;
    /**
     * The encoder to use.
     */
    private final Base64.Encoder encoder;

    /**
     * Ctor uses a RFC4648 {@link java.util.Base64.Encoder}.
     *
     * @param origin Origin bytes.
     */
    public BytesBase64(final Bytes origin) {
        this(origin, Base64.getEncoder());
    }

    /**
     * Ctor.
     *
     * @param origin Origin bytes.
     * @param enc The encoder to use.
     */
    public BytesBase64(final Bytes origin, final Base64.Encoder enc) {
        this.origin = origin;
        this.encoder = enc;
    }

    @Override
    public byte[] asBytes() throws IOException {
        return this.encoder.encode(this.origin.asBytes());
    }

}
