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
package org.cactoos.collection;

import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.MatcherOf;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Immutable}.
 *
 * @since 1.16
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public class ImmutableTest {

    @Test
    public void size() {
        new Assertion<>(
            "size() must be equals to original",
            new Immutable<>(
                new CollectionOf<>(1, 2)
            ).size(),
            new IsEqual<>(
                new CollectionOf<>(1, 2).size()
            )
        ).affirm();
    }

    @Test
    public void isEmpty() {
        new Assertion<>(
            "isEmpty() must be equals to original",
            new Immutable<>(
                new CollectionOf<>(1, 2)
            ).isEmpty(),
            new IsEqual<>(
                new CollectionOf<>(1, 2).isEmpty()
            )
        ).affirm();
    }

    @Test
    public void iterator() {
        MatcherAssert.assertThat(
            "iterator() is not equal to original",
            () -> new Immutable<>(
                new CollectionOf<>(1, 2)
            ).iterator(),
            IsIterableContainingInOrder.contains(
                1, 2
            )
        );
    }

    @Test
    public void contains() {
        new Assertion<>(
            "contains() must be equals to original",
            new Immutable<>(
                new CollectionOf<>(1, 2)
            ).contains(1),
            new IsEqual<>(
                new CollectionOf<>(1, 2).contains(1)
            )
        ).affirm();
    }

    @Test
    public void toArray() {
        new Assertion<>(
            "toArray() must be equals to original",
            new Immutable<>(
                new CollectionOf<>(1, 2)
            ).toArray(),
            new IsEqual<>(
                new CollectionOf<>(1, 2).toArray()
            )
        ).affirm();
    }

    @Test
    public void testToArray() {
        new Assertion<>(
            "toArray(T[]) must be equals to original",
            new Immutable<>(
                1, 2, 3
            ).toArray(new Integer[3]),
            new IsEqual<>(
                new CollectionOf<>(1, 2, 3).toArray(new Integer[3])
            )
        ).affirm();
    }

    @Test
    public void add() {
        new Assertion<>(
            "add() must throw exception",
            () -> new Immutable<>(
                new IterableOf<>(1, 2)
            ).add(3),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#add(): the collection is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void remove() {
        new Assertion<>(
            "remove() must throw exception",
            () -> new Immutable<>(
                new CollectionOf<>(1, 2)
            ).remove(1),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#remove(): the collection is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void containsAll() {
        final CollectionOf<Integer> another = new CollectionOf<>(3, 4, 5);
        new Assertion<>(
            "containsAll() must be equals to original",
            new Immutable<>(
                new CollectionOf<>(1, 2, 3)
            ).containsAll(another),
            new IsEqual<>(
                new CollectionOf<>(1, 2, 3).containsAll(another)
            )
        ).affirm();
    }

    @Test
    public void addAll() {
        new Assertion<>(
            "addAll() must throw exception",
            () -> new Immutable<>(
                new CollectionOf<>(1, 2)
            ).addAll(new CollectionOf<>(3, 4)),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#addAll(): the collection is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void removeAll() {
        new Assertion<>(
            "removeAll() must throw exception",
            () -> new Immutable<>(
                new CollectionOf<>(1, 2, 3)
            ).removeAll(new CollectionOf<>(2, 3)),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#removeAll(): the collection is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void retainAll() {
        new Assertion<>(
            "retainAll() must throw exception",
            () -> new Immutable<>(
                new CollectionOf<>(1, 2, 3)
            ).retainAll(new CollectionOf<>(1)),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#retainAll(): the collection is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void clear() {
        new Assertion<>(
            "clear() must throw exception",
            () -> {
                new Immutable<>(
                    new CollectionOf<>(1, 2, 3)
                ).clear();
                return new Object();
            },
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#clear(): the collection is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void testToString() {
        new Assertion<>(
            "toString() must be equals to original",
            new Immutable<>(
                new CollectionOf<>(1, 2, 3)
            ).toString(),
            new IsEqual<>(
                new CollectionOf<>(1, 2, 3).toString()
            )
        ).affirm();
    }

    @Test
    public void testHashCode() {
        new Assertion<>(
            "hashCode() must be equals to original",
            new Immutable<>(
                new CollectionOf<>(1, 2, 3)
            ).hashCode(),
            new IsEqual<>(
                new CollectionOf<>(1, 2, 3).hashCode()
            )
        ).affirm();
    }

    @Test
    public void testEquals() {
        final CollectionOf<Integer> another = new CollectionOf<>(4, 5, 6);
        new Assertion<>(
            "equals() must be equals to original",
            new Immutable<>(
                new CollectionOf<>(1, 2, 3)
            ).equals(another),
            new IsEqual<>(
                new CollectionOf<>(1, 2, 3).equals(another)
            )
        ).affirm();
    }
}
