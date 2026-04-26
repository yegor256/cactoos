/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

import org.cactoos.text.NoNulls;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Text}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class TextTest {

    @Test
    void failForNullArgument() {
        MatcherAssert.assertThat(
            "Must fail for null argument",
            () -> new NoNulls(null).asString(),
            new Throws<>(
                "NULL instead of a valid text",
                IllegalArgumentException.class
            )
        );
    }

    @Test
    void failForNullResult() {
        MatcherAssert.assertThat(
            "Must fail for null result",
            () -> new NoNulls(() -> null).asString(),
            new Throws<>(
                "NULL instead of a valid result string",
                IllegalStateException.class
            )
        );
    }

    @Test
    void okForNoNulls() {
        final String message = "Hello";
        MatcherAssert.assertThat(
            "Must work with NoNulls",
            new NoNulls(
                new TextOf(message)
            ),
            new IsText(message)
        );
    }
}
