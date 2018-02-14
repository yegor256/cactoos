/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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

import java.util.LinkedList;
import java.util.List;
import org.cactoos.Func;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Grouped tests.
 *
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class GroupedTest {

    @Test
    public void groupValues() {
        final List<Iterable<Integer>> grouped = new ListOf<>(
            new Grouped<>(
                new RangeOf<>(1, 10, value -> ++value),
                new IterableOf<Func<Integer, Boolean>>(
                    value -> value % 2 == 0,
                    value -> value % 2 != 0
                )
            )
        );
        MatcherAssert.assertThat(
            "Can't group iterable.",
            grouped.size(),
            new IsEqual<>(2)
        );
        MatcherAssert.assertThat(
            "Wrong content of first group.",
            new ListOf<>(grouped.get(0)).toArray(),
            new IsEqual<>(new ListOf<>(2, 4, 6, 8, 10).toArray())
        );
        MatcherAssert.assertThat(
            "Wrong content of second group.",
            new ListOf<>(grouped.get(1)).toArray(),
            new IsEqual<>(new ListOf<>(1, 3, 5, 7, 9).toArray())
        );
    }

    @Test
    public void groupValuesFromEmptyIterable() {
        final List<Iterable<Integer>> grouped = new ListOf<>(
            new Grouped<>(
                new LinkedList<>(),
                new IterableOf<Func<Integer, Boolean>>(
                    value -> value % 2 == 0,
                    value -> value % 2 != 0
                )
            )
        );
        MatcherAssert.assertThat(
            "Can't group iterable.",
            grouped.size(),
            new IsEqual<>(2)
        );
        MatcherAssert.assertThat(
            "Wrong content of first group.",
            new ListOf<>(grouped.get(0)).toArray(),
            new IsEqual<>(new Object[]{})
        );
        MatcherAssert.assertThat(
            "Wrong content of second group.",
            new ListOf<>(grouped.get(1)).toArray(),
            new IsEqual<>(new Object[]{})
        );
    }

    @Test
    public void groupValuesWithoutGroupingFunctions() {
        final List<Iterable<Integer>> grouped = new ListOf<>(
            new Grouped<>(
                new IterableOf<>(1, 2),
                new LinkedList<>()
            )
        );
        MatcherAssert.assertThat(
            "Can't group iterable.",
            grouped.size(),
            new IsEqual<>(0)
        );
    }

}
