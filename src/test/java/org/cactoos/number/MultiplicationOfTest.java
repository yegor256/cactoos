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
package org.cactoos.number;

import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link MultiplicationOf}.
 *
 * @since 0.49.2
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class MultiplicationOfTest {

    /**
     * Ensures that multiplication of int numbers return proper value.
     */
    @Test
    public void withListOfNumbersInt() {
        new Assertion<>(
            "Multiplication of int must return the appropriate value",
            new MultiplicationOf(2, 3).intValue(),
            new IsEqual<>(6)
        ).affirm();
    }

    /**
     * Ensures that multiplication of double numbers return proper value.
     */
    @Test
    public void withListOfNumbersDouble() {
        new Assertion<>(
            "Multiplication of double numbers must return proper value",
            new MultiplicationOf(2.0d, 2.5d, 3.0d).doubleValue(),
            new IsEqual<>(15.0d)
        ).affirm();
    }

    /**
     * Ensures that multiplication of float numbers return proper value.
     */
    @Test
    public void withListOfNumbersFloat() {
        new Assertion<>(
            "Multiplication of float numbers must return proper value",
            new MultiplicationOf(3.0f, 3.0f, 3.0f).floatValue(),
            new IsEqual<>(27.0f)
        ).affirm();
    }

    /**
     * Ensures that multiplication of long numbers return proper value.
     */
    @Test
    public void withListOfNumbersLong() {
        new Assertion<>(
            "Multiplication of long numbers must return proper value",
            new MultiplicationOf(2L, 3L, 2L).longValue(),
            new IsEqual<>(12L)
        ).affirm();
    }

    /**
     * Ensures that multiplication of int numbers iterable return proper value.
     */
    @Test
    public void withIterableInt() {
        new Assertion<>(
            "Multiplication of int numbers must return proper value",
            new MultiplicationOf(
                new IterableOf<>(2L, 3L, 3L)
            ).intValue(),
            new IsEqual<>(18)
        ).affirm();
    }

    /**
     * Ensures that multiplication of long numbers iterable return proper value.
     */
    @Test
    public void withIterableLong() {
        new Assertion<>(
            "Multiplication of long numbers iterable must return proper value",
            new MultiplicationOf(
                new IterableOf<>(1, 2, 3)
            ).longValue(),
            new IsEqual<>(6L)
        ).affirm();
    }

    /**
     * Ensures that multiplication of float numbers iterable
     * return proper value.
     */
    @Test
    public void withIterableFloat() {
        new Assertion<>(
            "Multiplication floating numbers must be iterable",
            new MultiplicationOf(
                new IterableOf<>(0.5f, 2.0f, 10.0f)
            ).floatValue(),
            new IsEqual<>(10.0f)
        ).affirm();
    }

    /**
     * Ensures that multiplication of double numbers iterable
     * return proper value.
     */
    @Test
    public void withIterableDouble() {
        new Assertion<>(
            "Multiplication double numbers must be iterable",
            new MultiplicationOf(
                new IterableOf<>(1.5d, 2.0d, 5.0d)
            ).doubleValue(),
            new IsEqual<>(15.0d)
        ).affirm();
    }

    /**
     * Ensures that empty iterable will not be multiplied.
     */
    @Test
    public void rejectsEmptyIterable() {
        new Assertion<>(
            "Must fail with empty iterable",
            () -> new MultiplicationOf(new IterableOf<>()).doubleValue(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }
}
