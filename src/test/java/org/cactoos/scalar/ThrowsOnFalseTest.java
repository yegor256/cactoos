/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
 * Test suite for {@link ThrowsOnFalse}.
 *
 * @since 0.56.0
 */
public final class ThrowsOnFalseTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwsOnFalse() throws Exception {
        new ThrowsOnFalse(
            () -> false, "test message"
        ).value();
    }

    @Test(expected = IllegalStateException.class)
    public void throwsSuppliedExceptionOnFalse() throws Exception {
        new ThrowsOnFalse(
            () -> false,
            () -> new IllegalStateException("illegal state")
        ).value();
    }

    @Test
    public void returnsTrueOnTrue() throws Exception {
        new Assertion<>(
            "Must return true on true statement",
            new ThrowsOnFalse(() -> true, "test").value(),
            new IsEqual<>(true)
        ).affirm();
    }
}
