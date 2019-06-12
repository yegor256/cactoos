/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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

import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
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
public final class NoNullsTest {

    @Test
    public void throwsErrorIfNullInToArray() {
        new Assertion<>(
            "Must throw exception",
            () -> new NoNulls<>(
                new CollectionOf<>(1, null, 3)
            ).toArray(),
            new Throws<>(
                "Item #1 of #toArray() is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    public void throwsErrorIfNullInToArrayWithArg() {
        new Assertion<>(
            "Must throw exception for the item#1",
            () -> new NoNulls<>(
                new CollectionOf<>(1, null, 3)
            ).toArray(new Object[3]),
            new Throws<>(
                "Item #1 of #toArray(array) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    public void throwsErrorIfNullInContainsArg() {
        new Assertion<>(
            "Must throw exception for #contains(null)",
            () -> new NoNulls<>(
                new CollectionOf<>(1, 2, 3)
            ).contains(null),
            new Throws<>(
                "Argument of #contains(T) is NULL",
                IllegalArgumentException.class
            )
        ).affirm();
    }
}
