/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
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
        MatcherAssert.assertThat(
            "Must convert scalars to iterable",
            new LengthOf(
                new IterableOf<>(
                    "a", "b", "c"
                )
            ),
            new HasValue<>(3L)
        );
    }

    @Test
    void convertsArrayOfIntsToIterable() {
        MatcherAssert.assertThat(
            "Must convert int scalars to iterable",
            new IterableOf<>(1, 2, 0, 2),
            new HasValues<>(0)
        );
    }

    @Test
    void convertsObjectsToIterable() {
        MatcherAssert.assertThat(
            "Must convert objects to iterable",
            new LengthOf(
                new IterableOf<>(
                    new TextOf("a"), new TextOf("b"), new TextOf("c")
                )
            ),
            new HasValue<>(3L)
        );
    }

    @Test
    void isNotEqualsToIterableWithMoreElements() {
        MatcherAssert.assertThat(
            "Must compare iterables and second one is bigger",
            new IterableOf<>("a", "b").equals(new IterableOf<>("a")),
            new IsNot<>(new IsTrue())
        );
    }

    @Test
    void isNotEqualsToIterableWithLessElements() {
        MatcherAssert.assertThat(
            "Must compare iterables and first one is bigger",
            new IterableOf<>("a").equals(new IterableOf<>("a", "b")),
            new IsNot<>(new IsTrue())
        );
    }

    @Test
    void notEqualsToObjectOfAnotherType() {
        MatcherAssert.assertThat(
            "Must not equal to object of another type",
            new IterableOf<>(),
            new IsNot<>(new IsEqual<>(new Object()))
        );
    }

    @Test
    void notEqualsToIterableWithDifferentElements() {
        MatcherAssert.assertThat(
            "Must not equal to Iterable with different elements",
            new IterableOf<>(1, 2),
            new IsNot<>(new IsEqual<>(new IterableOf<>(1, 0)))
        );
    }

    @Test
    void isEqualToItself() {
        final IterableOf<Integer> iterable = new IterableOf<>(1, 2);
        MatcherAssert.assertThat(
            "Must be equal to itself",
            iterable,
            new IsEqual<>(iterable)
        );
    }

    @Test
    void isEqualToIterableWithTheSameElements() {
        MatcherAssert.assertThat(
            "Must be equal to Iterable with the same elements",
            new IterableOf<>(1, 2),
            new IsEqual<>(new IterableOf<>(1, 2))
        );
    }

    @Test
    void equalToEmptyIterable() {
        MatcherAssert.assertThat(
            "Empty Iterable must be equal to empty Iterable",
            new IterableOf<>(),
            new IsEqual<>(new IterableOf<>())
        );
    }

    @Test
    void differentHashCode() {
        MatcherAssert.assertThat(
            "Must have different hashCode for Iterables with different content",
            new IterableOf<>(1, 2).hashCode(),
            new IsNot<>(new IsEqual<>(new IterableOf<>(2, 1).hashCode()))
        );
    }

    @Test
    void equalHashCode() {
        MatcherAssert.assertThat(
            "Must have equal hashCode for Iterables with equal content",
            new IterableOf<>(1, 2).hashCode(),
            new IsEqual<>(new IterableOf<>(1, 2).hashCode())
        );
    }
}
