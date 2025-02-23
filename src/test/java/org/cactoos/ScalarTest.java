/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

import org.cactoos.scalar.NoNulls;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link NoNulls}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class ScalarTest {

    @Test
    void failForNullArgument() {
        new Assertion<>(
            "Must fail for null argument",
            () -> new NoNulls<>(null).value(),
            new Throws<>(
                "NULL instead of a valid scalar",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void failForNullResult() {
        new Assertion<>(
            "Must fail for null result",
            () -> new NoNulls<>(() -> null).value(),
            new Throws<>(
                "NULL instead of a valid value",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void okForNoNulls() throws Exception {
        new NoNulls<>(() -> 1).value();
    }
}
