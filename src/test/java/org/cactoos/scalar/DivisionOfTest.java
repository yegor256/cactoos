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

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Test case for {@link DivisionOf}.
 *
 * @since 0.49.2
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class DivisionOfTest {

    /**
     * Ensures that division of int numbers return proper value.
     */
    @Test
    public void dividesIntNumbers() {
        MatcherAssert.assertThat(
            new DivisionOf(4, 2).intValue(),
            new IsEqual<>(2)
        );
    }

    /**
     * Ensures that division of long numbers return proper value.
     */
    @Test
    public void dividesLongNumbers() {
        MatcherAssert.assertThat(
            new DivisionOf(4L, 2L).longValue(),
            new IsEqual<>(2L)
        );
    }

    /**
     * Ensures that division of float numbers return proper value.
     */
    @Test
    public void dividesFloatNumbers() {
        MatcherAssert.assertThat(
            new DivisionOf(2f, 4f).floatValue(),
            new IsEqual<>(0.5f)
        );
    }

    /**
     * Ensures that division of double numbers return proper value.
     */
    @Test
    public void dividesDoubleNumbers() {
        MatcherAssert.assertThat(
            new DivisionOf(2d, 4d).doubleValue(),
            new IsEqual<>(0.5d)
        );
    }
}
