/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Joined}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class JoinedTest {

    @Test
    void joinsIterables() {
        MatcherAssert.assertThat(
            "Must concatenate iterables together",
            new Joined<String>(
                new IterableOf<>("h", "w"),
                new IterableOf<>("a", "y")
            ),
            new IsEqual<>(new IterableOf<>("h", "w", "a", "y"))
        );
    }

    @Test
    void joinsMappedIterables() {
        MatcherAssert.assertThat(
            "Must concatenate mapped iterables together",
            new Joined<String>(
                new Mapped<>(
                    IterableOf::new,
                    new IterableOf<>("x", "y")
                )
            ),
            new IsEqual<>(new IterableOf<>("x", "y"))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void joinItemAndIterable() {
        MatcherAssert.assertThat(
            "Must join item and iterable",
            new Joined<>(
                0,
                new IterableOf<>(1, 2, 3)
            ),
            new IsEqual<>(new IterableOf<>(0, 1, 2, 3))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void joinIterableAndItem() {
        new Assertion<>(
                "Must join item and iterable",
                new Joined<>(
                        new IterableOf<>(1, 2, 3),
                        4, 5
                ),
                new IsEqual<>(new IterableOf<>(1, 2, 3, 4, 5))
        ).affirm();
    }

}
