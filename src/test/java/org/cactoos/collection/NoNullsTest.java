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
package org.cactoos.collection;

import java.util.ArrayList;
import org.cactoos.list.ListOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link NoNulls}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
final class NoNullsTest {

    @Test
    void throwsErrorIfNullInToArray() {
        new Assertion<>(
            "Must throw exception",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).toArray(),
            new Throws<>(
                "Item #1 of #toArray() is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void throwsErrorIfNullInToArrayWithArg() {
        new Assertion<>(
            "Must throw exception for the item#1",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).toArray(new Object[3]),
            new Throws<>(
                "Item #1 of #toArray(array) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void throwsErrorIfNullInContainsArg() {
        new Assertion<>(
            "Must throw exception for #contains(null)",
            () -> new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).contains(null),
            new Throws<>(
                "Argument of #contains(T) is NULL",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void testSuccessNotNullArg() {
        new Assertion<>(
            "Must contain not null argument",
            new NoNulls<>(
                new ListOf<>(1)
            ).contains(1),
            new IsTrue()
        ).affirm();
    }

    @Test
    void testSuccessAddAll() {
        final NoNulls<Integer> nonulls = new NoNulls<>(new ArrayList<>(0));
        nonulls.addAll(new ListOf<>(1, 2));
        new Assertion<>(
            "Must add all",
            nonulls,
            new IsEqual<>(
                new ListOf<>(1, 2)
            )
        ).affirm();
    }

    @Test
    void throwsErrorIfNullInAddAll() {
        final NoNulls<Integer> nonulls = new NoNulls<>(new ArrayList<>(0));
        new Assertion<>(
            "Must throw exception for nullable #addAll() parameter collection",
            () -> nonulls.addAll(new ListOf<>(1, 2, null)),
            new Throws<>(
                "Item #2 of #toArray() is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }
}
