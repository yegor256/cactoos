/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Tests for {@link Newline}.
 * @since 1.0.0
 */
final class NewlineTest {
    @Test
    void test() {
        MatcherAssert.assertThat(
            "Must be equal to the System.lineSeparator()",
            new Newline(),
            new IsText(System.lineSeparator())
        );
    }
}
