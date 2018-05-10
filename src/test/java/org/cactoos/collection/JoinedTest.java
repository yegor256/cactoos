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

import java.util.Collections;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Limited}.
 *
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 line)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class JoinedTest {

    @Test
    public void behavesAsCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't behave as a collection",
            new Joined<Integer>(
                new IterableOf<>(1, -1, 2, 0),
                new IterableOf<>(1, -1, 2, 0),
                new IterableOf<>(1, -1, 2, 0)
            ),
            new BehavesAsCollection<>(-1)
        );
    }

    @Test
    public void size() {
        MatcherAssert.assertThat(
            new Joined<String>(
                new IterableOf<>("hello", "world", "друг"),
                new IterableOf<>("how", "are", "you"),
                new IterableOf<>("what's", "up")
            ).size(),
            Matchers.equalTo(8)
        );
    }

    @Test
    public void sizeEmptyReturnZero() {
        MatcherAssert.assertThat(
            new Joined<String>(
                Collections.emptyList()
            ).size(),
            Matchers.equalTo(0)
        );
    }

    @Test
    public void withItemsNotEmpty() throws Exception {
        MatcherAssert.assertThat(
            new Joined<String>(
                new IterableOf<>("1", "2"),
                new IterableOf<>("3", "4")
            ).isEmpty(),
            Matchers.equalTo(false)
        );
    }

    @Test
    public void withoutItemsIsEmpty() throws Exception {
        MatcherAssert.assertThat(
            new Joined<String>(
                Collections.emptyList()
            ).isEmpty(),
            Matchers.equalTo(true)
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() throws Exception {
        new Joined<Integer>(
            new IterableOf<>(1, 2),
            new IterableOf<>(3, 4),
            new IterableOf<>(5, 6)
        ).add(7);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() throws Exception {
        new Joined<String>(
            new IterableOf<>("w", "a"),
            new IterableOf<>("b", "c")
        ).remove("t");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() throws Exception {
        new Joined<Integer>(
            new IterableOf<>(111),
            new IterableOf<>(222),
            new IterableOf<>(333)
        ).addAll(new ListOf<>(111, 222));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() throws Exception {
        new Joined<Integer>(
            new IterableOf<>(111, 222),
            new IterableOf<>(444)
        ).removeAll(new ListOf<>(222));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() throws Exception {
        new Joined<Integer>(
            new IterableOf<>(22),
            new IterableOf<>(11)
        ).retainAll(new ListOf<>(66, 11));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() throws Exception {
        new Joined<Integer>(
            new IterableOf<>(10),
            new IterableOf<>(20)
        ).clear();
    }
}
