/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.cactoos.io.InputOf;
import org.cactoos.io.SlowInputStream;
import org.cactoos.iterable.Endless;
import org.cactoos.iterable.HeadOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.StartsWith;

/**
 * Test case for {@link InputAsBytes}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("unchecked")
final class InputAsBytesTest {

    @Test
    void readsLargeInMemoryContent() throws Exception {
        final int multiplier = 5_000;
        final String body = "1234567890";
        new Assertion<>(
            "must read large content from in-memory Input",
            new InputAsBytes(
                new InputOf(
                    String.join(
                        "",
                        new HeadOf<>(
                            multiplier, new Endless<>(body)
                        )
                    )
                )
            ).asBytes().length,
            new IsEqual<>(body.length() * multiplier)
        ).affirm();
    }

    @Test
    // @checkstyle AnonInnerLengthCheck (100 lines)
    void readsLargeContent() throws Exception {
        final int size = 100_000;
        try (InputStream slow = new SlowInputStream(size)) {
            new Assertion<>(
                "must read large content from Input",
                new InputAsBytes(
                    new InputOf(slow)
                ).asBytes().length,
                new IsEqual<>(size)
            ).affirm();
        }
    }

    @Test
    void readsInputIntoBytes() {
        new Assertion<>(
            "must read bytes from Input",
            new TextOf(
                new InputAsBytes(
                    new InputOf(
                        new BytesOf(
                            new TextOf("Hello, друг!")
                        )
                    )
                ),
                StandardCharsets.UTF_8
            ),
            new AllOf<>(
                new StartsWith("Hello, "),
                new EndsWith("друг!")
            )
        ).affirm();
    }

    @Test
    void readsInputIntoBytesWithSmallBuffer() {
        new Assertion<>(
            "must read bytes from Input with a small reading buffer",
            new TextOf(
                new InputAsBytes(
                    new InputOf(
                        new BytesOf(
                            new TextOf("Hello, товарищ!")
                        )
                    ),
                    2
                ),
                StandardCharsets.UTF_8
            ),
            new AllOf<>(
                new StartsWith("Hello,"),
                new EndsWith("товарищ!")
            )
        ).affirm();
    }

}
