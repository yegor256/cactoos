/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Collections;
import org.cactoos.scalar.ItemAt;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test Case for {@link Cycled}.
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class CycledTest {

    @Test
    void repeatIterableTest() {
        final String expected = "two";
        MatcherAssert.assertThat(
            "Must repeat iterable",
            new ItemAt<>(
                7, new Cycled<>(
                    new IterableOf<>(
                        "one", expected, "three"
                    )
                )
            ),
            new HasValue<>(
                expected
            )
        );
    }

    @Test
    void notCycledEmptyTest() {
        MatcherAssert.assertThat(
            "Must generate an empty iterable",
            new LengthOf(
                new Cycled<>(
                    Collections::emptyIterator
                )
            ),
            new HasValue<>(0L)
        );
    }

    @Test
    void varargsConstructorTest() {
        MatcherAssert.assertThat(
            "Must repeat varargs",
            new ItemAt<>(
                6,
                new Cycled<>(1, 2, 3, 4)
            ),
            new HasValue<>(3)
        );
    }
}
