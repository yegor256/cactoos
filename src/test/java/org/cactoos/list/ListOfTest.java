/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
package org.cactoos.list;

import java.util.Collections;
import java.util.List;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link ListOf}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods",
    "PMD.AvoidDuplicateLiterals",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class ListOfTest {

    @Test
    void behavesAsCollection() {
        MatcherAssert.assertThat(
            "Can't behave as a list",
            new ListOf<>(1, 2),
            new BehavesAsList<>(2)
        );
    }

    @Test
    void elementAtIndexTest() {
        final int num = 345;
        MatcherAssert.assertThat(
            "Can't convert an iterable to a list",
            new ListOf<>(-1, num, 0, 1).get(1),
            new IsEqual<>(num)
        );
    }

    @Test
    void sizeTest() {
        final int size = 42;
        MatcherAssert.assertThat(
            "Can't build a list with a certain size",
            new ListOf<>(
                Collections.nCopies(size, 0)
            ),
            new HasSize(size)
        );
    }

    @Test
    void emptyTest() {
        MatcherAssert.assertThat(
            "Can't convert an empty iterable to an empty list",
            new ListOf<>(
                new IterableOf<>()
            ),
            new HasSize(0)
        );
    }

    @Test
    void lowBoundTest() {
        new Assertion<>(
            "Exception is expected for negative index",
            () -> new ListOf<>(Collections.nCopies(10, 0)).get(-1),
            new Throws<>(IndexOutOfBoundsException.class)
        ).affirm();
    }

    @Test
    void highBoundTest() {
        new Assertion<>(
            "Exception is expected for index larger then size",
            () -> new ListOf<>(Collections.nCopies(10, 0)).get(11),
            new Throws<>(IndexOutOfBoundsException.class)
        ).affirm();
    }

    @Test
    void makesListFromMappedIterable() {
        final List<Integer> list = new ListOf<>(
            new Mapped<>(
                i -> i + 1,
                new IterableOf<>(1, -1, 0, 1)
            )
        );
        MatcherAssert.assertThat(
            "Can't turn a mapped iterable into a list",
            list,
            new HasSize(4)
        );
        MatcherAssert.assertThat(
            "Can't turn a mapped iterable into a list, again",
            list,
            new HasSize(4)
        );
    }

    @Test
    void equalsComparesContentBothListEnvelopes() {
        MatcherAssert.assertThat(
            "Can't compare using equals.",
            new ListOf<>(1, 2),
            new IsEqual<>(new ListOf<>(1, 2))
        );
    }

    @Test
    void equalsComparesContentListEnvelopeWithNormalList() {
        MatcherAssert.assertThat(
            "Can't compare using equals.",
            new ListOf<>(1, 2),
            new IsEqual<>(new ListOf<>(1, 2))
        );
    }

    @Test
    void equalsComparesEmptyLists() {
        MatcherAssert.assertThat(
            "Can't compare using equals.",
            new ListOf<>(),
            new IsEqual<>(new ListOf<>())
        );
    }

    @Test
    void toStringUsesListContent() {
        MatcherAssert.assertThat(
            "Can't print content using toString.",
            new ListOf<>(1, 2).toString(),
            new IsEqual<>("[1, 2]")
        );
    }

    @Test
    void hashCodesListContent() {
        MatcherAssert.assertThat(
            "Can't create hashcode.",
            new ListOf<>(1, 2).hashCode(),
            new IsEqual<>(new ListOf<>(1, 2).hashCode())
        );
    }

}
