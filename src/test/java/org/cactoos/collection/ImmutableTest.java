/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.collection;

import java.util.ArrayList;
import java.util.Arrays;
import org.cactoos.list.ListOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Immutable}.
 *
 * @since 1.16
 */
@SuppressWarnings("PMD.TooManyMethods")
final class ImmutableTest {

    @Test
    void size() {
        new Assertion<>(
            "size() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2)
            ).size(),
            new IsEqual<>(2)
        ).affirm();
    }

    @Test
    void isEmpty() {
        new Assertion<>(
            "isEmpty() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2)
            ).isEmpty(),
            new IsEqual<>(
                new ListOf<>(1, 2).isEmpty()
            )
        ).affirm();
    }

    @Test
    void iterator() {
        new Assertion<>(
            "iterator() is equal to original",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).iterator(),
            new HasValues<>(1, 2)
        ).affirm();
    }

    @Test
    void contains() {
        new Assertion<>(
            "contains() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2)
            ).contains(1),
            new IsEqual<>(
                new ListOf<>(1, 2).contains(1)
            )
        ).affirm();
    }

    @Test
    void toArray() {
        new Assertion<>(
            "toArray() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2)
            ).toArray(),
            new IsEqual<>(
                new ListOf<>(1, 2).toArray()
            )
        ).affirm();
    }

    @Test
    void testToArray() {
        new Assertion<>(
            "toArray(T[]) must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).toArray(new Integer[3]),
            new IsEqual<>(
                new ListOf<>(1, 2, 3).toArray(new Integer[3])
            )
        ).affirm();
    }

    @Test
    void add() {
        new Assertion<>(
            "add() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).add(3),
            new Throws<>(
                "#add(): the collection is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void remove() {
        new Assertion<>(
            "remove() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).remove(1),
            new Throws<>(
                "#remove(): the collection is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void containsAll() {
        final ListOf<Integer> another = new ListOf<>(3, 4, 5);
        new Assertion<>(
            "containsAll() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).containsAll(another),
            new IsEqual<>(
                new ListOf<>(1, 2, 3).containsAll(another)
            )
        ).affirm();
    }

    @Test
    void addAll() {
        new Assertion<>(
            "addAll() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).addAll(new ListOf<>(3, 4)),
            new Throws<>(
                "#addAll(): the collection is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void removeAll() {
        new Assertion<>(
            "removeAll() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).removeAll(new ListOf<>(2, 3)),
            new Throws<>(
                "#removeAll(): the collection is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void retainAll() {
        new Assertion<>(
            "retainAll() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).retainAll(new ListOf<>(1)),
            new Throws<>(
                "#retainAll(): the collection is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void clear() {
        new Assertion<>(
            "clear() must throw exception",
            () -> {
                new Immutable<>(
                    new ListOf<>(1, 2, 3)
                ).clear();
                return new Object();
            },
            new Throws<>(
                "#clear(): the collection is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void testToString() {
        new Assertion<>(
            "toString() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).toString(),
            new IsEqual<>(
                new ListOf<>(1, 2, 3).toString()
            )
        ).affirm();
    }

    @Test
    void testHashCode() {
        new Assertion<>(
            "hashCode() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).hashCode(),
            new IsEqual<>(
                new ListOf<>(1, 2, 3).hashCode()
            )
        ).affirm();
    }

    @Test
    void testEquals() {
        final ListOf<Integer> another = new ListOf<>(4, 5, 6);
        new Assertion<>(
            "equals() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).equals(another),
            new IsEqual<>(
                new ListOf<>(1, 2, 3).equals(another)
            )
        ).affirm();
    }

    @Test
    void isImmutableToExternalChanges() {
        final ArrayList<String> original = new ArrayList<>(
            Arrays.asList("a", "b", "c")
        );
        final Immutable<String> immutable = new Immutable<>(original);
        original.add("d");
        new Assertion<>(
            "Immutable collection must not reflect external changes",
            immutable.size(),
            new IsEqual<>(3)
        ).affirm();
        new Assertion<>(
            "Immutable collection must contain original elements only",
            immutable.contains("d"),
            new IsEqual<>(false)
        ).affirm();
    }
}
