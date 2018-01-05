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

import java.util.Collections;
import java.util.Comparator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Sorted}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class SortedTest {

    @Test
    public void sortsAnArray() throws Exception {
        MatcherAssert.assertThat(
            "Can't sort an iterable",
            new Sorted<>(
                new IterableOf<>(
                    3, 2, 10, 44, -6, 0
                )
            ),
            Matchers.hasItems(-6, 0, 2, 3, 10, 44)
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void sortsAnArrayWithComparator() throws Exception {
        MatcherAssert.assertThat(
            "Can't sort an iterable with a comparator",
            new Sorted<>(
                Comparator.reverseOrder(), new IterableOf<>(
                    "a", "c", "hello", "dude", "Friend"
                )
            ),
            Matchers.hasItems("hello", "dude", "c", "a", "Friend")
        );
    }

    @Test
    public void sortsAnEmptyArray() throws Exception {
        MatcherAssert.assertThat(
            "Can't sort an empty iterable",
            new Sorted<Integer>(
                Collections.emptyList()
            ),
            Matchers.iterableWithSize(0)
        );
    }

}
