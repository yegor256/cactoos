/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.LinkedList;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link SyncFunc}.
 *
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class SyncFuncTest {
    @Test
    void funcWorksInThreads() {
        MatcherAssert.assertThat(
            "Sync func can't work well in multiple threads",
            func -> func.apply(true),
            new RunsInThreads<>(
                new SyncFunc<Boolean, Boolean>(
                    input -> true
                ),
                100
            )
        );
    }

    @Test
    void addsAllElementsInThreads() throws Exception {
        final List<Integer> list = new LinkedList<>();
        final int threads = 100;
        for (int idx = 0; idx < threads; ++idx) {
            new SyncFunc<Boolean, Boolean>(input -> list.add(1)).apply(true);
        }
        MatcherAssert.assertThat(
            "Must run the expected amount of threads",
            list.size(),
            new IsEqual<>(threads)
        );
    }
}
