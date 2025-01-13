/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test suite for {@link ThrowsOnFalse}.
 *
 * @since 0.56.0
 */
final class ThrowsOnFalseTest {

    @Test
    void throwsOnFalse() {
        final String message = "test message";
        new Assertion<>(
            "Throws an exception",
            () -> new ThrowsOnFalse(
                () -> false, message
            ).value(),
            new Throws<>(message, IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void throwsSuppliedExceptionOnFalse() {
        final String message = "illegal state";
        new Assertion<>(
            "Throws supplied exception",
            () -> new ThrowsOnFalse(
                () -> false,
                () -> new IllegalStateException(message)
            ).value(),
            new Throws<>(message, IllegalStateException.class)
        ).affirm();
    }

    @Test
    void returnsTrueOnTrue() throws Exception {
        new Assertion<>(
            "Must return true on true statement",
            new ThrowsOnFalse(() -> true, "test").value(),
            new IsEqual<>(true)
        ).affirm();
    }
}
