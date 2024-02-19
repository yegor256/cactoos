/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
package org.cactoos.func;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link BiFuncNoNulls}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class BiFuncNoNullsTest {

    @Test
    void failForNullFunc() {
        new Assertion<>(
            "Must fail for null func",
            () -> new BiFuncNoNulls<>(null).apply(new Object(), new Object()),
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void failForNullFirstArg() {
        new Assertion<>(
            "Must fail for null first argument",
            () -> new BiFuncNoNulls<>(
                (first, second) -> first
            ).apply(null, new Object()),
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void failForNullSecondArg() {
        new Assertion<>(
            "Must fail for null second argument",
            () -> new BiFuncNoNulls<>(
                (first, second) -> first
            ).apply(new Object(), null),
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void failForNullResult() {
        new Assertion<>(
            "Must fail for null result",
            () -> new BiFuncNoNulls<>(
                (first, second) -> null
            ).apply(new Object(), new Object()),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void okForNoNulls() throws Exception {
        new BiFuncNoNulls<>(
            (first, second) -> first
        ).apply(new Object(), new Object());
    }
}
