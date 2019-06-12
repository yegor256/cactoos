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
package org.cactoos;

import org.cactoos.scalar.NoNulls;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link NoNulls}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ScalarTest {

    @Test
    public void failForNullArgument() {
        new Assertion<>(
            "Must fail for null argument",
            () -> new NoNulls<>(null).value(),
            new Throws<>(
                "NULL instead of a valid scalar",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    public void failForNullResult() {
        new Assertion<>(
            "Must fail for null result",
            () -> new NoNulls<>(() -> null).value(),
            new Throws<>(
                "NULL instead of a valid value",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    public void okForNoNulls() throws Exception {
        new NoNulls<>(() -> 1).value();
    }
}
