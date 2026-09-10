/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link SetOf}.
 * @since 0.49.2
 */
final class SetOfTest {

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetWithOriginalDuplicationsInTheTail() {
        MatcherAssert.assertThat(
            "Must keep unique integer numbers",
            new SetOf<>(1, 2, 2),
            new AllOf<>(
                new HasSize(2),
                new HasValues<>(1, 2)
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetWithOriginalDuplicationsInTheHead() {
        MatcherAssert.assertThat(
            "Must keep unique integer numbers",
            new SetOf<>(1, 1, 2, 3),
            new AllOf<>(
                new HasSize(3),
                new HasValues<>(1, 2, 3)
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetWithOriginalDuplicationsInTheMiddle() {
        MatcherAssert.assertThat(
            "Must keep unique integer numbers",
            new SetOf<>(1, 2, 2, 3),
            new AllOf<>(
                new HasSize(3),
                new HasValues<>(1, 2, 3)
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetMergedCollectionsOfLiteralsWithDuplicates() {
        MatcherAssert.assertThat(
            "Must keep unique string literals",
            new SetOf<>(
                new Joined<String>(
                    new IterableOf<>("cc", "ff"),
                    new IterableOf<>("aa", "bb", "cc", "dd")
                )
            ),
            new AllOf<>(
                new HasSize(5),
                new HasValues<>("aa", "bb", "cc", "dd", "ff")
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetWithOriginalDuplicationsOfCharsInTheMiddle() {
        MatcherAssert.assertThat(
            "Must keep unique characters",
            new SetOf<>('a', 'b', 'b', 'c', 'a', 'b', 'd'),
            new AllOf<>(
                new HasSize(4),
                new HasValues<>('a', 'b', 'c', 'd')
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetWithOriginalDuplicationsOfDoublesInTheMiddle() {
        MatcherAssert.assertThat(
            "Must keep unique double numbers",
            new SetOf<>(1.5d, 2.4d, 1.5d, 2.4d, 2.4d, 1.5d),
            new AllOf<>(
                new HasSize(2),
                new HasValues<>(1.5d, 2.4d)
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetWithOriginalDuplicationsOfTextsInTheMiddle() {
        MatcherAssert.assertThat(
            "Must keep unique TextOf objects",
            new SetOf<>(
                new TextOf("12345"),
                new TextOf("67890"),
                new TextOf("12345"),
                new TextOf("00000")
            ),
            new AllOf<>(
                new HasSize(3),
                new HasValues<>(
                    new TextOf("12345"),
                    new TextOf("67890"),
                    new TextOf("00000")
                )
            )
        );
    }
}
