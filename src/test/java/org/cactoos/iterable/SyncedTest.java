/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test Case for {@link Synced}.
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SyncedTest {

    @Test
    void worksInThreads() {
        MatcherAssert.assertThat(
            "Can't behave as an iterable in multiple threads",
            list -> {
                MatcherAssert.assertThat(
                    "Should be the same iterator",
                    list.iterator().next(),
                    Matchers.equalTo(list.iterator().next())
                );
                return true;
            },
            new RunsInThreads<>(new Synced<>(1, 0, -1, -1, 2))
        );
    }

}
