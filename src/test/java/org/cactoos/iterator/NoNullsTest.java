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
package org.cactoos.iterator;

import java.util.Iterator;
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
 */
public final class NoNullsTest {

    @Test
    public void nextThrowsErrorIfNull() {
        new Assertion<>(
            "Must throw exception",
            () -> new NoNulls<>(
                new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public Integer next() {
                        return null;
                    }

                    @Override
                    public String toString() {
                        return "Iterator@NoNullsTest";
                    }
                }
            ).next(),
            new Throws<>(
                "Item #0 of Iterator@NoNullsTest is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    public void nthThrowsErrorIfNull() {
        new Assertion<>(
            "nth throws error if null",
            () -> {
                final Iterator<String> itr = new NoNulls<>(
                    new IteratorOf<>("a", "b", null, "c")
                );
                while (itr.hasNext()) {
                    itr.next();
                }
                return null;
            },
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }
}
