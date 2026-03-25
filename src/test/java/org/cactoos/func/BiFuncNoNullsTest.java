/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link BiFuncNoNulls}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class BiFuncNoNullsTest {

    @Test
    void failForNullFunc() {
        MatcherAssert.assertThat(
            "Must fail if function is null.",
            () -> new BiFuncNoNulls<>(null).apply(new Object(), new Object()),
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void failForNullFirstArg() {
        MatcherAssert.assertThat(
            "Must fail if first arg is null.",
            () -> new BiFuncNoNulls<>(
                (first, second) -> first
                ).apply(null, new Object()),
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void failForNullSecondArg() {
        MatcherAssert.assertThat(
            "Must fail if second arg is null.",
            () -> new BiFuncNoNulls<>(
                (first, second) -> first
            ).apply(new Object(), null),
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void failForNullResult() {
        MatcherAssert.assertThat(
            "Must fail if function is null.",
            () -> new BiFuncNoNulls<>(
                (first, second) -> null
            ).apply(new Object(), new Object()),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void okForNoNulls() throws Exception {
        MatcherAssert.assertThat(
            "Must return value for valid arguments",
            new BiFuncNoNulls<>(
                (first, second) -> first
            ).apply(new Object(), new Object()),
            new IsNot<>(new IsNull<>())
        );
    }
}
