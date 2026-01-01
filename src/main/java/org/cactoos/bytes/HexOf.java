/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
 */
public final class HexOf implements Bytes {
    /**
     * Origin hexadecimal text.
     */
    private final Text origin;

    /**
     * Ctor.
     *
     * @param origin Hexadecimal text.
     */
    public HexOf(final Text origin) {
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
