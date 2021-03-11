/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
final class FilteredTest {

    @Test
    void filtersList() throws Exception {
        new Assertion<>(
            "Must calculate the length of an iterable",
            new LengthOf(
                new Filtered<>(
                    // @checkstyle MagicNumber (1 line)
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
    void filtersEmptyList() throws Exception {
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
            new Filtered<Double>(
                (Number d) -> d.doubleValue() > 0,
                new IterableOf<Double>(1d, -2d, 3d)
            ),
            new HasValues<>(1d, 3d)
        ).affirm();
    }

}
