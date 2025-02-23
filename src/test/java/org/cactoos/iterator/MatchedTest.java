/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Objects;
import org.cactoos.list.ListOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Mapped}.
 * @since 0.47
 */
final class MatchedTest {
    @Test
    void failsWhenElementsNotMatch() {
        new Assertion<>(
            "All elements have correlation function 'equals'",
            () -> new ListOf<>(
                new Matched<>(
                    Objects::equals,
                    new IteratorOf<>(1),
                    new IteratorOf<>(0)
                )
            ),
            new Throws<>(
                new IsEqual<>(
                    "There is no correlation between `1` and `0`."
                ),
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void failsOnSizeMismatch() {
        new Assertion<>(
            "must fail if sizes of iterators are different",
            () -> new ListOf<>(
                new Matched<>(
                    Objects::equals,
                    new IteratorOf<>(1),
                    new IteratorOf<>()
                )
            ),
            new Throws<>(
                new IsEqual<>("Size mismatch of iterators"),
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void shouldProduceValuesOfFirstIterator() {
        new Assertion<>(
            "must match all items of first iterator",
            new ListOf<>(
                new Matched<>(
                    (Number first, Number second) -> first.intValue() == second.intValue(),
                    new IteratorOf<>(1f, 2f, 3f),
                    new IteratorOf<>(1L, 2L, 3L)
                )
            ),
            new HasValues<>(1f, 2f, 3f)
        ).affirm();
    }

}
