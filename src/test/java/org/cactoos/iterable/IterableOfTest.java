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
    void convertsScalarsToIterable() throws Exception {
        new Assertion<>(
            "Must convert scalars to iterable",
            new LengthOf(
                new IterableOf<>(
                    "a", "b", "c"
                )
            ),
            // @checkstyle MagicNumber (1 line)
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
    void convertsObjectsToIterable() throws Exception {
        new Assertion<>(
            "Must convert objects to iterable",
            new LengthOf(
                new IterableOf<>(
                    new TextOf("a"), new TextOf("b"), new TextOf("c")
                )
            ),
            // @checkstyle MagicNumber (1 line)
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
