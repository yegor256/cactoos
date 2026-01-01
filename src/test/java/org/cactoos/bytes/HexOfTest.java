/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.bytes;

import java.io.IOException;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Satisfies;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link HexOf}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 line)
 */
final class HexOfTest {

    @Test
    void emptyText() throws Exception {
        new Assertion<>(
            "Must represent an empty hexadecimal text",
            new HexOf(new TextOf("")).asBytes(),
            new Satisfies<>(array -> array.length == 0)
        ).affirm();
    }

    @Test
    void validHex() throws Exception {
        final byte[] bytes = new byte[256];
        for (int index = 0; index < 256; ++index) {
            bytes[index] = (byte) (index + (int) Byte.MIN_VALUE);
        }
        new Assertion<>(
            "Must convert hexadecimal text to bytes",
            new HexOf(
                new org.cactoos.text.HexOf(
                    new BytesOf(bytes)
                )
            ).asBytes(),
            new IsEqual<>(bytes)
        ).affirm();
    }

    @Test
    void invalidHexLength() {
        new Assertion<>(
            "Must invalid hex length",
            () -> new HexOf(new TextOf("ABF")).asBytes(),
            new Throws<>(
                "Length of hexadecimal text is odd",
                IOException.class
            )
        ).affirm();
    }

    @Test
    void invalidHex() {
        new Assertion<>(
            "Must invalid hex",
            () -> new HexOf(new TextOf("ABG!")).asBytes(),
            new Throws<>(
                "Unexpected character 'G'",
                IOException.class
            )
        ).affirm();
    }
}
