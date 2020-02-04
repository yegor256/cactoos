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
package org.cactoos.collection;

import java.util.Iterator;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test Case for {@link CollectionOf}.
 * @since 0.23
 * @deprecated This test class is to be removed after {@link CollectionOf}
 *  is completely removed.
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@Deprecated
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
public final class CollectionOfTest {

    @Test
    public void behavesAsCollection() {
        new Assertion<>(
            "Can't behave as a collection",
            new CollectionOf<>(1, 2, 0, -1),
            new BehavesAsCollection<>(-1)
        ).affirm();
    }

    @Test
    public void buildsCollectionFromIterable() {
        new Assertion<>(
            "Can't build a collection from iterable",
            new CollectionOf<>(
                new ListOf<>(
                    new IterableOf<>(1, 2, 0, -1)
                )
            ),
            new BehavesAsCollection<>(-1)
        ).affirm();
    }

    @Test
    public void testToString() {
        new Assertion<>(
            "Wrong toString output. Expected \"[1, 2, 0, -1]\".",
            new CollectionOf<>(
                new ListOf<>(1, 2, 0, -1)
            ).toString(),
            new IsEqual<>("[1, 2, 0, -1]")
        ).affirm();
    }

    @Test
    public void testToStringEmpty() {
        new Assertion<>(
            "Wrong toString output. Expected \"[]\".",
            new CollectionOf<>(new ListOf<>()).toString(),
            new IsEqual<>("[]")
        ).affirm();
    }

    @Test()
    public void returnsIteratorWithSupportedRemove() {
        final CollectionEnvelope<String> list = new CollectionEnvelope<String>(
            new CollectionOf<>("eleven")
        ) {
        };
        final Iterator<String> iterator = list.iterator();
        iterator.next();
        iterator.remove();
        new Assertion<>(
            "Must return an empty Iterator",
            new IterableOf<>(iterator),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    public void notEqualToObjectOfAnotherType() {
        new Assertion<>(
            "Must not equal an object of different type",
            new CollectionOf<>(),
            new IsNot<>(new IsEqual<>("a"))
        ).affirm();
    }

    @Test
    public void notEqualToCollectionOfDifferentSize() {
        new Assertion<>(
            "Must not equal a collection of different size",
            new CollectionOf<>(),
            new IsNot<>(new IsEqual<>(new CollectionOf<>("b")))
        ).affirm();
    }

    @Test
    public void notEqualToCollectionOfDifferentElements() {
        new Assertion<>(
            "Must not equal a collection with different content",
            new CollectionOf<>("a", "b"),
            new IsNot<>(new IsEqual<>(new CollectionOf<>("a", "c")))
        ).affirm();
    }

    @Test
    public void equalToItself() {
        final CollectionOf<String> col = new CollectionOf<>("val1", "val2");
        new Assertion<>(
            "Must equal to itself",
            col,
            new IsEqual<>(col)
        ).affirm();
    }

    @Test
    public void equalToCollectionWithIdenticalContent() {
        new Assertion<>(
            "Must equal a collection with identical content",
            new CollectionOf<>("val1", "val2"),
            new IsEqual<>(new CollectionOf<>("val1", "val2"))
        ).affirm();
    }

    @Test
    public void equalToListWithIdenticalContent() {
        new Assertion<>(
            "Must equal a list with identical content",
            new CollectionOf<>("a"),
            new IsEqual<>(new ListOf<>("a"))
        ).affirm();
    }

    @Test
    public void equalToEmptyCollection() {
        new Assertion<>(
            "Must equal an empty collection",
            new CollectionOf<>(),
            new IsEqual<>(new CollectionOf<>())
        ).affirm();
    }

    @Test
    public void notEqualToNull() {
        new Assertion<>(
            "Must not equal to null",
            new CollectionOf<>(),
            new IsNot<>(new IsEqual<>(null))
        ).affirm();
    }

    @Test
    public void hashCodeEqual() {
        new Assertion<>(
            "Must have same hash code for same entries",
            new CollectionOf<>("a", "b").hashCode(),
            new IsEqual<>(new CollectionOf<>("a", "b").hashCode())
        ).affirm();
    }

    @Test
    public void differentHashCode() {
        new Assertion<>(
            "Must have different hash code for different entries",
            new CollectionOf<>("a", "b").hashCode(),
            new IsNot<>(new IsEqual<>(new CollectionOf<>("b", "a").hashCode()))
        ).affirm();
    }
}
