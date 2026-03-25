/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.list.ListOf;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.StartsWith;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link Filtered}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class FilteredTest {

    @Test
    void filtersList() {
        MatcherAssert.assertThat(
            "Must calculate the length of an iterable",
            new LengthOf(
                new Filtered<>(
                    input -> input.length() > 4,
                    new IterableOf<>(
                        "hello", "world", "друг"
                    )
                )
            ),
            new HasValue<>(2L)
        );
    }

    @Test
    @SuppressWarnings("PMD.UseStringIsEmptyRule")
    void filtersEmptyList() {
        MatcherAssert.assertThat(
            "Must calculate the length of an empty iterable",
            new LengthOf(
                new Filtered<>(
                    input -> input.length() > 1,
                    new IterableOf<String>()
                )
            ),
            new HasValue<>(0L)
        );
    }

    @Test
    void filtersIterablesWithSize() {
        MatcherAssert.assertThat(
            "Must filter the iterable",
            new Filtered<>(
                i -> i > 0,
                new IterableOf<>(1, 2, -1, 0, 1)
            ),
            Matchers.iterableWithSize(3)
        );
    }

    @Test
    void filtersIterablesWithSizeOnSecondTraversal() {
        final Iterable<Integer> list = new Filtered<>(
            i -> i > 0,
            new IterableOf<>(1, 2, -1, 0, 1)
        );
        list.iterator().next();
        MatcherAssert.assertThat(
            "Must filter the iterable on second traversal",
            list,
            Matchers.iterableWithSize(3)
        );
    }

    @Test
    void filterEmptyList() {
        MatcherAssert.assertThat(
            "Filter must work on empty collection",
            new Filtered<String>(
                input -> input.length() > 4,
                new ListOf<>()
            ),
            new IsEmptyIterable<>()
        );
    }

    @Test
    void length() {
        MatcherAssert.assertThat(
            "Size must be equal to number of items matching the filter",
            new LengthOf(
                new Filtered<>(
                    input -> input.length() >= 4,
                    new IterableOf<>("some", "text", "yes")
                )
            ),
            new HasValue<>(2L)
        );
    }

    @Test
    void withItemsNotEmpty() {
        MatcherAssert.assertThat(
            "Must not be empty with items",
            new Filtered<>(
                input -> input.length() > 4,
                new IterableOf<>("first", "second")
            ),
            new IsNot<>(new IsEmptyIterable<>())
        );
    }

    @Test
    void withoutItemsIsEmpty() {
        MatcherAssert.assertThat(
            "Must be empty without items",
            new Filtered<>(
                input -> input.length() > 16,
                new IterableOf<>("third", "fourth")
            ),
            new IsEmptyIterable<>()
        );
    }

    @Test
    void filtersWithFuncToScalar() {
        MatcherAssert.assertThat(
            "Must be filtered",
            new Filtered<>(
                new IterableOf<>("ay", "xb", "yx", "xy"),
                input -> new StartsWith(input, "x")
            ),
            new HasValues<>("xy")
        );
    }

    @Test
    void filterWithNumberFilter() {
        MatcherAssert.assertThat(
            "Must be filtered with super type filter",
            new Filtered<>(
                (Number d) -> d.doubleValue() > 0,
                new IterableOf<>(1d, -2d, 3d)
            ),
            new HasValues<>(1d, 3d)
        );
    }

    @Test
    void varargsConstructorTest() {
        MatcherAssert.assertThat(
            "Must have filtered varargs",
            new Filtered<>(
                num -> num % 2 == 0,
                1, 2, 3, 4, 11, 20
            ),
            new HasValues<>(2, 4, 20)
        );
    }
}
