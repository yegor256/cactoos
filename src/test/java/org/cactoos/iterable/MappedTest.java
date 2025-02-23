/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Collections;
import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.Upper;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Mapped}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class MappedTest {

    @Test
    void transformsList() {
        new Assertion<>(
            "Must transform an iterable",
            new Mapped<>(
                input -> new Upper(new TextOf(input)),
                new IterableOf<>(
                    "hello", "world", "друг"
                )
            ).iterator().next(),
            new IsText("HELLO")
        ).affirm();
    }

    @Test
    void transformsEmptyList() {
        new Assertion<>(
            "Must transform an empty iterable",
            new Mapped<>(
                (String input) -> new Upper(new TextOf(input)),
                Collections.emptyList()
            ),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    void string() {
        new Assertion<>(
            "Must convert to string",
            new Mapped<>(
                x -> x * 2,
                new ListOf<>(1, 2, 3)
            ).toString(),
            new IsEqual<>("2, 4, 6")
        ).affirm();
    }

    @Test
    void transformsArray() {
        new Assertion<>(
            "Transforms an array",
            new Mapped<>(
                input -> new Upper(new TextOf(input)).asString(),
                "a", "b", "c"
            ),
            new IsEqual<>(new IterableOf<>("A", "B", "C"))
        ).affirm();
    }
}
