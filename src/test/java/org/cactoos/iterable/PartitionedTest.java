/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Partitioned}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class PartitionedTest {

    @Test
    void partitionedEmpty() {
        MatcherAssert.assertThat(
            "Must generate a Partitioned without values.",
            new LengthOf(
                new Partitioned<>(2)
            ),
            new HasValue<>(0L)
        );
    }

    @Test
    void partitionedWithPartial() {
        MatcherAssert.assertThat(
            "Must generate a Partitioned with partition size.",
            new LengthOf(
                new Partitioned<>(2, new IterableOf<>(1, 2, 3))
            ),
            new HasValue<>(2L)
        );
    }

}
