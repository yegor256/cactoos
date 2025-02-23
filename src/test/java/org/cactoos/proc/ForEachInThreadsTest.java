/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.List;
import org.cactoos.list.ListOf;
import org.cactoos.list.Synced;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link ForEachInThreads}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ForEachInThreadsTest {

    @Test
    @SuppressWarnings("unchecked")
    void testProcIterable() throws Exception {
        final List<Integer> list = new Synced<>(
            new ListOf<>()
        );
        new ForEachInThreads<Integer>(
            new ProcNoNulls<>(
                list::add
            )
        ).exec(
            new ListOf<>(
                1, 2
            )
        );
        new Assertion<>(
            "List does not contain mapped Iterable elements",
            list,
            new IsIterableContainingInAnyOrder<>(
                new ListOf<>(
                    new IsEqual<>(1),
                    new IsEqual<>(2)
                )
            )
        ).affirm();
    }

}
