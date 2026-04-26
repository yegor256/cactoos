/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MappedWithIndex}.
 * @since 1.0.0
 */
final class MappedWithIndexTest {

    @Test
    void transformsIterable() {
        MatcherAssert.assertThat(
            "must transform an iterable",
            new MappedWithIndex<>(
                (input, index) -> new Joined(
                    "-",
                    new TextOf(index.toString()),
                    new TextOf(input)
                ),
                new IterableOf<>(
                    "hello", "world"
                )
            ),
            new IsEqual<>(
                new IterableOf<>(
                    new TextOf("0-hello"),
                    new TextOf("1-world")
                )
            )
        );
    }

    @Test
    void transformsEmptyIterable() {
        MatcherAssert.assertThat(
            "must transform an empty iterable",
            new MappedWithIndex<>(
                (input, index) -> {
                    Assertions.fail("must do not be executed");
                    return input;
                },
                new IterableOf<>()
            ),
            new IsEqual<>(
                new IterableOf<>()
            )
        );
    }

    @Test
    void string() {
        MatcherAssert.assertThat(
            "must convert to string",
            new MappedWithIndex<>(
                (x, index) -> x * index * 2,
                new IterableOf<>(1, 2, 3)
            ).toString(),
            new IsEqual<>("0, 4, 12")
        );
    }

    @Test
    void transformsArray() {
        MatcherAssert.assertThat(
            "Transforms an array",
            new MappedWithIndex<>(
                (input, index) -> new Joined(
                    "-",
                    index.toString(),
                    input
                ),
                "a", "b", "c"
            ),
            new IsEqual<>(
                new IterableOf<>(
                    new TextOf("0-a"),
                    new TextOf("1-b"),
                    new TextOf("2-c")
                )
            )
        );
    }
}
