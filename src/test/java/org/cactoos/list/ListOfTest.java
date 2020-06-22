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
package org.cactoos.list;

import java.util.Collections;
import java.util.List;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link ListOf}.
 *
 * @since 0.1
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"
    }
)
public final class ListOfTest {

    @Test
    public void behavesAsCollection() throws Exception {
        new Assertion<>(
            "Can't behave as a list",
            new ListOf<Integer>(1, 2),
            new BehavesAsList<Integer>(2)
        ).affirm();
    }

    @Test
    public void elementAtIndexTest() throws Exception {
        final int num = 345;
        new Assertion<>(
            "Can't convert an iterable to a list",
            new ListOf<>(-1, num, 0, 1).get(1),
            new IsEqual<Integer>(num)
        ).affirm();
    }

    @Test
    public void sizeTest() throws Exception {
        final int size = 42;
        new Assertion<>(
            "Can't build a list with a certain size",
            new ListOf<Integer>(
                Collections.nCopies(size, 0)
            ),
            new HasSize(size)
        ).affirm();
    }

    @Test
    public void emptyTest() throws Exception {
        new Assertion<>(
            "Can't convert an empty iterable to an empty list",
            new ListOf<Object>(
                new IterableOf<>()
            ),
            new HasSize(0)
        ).affirm();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void lowBoundTest() throws Exception {
        // @checkstyle MagicNumber (1 line)
        new ListOf<>(Collections.nCopies(10, 0)).get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void highBoundTest() throws Exception {
        // @checkstyle MagicNumber (1 line)
        new ListOf<>(Collections.nCopies(10, 0)).get(11);
    }

    @Test
    public void makesListFromMappedIterable() throws Exception {
        final List<Integer> list = new ListOf<>(
            new Mapped<>(
                i -> i + 1,
                new IterableOf<>(1, -1, 0, 1)
            )
        );
        new Assertion<>(
            "Can't turn a mapped iterable into a list",
            list,
            new HasSize(4)
        ).affirm();
        new Assertion<>(
            "Can't turn a mapped iterable into a list, again",
            list,
            new HasSize(4)
        ).affirm();
    }

    @Test
    public void equalsComparesContentBothListEnvelopes() {
        new Assertion<>(
            "Can't compare using equals.",
            new ListOf<Integer>(1, 2),
            new IsEqual<ListOf<Integer>>(new ListOf<>(1, 2))
        ).affirm();
    }

    @Test
    public void equalsComparesContentListEnvelopeWithNormalList() {
        new Assertion<>(
            "Can't compare using equals.",
            new ListOf<Integer>(1, 2),
            new IsEqual<ListOf<Integer>>(new ListOf<>(1, 2))
        ).affirm();
    }

    @Test
    public void equalsComparesEmptyLists() {
        new Assertion<>(
            "Can't compare using equals.",
            new ListOf<Object>(),
            new IsEqual<ListOf<Object>>(new ListOf<>())
        ).affirm();
    }

    @Test
    public void toStringUsesListContent() {
        new Assertion<>(
            "Can't print content using toString.",
            new ListOf<>(1, 2).toString(),
            new IsEqual<String>("[1, 2]")
        ).affirm();
    }

    @Test
    public void hashCodesListContent() {
        new Assertion<>(
            "Can't create hashcode.",
            new ListOf<>(1, 2).hashCode(),
            new IsEqual<Integer>(new ListOf<>(1, 2).hashCode())
        ).affirm();
    }

}
