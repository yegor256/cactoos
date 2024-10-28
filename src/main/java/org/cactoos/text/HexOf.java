/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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

import org.cactoos.Bytes;

/**
 * Hexadecimal representation of Bytes.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.28
 */
public final class HexOf extends TextEnvelope {

    /**
     * The hexadecimal chars.
     */
    private static final char[] HEX_CHARS = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
    };

    /**
     * Ctor.
     * @param bytes The bytes
     */
    public HexOf(final Bytes bytes) {
        super(
            new TextOf(
                () -> {
                    final byte[] bts = bytes.asBytes();
                    final char[] hex = new char[bts.length * 2];
                    int chr = -1;
                    for (final byte byt: bts) {
                        final int value = 0xff & (int) byt;
                        hex[++chr] = HexOf.HEX_CHARS[value >>> 4];
                        hex[++chr] = HexOf.HEX_CHARS[value & 0x0f];
                    }
                    return new String(hex);
                }
            )
        );
    }
}
