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
package org.cactoos.collection;

import java.util.ArrayList;
import java.util.Collections;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link org.cactoos.collection.Limited}.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 line)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class LimitedTest {

    @Test
    public void behavesAsCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't behave as a collection",
            new Limited<>(
                2,
                new IterableOf<>(1, -1, 2, 0)
            ),
            new BehavesAsCollection<>(-1)
        );
    }

    @Test
    public void size() {
        MatcherAssert.assertThat(
            new Limited<>(
                2,
                new IterableOf<>(
                    "hello", "world", "друг"
                )
            ).size(),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void sizeEmptyReturnZero() {
        MatcherAssert.assertThat(
            new Limited<>(
                2,
                Collections.emptyList()
            ).size(),
            Matchers.equalTo(0)
        );
    }

    @Test
    public void sizeLimitZeroReturnZero() {
        MatcherAssert.assertThat(
            new Limited<>(
                0, new IterableOf<>("1", "2", "3")
            ).size(),
            Matchers.equalTo(0)
        );
    }

    @Test
    public void withItemsNotEmpty() {
        MatcherAssert.assertThat(
            new Limited<String>(
                2, new IterableOf<>("first", "second")
            ).isEmpty(),
            Matchers.equalTo(false)
        );
    }

    @Test
    public void withoutItemsIsEmpty() {
        MatcherAssert.assertThat(
            new Limited<String>(
                0, new IterableOf<>("third", "fourth")
            ).isEmpty(),
            Matchers.equalTo(true)
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        new Limited<>(
            2, new IterableOf<>(1, 2, 3, 4)
        ).add(6);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        new Limited<>(
            2, new IterableOf<>(1, 2, 3, 4)
        ).remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() {
        new Limited<>(
            2, new IterableOf<Integer>(1, 2, 3, 4)
        ).addAll(new ArrayList<>(6));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() {
        new Limited<>(
            2, new IterableOf<>(1, 2, 3, 4)
        ).removeAll(new ArrayList<Integer>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() {
        new Limited<>(
            2, new IterableOf<>(1, 2, 3, 4)
        ).retainAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        new Limited<Integer>(
            2, new IterableOf<>(1, 2, 3, 4)
        ).clear();
    }
}
