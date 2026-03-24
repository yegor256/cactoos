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
final class SyncFuncTest {
    @Test
    void funcWorksInThreads() {
        final List<Integer> list = new LinkedList<>();
        MatcherAssert.assertThat(
            "Sync func can't work well in multiple threads",
            func -> func.apply(true),
            new RunsInThreads<>(
                new SyncFunc<Boolean, Boolean>(
                    input -> list.add(1)
                ),
                100
            )
        );
        MatcherAssert.assertThat(
            "Must run the expected amount of threads",
            list.size(),
            new IsEqual<>(100)
        );
    }
}
