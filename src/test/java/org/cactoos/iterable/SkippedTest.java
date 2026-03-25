/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test Case for {@link Skipped}.
 *
 * @since 0.34
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SkippedTest {

    @Test
    void skipIterable() {
        final String three = "three";
        final String four = "four";
        MatcherAssert.assertThat(
            "Must skip elements in iterable",
            new Skipped<>(
                2,
                new IterableOf<>("one", "two", three, four)
            ),
            new IsEqual<>(
                new IterableOf<>(
                    three,
                    four
                )
            )
        );
    }

    @Test
    void skipArray() {
        final String seven = "seven";
        final String eight = "eight";
        MatcherAssert.assertThat(
            "Must skip elements in array",
            new Skipped<>(
                2,
                "five", "six", seven, eight
            ),
            new IsEqual<>(
                new IterableOf<>(
                    seven,
                    eight
                )
            )
        );
    }

    @Test
    void skipCollection() {
        final String twelve = "twelve";
        final String hundred = "hundred";
        MatcherAssert.assertThat(
            "Must skip elements in collection",
            new Skipped<>(
                2,
                new ListOf<>("nine", "eleven", twelve, hundred)
            ),
            new IsEqual<>(
                new IterableOf<>(twelve, hundred)
            )
        );
    }

    @Test
    void skippedAllElements() {
        MatcherAssert.assertThat(
            "Must skip all elements",
            new Skipped<>(
                2,
                "Frodo", "Gandalf"
            ),
            new IsEmptyIterable<>()
        );
    }

    @Test
    void skippedMoreThanExists() {
        MatcherAssert.assertThat(
            "Can't skip more than exists",
            new Skipped<>(
                Integer.MAX_VALUE,
                "Sauron", "Morgoth"
            ),
            new IsEmptyIterable<>()
        );
    }

    @Test
    void skippedNegativeSize() {
        final String varda = "varda";
        final String yavanna = "yavanna";
        final String nessa = "nessa";
        final String vaire = "vaire";
        MatcherAssert.assertThat(
            "Must process negative skipped size",
            new Skipped<>(
                -1,
                varda, yavanna, nessa, vaire
            ),
            new IsEqual<>(
                new IterableOf<>(varda, yavanna, nessa, vaire)
            )
        );
    }
}
