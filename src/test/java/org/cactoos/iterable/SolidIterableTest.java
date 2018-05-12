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
package org.cactoos.iterable;

import org.cactoos.Scalar;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test Case for {@link SolidIterable}.
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 lines)
 */
public final class SolidIterableTest {

    @Test
    public void makesListFromMappedIterable() {
        final Iterable<Integer> list = new SolidIterable<>(
            new org.cactoos.list.Mapped<>(
                i -> i + 1,
                new IterableOf<>(1, -1, 0, 1)
            )
        );
        MatcherAssert.assertThat(
            "Can't turn a mapped iterable into a list",
            list, Matchers.iterableWithSize(4)
        );
        MatcherAssert.assertThat(
            "Can't turn a mapped iterable into a list, again",
            list, Matchers.iterableWithSize(4)
        );
    }

    @Test
    public void mapsToSameObjects() {
        final Iterable<Scalar<Integer>> list = new SolidIterable<>(
            new org.cactoos.list.Mapped<>(
                i -> (Scalar<Integer>) () -> i,
                new IterableOf<>(1, -1, 0, 1)
            )
        );
        MatcherAssert.assertThat(
            "Can't map only once",
            list.iterator().next(), Matchers.equalTo(list.iterator().next())
        );
    }

    @Test
    public void worksInThreadsMultipleTimes() {
        for (int count = 0; count < 100; ++count) {
            this.worksInThreads();
        }
    }

    @Test
    public void worksInThreads() {
        MatcherAssert.assertThat(
            "Can't behave as an iterable in multiple threads",
            list -> {
                MatcherAssert.assertThat(
                    list.iterator().next(),
                    Matchers.equalTo(list.iterator().next())
                );
                return true;
            },
            new RunsInThreads<>(new SolidIterable<>(1, 0, -1, -1, 2))
        );
    }

}
