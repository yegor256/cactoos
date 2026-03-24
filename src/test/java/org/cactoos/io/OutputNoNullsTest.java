/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link OutputNoNulls}.
 * @since 0.10
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class OutputNoNullsTest {

    @Test
    void failForNullOutput() {
        MatcherAssert.assertThat(
            "Doesn't fail for null output",
            () -> new OutputNoNulls(null).stream(),
            new Throws<>(Exception.class)
        );
    }

    @Test
    void failForNullStream() {
        MatcherAssert.assertThat(
            "Doesn't fail for null output stream",
            () -> new OutputNoNulls(() -> null).stream(),
            new Throws<>(Exception.class)
        );
    }

    @Test
    void okForNoNullOutput() throws Exception {
        MatcherAssert.assertThat(
            "Must return stream for valid output",
            new OutputNoNulls(new DeadOutput()).stream(),
            new IsNot<>(new IsNull<>())
        );
    }
}
