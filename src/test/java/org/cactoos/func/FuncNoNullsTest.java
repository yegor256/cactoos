/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link FuncNoNulls}.
 * @since 0.10
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class FuncNoNullsTest {

    @Test
    void failForNullFunc() {
        MatcherAssert.assertThat(
            "Doesn't fail for null func",
            () -> new FuncNoNulls<>(null).apply(new Object()),
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void failForNullInput() {
        MatcherAssert.assertThat(
            "Doesn't fail for null input",
            () -> new FuncNoNulls<>(input -> input).apply(null),
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void okForNoNulls() throws Exception {
        MatcherAssert.assertThat(
            "Must return value for valid argument",
            new FuncNoNulls<>(input -> input).apply(new Object()),
            new IsNot<>(new IsNull<>())
        );
    }

    @Test
    void failForNullResult() {
        MatcherAssert.assertThat(
            "Doesn't fail for null result",
            () -> new FuncNoNulls<>(input -> null).apply(new Object()),
            new Throws<>(IOException.class)
        );
    }
}
