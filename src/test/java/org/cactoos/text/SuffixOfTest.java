/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link SuffixOf}.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class SuffixOfTest {

    /**
     * Ensures that After is returning empty string if
     * there is no given boundary.
     */
    @Test
    void returnsEmptyIfThereIsNoBoundary() {
        new Assertion<>(
            "Given string is not empty",
            new SuffixOf("Cactoos with description", "after"),
            new IsText("")
        ).affirm();
    }

    /**
     * Ensures that After is returning empty string if
     * given boundary is equal to given string.
     */
    @Test
    void returnsEmptyIfStringIsBoundary() {
        new Assertion<>(
            "Given string is not empty",
            new SuffixOf("Boundary", "Boundary"),
            new IsText("")
        ).affirm();
    }

    /**
     * Ensures that After is returning string
     * after given boundary.
     */
    @Test
    void returnsAfterBoundaryString() {
        new Assertion<>(
            "Given strings are not equal",
            new SuffixOf("Anti-pattern", "Anti-"),
            new IsText("pattern")
        ).affirm();
    }
}
