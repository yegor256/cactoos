/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Immutable}.
 *
 * @since 0.58.0
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.UnnecessaryFullyQualifiedName"})
final class ImmutableTest {
    @Test
    void size() {
        new Assertion<>(
            "size() must be equals to original",
            new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2)
            ).size(),
            new IsEqual<>(
                new SetOf<>(1, 2).size()
            )
        ).affirm();
    }

    @Test
    void isEmpty() {
        new Assertion<>(
            "isEmpty() must be equals to original",
            new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2)
            ).isEmpty(),
            new IsEqual<>(
                new SetOf<>(1, 2).isEmpty()
            )
        ).affirm();
    }

    @Test
    void contains() {
        new Assertion<>(
            "contains() must be equals to original",
            new org.cactoos.set.Immutable<>(
                new SetOf<>("a", "b")
            ).contains("b"),
            new IsEqual<>(
                new SetOf<>("a", "b").contains("b")
            )
        ).affirm();
    }

    @Test
    void iterator() {
        new Assertion<>(
            "iterator() is equal to original",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2)
            ).iterator(),
            new HasValues<>(1, 2)
        ).affirm();
    }

    @Test
    void toArray() {
        new Assertion<>(
            "toArray() must be equals to original",
            new org.cactoos.set.Immutable<>(
                new SetOf<>("a", "b")
            ).toArray(),
            new IsEqual<>(
                new SetOf<>("a", "b").toArray()
            )
        ).affirm();
    }

    @Test
    void testToArray() {
        new Assertion<>(
            "toArray(T[]) must be equals to original",
            new org.cactoos.set.Immutable<>(
                new SetOf<>("a", "b")
            ).toArray(new String[3]),
            new IsEqual<>(
                new SetOf<>("a", "b").toArray(new String[3])
            )
        ).affirm();
    }

    @Test
    void add() {
        new Assertion<>(
            "add(T) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2)
            ).add(3),
            new Throws<>(
                "#add(T): the set is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void remove() {
        new Assertion<>(
            "remove(Object) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>("1", "2")
            ).remove("1"),
            new Throws<>(
                "#remove(Object): the set is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void containsAll() {
        new Assertion<>(
            "containsAll() must be equals to original",
            new org.cactoos.set.Immutable<>(
                new SetOf<>("a", "b", "c")
            ).containsAll(new SetOf<>("a", "c")),
            new IsEqual<>(
                new SetOf<>("a", "b", "c").containsAll(
                    new SetOf<>("a", "c")
                )
            )
        ).affirm();
    }

    @Test
    void addAll() {
        new Assertion<>(
            "addAll(Collection) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2)
            ).addAll(new SetOf<>(3, 4)),
            new Throws<>(
                "#addAll(Collection): the set is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void testAddAll() {
        new Assertion<>(
            "addAll(Collection) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2)
            ).addAll(new SetOf<>(3, 4)),
            new Throws<>(
                "#addAll(Collection): the set is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void retainAll() {
        new Assertion<>(
            "retainAll(Collection) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2, 3)
            ).retainAll(new SetOf<>(1, 3)),
            new Throws<>(
                "#retainAll(Collection): the set is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void removeAll() {
        new Assertion<>(
            "removeAll(Collection) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2, 3)
            ).removeAll(new SetOf<>(1, 3)),
            new Throws<>(
                "#removeAll(Collection): the set is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void clear() {
        new Assertion<>(
            "clear() must throw exception",
            () -> {
                new org.cactoos.set.Immutable<>(
                    new SetOf<>(1, 2, 3)
                ).clear();
                return new Object();
            },
            new Throws<>(
                "#clear(): the set is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void testAdd() {
        new Assertion<>(
            "add(T) must throw exception",
            () -> {
                new org.cactoos.set.Immutable<>(
                    new SetOf<>("a", "b")
                ).add("c");
                return new Object();
            },
            new Throws<>(
                "#add(T): the set is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void testRemove() {
        new Assertion<>(
            "remove(Objet) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>("a", "b")
            ).remove("b"),
            new Throws<>(
                "#remove(Object): the set is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void notEqualsToObjectOfAnotherType() {
        new Assertion<>(
            "must not equal to object of another type",
            new org.cactoos.set.Immutable<>(new SetOf<>()),
            new IsNot<>(new IsEqual<>(new Object()))
        ).affirm();
    }

    @Test
    void notEqualsToListWithDifferentElements() {
        new Assertion<>(
            "must not equal to List with different elements",
            new org.cactoos.set.Immutable<>(new SetOf<>(1, 2)),
            new IsNot<>(new IsEqual<>(new SetOf<>(1, 0)))
        ).affirm();
    }

    @Test
    void isEqualToItself() {
        final Set<Integer>
            set = new org.cactoos.set.Immutable<>(new SetOf<>(1, 2));
        new Assertion<>(
            "must be equal to itself",
            set,
            new IsEqual<>(set)
        ).affirm();
    }

    @Test
    void isEqualToListWithTheSameElements() {
        new Assertion<>(
            "must be equal to Set with the same elements",
            new org.cactoos.set.Immutable<>(new SetOf<>(1, 2)),
            new IsEqual<>(new SetOf<>(1, 2))
        ).affirm();
    }

    @Test
    void equalToEmptyImmutable() {
        new Assertion<>(
            "empty Immutable must be equal to empty Immutable",
            new org.cactoos.set.Immutable<>(new SetOf<>()),
            new IsEqual<>(new org.cactoos.set.Immutable<>(new SetOf<>()))
        ).affirm();
    }

    @Test
    void testHashCode() {
        new Assertion<>(
            "hashCode() must be equal to hashCode of the corresponding Set",
            new org.cactoos.set.Immutable<>(new SetOf<>(1, 2)).hashCode(),
            new IsEqual<>(
                new SetOf<>(1, 2).hashCode()
            )
        ).affirm();
    }

    @Test
    void testToString() {
        new Assertion<>(
            "toString() must be equal to toString of the corresponding Set",
            new org.cactoos.set.Immutable<>(new SetOf<>("a", "b", "c")).toString(),
            new IsEqual<>(new SetOf<>("a", "b", "c").toString())
        ).affirm();
    }

    @Test
    void innerSetIsDecorated() {
        final Set<String> strings = new HashSet<>(Arrays.asList("a", "b", "c"));
        final Set<String> immutable = new org.cactoos.set.Immutable<>(strings);
        strings.add("d");
        new Assertion<>(
            "Must reflect inner set in the decorator",
            immutable,
            new IsEqual<>(strings)
        ).affirm();
    }

    @Test
    void returnsIteratorWithUnsupportedRemove() {
        new Assertion<>(
            "Must return an iterator that does not support remove()",
            () -> {
                final Set<String>
                    set = new org.cactoos.set.Immutable<>(new SetOf<>("one"));
                final Iterator<String> iterator = set.iterator();
                iterator.next();
                iterator.remove();
                return true;
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }
}
