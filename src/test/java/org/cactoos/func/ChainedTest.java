/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.Func;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.LengthOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Chained}.
 *
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class ChainedTest {

    @Test
    void withoutIterable() {
        new Assertion<>(
            "Must work without iterable",
            new LengthOf(
                new Filtered<>(
                    input -> input.endsWith("12"),
                    new Mapped<>(
                        new Chained<>(
                            input -> input.concat("1"),
                            (String input) -> input.concat("2")
                        ),
                        new IterableOf<>("public", "final", "class")
                    )
                )
            ),
            new HasValue<>(3L)
        ).affirm();
    }

    @Test
    void withIterable() {
        new Assertion<>(
            "Must work with iterable",
            new LengthOf(
                new Filtered<>(
                    input -> !input.startsWith("st"),
                    new Mapped<>(
                        new Chained<>(
                            input -> input.concat("1"),
                            new IterableOf<Func<String, String>>(
                                input -> input.concat("2"),
                                input -> input.replaceAll("a", "b")
                            ),
                            String::trim
                        ),
                        new IterableOf<>("private", "static", "String")
                    )
                )
            ),
            new HasValue<>(2L)
        ).affirm();
    }
}
