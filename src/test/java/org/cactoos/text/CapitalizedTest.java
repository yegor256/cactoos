/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Capitalized}.
 * @since 0.46
 */
final class CapitalizedTest {

    @Test
    void capitalizeEmptyText() {
        MatcherAssert.assertThat(
            "Must capitalize an empty text",
            new Capitalized(new TextOf("")),
            new IsText("")
        );
    }

    @Test
    void capitalizeSingleLowerCaseText() {
        MatcherAssert.assertThat(
            "Must capitalize single lower case text",
            new Capitalized(new TextOf("f")),
            new IsText("F")
        );
    }

    @Test
    void capitalizeSingleUpperCaseText() {
        MatcherAssert.assertThat(
            "Must capitalize single upper case text",
            new Capitalized(new TextOf("F")),
            new IsText("F")
        );
    }

    @Test
    void capitalizeTextStartingWithUpperCaseCharacter() {
        MatcherAssert.assertThat(
            "Must capitalize text starting with upper case character",
            new Capitalized("Bar"),
            new IsText("Bar")
        );
    }

    @Test
    void capitalizeTextStartingWithLowerCaseCharacter() {
        MatcherAssert.assertThat(
            "Must capitalize text starting with lower case character",
            new Capitalized(new TextOf("xyz")),
            new IsText("Xyz")
        );
    }

    @Test
    void capitalizeTextWithUnicodeCharacter() {
        MatcherAssert.assertThat(
            "Must capitalize unicode character ǆ",
            new Capitalized("ǆ"),
            new IsText("ǅ")
        );
    }

    @Test
    void capitalizeString() {
        MatcherAssert.assertThat(
            "Must capitalize string",
            new Capitalized("foo"),
            new IsText("Foo")
        );
    }
}
