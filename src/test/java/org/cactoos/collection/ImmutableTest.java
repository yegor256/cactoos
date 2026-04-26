/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.collection;

import java.util.Collection;
import java.util.List;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Immutable}.
 * @since 1.16
 */
@SuppressWarnings("PMD.TooManyMethods")
final class ImmutableTest {

    @Test
    void size() {
        MatcherAssert.assertThat(
            "size() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2)
            ).size(),
            new IsEqual<>(2)
        );
    }

    @Test
    void isEmpty() {
        MatcherAssert.assertThat(
            "isEmpty() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2)
            ).isEmpty(),
            new IsEqual<>(
                new ListOf<>(1, 2).isEmpty()
            )
        );
    }

    @Test
    void iterator() {
        MatcherAssert.assertThat(
            "iterator() is equal to original",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).iterator(),
            new HasValues<>(1, 2)
        );
    }

    @Test
    void contains() {
        MatcherAssert.assertThat(
            "contains() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2)
            ).contains(1),
            new IsEqual<>(
                new ListOf<>(1, 2).contains(1)
            )
        );
    }

    @Test
    void toArray() {
        MatcherAssert.assertThat(
            "toArray() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2)
            ).toArray(),
            new IsEqual<>(
                new ListOf<>(1, 2).toArray()
            )
        );
    }

    @Test
    void toArrayTyped() {
        MatcherAssert.assertThat(
            "toArray(T[]) must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).toArray(new Integer[0]),
            new IsEqual<>(
                new ListOf<>(1, 2, 3).toArray(new Integer[0])
            )
        );
    }

    @Test
    void add() {
        MatcherAssert.assertThat(
            "add() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).add(3),
            new Throws<>(
                "#add(): the collection is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void remove() {
        MatcherAssert.assertThat(
            "remove() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).remove(1),
            new Throws<>(
                "#remove(): the collection is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void containsAll() {
        final List<Integer> another = new ListOf<>(3, 4, 5);
        MatcherAssert.assertThat(
            "containsAll() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).containsAll(another),
            new IsEqual<>(
                new ListOf<>(1, 2, 3).containsAll(another)
            )
        );
    }

    @Test
    void addAll() {
        MatcherAssert.assertThat(
            "addAll() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).addAll(new ListOf<>(3, 4)),
            new Throws<>(
                "#addAll(): the collection is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void removeAll() {
        MatcherAssert.assertThat(
            "removeAll() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).removeAll(new ListOf<>(2, 3)),
            new Throws<>(
                "#removeAll(): the collection is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void retainAll() {
        MatcherAssert.assertThat(
            "retainAll() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).retainAll(new ListOf<>(1)),
            new Throws<>(
                "#retainAll(): the collection is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void clear() {
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void stringRepresentation() {
        MatcherAssert.assertThat(
            "toString() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).toString(),
            new IsEqual<>(
                new ListOf<>(1, 2, 3).toString()
            )
        );
    }

    @Test
    void hashes() {
        MatcherAssert.assertThat(
            "hashCode() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).hashCode(),
            new IsEqual<>(
                new ListOf<>(1, 2, 3).hashCode()
            )
        );
    }

    @Test
    void equalsToOriginal() {
        final List<Integer> another = new ListOf<>(4, 5, 6);
        MatcherAssert.assertThat(
            "equals() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).equals(another),
            new IsEqual<>(
                new ListOf<>(1, 2, 3).equals(another)
            )
        );
    }

    @Test
    void equalsIsSymmetricWithPlainList() {
        final List<Integer> plain = new ListOf<>(1, 2, 3);
        final Collection<Integer> wrapper = new Immutable<>(
            new ListOf<>(1, 2, 3)
        );
        MatcherAssert.assertThat(
            "equals() must be symmetric between wrapper and plain list",
            wrapper.equals(plain),
            new IsEqual<>(plain.equals(wrapper))
        );
    }
}
