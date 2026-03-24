/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link Synced}.
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SyncedTest {

    @Test
    void behavesAsCollection() {
        MatcherAssert.assertThat(
            "Can't behave as a list",
            new Synced<>(new ListOf<>(1, 0, -1, -1, 2)),
            new BehavesAsList<>(0)
        );
    }

    @Test
    void worksInThreads() {
        MatcherAssert.assertThat(
            "should be run in threads",
            list -> !list.iterator().hasNext(),
            new RunsInThreads<>(new Synced<>(Collections.emptyList()))
        );
        MatcherAssert.assertThat(
            "should work as list",
            list -> {
                MatcherAssert.assertThat(
                    "should behave as a list",
                    list,
                    new BehavesAsList<>(0)
                );
                return true;
            },
            new RunsInThreads<>(new Synced<>(new ListOf<>(1, 0, -1, -1, 2)))
        );
    }
}
