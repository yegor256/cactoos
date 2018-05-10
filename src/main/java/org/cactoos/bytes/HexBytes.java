/*
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
import java.io.UncheckedIOException;
import java.util.Iterator;
import org.cactoos.Bytes;
import org.cactoos.Text;
import org.cactoos.iterator.Mapped;
import org.cactoos.text.FormattedText;

/**
 * Decodes origin {@link Text} using the hexadecimal encoding scheme.
 *
 * @since 0.30
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class HexBytes implements Bytes {
    /**
     * Origin hexadecimal text.
     */
    private final Text origin;

    /**
     * Ctor.
     *
     * @param origin Hexadecimal text.
     */
    public HexBytes(final Text origin) {
        this.origin = origin;
    }

    @Override
    public byte[] asBytes() throws Exception {
        final String hex = this.origin.asString();
        if ((hex.length() & 1) == 1) {
            throw new IOException("Length of hexadecimal text is odd");
        }
        final Iterator<Integer> iter = new Mapped<>(
            c -> {
                final int result = Character.digit(c, 16);
                if (result == -1) {
                    throw new IOException(
                        new FormattedText(
                            "Unexpected character '%c'",
                            c
                        ).asString()
                    );
                }
                return result;
            },
            hex.chars().mapToObj(c -> (char) c).iterator()
        );
        final byte[] result = new byte[hex.length() / 2];
        int index = 0;
        while (index < hex.length()) {
            try {
                final int most = iter.next();
                final int less = iter.next();
                result[index >>> 1] = (byte) ((most << 4) + less);
                index += 2;
            } catch (final UncheckedIOException ex) {
                throw ex.getCause();
            }
        }
        return result;
    }
}
