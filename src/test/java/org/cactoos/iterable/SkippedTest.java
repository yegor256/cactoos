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

import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test Case for {@link Skipped}.
 *
 * @since 0.34
 * @todo #1303:30min Remove the PMD.AvoidDuplicateLiterals suppress warning
 *  below and change the tests literals so that they are different. Do not
 *  use constants. The rationale si to make the tests more strong by not
 *  tying them to the same literals.
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class SkippedTest {

    @Test
    public void skipIterable() {
        new Assertion<>(
            "Must skip elements in iterable",
            new Skipped<>(
                2,
                new IterableOf<>("one", "two", "three", "four")
            ),
            new IsEqual<>(
                new IterableOf<>(
                    "three",
                    "four"
                )
            )
        ).affirm();
    }

    @Test
    public void skipArray() {
        new Assertion<>(
            "Must skip elements in array",
            new Skipped<>(
                2,
                "one", "two", "three", "four"
            ),
            new IsEqual<>(
                new IterableOf<>(
                    "three",
                    "four"
                )
            )
        ).affirm();
    }

    @Test
    public void skipCollection() {
        new Assertion<>(
            "Must skip elements in collection",
            new Skipped<>(
                2,
                new ListOf<>("one", "two", "three", "four")
            ),
            new IsEqual<>(
                new IterableOf<>(
                    "three",
                    "four"
                )
            )
        ).affirm();
    }

    @Test
    public void skippedAllElements() {
        new Assertion<>(
            "Must skip all elements",
            new Skipped<>(
                2,
                "one", "two"
            ),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    public void skippedMoreThanExists() {
        new Assertion<>(
            "Can't skip more than exists",
            new Skipped<>(
                Integer.MAX_VALUE,
                "one", "two"
            ),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void skippedNegativeSize() {
        new Assertion<>(
            "Must process negative skipped size",
            new Skipped<>(
                -1,
                "one", "two", "three", "four"
            ),
            new IsEqual<>(
                new IterableOf<>(
                    "one",
                    "two",
                    "three",
                    "four"
                )
            )
        ).affirm();
    }
}
