/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Collections;
import org.cactoos.scalar.ItemAt;
import org.cactoos.scalar.LengthOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
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
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void notCycledEmptyTest() {
        new Assertion<>(
            "Must generate an empty iterable",
            new LengthOf(
                new Cycled<>(
                    Collections::emptyIterator
                )
            ),
            new HasValue<>(0L)
        ).affirm();
    }
}
