/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link IterableOf}.
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
final class IterableOfTest {

    @Test
    void convertsScalarsToIterable() {
        new Assertion<>(
            "Must convert scalars to iterable",
            new LengthOf(
                new IterableOf<>(
                    "a", "b", "c"
                )
            ),
            new HasValue<>(3L)
        ).affirm();
    }

    @Test
    void convertsArrayOfIntsToIterable() {
        new Assertion<>(
            "Must convert int scalars to iterable",
            new IterableOf<>(1, 2, 0, 2),
            new HasValues<>(0)
        ).affirm();
    }

    @Test
    void convertsObjectsToIterable() {
        new Assertion<>(
            "Must convert objects to iterable",
            new LengthOf(
                new IterableOf<>(
                    new TextOf("a"), new TextOf("b"), new TextOf("c")
                )
            ),
            new HasValue<>(3L)
        ).affirm();
    }

    @Test
    void isNotEqualsToIterableWithMoreElements() {
        new Assertion<>(
            "Must compare iterables and second one is bigger",
            new IterableOf<>("a", "b").equals(new IterableOf<>("a")),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @Test
    void isNotEqualsToIterableWithLessElements() {
        new Assertion<>(
            "Must compare iterables and first one is bigger",
            new IterableOf<>("a").equals(new IterableOf<>("a", "b")),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @Test
    void notEqualsToObjectOfAnotherType() {
        new Assertion<>(
            "Must not equal to object of another type",
            new IterableOf<>(),
            new IsNot<>(new IsEqual<>(new Object()))
        ).affirm();
    }

    @Test
    void notEqualsToIterableWithDifferentElements() {
        new Assertion<>(
            "Must not equal to Iterable with different elements",
            new IterableOf<>(1, 2),
            new IsNot<>(new IsEqual<>(new IterableOf<>(1, 0)))
        ).affirm();
    }

    @Test
    void isEqualToItself() {
        final IterableOf<Integer> iterable = new IterableOf<>(1, 2);
        new Assertion<>(
            "Must be equal to itself",
            iterable,
            new IsEqual<>(iterable)
        ).affirm();
    }

    @Test
    void isEqualToIterableWithTheSameElements() {
        new Assertion<>(
            "Must be equal to Iterable with the same elements",
            new IterableOf<>(1, 2),
            new IsEqual<>(new IterableOf<>(1, 2))
        ).affirm();
    }

    @Test
    void equalToEmptyIterable() {
        new Assertion<>(
            "Empty Iterable must be equal to empty Iterable",
            new IterableOf<>(),
            new IsEqual<>(new IterableOf<>())
        ).affirm();
    }

    @Test
    void differentHashCode() {
        new Assertion<>(
            "Must have different hashCode for Iterables with different content",
            new IterableOf<>(1, 2).hashCode(),
            new IsNot<>(new IsEqual<>(new IterableOf<>(2, 1).hashCode()))
        ).affirm();
    }

    @Test
    void equalHashCode() {
        new Assertion<>(
            "Must have equal hashCode for Iterables with equal content",
            new IterableOf<>(1, 2).hashCode(),
            new IsEqual<>(new IterableOf<>(1, 2).hashCode())
        ).affirm();
    }
}
