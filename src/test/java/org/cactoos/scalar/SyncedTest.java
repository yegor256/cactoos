/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.LinkedList;
import java.util.List;
import org.cactoos.Scalar;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link Synced}.
 *
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SyncedTest {

    @Test
    void worksInThreads() {
        final List<Integer> list = new LinkedList<>();
        final int threads = 100;
        new Assertion<>(
            "must work well in multiple threads",
            Scalar::value,
            new RunsInThreads<>(
                new Synced<>(() -> list.add(1)), threads
            )
        ).affirm();
        new Assertion<>(
            "must have correct size",
            list.size(),
            new IsEqual<>(threads)
        ).affirm();
    }

}
