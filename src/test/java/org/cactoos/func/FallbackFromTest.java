/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import java.util.IllegalFormatWidthException;
import org.cactoos.Fallback;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Fallback.From}.
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("unchecked")
final class FallbackFromTest {

    @Test
    void supportsException() {
        MatcherAssert.assertThat(
            "Must support exactly exception class",
            new Fallback.From<>(
                IOException.class,
                exp -> "IOException fallback"
            ).support(new IOException()),
            new IsEqual<>(0)
        );
    }

    @Test
    void supportsInheritedException() {
        MatcherAssert.assertThat(
            "Must support inherited exception class",
            new Fallback.From<>(
                RuntimeException.class,
                exp -> "RuntimeException fallback #1"
            ).support(new IllegalFormatWidthException(1)),
            new IsEqual<>(3)
        );
    }

    @Test
    void doesNotSupportException() {
        MatcherAssert.assertThat(
            "Must not support unrelated exception class",
            new Fallback.From<>(
                RuntimeException.class,
                exp -> "RuntimeException fallback #2"
            ).support(new ClassNotFoundException()),
            new IsEqual<>(Integer.MIN_VALUE)
        );
    }
}
