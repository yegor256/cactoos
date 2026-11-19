/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.Collections;
import java.util.List;
import org.cactoos.Func;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Chained}.
 *
 * @since 0.7
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class ChainedTest {

    @Test
    void withoutIterable() {
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void withIterable() {
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void acceptsFuncsOfCompatibleSubtypes() throws Exception {
        final Func<Object, StringBuilder> before =
            input -> new StringBuilder(input.toString());
        final List<Func<Object, StringBuilder>> funcs =
            Collections.singletonList(
                input -> new StringBuilder(input.toString())
            );
        final Func<CharSequence, String> after = CharSequence::toString;
        MatcherAssert.assertThat(
            "Must accept funcs of compatible subtypes",
            new Chained<CharSequence, StringBuilder, CharSequence>(
                before, funcs, after
            ).apply("abc"),
            new IsEqual<>("abc")
        );
    }

    @Test
    void chainsFuncsOfCompatibleSubtypes() throws Exception {
        final Func<Object, StringBuilder> before =
            input -> new StringBuilder(input.toString());
        final Func<CharSequence, String> after = CharSequence::toString;
        MatcherAssert.assertThat(
            "Must chain funcs of compatible subtypes",
            new Chained<CharSequence, StringBuilder, CharSequence>(
                before, after
            ).apply("abc"),
            new IsEqual<>("abc")
        );
    }
}
