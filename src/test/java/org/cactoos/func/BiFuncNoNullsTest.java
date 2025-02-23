/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link BiFuncNoNulls}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class BiFuncNoNullsTest {

    @Test
    void failForNullFunc() {
        new Assertion<>(
            "Must fail if function is null.",
            () -> new BiFuncNoNulls<>(null).apply(new Object(), new Object()),
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void failForNullFirstArg() {
        new Assertion<>(
            "Must fail if first arg is null.",
            () -> new BiFuncNoNulls<>(
                (first, second) -> first
                ).apply(null, new Object()),
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void failForNullSecondArg() {
        new Assertion<>(
            "Must fail if second arg is null.",
            () -> new BiFuncNoNulls<>(
                (first, second) -> first
            ).apply(new Object(), null),
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void failForNullResult() {
        new Assertion<>(
            "Must fail if function is null.",
            () -> new BiFuncNoNulls<>(
                (first, second) -> null
            ).apply(new Object(), new Object()),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void okForNoNulls() throws Exception {
        new BiFuncNoNulls<>(
            (first, second) -> first
        ).apply(new Object(), new Object());
    }
}
