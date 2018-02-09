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

import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test Case for {@link Skipped}.
 * @author Alexander Menshikov (sharplermc@gmail.com)
 * @version $Id$
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class SkippedTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void skipIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't skip elements in iterable",
            new Skipped<>(
                2,
                new IterableOf<>("one", "two", "three", "four")
            ),
            Matchers.contains(
                "three",
                "four"
            )
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void skipArray() throws Exception {
        MatcherAssert.assertThat(
            "Can't skip elements in array",
            new Skipped<>(
                2,
                "one", "two", "three", "four"
            ),
            Matchers.contains(
                "three",
                "four"
            )
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void skipCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't skip elements in collection",
            new Skipped<>(
                2,
                new CollectionOf<>("one", "two", "three", "four")
            ),
            Matchers.contains(
                "three",
                "four"
            )
        );
    }

    @Test
    public void skippedAllElements() throws Exception {
        MatcherAssert.assertThat(
            "Can't skip all elements",
            new Skipped<>(
                2,
                "one", "two"
            ),
            Matchers.empty()
        );
    }

    @Test
    public void skippedMoreThanExists() throws Exception {
        MatcherAssert.assertThat(
            "Can't skip more than exists",
            new Skipped<>(
                Integer.MAX_VALUE,
                "one", "two"
            ),
            Matchers.empty()
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void skippedNegativeSize() throws Exception {
        MatcherAssert.assertThat(
            "Can't process negative skipped size",
            new Skipped<>(
                -1,
                "one", "two", "three", "four"
            ),
            Matchers.contains(
                "one", "two", "three", "four"
            )
        );
    }
}
