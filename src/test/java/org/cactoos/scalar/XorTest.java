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

import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Xor}.
 * @since 0.48
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.TooManyMethods"})
final class XorTest {

    @Test
    void trueTrue() throws Exception {
        new Assertion<>(
            "Either one, but not both nor none",
            new Xor(
                new True(),
                new True()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void falseTrue() throws Exception {
        new Assertion<>(
            "Either one, but not both nor none",
            new Xor(
                new False(),
                new True()
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void treuFalse() throws Exception {
        new Assertion<>(
            "Either one, but not both nor none",
            new Xor(
                new True(),
                new False()
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void falseFalse() throws Exception {
        new Assertion<>(
            "Either one, but not both nor none",
            new Xor(
                new False(),
                new False()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void singleTrue() throws Exception {
        new Assertion<>(
            "Single True must be True",
            new Xor(
                new True()
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void singleFalse() throws Exception {
        new Assertion<>(
            "Single False must be False",
            new Xor(
                new False()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void emptyIterable() throws Exception {
        new Assertion<>(
            "Empty iterable must be False",
            new Xor(new IterableOf<Scalar<Boolean>>()),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void oddNumberOfTrue() throws Exception {
        new Assertion<>(
            "Odd number of True must be True",
            new Xor(
                new False(),
                new False(),
                new True()
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void evenNumberOfTrue() throws Exception {
        new Assertion<>(
            "Even number of True must be False",
            new Xor(
                new False(),
                new True(),
                new True()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void allFalse() throws Exception {
        new Assertion<>(
            "All False must be False",
            new Xor(
                new False(),
                new False(),
                new False()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void allTrue() throws Exception {
        new Assertion<>(
            "Odd number of True must be True",
            new Xor(
                new True(),
                new True(),
                new True()
            ),
            new HasValue<>(true)
        ).affirm();
    }
}
