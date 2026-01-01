/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link PrefixOf}.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class PrefixOfTest {

    /**
     * Ensures that Before is returning given string if
     * there is no given boundary.
     */
    @Test
    void returnsInputIfThereIsNoBoundary() {
        new Assertion<>(
            "Given strings are not equal",
            new PrefixOf("Cactoos", "bnd"),
            new IsText("Cactoos")
        ).affirm();
    }

    /**
     * Ensures that Before is returning empty string if
     * given boundary is equal to given string.
     */
    @Test
    void returnsEmptyIfStringIsBoundary() {
        new Assertion<>(
            "Given string is not empty",
            new PrefixOf("Boundary", "Boundary"),
            new IsText("")
        ).affirm();
    }

    /**
     * Ensures that Before is returning string
     * before given boundary.
     */
    @Test
    void returnsBeforeBoundaryString() {
        new Assertion<>(
            "Given strings are not equal",
            new PrefixOf("Anti-pattern", "-pattern"),
            new IsText("Anti")
        ).affirm();
    }
}
