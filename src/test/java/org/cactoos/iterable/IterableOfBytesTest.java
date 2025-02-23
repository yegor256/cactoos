/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link IterableOfBytes}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IterableOfBytesTest {
    @Test
    void convertsTextToIterableOfBytes() {
        new Assertion<>(
            "Must create Iterable from Text",
            new IterableOfBytes(
                new TextOf("ABC")
            ),
            new HasValues<>(
                (byte) 'A', (byte) 'B', (byte) 'C'
            )
        ).affirm();
    }

    @Test
    void convertsBytesToIterable() {
        final byte[] bytes = "txt".getBytes();
        new Assertion<>(
            "Must create Iterable from bytes",
            new IterableOfBytes(bytes),
            new HasValues<>(bytes[0], bytes[1], bytes[2])
        ).affirm();
    }
}
