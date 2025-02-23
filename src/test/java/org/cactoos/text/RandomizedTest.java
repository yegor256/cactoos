/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test for {@link Randomized}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class RandomizedTest {

    @Test
    void generatesRandomTextOfRandomLength() throws Exception {
        new Assertion<>(
            "Generated text is empty",
            new Randomized().asString().length(),
            Matchers.greaterThan(0)
        ).affirm();
    }

    @Test
    void generatesRandomTextOfSpecifiedLength() throws Exception {
        new Assertion<>(
            "Generated text has incorrect length",
            new Randomized(512).asString().length(),
            new IsEqual<>(512)
        ).affirm();
    }

    @Test
    void generatesRandomTextOfSpecifiedChars() throws Exception {
        new Assertion<>(
            "Generated text contains not allowed characters",
            new Randomized('a')
                .asString()
                .replaceAll("a", "")
                .length(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    void generatesRandomTextOfSpecifiedCharsAndLength() {
        new Assertion<>(
            "Generated text doesn't match specification",
            new Randomized(10, 'a'),
            new HasString("aaaaaaaaaa")
        ).affirm();
    }
}
