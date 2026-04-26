/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Immutable}.
 * @since 0.58.0
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.UnnecessaryLocalRule"})
final class ImmutableTest {

    @Test
    void size() {
        MatcherAssert.assertThat(
            "size() must be equals to original",
            new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2)
            ).size(),
            new IsEqual<>(
                new SetOf<>(1, 2).size()
            )
        );
    }

    @Test
    void isEmpty() {
        MatcherAssert.assertThat(
            "isEmpty() must be equals to original",
            new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2)
            ).isEmpty(),
            new IsEqual<>(
                new SetOf<>(1, 2).isEmpty()
            )
        );
    }

    @Test
    void contains() {
        MatcherAssert.assertThat(
            "contains() must be equals to original",
            new org.cactoos.set.Immutable<>(
                new SetOf<>("a", "b")
            ).contains("b"),
            new IsEqual<>(
                new SetOf<>("a", "b").contains("b")
            )
        );
    }

    @Test
    void iterator() {
        MatcherAssert.assertThat(
            "iterator() is equal to original",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2)
            ).iterator(),
            new HasValues<>(1, 2)
        );
    }

    @Test
    void toArray() {
        MatcherAssert.assertThat(
            "toArray() must be equals to original",
            new org.cactoos.set.Immutable<>(
                new SetOf<>("a", "b")
            ).toArray(),
            new IsEqual<>(
                new SetOf<>("a", "b").toArray()
            )
        );
    }

    @Test
    void toArrayTyped() {
        MatcherAssert.assertThat(
            "toArray(T[]) must be equals to original",
            new org.cactoos.set.Immutable<>(
                new SetOf<>("a", "b")
            ).toArray(new String[0]),
            new IsEqual<>(
                new SetOf<>("a", "b").toArray(new String[0])
            )
        );
    }

    @Test
    void add() {
        MatcherAssert.assertThat(
            "add(T) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2)
            ).add(3),
            new Throws<>(
                "#add(T): the set is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void remove() {
        MatcherAssert.assertThat(
            "remove(Object) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>("1", "2")
            ).remove("1"),
            new Throws<>(
                "#remove(Object): the set is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void containsAll() {
        MatcherAssert.assertThat(
            "containsAll() must be equals to original",
            new org.cactoos.set.Immutable<>(
                new SetOf<>("a", "b", "c")
            ).containsAll(new SetOf<>("a", "c")),
            new IsEqual<>(
                new SetOf<>("a", "b", "c").containsAll(
                    new SetOf<>("a", "c")
                )
            )
        );
    }

    @Test
    void addAll() {
        MatcherAssert.assertThat(
            "addAll(Collection) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2)
            ).addAll(new SetOf<>(3, 4)),
            new Throws<>(
                "#addAll(Collection): the set is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void addAllAgain() {
        MatcherAssert.assertThat(
            "addAll(Collection) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2)
            ).addAll(new SetOf<>(3, 4)),
            new Throws<>(
                "#addAll(Collection): the set is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void retainAll() {
        MatcherAssert.assertThat(
            "retainAll(Collection) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2, 3)
            ).retainAll(new SetOf<>(1, 3)),
            new Throws<>(
                "#retainAll(Collection): the set is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void removeAll() {
        MatcherAssert.assertThat(
            "removeAll(Collection) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>(1, 2, 3)
            ).removeAll(new SetOf<>(1, 3)),
            new Throws<>(
                "#removeAll(Collection): the set is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void clear() {
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void addsWithReturn() {
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void removesWithReturn() {
        MatcherAssert.assertThat(
            "remove(Object) must throw exception",
            () -> new org.cactoos.set.Immutable<>(
                new SetOf<>("a", "b")
            ).remove("b"),
            new Throws<>(
                "#remove(Object): the set is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void notEqualsToObjectOfAnotherType() {
        MatcherAssert.assertThat(
            "must not equal to object of another type",
            new org.cactoos.set.Immutable<>(new SetOf<>()),
            new IsNot<>(new IsEqual<>(new Object()))
        );
    }

    @Test
    void notEqualsToListWithDifferentElements() {
        MatcherAssert.assertThat(
            "must not equal to List with different elements",
            new org.cactoos.set.Immutable<>(new SetOf<>(1, 2)),
            new IsNot<>(new IsEqual<>(new SetOf<>(1, 0)))
        );
    }

    @Test
    void isEqualToItself() {
        final Set<Integer>
            set = new org.cactoos.set.Immutable<>(new SetOf<>(1, 2));
        MatcherAssert.assertThat(
            "must be equal to itself",
            set,
            new IsEqual<>(set)
        );
    }

    @Test
    void isEqualToListWithTheSameElements() {
        MatcherAssert.assertThat(
            "must be equal to Set with the same elements",
            new org.cactoos.set.Immutable<>(new SetOf<>(1, 2)),
            new IsEqual<>(new SetOf<>(1, 2))
        );
    }

    @Test
    void equalToEmptyImmutable() {
        MatcherAssert.assertThat(
            "empty Immutable must be equal to empty Immutable",
            new org.cactoos.set.Immutable<>(new SetOf<>()),
            new IsEqual<>(new org.cactoos.set.Immutable<>(new SetOf<>()))
        );
    }

    @Test
    void hashes() {
        MatcherAssert.assertThat(
            "hashCode() must be equal to hashCode of the corresponding Set",
            new org.cactoos.set.Immutable<>(new SetOf<>(1, 2)).hashCode(),
            new IsEqual<>(
                new SetOf<>(1, 2).hashCode()
            )
        );
    }

    @Test
    void stringRepresentation() {
        MatcherAssert.assertThat(
            "toString() must be equal to toString of the corresponding Set",
            new org.cactoos.set.Immutable<>(new SetOf<>("a", "b", "c")).toString(),
            new IsEqual<>(new SetOf<>("a", "b", "c").toString())
        );
    }

    @Test
    void innerSetIsDecorated() {
        final Set<String> strings = new HashSet<>(Arrays.asList("a", "b", "c"));
        final Set<String> immutable = new org.cactoos.set.Immutable<>(strings);
        strings.add("d");
        MatcherAssert.assertThat(
            "Must reflect inner set in the decorator",
            immutable,
            new IsEqual<>(strings)
        );
    }

    @Test
    void returnsIteratorWithUnsupportedRemove() {
        MatcherAssert.assertThat(
            "Must return an iterator that does not support remove()",
            () -> {
                final Iterator<String> iterator =
                    new org.cactoos.set.Immutable<>(new SetOf<>("one")).iterator();
                iterator.next();
                iterator.remove();
                return true;
            },
            new Throws<>(UnsupportedOperationException.class)
        );
    }
}
