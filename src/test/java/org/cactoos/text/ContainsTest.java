/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Contains}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ContainsTest {

    @Test
    void textContainsText() {
        new Assertion<>(
            "Text contains other Text",
            new Contains(
                new TextOf("Elegant Object"),
                new TextOf("Elegant")
            ),
            new HasValue<>(Boolean.TRUE)
        ).affirm();
    }

    @Test
    void textDoesNotContainText() {
        new Assertion<>(
            "Text does not contain other Text",
            new Contains(
                new TextOf("Java is awesome"),
                new TextOf("good")
            ),
            new HasValue<>(Boolean.FALSE)
        ).affirm();
    }

    @Test
    void textContainsString() {
        new Assertion<>(
            "Text contains other String",
            new Contains(
                new TextOf("The quick brown fox"),
                "fox"
            ),
            new HasValue<>(Boolean.TRUE)
        ).affirm();
    }

    @Test
    void textDoesNotContainString() {
        new Assertion<>(
            "Text does not contain other String",
            new Contains(
                new TextOf("Stack Overflow"),
                "nope"
            ),
            new HasValue<>(Boolean.FALSE)
        ).affirm();
    }

    @Test
    void stringContainsText() {
        new Assertion<>(
            "String contains other Text",
            new Contains(
                "Terra incognita",
                new TextOf("cognita")
            ),
            new HasValue<>(Boolean.TRUE)
        ).affirm();
    }

    @Test
    void stringDoesNotContainText() {
        new Assertion<>(
            "String does not contain other Text",
            new Contains(
                "ratio",
                new TextOf("Cogito egro sum")
            ),
            new HasValue<>(Boolean.FALSE)
        ).affirm();
    }

    @Test
    void stringContainsString() {
        new Assertion<>(
            "String contains other String",
            new Contains(
                "Lazy dog",
                "dog"
            ),
            new HasValue<>(Boolean.TRUE)
        ).affirm();
    }

    @Test
    void stringDoesNotContainString() {
        new Assertion<>(
            "String does not contain other String",
            new Contains(
                "Lorem ipsum",
                "test"
            ),
            new HasValue<>(Boolean.FALSE)
        ).affirm();
    }
}
