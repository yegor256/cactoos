/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.Collection;
import java.util.List;
import org.cactoos.Proc;
import org.cactoos.list.ListOf;
import org.cactoos.list.Synced;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link ForEachInThreads}.
 *
 * @since 1.0
 */
final class ForEachInThreadsTest {

    @Test
    @SuppressWarnings("unchecked")
    void iteratesInThreads() throws Exception {
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
        MatcherAssert.assertThat(
            "List does not contain mapped Iterable elements",
            list,
            new IsIterableContainingInAnyOrder<>(
                new ListOf<>(
                    new IsEqual<>(1),
                    new IsEqual<>(2)
                )
            )
        );
    }

    @Test
    void worksWithGenerics() throws Exception {
        final Collection<Iterable<Integer>> list = new Synced<>(
            new ListOf<>()
        );
        new ForEachInThreads<Collection<Integer>>(
            (Proc<? super Iterable<Integer>>) list::add
        ).exec(
            new ListOf<>(
                new ListOf<>(1), new ListOf<>(2)
            )
        );
        MatcherAssert.assertThat(
            "List does not contain elements from sub-type iterables",
            list,
            new IsIterableContainingInAnyOrder<>(
                new ListOf<>(
                    new IsEqual<>(new ListOf<>(1)),
                    new IsEqual<>(new ListOf<>(2))
                )
            )
        );
    }
}
