/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

import org.cactoos.bytes.NoNulls;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link NoNulls}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class BytesTest {

    @Test
    void failForNullArgument() {
        MatcherAssert.assertThat(
            "Must fail for null argument.",
            () -> new NoNulls(null).asBytes(),
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void failForNullResult() {
        MatcherAssert.assertThat(
            "Must fail for null result.",
            () -> new NoNulls(() -> null).asBytes(),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void okForNoNulls() throws Exception {
        MatcherAssert.assertThat(
            "must return bytes without throwing",
            new NoNulls(() -> new byte[1]).asBytes().length,
            new IsEqual<>(1)
        );
    }
}
