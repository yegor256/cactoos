/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
    public void size() {
        MatcherAssert.assertThat(
            new Limited<>(
                new IterableOf<>(
                    "hello", "world", "друг"
                ),
                2
            ).size(),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void sizeEmptyReturnZero() {
        MatcherAssert.assertThat(
            new Limited<>(
                Collections.emptyList(),
                2
            ).size(),
            Matchers.equalTo(0)
        );
    }

    @Test
    public void sizeLimitZeroReturnZero() {
        MatcherAssert.assertThat(
            new Limited<>(
                new IterableOf<>(
                    "1", "2", "3"
                ),
                0
            ).size(),
            Matchers.equalTo(0)
        );
    }

    @Test
    public void withItemsNotEmpty() throws Exception {
        MatcherAssert.assertThat(
            new Limited<String>(
                new IterableOf<>(
                    "first", "second"
                ),
                2
            ).isEmpty(),
            Matchers.equalTo(false)
        );
    }

    @Test
    public void withoutItemsIsEmpty() throws Exception {
        MatcherAssert.assertThat(
            new Limited<String>(
                new IterableOf<>(
                    "third", "fourth"
                ),
                0
            ).isEmpty(),
            Matchers.equalTo(true)
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() throws Exception {
        new Limited<>(
            new IterableOf<>(
                1, 2, 3, 4
            ),
            2
        ).add(6);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() throws Exception {
        new Limited<>(
            new IterableOf<>(
                1, 2, 3, 4
            ),
            2
        ).remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() throws Exception {
        new Limited<>(
            new IterableOf<>(
                1, 2, 3, 4
            ),
            2
        ).addAll(new ArrayList<>(6));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() throws Exception {
        new Limited<>(
            new IterableOf<>(
                1, 2, 3, 4
            ),
            2
        ).removeAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() throws Exception {
        new Limited<>(
            new IterableOf<>(
                1, 2, 3, 4
            ),
            2
        ).retainAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() throws Exception {
        new Limited<>(
            new IterableOf<>(
                1, 2, 3, 4
            ),
            2
        ).clear();
    }
}
