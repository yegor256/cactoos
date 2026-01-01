/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link OutputNoNulls}.
 * @since 0.10
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class OutputNoNullsTest {

    @Test
    void failForNullOutput() {
        new Assertion<>(
            "Doesn't fail for null output",
            () -> new OutputNoNulls(null).stream(),
            new Throws<>(Exception.class)
        ).affirm();
    }

    @Test
    void failForNullStream() {
        new Assertion<>(
            "Doesn't fail for null output stream",
            () -> new OutputNoNulls(() -> null).stream(),
            new Throws<>(Exception.class)
        ).affirm();
    }

    @Test
    void okForNoNullOutput() throws Exception {
        new OutputNoNulls(new DeadOutput()).stream();
    }
}
