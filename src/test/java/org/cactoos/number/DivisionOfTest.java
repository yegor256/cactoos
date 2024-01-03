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
package org.cactoos.number;

import java.math.BigDecimal;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsNumber;

/**
 * Test case for {@link DivisionOf}.
 *
 * @since 0.49.2
 */
final class DivisionOfTest {

    @Test
    void dividesIntNumbers() {
        new Assertion<>(
            "Must divide int numbers",
            new DivisionOf(4, 2).intValue(),
            new IsEqual<>(2)
        ).affirm();
    }

    @Test
    void dividesLongNumbers() {
        new Assertion<>(
            "Must divide long numbers",
            new DivisionOf(4L, 2L).longValue(),
            new IsEqual<>(2L)
        ).affirm();
    }

    @Test
    void dividesFloatNumbers() {
        new Assertion<>(
            "Must divide float numbers",
            new DivisionOf(2f, 4f).floatValue(),
            new IsEqual<>(0.5f)
        ).affirm();
    }

    @Test
    void dividesDoubleNumbers() {
        new Assertion<>(
            "Must divide double numbers",
            new DivisionOf(2d, 4d).doubleValue(),
            new IsEqual<>(0.5d)
        ).affirm();
    }

    @Test
    void dividesBigDecimalNumbers() {
        new Assertion<>(
            "Must divide BigDecimal numbers",
            new DivisionOf(BigDecimal.valueOf(2d), BigDecimal.valueOf(4d)),
            new IsNumber(0.5)
        ).affirm();
    }
}
