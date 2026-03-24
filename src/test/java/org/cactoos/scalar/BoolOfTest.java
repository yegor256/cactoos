/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link BoolOf}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class BoolOfTest {

    @Test
    void trueTest() {
        MatcherAssert.assertThat(
            "Must be parsed string 'true'",
            new BoolOf("true"),
            new HasValue<>(true)
        );
    }

    @Test
    void falseTest() {
        MatcherAssert.assertThat(
            "Must be parsed string 'false'",
            new BoolOf("false"),
            new HasValue<>(false)
        );
    }

    @Test
    void isFalseIfTextDoesNotRepresentABoolean() {
        MatcherAssert.assertThat(
            "Must be parsed a non-boolean string",
            new BoolOf("abc"),
            new HasValue<>(false)
        );
    }
}
