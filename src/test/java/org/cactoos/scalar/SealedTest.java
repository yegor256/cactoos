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
package org.cactoos.scalar;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Sealed}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class SealedTest {

    @Test
    public void testInteger() {
        new Assertion<>(
            "Integer did not pass equality",
            new Sealed(100).intValue(),
            new IsEqual<>(100)
        ).affirm();
    }

    @Test
    public void testFloat() {
        new Assertion<>(
            "Float did not pass equality",
            new Sealed(100).floatValue(),
            new IsEqual<>(100.0f)
        ).affirm();
    }

    @Test
    public void testLong() {
        new Assertion<>(
            "Long did not pass equality",
            new Sealed(100).longValue(),
            new IsEqual<>(100L)
        ).affirm();
    }

    @Test
    public void testDouble() {
        new Assertion<>(
            "Double did not pass equality",
            new Sealed(100).doubleValue(),
            new IsEqual<>(100.0d)
        ).affirm();
    }

    @Test
    public void testFailed() {
        new Assertion<>(
            "Float did not pass equality",
            new Sealed(100).floatValue(),
            new IsEqual<>(100.0f)
        ).affirm();
    }
}
