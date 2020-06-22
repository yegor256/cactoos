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

import java.util.Collections;
import java.util.Comparator;
import org.cactoos.list.ListOf;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Sorted}.
 *
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class SortedTest {

    @Test
    public void sortsAnArray() throws Exception {
        new Assertion<>(
            "Can't sort an iterable",
            new Sorted<Integer>(
                new IterableOf<>(
                    3, 2, 10, 44, -6, 0
                )
            ),
            Matchers.hasItems(-6, 0, 2, 3, 10, 44)
        ).affirm();
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void sortsAnArrayWithComparator() throws Exception {
        new Assertion<>(
            "Can't sort an iterable with a comparator",
            new Sorted<String>(
                Comparator.reverseOrder(), new IterableOf<>(
                "a", "c", "hello", "dude", "Friend"
            )
            ),
            Matchers.hasItems("hello", "dude", "c", "a", "Friend")
        ).affirm();
    }

    @Test
    public void sortsAnEmptyArray() throws Exception {
        new Assertion<>(
            "Can't sort an empty iterable",
            new Sorted<Integer>(
                Collections.emptyList()
            ),
            Matchers.iterableWithSize(0)
        ).affirm();
    }

    @Test
    public void sortsCollection() {
        new Assertion<>(
            "Must sort elements in collection",
            new Sorted<>(
                new ListOf<>(
                    "o", "t", "t", "f"
                )
            ),
            new IsEqual<>(new IterableOf<>("f", "o", "t", "t"))
        ).affirm();
    }
}
