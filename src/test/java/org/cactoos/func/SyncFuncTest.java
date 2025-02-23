/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.LinkedList;
import java.util.List;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link SyncFunc}.
 *
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class SyncFuncTest {
    @Test
    void funcWorksInThreads() {
        final List<Integer> list = new LinkedList<>();
        final int threads = 100;
        new Assertion<>(
            "Sync func can't work well in multiple threads",
            func -> func.apply(true),
            new RunsInThreads<>(
                new SyncFunc<Boolean, Boolean>(
                    input -> list.add(1)
                ),
                threads
            )
        ).affirm();
        new Assertion<>(
            "Must run the expected amount of threads",
            list.size(),
            new IsEqual<>(threads)
        ).affirm();
    }
}
