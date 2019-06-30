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
package org.cactoos.iterable;

import java.util.Iterator;
import org.cactoos.iterator.IteratorOf;
import org.cactoos.scalar.LengthOf;
import org.cactoos.scalar.Ternary;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableWithSize;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link IterableOf}.
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
public final class IterableOfTest {

    @Test
    public void convertsScalarsToIterable() {
        MatcherAssert.assertThat(
            "must convert scalars to iterable",
            new LengthOf(
                new IterableOf<>(
                    "a", "b", "c"
                )
            ).intValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(3)
        );
    }

    @Test
    public void convertsArrayOfIntsToIterable() {
        MatcherAssert.assertThat(
            "must convert int scalars to iterable",
            new IterableOf<>(1, 2, 0, 2),
            Matchers.hasItem(0)
        );
    }

    @Test
    public void convertsObjectsToIterable() {
        MatcherAssert.assertThat(
            "must convert objects to iterable",
            new LengthOf(
                new IterableOf<>(
                    new TextOf("a"), new TextOf("b"), new TextOf("c")
                )
            ).intValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(3)
        );
    }

    @Test
    @SuppressWarnings({"unchecked", "PMD.AvoidDuplicateLiterals"})
    public void containAllPagedContentInOrder() throws Exception {
        final Iterable<String> first = new IterableOf<>("one", "two");
        final Iterable<String> second = new IterableOf<>("three", "four");
        final Iterable<String> third = new IterableOf<>("five");
        final Iterable<Iterable<String>> service = new IterableOf<>(
            first, second, third
        );
        final Iterator<Iterable<String>> pages = service.iterator();
        MatcherAssert.assertThat(
            "must have all page values",
            new IterableOf<>(
                () -> pages.next().iterator(),
                page -> new Ternary<>(
                    () -> pages.hasNext(),
                    () -> pages.next().iterator(),
                    () -> new IteratorOf<String>()
                ).value()
            ),
            new IsEqual<>(new Joined<>(first, second, third))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void reportTotalPagedLength() throws Exception {
        final Iterable<String> first = new IterableOf<>("A", "five");
        final Iterable<String> second = new IterableOf<>("word", "long");
        final Iterable<String> third = new IterableOf<>("sentence");
        final Iterable<Iterable<String>> service = new IterableOf<>(
            first, second, third
        );
        final Iterator<Iterable<String>> pages = service.iterator();
        MatcherAssert.assertThat(
            "length must be equal to total number of elements",
            new IterableOf<>(
                () -> pages.next().iterator(),
                page -> new Ternary<>(
                    () -> pages.hasNext(),
                    () -> pages.next().iterator(),
                    () -> new IteratorOf<String>()
                ).value()
            ),
            new IsIterableWithSize<>(
                new IsEqual<>(
                    new LengthOf(
                        new Joined<>(first, second, third)
                    ).intValue()
                )
            )
        );
    }
    @Test
    public void notEqualsToObjectOfAnotherType() {
        new Assertion<>(
            "must not equal to object of another type",
            new IterableOf<>(),
            new IsNot<>(new IsEqual<>(new Object()))
        ).affirm();
    }

    @Test
    public void notEqualsToIterableWithDifferentElements() {
        new Assertion<>(
            "must not equal to Iterable with different elements",
            new IterableOf<>(1, 2),
            new IsNot<>(new IsEqual<>(new IterableOf<>(1, 0)))
        ).affirm();
    }

    @Test
    public void isEqualToItself() {
        final IterableOf<Integer> iterable = new IterableOf<>(1, 2);
        new Assertion<>(
            "must be equal to itself",
            iterable,
            new IsEqual<>(iterable)
        ).affirm();
    }

    @Test
    public void isEqualToIterableWithTheSameElements() {
        new Assertion<>(
            "must be equal to Iterable with the same elements",
            new IterableOf<>(1, 2),
            new IsEqual<>(new IterableOf<>(1, 2))
        ).affirm();
    }

    @Test
    public void equalToEmptyIterable() {
        new Assertion<>(
            "empty Iterable must be equal to empty Iterable",
            new IterableOf<>(),
            new IsEqual<>(new IterableOf<>())
        ).affirm();
    }

    @Test
    public void differentHashCode() {
        new Assertion<>(
            "must have different hashCode for Iterables with different content",
            new IterableOf<>(1, 2).hashCode(),
            new IsNot<>(new IsEqual<>(new IterableOf<>(2, 1).hashCode()))
        ).affirm();
    }

    @Test
    public void equalHashCode() {
        new Assertion<>(
            "must have equal hashCode for Iterables with equal content",
            new IterableOf<>(1, 2).hashCode(),
            new IsEqual<>(new IterableOf<>(1, 2).hashCode())
        ).affirm();
    }
}
