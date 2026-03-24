/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Collections;
import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.Upper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Mapped}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class MappedTest {

    @Test
    void transformsList() {
        MatcherAssert.assertThat(
            "Must transform an iterable",
            new Mapped<>(
                input -> new Upper(new TextOf(input)),
                new IterableOf<>(
                    "hello", "world", "друг"
                )
            ).iterator().next(),
            new IsText("HELLO")
        );
    }

    @Test
    void transformsEmptyList() {
        MatcherAssert.assertThat(
            "Must transform an empty iterable",
            new Mapped<>(
                (String input) -> new Upper(new TextOf(input)),
                Collections.emptyList()
            ),
            new IsEmptyIterable<>()
        );
    }

    @Test
    void string() {
        MatcherAssert.assertThat(
            "Must convert to string",
            new Mapped<>(
                x -> x * 2,
                new ListOf<>(1, 2, 3)
            ).toString(),
            new IsEqual<>("2, 4, 6")
        );
    }

    @Test
    void transformsArray() {
        MatcherAssert.assertThat(
            "Transforms an array",
            new Mapped<>(
                input -> new Upper(new TextOf(input)).asString(),
                "a", "b", "c"
            ),
            new IsEqual<>(new IterableOf<>("A", "B", "C"))
        );
    }
}
