/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Repeated}.
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class RepeatedTest {

    @Test
    void repeatsWordsText() {
        MatcherAssert.assertThat(
            "Can't repeats a text",
            new Repeated("hello", 2),
            new HasString("hellohello")
        );
    }

    @Test
    void repeatsCharText() {
        MatcherAssert.assertThat(
            "Can't repeats a char",
            new Repeated("A", 5),
            new HasString("AAAAA")
        );
    }
}
