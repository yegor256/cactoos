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
package org.cactoos.list;

import java.util.Comparator;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test Case for {@link Sorted}.
 * @since 0.19
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class SortedTest {

    @Test
    public void behavesAsCollection() throws Exception {
        new Assertion<>(
            "Can't behave as a list",
            new Sorted<Integer>(
                new ListOf<>(1, 0, -1, -1, 2)
            ),
            new BehavesAsList<Integer>(0)
        ).affirm();
    }

    @Test
    public void sortsCollection() throws Exception {
        new Assertion<>(
            "Can't sort elements in list",
            new Sorted<String>(
                new ListOf<>(
                    "one", "two", "three", "four"
                )
            ),
            new IsEqual<ListEnvelope<String>>(
                new ListOf<>("four", "one", "three", "two")
            )
        ).affirm();
    }

    @Test
    public void takesItemFromSortedList() throws Exception {
        new Assertion<>(
            "Can't take one element from sorted list",
            new Sorted<>(
                Comparator.reverseOrder(),
                new ListOf<>("alpha", "beta", "gamma", "delta")
            ).get(1),
            new IsEqual<String>("delta")
        ).affirm();
    }

}
