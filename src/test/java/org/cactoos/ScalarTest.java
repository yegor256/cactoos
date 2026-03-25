/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

import org.cactoos.scalar.NoNulls;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link NoNulls}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ScalarTest {

    @Test
    void failForNullArgument() {
        MatcherAssert.assertThat(
            "Must fail for null argument",
            () -> new NoNulls<>(null).value(),
            new Throws<>(
                "NULL instead of a valid scalar",
                IllegalArgumentException.class
            )
        );
    }

    @Test
    void failForNullResult() {
        MatcherAssert.assertThat(
            "Must fail for null result",
            () -> new NoNulls<>(() -> null).value(),
            new Throws<>(
                "NULL instead of a valid value",
                IllegalStateException.class
            )
        );
    }

    @Test
    void okForNoNulls() throws Exception {
        MatcherAssert.assertThat(
            "Must return value for non-null result",
            new NoNulls<>(() -> 1).value(),
            new IsEqual<>(1)
        );
    }
}
