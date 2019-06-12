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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

    /**
     * A rule for handling an exception.
     */
    @Rule
    public final ExpectedException exception = ExpectedException.none();

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

    /*
    * @todo #1039:15min Currently it's impossible to match error messages
    *  by a pattern or partially. Replace {@link Rule} with {@link Throws} after
    *  <a href="https://github.com/llorllale/cactoos-matchers/issues/108">llorllale/cactoos-matchers#108</a>
    *  is fixed
    */
    @Test
    public void nthThrowsErrorIfNull() {
        this.exception.expect(IllegalStateException.class);
        final Iterator<String> itr = new NoNulls<>(
            new IteratorOf<>("a", "b", null, "c")
        );
        while (itr.hasNext()) {
            itr.next();
        }
    }
}
