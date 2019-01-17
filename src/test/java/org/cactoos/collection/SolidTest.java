/*
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
package org.cactoos.collection;

import java.util.Collection;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test Case for {@link Solid}.
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 lines)
 */
public final class SolidTest {

    @Test
    public void behavesAsCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't behave as a collection",
            new Solid<>(1, 2, 0, -1),
            new BehavesAsCollection<>(-1)
        );
    }

    @Test
    public void makesListFromMappedIterable() throws Exception {
        final Collection<Integer> list = new Solid<>(
            new org.cactoos.list.Mapped<>(
                i -> i + 1,
                new IterableOf<>(1, -1, 0, 1)
            )
        );
        MatcherAssert.assertThat(
            "Can't turn a mapped iterable into a list",
            list, new IsCollectionWithSize<>(new IsEqual<>(4))
        );
        MatcherAssert.assertThat(
            "Can't turn a mapped iterable into a list, again",
            list, new IsCollectionWithSize<>(new IsEqual<>(4))
        );
    }

    @Test
    public void mapsToSameObjects() throws Exception {
        final Iterable<Scalar<Integer>> list = new Solid<>(
            new org.cactoos.list.Mapped<>(
                i -> (Scalar<Integer>) () -> i,
                new IterableOf<>(1, -1, 0, 1)
            )
        );
        MatcherAssert.assertThat(
            "Can't map only once",
            list.iterator().next(),
            new IsEqual<>(list.iterator().next())
        );
    }

    @Test
    public void worksInThreads() {
        MatcherAssert.assertThat(
            "Can't behave as a collection in multiple threads",
            list -> {
                MatcherAssert.assertThat(list, new BehavesAsCollection<>(0));
                return true;
            },
            new RunsInThreads<>(new Solid<>(1, 0, -1, -1, 2))
        );
    }
}
