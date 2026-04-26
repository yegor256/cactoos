/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link MultiplicationOf}.
 * @since 0.49.2
 */
final class MultiplicationOfTest {

    /**
     * Ensures that multiplication of int numbers return proper value.
     */
    @Test
    void withListOfNumbersInt() {
        MatcherAssert.assertThat(
            "Multiplication of int must return the appropriate value",
            new MultiplicationOf(2, 3).intValue(),
            new IsEqual<>(6)
        );
    }

    /**
     * Ensures that multiplication of double numbers return proper value.
     */
    @Test
    void withListOfNumbersDouble() {
        MatcherAssert.assertThat(
            "Multiplication of double numbers must return proper value",
            new MultiplicationOf(2.0d, 2.5d, 3.0d).doubleValue(),
            new IsEqual<>(15.0d)
        );
    }

    /**
     * Ensures that multiplication of float numbers return proper value.
     */
    @Test
    void withListOfNumbersFloat() {
        MatcherAssert.assertThat(
            "Multiplication of float numbers must return proper value",
            new MultiplicationOf(3.0f, 3.0f, 3.0f).floatValue(),
            new IsEqual<>(27.0f)
        );
    }

    /**
     * Ensures that multiplication of long numbers return proper value.
     */
    @Test
    void withListOfNumbersLong() {
        MatcherAssert.assertThat(
            "Multiplication of long numbers must return proper value",
            new MultiplicationOf(2L, 3L, 2L).longValue(),
            new IsEqual<>(12L)
        );
    }

    /**
     * Ensures that multiplication of int numbers iterable return proper value.
     */
    @Test
    void withIterableInt() {
        MatcherAssert.assertThat(
            "Multiplication of int numbers must return proper value",
            new MultiplicationOf(
                new IterableOf<>(2L, 3L, 3L)
            ).intValue(),
            new IsEqual<>(18)
        );
    }

    /**
     * Ensures that multiplication of long numbers iterable return proper value.
     */
    @Test
    void withIterableLong() {
        MatcherAssert.assertThat(
            "Multiplication of long numbers iterable must return proper value",
            new MultiplicationOf(
                new IterableOf<>(1, 2, 3)
            ).longValue(),
            new IsEqual<>(6L)
        );
    }

    /**
     * Ensures that multiplication of float numbers iterable
     * return proper value.
     */
    @Test
    void withIterableFloat() {
        MatcherAssert.assertThat(
            "Multiplication floating numbers must be iterable",
            new MultiplicationOf(
                new IterableOf<>(0.5f, 2.0f, 10.0f)
            ).floatValue(),
            new IsEqual<>(10.0f)
        );
    }

    /**
     * Ensures that multiplication of double numbers iterable
     * return proper value.
     */
    @Test
    void withIterableDouble() {
        MatcherAssert.assertThat(
            "Multiplication double numbers must be iterable",
            new MultiplicationOf(
                new IterableOf<>(1.5d, 2.0d, 5.0d)
            ).doubleValue(),
            new IsEqual<>(15.0d)
        );
    }

    /**
     * Ensures that empty iterable will not be multiplied.
     */
    @Test
    void rejectsEmptyIterable() {
        MatcherAssert.assertThat(
            "Must fail with empty iterable",
            () -> new MultiplicationOf(new IterableOf<>()).doubleValue(),
            new Throws<>(NoSuchElementException.class)
        );
    }
}
