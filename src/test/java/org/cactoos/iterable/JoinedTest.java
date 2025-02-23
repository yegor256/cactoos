/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Joined}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class JoinedTest {

    @Test
    void joinsIterables() {
        new Assertion<>(
            "Must concatenate iterables together",
            new Joined<String>(
                new IterableOf<>("h", "w"),
                new IterableOf<>("a", "y")
            ),
            new IsEqual<>(new IterableOf<>("h", "w", "a", "y"))
        ).affirm();
    }

    @Test
    void joinsMappedIterables() {
        new Assertion<>(
            "Must concatenate mapped iterables together",
            new Joined<String>(
                new Mapped<>(
                    IterableOf::new,
                    new IterableOf<>("x", "y")
                )
            ),
            new IsEqual<>(new IterableOf<>("x", "y"))
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void joinItemAndIterable() {
        new Assertion<>(
            "Must join item and iterable",
            new Joined<>(
                0,
                new IterableOf<>(1, 2, 3)
            ),
            new IsEqual<>(new IterableOf<>(0, 1, 2, 3))
        ).affirm();
    }

}
