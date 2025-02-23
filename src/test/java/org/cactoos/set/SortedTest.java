/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.Comparator;
import org.cactoos.Text;
import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Sorted}.
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals",
    "PMD.TooManyMethods",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class SortedTest {

    @Test
    @SuppressWarnings("unchecked")
    void mustSortIntegerArrayAsSetInAscendingOrder() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void mustSortIntegerIterableAsSetInDescendingOrder() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void mustSortTextIterableAsSetUsingCustomCOmparator() {
        new Assertion<>(
            "Must keep unique integer numbers sorted in descending order",
            new Sorted<Text>(
                (first, second) -> {
                    final String left = new UncheckedText(first).asString();
                    final String right = new UncheckedText(second).asString();
                    return left.compareTo(right);
                },
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
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void mustNotBeEqualToSortedSet() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void returnsCorrectComparator() {
        final Comparator<Integer> comparator = Integer::compareTo;
        new Assertion<>(
            "Comparator must be the same",
            new Sorted<>(comparator, 1, 2, 3).comparator(),
            new IsEqual<>(comparator)
        ).affirm();
    }

    @Test
    void returnsSubset() {
        new Assertion<>(
            "Must return sorted subset",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).subSet(3, 9),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Integer>>(
                    new IsEqual<>(3),
                    new IsEqual<>(6)
                )
            )
        ).affirm();
    }

    @Test
    void returnsHeadset() {
        new Assertion<>(
            "Must return sorted headset",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).headSet(9),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Integer>>(
                    new IsEqual<>(1),
                    new IsEqual<>(3),
                    new IsEqual<>(6)
                )
            )
        ).affirm();
    }

    @Test
    void returnsTailset() {
        new Assertion<>(
            "Must return sorted tailset",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).tailSet(6),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Integer>>(
                    new IsEqual<>(6),
                    new IsEqual<>(9)
                )
            )
        ).affirm();
    }

    @Test
    void returnsFirst() {
        new Assertion<>(
            "Must return first element",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).first(),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void returnsLast() {
        new Assertion<>(
            "Must return last element",
            new Sorted<>(Integer::compareTo, 3, 6, 1, 9, 3).last(),
            new IsEqual<>(9)
        ).affirm();
    }

    @Test
    void mustSortIntegersByNumberComparator() {
        new Assertion<>(
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
        ).affirm();
    }

}
