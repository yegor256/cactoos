/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
import java.util.LinkedList;
import java.util.List;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;

/**
 * Test case for {@link CollectionEnvelope}.
 *
 * @since 0.32
 * @todo #813:30min IterableEnvelope appears to be read only but it returns
 *  mutable Iterator through which user can remove elements. Implement immutable
 *  Iterator for IterableEnvelope `iterator()` method.
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.TooManyMethods"})
public final class CollectionEnvelopeTest {
    @Test(expected = UnsupportedOperationException.class)
    public void returnsIteratorWithUnsupportedRemove() {
        final CollectionEnvelope<String> list = new CollectionEnvelope<String>(
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
    public void notEqualToObjectOfAnotherType() {
        MatcherAssert.assertThat(
            "Collection is equal to object of different type",
            new CollectionOf<>(),
            new IsNot<>(new IsEqual<>("a"))
        );
    }

    @Test
    public void notEqualToCollectionOfDifferentSize() {
        MatcherAssert.assertThat(
            "Collection is equal to a collection of different size",
            new CollectionOf<>(),
            new IsNot<>(new IsEqual<>(new CollectionOf<>("b")))
        );
    }

    @Test
    public void notEqualToCollectionOfDifferentElements() {
        MatcherAssert.assertThat(
            "Collection is equal to a collection with different content",
            new CollectionOf<>("a", "b"),
            new IsNot<>(new IsEqual<>(new CollectionOf<>("a", "c")))
        );
    }

    @Test
    public void equalToCollectionWithIdenticalContent() {
        MatcherAssert.assertThat(
            "Collection is not equal to a collection with identical content",
            new CollectionOf<>("val1", "val2"),
            new IsEqual<>(new CollectionOf<>("val1", "val2"))
        );
    }

    @Test
    public void equalToListWithIdenticalContent() {
        MatcherAssert.assertThat(
            "Collection not equal to a list with identical content",
            new CollectionOf<>("a"),
            new IsEqual<>(new ListOf<>("a"))
        );
    }

    @Test
    public void equalToDerivedCollection() {
        MatcherAssert.assertThat(
            "Collection not equal to derived collection with identical content",
            new CollectionOf<>("a"),
            new IsEqual<>(new CollectionEnvelopeTest.CustomCollection("a"))
        );
    }

    @Test
    public void equalToEmptyCollection() {
        MatcherAssert.assertThat(
            "Empty collection not equal with empty collection",
            new CollectionOf<>(),
            new IsEqual<>(new CollectionOf<>())
        );
    }

    @Test
    public void notEqualToNull() {
        MatcherAssert.assertThat(
            "Empty collection equal to null",
            new CollectionOf<>(),
            new IsNot<>(new IsEqual<>(null))
        );
    }

    @Test
    public void hashCodeEqual() {
        MatcherAssert.assertThat(
            "HashCode returns different results for same entries",
            new CollectionOf<>("a", "b").hashCode(),
            new IsEqual<>(new CollectionOf<>("a", "b").hashCode())
        );
    }

    @Test
    public void differentHashCode() {
        MatcherAssert.assertThat(
            "HashCode returns identical results for different entries",
            new CollectionOf<>("a", "b").hashCode(),
            new IsNot<>(new IsEqual<>(new CollectionOf<>("b", "a").hashCode()))
        );
    }

    /**
     * Custom collection.
     */
    private static class CustomCollection extends CollectionEnvelope<String> {

        /**
         * Ctor.
         * @param elements String elements
         */
        CustomCollection(final String... elements) {
            super(() -> new CollectionOf<>(elements));
        }
    }
}
