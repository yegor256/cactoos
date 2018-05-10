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

import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test Case for {@link Shuffled}.
 * @since 0.23
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ShuffledTest {

    @Test
    public void behavesAsCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't behave as a collection",
            new Shuffled<>(new ListOf<Integer>(1, 2, 0, -1)),
            new BehavesAsCollection<>(0)
        );
    }

    @Test
    public void shufflesCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't shuffle elements in collection",
            new Shuffled<>(new ListOf<Integer>(1, 2, 0, -1)),
            Matchers.hasItem(-1)
        );
    }

    @Test
    public void shufflesArray() throws Exception {
        MatcherAssert.assertThat(
            "Can't shuffle elements in array",
            new Shuffled<>(1, 2, 0, -1),
            Matchers.hasItem(-1)
        );
    }

    @Test
    public void shufflesIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't shuffle elements in iterable",
            new Shuffled<>(new IterableOf<>(1, 2, 0, -1)),
            Matchers.hasItem(-1)
        );
    }

}
