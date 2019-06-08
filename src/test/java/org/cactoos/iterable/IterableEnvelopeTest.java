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
import java.util.LinkedList;
import java.util.List;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link IterableEnvelope}.
 *
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class IterableEnvelopeTest {

    @Test(expected = UnsupportedOperationException.class)
    public void returnsIteratorWithUnsupportedRemove() {
        final IterableEnvelope<String> list = new IterableEnvelope<String>(
            () -> {
                final List<String> inner = new LinkedList<>();
                inner.add("eleven");
                return inner;
            }
        ) { };
        final Iterator<String> iterator = list.iterator();
        iterator.next();
        iterator.remove();
    }

    @Test
    public void notEqualsToObjectOfAnotherType() {
        new Assertion<>(
            "Must not equal to object of another type",
            new IterableOf<>(),
            new IsNot<>(new IsEqual<>(new Object()))
        ).affirm();
    }

    @Test
    public void notEqualsToIterableWithDifferentElements() {
        final IterableOf<Integer> first = new IterableOf<>(1, 2);
        final IterableOf<Integer> second = new IterableOf<>(1, 3);
        new Assertion<>(
            "Must not equal to Iterable with different elements",
            first,
            new IsNot<>(new IsEqual<>(second))
        ).affirm();
    }

    @Test
    public void isEqualToItself() {
        final IterableOf<Integer> iterable = new IterableOf<>(1, 2);
        new Assertion<>(
            "Must be equal to itself",
            iterable,
            new IsEqual<>(iterable)
        ).affirm();
    }

    @Test
    public void isEqualToIterableWithTheSameElements() {
        final IterableOf<Integer> iterable = new IterableOf<>(1, 2);
        new Assertion<>(
            "Must be equal to Iterable with the same elements",
            iterable,
            new IsEqual<>(new IterableOf<>(1, 2))
        ).affirm();
    }

    @Test
    public void equalToEmptyIterable() {
        final IterableOf<Integer> iterable = new IterableOf<>();
        new Assertion<>(
            "Empty Iterable must be equal to empty Iterable",
            iterable,
            new IsEqual<>(new IterableOf<>())
        ).affirm();
    }

    @Test
    public void differentHashCode() {
        final IterableOf<Integer> first = new IterableOf<>(1, 2);
        final IterableOf<Integer> second = new IterableOf<>(2, 1);
        new Assertion<>(
            "Must have different hashCode for Iterables with different content",
            first.hashCode(),
            new IsNot<>(new IsEqual<>(second.hashCode()))
        ).affirm();
    }

    @Test
    public void equalHashCode() {
        final IterableOf<Integer> iterable = new IterableOf<>(1, 2);
        new Assertion<>(
            "Must have equal hashCode for Iterables with equal content",
            iterable.hashCode(),
            new IsEqual<>(new IterableOf<>(1, 2).hashCode())
        ).affirm();
    }
}
