/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.cactoos.list.ListOf;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test for {@link Synced}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle TodoCommentCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class SyncedTest {

    @Test
    void syncIteratorReturnsCorrectValuesWithExternalLock() {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        new Assertion<>(
            "Unexpected value found.",
            new ListOf<>(
                new Synced<>(
                    lock, new IteratorOf<>("a", "b")
                )
            ).toArray(),
            new IsEqual<>(new Object[]{"a", "b"})
        ).affirm();
    }

    @Test
    void syncIteratorReturnsCorrectValuesWithInternalLock() {
        new Assertion<>(
            "Unexpected value found.",
            new ListOf<>(
                new Synced<>(
                    new IteratorOf<>("a", "b")
                )
            ).toArray(),
            new IsEqual<>(new Object[]{"a", "b"})
        ).affirm();
    }

    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    void correctValuesForConcurrentNextNext() {
        for (int iter = 0; iter < 5000; iter += 1) {
            new Assertion<>(
                "",
                map -> {
                    new Assertion<>(
                        "",
                        map.next(),
                        Matchers.anyOf(
                            new IsEqual<>("a"),
                            new IsEqual<>("b")
                        )
                    ).affirm();
                    return true;
                },
                new RunsInThreads<>(
                    new Synced<>(
                        new IteratorOf<>("a", "b")
                    ),
                    2
                )
            ).affirm();
        }
    }

    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    void correctValuesForConcurrentNextHasNext() {
        for (int iter = 0; iter < 5000; iter += 1) {
            new Assertion<>(
                "",
                map -> {
                    new Assertion<>(
                        "",
                        map.hasNext(),
                        Matchers.anyOf(
                            new IsEqual<>(true),
                            new IsEqual<>(true)
                        )
                    ).affirm();
                    new Assertion<>(
                        "",
                        map.next(),
                        Matchers.anyOf(
                            new IsEqual<>("a"),
                            new IsEqual<>("b")
                        )
                    ).affirm();
                    new Assertion<>(
                        "",
                        map.hasNext(),
                        Matchers.anyOf(
                            new IsEqual<>(true),
                            new IsEqual<>(false)
                        )
                    ).affirm();
                    return true;
                },
                new RunsInThreads<>(
                    new Synced<>(
                        new IteratorOf<>("a", "b")
                    ),
                    2
                )
            ).affirm();
        }
    }
}
