/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import java.util.IllegalFormatWidthException;
import org.cactoos.Fallback;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Fallback.From}.
 *
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"unchecked", "PMD.JUnitTestsShouldIncludeAssert"})
final class FallbackFromTest {

    @Test
    void supportsException() {
        new Assertion<>(
            "Must support exactly exception class",
            new Fallback.From<>(
                IOException.class,
                exp -> "IOException fallback"
            ).support(new IOException()),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    void supportsInheritedException() {
        new Assertion<>(
            "Must support inherited exception class",
            new Fallback.From<>(
                RuntimeException.class,
                exp -> "RuntimeException fallback #1"
            ).support(new IllegalFormatWidthException(1)),
            new IsEqual<>(3)
        ).affirm();
    }

    @Test
    void doesNotSupportException() {
        new Assertion<>(
            "Must not support unrelated exception class",
            new Fallback.From<>(
                RuntimeException.class,
                exp -> "RuntimeException fallback #2"
            ).support(new ClassNotFoundException()),
            new IsEqual<>(Integer.MIN_VALUE)
        ).affirm();
    }
}
