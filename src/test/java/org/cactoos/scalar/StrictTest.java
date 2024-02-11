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

import org.cactoos.func.FuncOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Strict}.
 *
 * @since 0.56
 */
public final class StrictTest {

    @Test
    public void throwsExceptionOnFailure() {
        new Assertion<>(
            "Must throw the provided exception on rule failure",
            new Strict<>(
                new False(),
                value -> new Equals<>(value, new True()),
                new FuncOf<>(() -> new IllegalArgumentException("not a true"))
            ),
            new Throws<>("not a true", IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    public void returnsIncapsulatedValue() throws Exception {
        new Assertion<>(
            "Must return the original value on rule match",
            new Strict<>(
                () -> 123,
                value -> new Equals<>(value, () -> 123),
                new FuncOf<>(() -> new IllegalArgumentException("unexpected"))
            ).value(),
            new IsEqual<>(123)
        ).affirm();
    }
}
