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
package org.cactoos.iterator;

import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.MatcherOf;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link NoNulls}.
 *
 * <p>There is no thread-safety guarantee.
 * @checkstyle JavadocMethodCheck (500 lines)
 * @since 0.35
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class NoNullsTest {

    @Test
    public void nextThrowsErrorIfNull() {
        new Assertion<>(
            "Must throw exception",
            () -> new NoNulls<>(
                new IteratorOf<>(new String[]{null})
            ).next(),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.matches("^Item #0 of .*? is NULL")
                ),
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    public void nthThrowsErrorIfNull() {
        new Assertion<>(
            "Must throw exception",
            () -> new TailOf<>(
                1,
                new NoNulls<>(
                    new IteratorOf<>("a", "b", null, "c")
                )
            ).next(),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.matches("^Item #2 of .*? is NULL")
                ),
                IllegalStateException.class
            )
        ).affirm();
    }
}
