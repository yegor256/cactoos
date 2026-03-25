/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Partitioned}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class PartitionedTest {

    @Test
    void emptyPartitioned() {
        MatcherAssert.assertThat(
            "Can't generate an empty Partitioned.",
            new LengthOf(
                new IterableOf<>(
                    new Partitioned<>(1, Collections.emptyIterator())
                )
            ),
            new HasValue<>(0L)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void partitionedOne() {
        MatcherAssert.assertThat(
            "Can't generate a Partitioned of partition size 1.",
            new ArrayList<>(
                new ListOf<>(
                    new Partitioned<>(1, new ListOf<>(1, 2, 3).iterator())
                )
            ),
            Matchers.equalTo(
                new ListOf<>(
                    Collections.singletonList(1), Collections.singletonList(2),
                    Collections.singletonList(3)
                )
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void partitionedEqualSize() {
        MatcherAssert.assertThat(
            "Can't generate a Partitioned of partition size 2.",
            new ArrayList<>(
                new ListOf<>(
                    new Partitioned<>(2, new ListOf<>(1, 2, 3, 4).iterator())
                )
            ),
            Matchers.equalTo(
                new ListOf<>(new ListOf<>(1, 2), new ListOf<>(3, 4))
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void partitionedLastPartitionSmaller() {
        MatcherAssert.assertThat(
            "Can't generate a Partitioned of size 2 last partition smaller.",
            new ListOf<>(
                new Partitioned<>(2, new ListOf<>(1, 2, 3).iterator())
            ),
            new IsEqual<>(
                new ListOf<>(
                    new ListOf<>(1, 2),
                    new ListOf<>(3)
                )
            )
        );
    }

    @Test
    void partitionedWithPartitionSizeSmallerOne() {
        MatcherAssert.assertThat(
            "Exception is expected for partition size lower 1",
            () -> new Partitioned<>(0, new ListOf<>(1).iterator()).next(),
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void partitionedListsAreUnmodifiable() {
        MatcherAssert.assertThat(
            "Exception is expected on modification operations",
            () -> {
                new Partitioned<>(
                    2, new ListOf<>(1, 2).iterator()
                ).next().clear();
                return 1;
            },
            new Throws<>(UnsupportedOperationException.class)
        );
    }

    @Test
    void emptyPartitionedNextThrowsException() {
        MatcherAssert.assertThat(
            "Exception is expected for iteration empty",
            () -> new Partitioned<>(
                2, Collections.emptyIterator()
            ).next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

}
