/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Capitalized}.
 * @since 0.46
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class CapitalizedTest {

    @Test
    void capitalizeEmptyText() {
        new Assertion<>(
            "Must capitalize an empty text",
            new Capitalized(new TextOf("")),
            new IsText("")
        ).affirm();
    }

    @Test
    void capitalizeSingleLowerCaseText() {
        new Assertion<>(
            "Must capitalize single lower case text",
            new Capitalized(new TextOf("f")),
            new IsText("F")
        ).affirm();
    }

    @Test
    void capitalizeSingleUpperCaseText() {
        new Assertion<>(
            "Must capitalize single upper case text",
            new Capitalized(new TextOf("F")),
            new IsText("F")
        ).affirm();
    }

    @Test
    void capitalizeTextStartingWithUpperCaseCharacter() {
        new Assertion<>(
            "Must capitalize text starting with upper case character",
            new Capitalized("Bar"),
            new IsText("Bar")
        ).affirm();
    }

    @Test
    void capitalizeTextStartingWithLowerCaseCharacter() {
        new Assertion<>(
            "Must capitalize text starting with lower case character",
            new Capitalized(new TextOf("xyz")),
            new IsText("Xyz")
        ).affirm();
    }

    @Test
    void capitalizeTextWithUnicodeCharacter() {
        new Assertion<>(
            "Must capitalize unicode character ǆ",
            new Capitalized("ǆ"),
            new IsText("ǅ")
        ).affirm();
    }

    @Test
    void capitalizeString() {
        new Assertion<>(
            "Must capitalize string",
            new Capitalized("foo"),
            new IsText("Foo")
        ).affirm();
    }
}
