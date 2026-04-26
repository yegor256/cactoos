/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.Comparator;
import org.cactoos.Text;
import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Sorted}.
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class SortedTest {

    @Test
    @SuppressWarnings("unchecked")
    void mustSortIntegerArrayAsSetInAscendingOrder() {
        MatcherAssert.assertThat(
            "Must keep unique integer numbers sorted",
            new Sorted<Integer>(
                Integer::compareTo,
                2, 1, 3, 2, 1
            ),
            new IsIterableContainingInOrder<Integer>(
                new ListOf<>(
                    new IsEqual<>(1),
                    new IsEqual<>(2),
                    new IsEqual<>(3)
                )
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void mustSortIntegerIterableAsSetInDescendingOrder() {
        MatcherAssert.assertThat(
            "Must keep unique integer numbers sorted in descending order",
            new Sorted<Integer>(
                Comparator.reverseOrder(),
                2, 1, 3, 2, 1
            ),
            new IsIterableContainingInOrder<Integer>(
                new ListOf<>(
                    new IsEqual<>(3),
                    new IsEqual<>(2),
                    new IsEqual<>(1)
                )
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void mustSortTextIterableAsSetUsingCustomCOmparator() {
        MatcherAssert.assertThat(
            "Must keep unique integer numbers sorted in descending order",
            new Sorted<Text>(
                (first, second) -> new UncheckedText(first).asString()
                    .compareTo(new UncheckedText(second).asString()),
                new TextOf("cd"),
                new TextOf("ab"),
                new TextOf("gh"),
                new TextOf("ef")
            ),
            new IsIterableContainingInOrder<Text>(
                new ListOf<>(
                    new IsText("ab"),
                    new IsText("cd"),
                    new IsText("ef"),
                    new IsText("gh")
                )
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void mustNotBeEqualToSortedSet() {
        MatcherAssert.assertThat(
            "Sorted set must not be equal to the tested collection",
            new Sorted<Integer>(
                Integer::compareTo,
                2, 1, 3, 2, 1
            ),
            new IsNot<>(
                new IsIterableContainingInOrder<Integer>(
                    new ListOf<>(
                        new IsEqual<>(1),
                        new IsEqual<>(3),
                        new IsEqual<>(2)
                    )
                )
            )
        );
    }

    @Test
    void returnsCorrectComparator() {
        final Comparator<Integer> comparator = Integer::compareTo;
        MatcherAssert.assertThat(
            "Comparator must be the same",
            new Sorted<>(comparator, 1, 2, 3).comparator(),
            new IsEqual<>(comparator)
        );
    }

    @Test
    void returnsSubset() {
        MatcherAssert.assertThat(
            "Must return sorted subset",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).subSet(3, 9),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Integer>>(
                    new IsEqual<>(3),
                    new IsEqual<>(6)
                )
            )
        );
    }

    @Test
    void returnsHeadset() {
        MatcherAssert.assertThat(
            "Must return sorted headset",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).headSet(9),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Integer>>(
                    new IsEqual<>(1),
                    new IsEqual<>(3),
                    new IsEqual<>(6)
                )
            )
        );
    }

    @Test
    void returnsTailset() {
        MatcherAssert.assertThat(
            "Must return sorted tailset",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).tailSet(6),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Integer>>(
                    new IsEqual<>(6),
                    new IsEqual<>(9)
                )
            )
        );
    }

    @Test
    void returnsFirst() {
        MatcherAssert.assertThat(
            "Must return first element",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).first(),
            new IsEqual<>(1)
        );
    }

    @Test
    void returnsLast() {
        MatcherAssert.assertThat(
            "Must return last element",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).last(),
            new IsEqual<>(9)
        );
    }

    @Test
    void mustSortIntegersByNumberComparator() {
        MatcherAssert.assertThat(
            "Must keep unique integer numbers sorted",
            new Sorted<Number>(
                Comparator.comparing(Number::intValue),
                2, 1, 3, 2, 1
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Number>>(
                    new IsEqual<>(1),
                    new IsEqual<>(2),
                    new IsEqual<>(3)
                )
            )
        );
    }
}
