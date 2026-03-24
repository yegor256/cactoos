/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Contains}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ContainsTest {

    @Test
    void textContainsText() {
        MatcherAssert.assertThat(
            "Text contains other Text",
            new Contains(
                new TextOf("Elegant Object"),
                new TextOf("Elegant")
            ),
            new HasValue<>(Boolean.TRUE)
        );
    }

    @Test
    void textDoesNotContainText() {
        MatcherAssert.assertThat(
            "Text does not contain other Text",
            new Contains(
                new TextOf("Java is awesome"),
                new TextOf("good")
            ),
            new HasValue<>(Boolean.FALSE)
        );
    }

    @Test
    void textContainsString() {
        MatcherAssert.assertThat(
            "Text contains other String",
            new Contains(
                new TextOf("The quick brown fox"),
                "fox"
            ),
            new HasValue<>(Boolean.TRUE)
        );
    }

    @Test
    void textDoesNotContainString() {
        MatcherAssert.assertThat(
            "Text does not contain other String",
            new Contains(
                new TextOf("Stack Overflow"),
                "nope"
            ),
            new HasValue<>(Boolean.FALSE)
        );
    }

    @Test
    void stringContainsText() {
        MatcherAssert.assertThat(
            "String contains other Text",
            new Contains(
                "Terra incognita",
                new TextOf("cognita")
            ),
            new HasValue<>(Boolean.TRUE)
        );
    }

    @Test
    void stringDoesNotContainText() {
        MatcherAssert.assertThat(
            "String does not contain other Text",
            new Contains(
                "ratio",
                new TextOf("Cogito egro sum")
            ),
            new HasValue<>(Boolean.FALSE)
        );
    }

    @Test
    void stringContainsString() {
        MatcherAssert.assertThat(
            "String contains other String",
            new Contains(
                "Lazy dog",
                "dog"
            ),
            new HasValue<>(Boolean.TRUE)
        );
    }

    @Test
    void stringDoesNotContainString() {
        MatcherAssert.assertThat(
            "String does not contain other String",
            new Contains(
                "Lorem ipsum",
                "test"
            ),
            new HasValue<>(Boolean.FALSE)
        );
    }
}
