/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test for {@link Synced}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle TodoCommentCheck (500 lines)
 */
final class SyncedTest {

    @Test
    void syncIteratorReturnsCorrectValuesWithExternalLock() {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        MatcherAssert.assertThat(
            "Unexpected value found.",
            new ListOf<>(
                new Synced<>(
                    lock, new IteratorOf<>("a", "b")
                )
            ).toArray(),
            new IsEqual<>(new Object[]{"a", "b"})
        );
    }

    @Test
    void syncIteratorReturnsCorrectValuesWithInternalLock() {
        MatcherAssert.assertThat(
            "Unexpected value found.",
            new ListOf<>(
                new Synced<>(
                    new IteratorOf<>("a", "b")
                )
            ).toArray(),
            new IsEqual<>(new Object[]{"a", "b"})
        );
    }

    @Test
    void correctValuesForConcurrentNextNext() {
        for (int iter = 0; iter < 5000; iter += 1) {
            MatcherAssert.assertThat(
                "",
                map -> {
                    MatcherAssert.assertThat(
                        "",
                        map.next(),
                        Matchers.anyOf(
                            new IsEqual<>("a"),
                            new IsEqual<>("b")
                        )
                    );
                    return true;
                },
                new RunsInThreads<>(
                    new Synced<>(
                        new IteratorOf<>("a", "b")
                    ),
                    2
                )
            );
        }
    }

    @Test
    void correctValuesForConcurrentNextHasNext() {
        for (int iter = 0; iter < 5000; iter += 1) {
            MatcherAssert.assertThat(
                "",
                map -> {
                    MatcherAssert.assertThat(
                        "",
                        map.hasNext(),
                        Matchers.anyOf(
                            new IsEqual<>(true),
                            new IsEqual<>(true)
                        )
                    );
                    MatcherAssert.assertThat(
                        "",
                        map.next(),
                        Matchers.anyOf(
                            new IsEqual<>("a"),
                            new IsEqual<>("b")
                        )
                    );
                    MatcherAssert.assertThat(
                        "",
                        map.hasNext(),
                        Matchers.anyOf(
                            new IsEqual<>(true),
                            new IsEqual<>(false)
                        )
                    );
                    return true;
                },
                new RunsInThreads<>(
                    new Synced<>(
                        new IteratorOf<>("a", "b")
                    ),
                    2
                )
            );
        }
    }
}
