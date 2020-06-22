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

import org.cactoos.MatcherAssert;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test case for {@link Filtered}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class FilteredTest {

    @Test
    public void filtersList() {
        MatcherAssert.assertThat(
            "Can't calculate the length of an iterable",
            new LengthOf(
                new Filtered<>(
                    // @checkstyle MagicNumber (1 line)
                    input -> input.length() > 4, new IterableOf<>(
                        "hello", "world", "друг"
                    )
                )
            ).intValue(),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void filtersEmptyList() {
        MatcherAssert.assertThat(
            "Can't calculate the length of an empty iterable",
            new LengthOf(
                new Filtered<>(
                    input -> input.length() > 1,
                    new IterableOf<String>()
                )
            ).intValue(),
            Matchers.equalTo(0)
        );
    }

    @Test
    public void filtersIterablesWithSize() {
        final Iterable<Integer> list = new Filtered<>(
            i -> i > 0,
            new IterableOf<>(1, 2, -1, 0, 1)
        );
        MatcherAssert.assertThat(
            "Can't filter the iterable twice",
            list, Matchers.iterableWithSize(3)
        );
        MatcherAssert.assertThat(
            "Can't filter the iterable twice, again",
            list, Matchers.iterableWithSize(3)
        );
    }

    @Test
    public void filterEmptyList() {
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
    public void length() {
        new Assertion<>(
            "Size must be equal to number of items matching the filter",
            new LengthOf(
                new Filtered<>(
                    input -> input.length() >= 4,
                    new IterableOf<>("some", "text", "yes")
                )
            ),
            new ScalarHasValue<>(2.)
        ).affirm();
    }

    @Test
    public void withItemsNotEmpty() {
        new Assertion<>(
            "Must not be empty with items",
            new Filtered<String>(
                input -> input.length() > 4,
                new IterableOf<>("first", "second")
            ),
            new IsNot<>(new IsEmptyIterable<>())
        ).affirm();
    }

    @Test
    public void withoutItemsIsEmpty() {
        new Assertion<>(
            "Must be empty without items",
            new Filtered<String>(
                input -> input.length() > 16,
                new IterableOf<>("third", "fourth")
            ),
            new IsEmptyIterable<>()
        ).affirm();
    }

}
