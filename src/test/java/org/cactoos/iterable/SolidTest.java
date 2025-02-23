/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.Scalar;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test Case for {@link Solid}.
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SolidTest {

    @Test
    void makesListFromMappedIterable() {
        final Iterable<Integer> list = new Solid<>(
            new Mapped<>(
                i -> i + 1,
                new IterableOf<>(1, -1, 0, 1)
            )
        );
        MatcherAssert.assertThat(
            "Can't turn a mapped iterable into a list",
            list, Matchers.iterableWithSize(4)
        );
        MatcherAssert.assertThat(
            "Can't turn a mapped iterable into a list, again",
            list, Matchers.iterableWithSize(4)
        );
    }

    @Test
    void mapsToSameObjects() {
        final Iterable<Scalar<Integer>> list = new Solid<>(
            new Mapped<>(
                i -> () -> i,
                new IterableOf<>(1, -1, 0, 1)
            )
        );
        MatcherAssert.assertThat(
            "Can't map only once",
            list.iterator().next(), Matchers.equalTo(list.iterator().next())
        );
    }

    @Test
    void worksInThreadsMultipleTimes() {
        for (int count = 0; count < 10; ++count) {
            this.worksInThreads();
        }
    }

    @Test
    void worksInThreads() {
        MatcherAssert.assertThat(
            "Can't behave as an iterable in multiple threads",
            list -> {
                MatcherAssert.assertThat(
                    "Iterator should be the same",
                    list.iterator().next(),
                    Matchers.equalTo(list.iterator().next())
                );
                return true;
            },
            new RunsInThreads<>(new Solid<>(1, 0, -1, -1, 2))
        );
    }

}
