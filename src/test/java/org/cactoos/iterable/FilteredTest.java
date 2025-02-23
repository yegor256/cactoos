/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.list.ListOf;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.StartsWith;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link Filtered}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class FilteredTest {

    @Test
    void filtersList() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void filtersEmptyList() {
        new Assertion<>(
            "Must calculate the length of an empty iterable",
            new LengthOf(
                new Filtered<>(
                    input -> input.length() > 1,
                    new IterableOf<String>()
                )
            ),
            new HasValue<>(0L)
        ).affirm();
    }

    @Test
    void filtersIterablesWithSize() {
        final Iterable<Integer> list = new Filtered<>(
            i -> i > 0,
            new IterableOf<>(1, 2, -1, 0, 1)
        );
        new Assertion<>(
            "Must filter the iterable twice",
            list, Matchers.iterableWithSize(3)
        ).affirm();
        new Assertion<>(
            "Must filter the iterable twice, again",
            list, Matchers.iterableWithSize(3)
        ).affirm();
    }

    @Test
    void filterEmptyList() {
        new Assertion<>(
            "Filter must work on empty collection",
            new Filtered<String>(
                input -> input.length() > 4,
                new ListOf<>()
            ),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    void length() {
        new Assertion<>(
            "Size must be equal to number of items matching the filter",
            new LengthOf(
                new Filtered<>(
                    input -> input.length() >= 4,
                    new IterableOf<>("some", "text", "yes")
                )
            ),
            new HasValue<>(2L)
        ).affirm();
    }

    @Test
    void withItemsNotEmpty() {
        new Assertion<>(
            "Must not be empty with items",
            new Filtered<>(
                input -> input.length() > 4,
                new IterableOf<>("first", "second")
            ),
            new IsNot<>(new IsEmptyIterable<>())
        ).affirm();
    }

    @Test
    void withoutItemsIsEmpty() {
        new Assertion<>(
            "Must be empty without items",
            new Filtered<>(
                input -> input.length() > 16,
                new IterableOf<>("third", "fourth")
            ),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    void filtersWithFuncToScalar() {
        new Assertion<>(
            "Must be filtered",
            new Filtered<>(
                new IterableOf<>("ay", "xb", "yx", "xy"),
                input -> new StartsWith(input, "x")
            ),
            new HasValues<>("xy")
        ).affirm();
    }

    @Test
    void filterWithNumberFilter() {
        new Assertion<>(
            "Must be filtered with super type filter",
            new Filtered<>(
                (Number d) -> d.doubleValue() > (double) 0,
                new IterableOf<>(1d, -2d, 3d)
            ),
            new HasValues<>(1d, 3d)
        ).affirm();
    }

}
