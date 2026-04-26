/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Tests for @{link Mapped}.
 * @since 0.47
 */
final class MappedTest {

    @Test
    void resultShouldBeEqual() {
        MatcherAssert.assertThat(
            "must be equal to the same text",
            new Mapped(
                String::toUpperCase,
                new TextOf("hi")
            ),
            new IsEqual<>(new TextOf("HI"))
        );
    }

    @Test
    void mapsWithFormat() {
        MatcherAssert.assertThat(
            "must apply lambda to a string",
            new Mapped(
                s -> String.format("<%s>", s),
                new TextOf("hi")
            ),
            new IsText("<hi>")
        );
    }

    @Test
    void maps() {
        MatcherAssert.assertThat(
            "must apply method reference to a string",
            new Mapped(
                String::toLowerCase,
                new TextOf("ABC")
            ),
            new IsText("abc")
        );
    }
}
