/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link Synced}.
 *
 * @since 0.24
 */
final class SyncedTest {

    @Test
    void behavesAsCollection() {
        new Assertion<>(
            "Can't behave as a list",
            new Synced<>(new ListOf<>(1, 0, -1, -1, 2)),
            new BehavesAsList<>(0)
        ).affirm();
    }

    @Test
    void worksInThreadsWithEmptyList() {
        new Assertion<>(
            "should be run in threads",
            list -> !list.iterator().hasNext(),
            new RunsInThreads<>(new Synced<>(Collections.emptyList()))
        ).affirm();
    }

    @Test
    void behavesAsListInThreads() {
        new Assertion<>(
            "should work as list",
            list -> {
                new Assertion<>(
                    "should behave as a list",
                    list,
                    new BehavesAsList<>(0)
                ).affirm();
                return true;
            },
            new RunsInThreads<>(new Synced<>(new ListOf<>(1, 0, -1, -1, 2)))
        ).affirm();
    }
}
