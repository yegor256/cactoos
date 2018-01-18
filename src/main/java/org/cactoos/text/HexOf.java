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
package org.cactoos.text;

import java.io.IOException;
import org.cactoos.Bytes;
import org.cactoos.Text;

/**
 * Hexadecimal representation of Bytes.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.28
 */
@SuppressWarnings("PMD.ConstructorShouldDoInitialization")
public final class HexOf implements Text {

    /**
     * The hexadecimal chars.
     */
    private static final char[] HEX_CHARS = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
    };

    /**
     * The Bytes.
     */
    private final Bytes bytes;

    /**
     * Ctor.
     * @param source The bytes
     */
    public HexOf(final Bytes source) {
        this.bytes = source;
    }

    @Override
    public String asString() throws IOException {
        final byte[] bts = this.bytes.asBytes();
        final char[] hex = new char[bts.length * 2];
        int chr = -1;
        for (int idx = 0; idx < bts.length; ++idx) {
            // @checkstyle MagicNumber (3 line)
            final int value = 0xff & bts[idx];
            hex[++chr] = HexOf.HEX_CHARS[value >>> 4];
            hex[++chr] = HexOf.HEX_CHARS[value & 0x0f];
        }
        return new String(hex);
    }

}
