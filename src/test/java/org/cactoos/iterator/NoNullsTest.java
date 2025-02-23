/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Satisfies;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link NoNulls}.
 *
 * <p>There is no thread-safety guarantee.
 * @since 0.35
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class NoNullsTest {

    @Test
    void nextThrowsErrorIfNull() {
        new Assertion<>(
            "Must throw exception",
            () -> new NoNulls<>(
                new IteratorOf<>(new String[]{null})
            ).next(),
            new Throws<>(
                new Satisfies<>(
                    (String msg) -> msg.matches("^Item #0 of .*? is NULL")
                ),
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void nthThrowsErrorIfNull() {
        new Assertion<>(
            "Must throw exception",
            () -> new TailOf<>(
                1,
                new NoNulls<>(
                    new IteratorOf<>("a", "b", null, "c")
                )
            ).next(),
            new Throws<>(
                new Satisfies<>(
                    (String msg) -> msg.matches("^Item #2 of .*? is NULL")
                ),
                IllegalStateException.class
            )
        ).affirm();
    }
}
