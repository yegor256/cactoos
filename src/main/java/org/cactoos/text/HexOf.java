/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
                    for (final byte b: bts) {
                        final int value = 0xff & (int) b;
                        hex[++chr] = HexOf.HEX_CHARS[value >>> 4];
                        hex[++chr] = HexOf.HEX_CHARS[value & 0x0f];
                    }
                    return new String(hex);
                }
            )
        );
    }
}
