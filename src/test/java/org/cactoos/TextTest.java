/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

import org.cactoos.text.NoNulls;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Text}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TextTest {

    @Test
    void failForNullArgument() {
        new Assertion<>(
            "Must fail for null argument",
            () -> new NoNulls(null).asString(),
            new Throws<>(
                "NULL instead of a valid text",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void failForNullResult() {
        new Assertion<>(
            "Must fail for null result",
            () -> new NoNulls(() -> null).asString(),
            new Throws<>(
                "NULL instead of a valid result string",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void okForNoNulls() {
        final String message = "Hello";
        new Assertion<>(
            "Must work with NoNulls",
            new NoNulls(
                new TextOf(message)
            ),
            new IsText(message)
        ).affirm();
    }

}
