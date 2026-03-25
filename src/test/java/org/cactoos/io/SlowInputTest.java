/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.nio.charset.StandardCharsets;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link SlowInput}.
 *
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SlowInputTest {

    @Test
    void calculatesLength() {
        final String text = "What's up, друг?";
        MatcherAssert.assertThat(
            "Can't calculate the length of Input",
            new LengthOf(
                new SlowInput(
                    new InputOf(
                        new TextOf(text)
                    )
                )
            ),
            new HasValue<>((long) text.getBytes(StandardCharsets.UTF_8).length)
        );
    }

    @Test
    void readsFileContentSlowly() {
        final long size = 100_000L;
        MatcherAssert.assertThat(
            "Can't calculate length if the input is slow",
            new LengthOf(
                new SlowInput(size)
            ),
            new HasValue<>(size)
        );
    }

}
