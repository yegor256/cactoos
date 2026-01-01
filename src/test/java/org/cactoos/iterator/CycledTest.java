/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Collections;
import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.NoNulls;
import org.cactoos.scalar.ItemAt;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test Case for {@link Cycled}.
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class CycledTest {

    @Test
    void repeatIteratorTest() {
        final String expected = "two";
        new Assertion<>(
            "must repeat iterator",
            new ItemAt<>(
                7,
                new IterableOf<>(
                    new Cycled<>(
                        new NoNulls<>(
                            new IterableOf<>(
                                "one", expected, "three"
                            )
                        )
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
            "Error is expected for empty iterator",
            () -> new Cycled<>(
                Collections::emptyIterator
            ).next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }
}
