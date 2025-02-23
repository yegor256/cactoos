/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link Synced}.
 *
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class SyncedTest {

    @Test
    void behavesAsMap() {
        MatcherAssert.assertThat(
            "Can't behave as a map",
            new Synced<Integer, Integer>(
                new MapEntry<>(0, -1),
                new MapEntry<>(1, 1)
            ),
            new BehavesAsMap<>(1, 1)
        );
    }

    @Test
    void worksInThreads() {
        MatcherAssert.assertThat(
            "Can't behave as a map in multiple threads",
            map -> {
                MatcherAssert.assertThat(
                    "Can't behave as a map in thread",
                    map,
                    new BehavesAsMap<>(1, 1)
                );
                return true;
            },
            new RunsInThreads<>(
                new Synced<Integer, Integer>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            )
        );
    }

}
