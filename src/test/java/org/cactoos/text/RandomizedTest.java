/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
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
        MatcherAssert.assertThat(
            "Generated text is empty",
            new Randomized().asString().length(),
            Matchers.greaterThan(0)
        );
    }

    @Test
    void generatesRandomTextOfSpecifiedLength() throws Exception {
        MatcherAssert.assertThat(
            "Generated text has incorrect length",
            new Randomized(512).asString().length(),
            new IsEqual<>(512)
        );
    }

    @Test
    void generatesRandomTextOfSpecifiedChars() throws Exception {
        MatcherAssert.assertThat(
            "Generated text contains not allowed characters",
            new Randomized('a')
                .asString()
                .replaceAll("a", "")
                .length(),
            new IsEqual<>(0)
        );
    }

    @Test
    void generatesRandomTextOfSpecifiedCharsAndLength() {
        MatcherAssert.assertThat(
            "Generated text doesn't match specification",
            new Randomized(10, 'a'),
            new HasString("aaaaaaaaaa")
        );
    }
}
