/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

import org.cactoos.bytes.NoNulls;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link NoNulls}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class BytesTest {
    @Test
    void failForNullArgument() {
        new Assertion<>(
            "Must fail for null argument.",
            () -> new NoNulls(null).asBytes(),
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void failForNullResult() {
        new Assertion<>(
            "Must fail for null result.",
            () -> new NoNulls(() -> null).asBytes(),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void okForNoNulls() throws Exception {
        new NoNulls(() -> new byte[1]).asBytes();
    }
}
