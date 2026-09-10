/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Satisfies;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link NoNulls}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.35
 */
final class NoNullsTest {

    @Test
    void nextThrowsErrorIfNull() {
        MatcherAssert.assertThat(
            "Must throw exception",
            () -> new NoNulls<>(
                new IteratorOf<>((String) null)
            ).next(),
            new Throws<>(
                new Satisfies<>(
                    (String msg) -> msg.matches("^Item #0 of .*? is NULL")
                ),
                IllegalStateException.class
            )
        );
    }

    @Test
    void nthThrowsErrorIfNull() {
        MatcherAssert.assertThat(
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
        );
    }
}
