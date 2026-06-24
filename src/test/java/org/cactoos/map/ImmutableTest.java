/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Immutable}.
 * @since 0.67
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.UnnecessaryLocalRule"})
final class ImmutableTest {

    @Test
    void size() {
        MatcherAssert.assertThat(
            "size() must be equals to original",
            new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            ).size(),
            new IsEqual<>(2)
        );
    }

    @Test
    void isEmpty() {
        MatcherAssert.assertThat(
            "isEmpty() must be equals to original",
            new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1)
                )
            ).isEmpty(),
            new IsEqual<>(false)
        );
    }

    @Test
    void containsKey() {
        MatcherAssert.assertThat(
            "containsKey() must be equals to original",
            new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            ).containsKey("b"),
            new IsEqual<>(true)
        );
    }

    @Test
    void containsValue() {
        MatcherAssert.assertThat(
            "containsValue() must be equals to original",
            new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            ).containsValue(2),
            new IsEqual<>(true)
        );
    }

    @Test
    void get() {
        MatcherAssert.assertThat(
            "get() must be equals to original",
            new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            ).get("a"),
            new IsEqual<>(1)
        );
    }

    @Test
    void put() {
        MatcherAssert.assertThat(
            "put(K,V) must throw exception",
            () -> new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1)
                )
            ).put("b", 2),
            new Throws<>(
                "#put(K,V): the map is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void remove() {
        MatcherAssert.assertThat(
            "remove(Object) must throw exception",
            () -> new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1)
                )
            ).remove("a"),
            new Throws<>(
                "#remove(Object): the map is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void putAll() {
        MatcherAssert.assertThat(
            "putAll(Map) must throw exception",
            () -> {
                new Immutable<String, Integer>(
                    new MapOf<String, Integer>(
                        new MapEntry<>("a", 1)
                    )
                ).putAll(
                    new MapOf<String, Integer>(
                        new MapEntry<>("b", 2)
                    )
                );
                return new Object();
            },
            new Throws<>(
                "#putAll(Map): the map is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void clear() {
        MatcherAssert.assertThat(
            "clear() must throw exception",
            () -> {
                new Immutable<String, Integer>(
                    new MapOf<String, Integer>(
                        new MapEntry<>("a", 1)
                    )
                ).clear();
                return new Object();
            },
            new Throws<>(
                "#clear(): the map is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void keySet() {
        MatcherAssert.assertThat(
            "keySet() must contain original keys",
            () -> new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            ).keySet().iterator(),
            new HasValues<>("a", "b")
        );
    }

    @Test
    void values() {
        MatcherAssert.assertThat(
            "values() must contain original values",
            () -> new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            ).values().iterator(),
            new HasValues<>(1, 2)
        );
    }

    @Test
    void entrySetSize() {
        MatcherAssert.assertThat(
            "entrySet() size must be equals to original",
            new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            ).entrySet().size(),
            new IsEqual<>(2)
        );
    }

    @Test
    void keySetIsImmutable() {
        MatcherAssert.assertThat(
            "keySet().add() must throw exception",
            () -> new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1)
                )
            ).keySet().add("b"),
            new Throws<>(UnsupportedOperationException.class)
        );
    }

    @Test
    void valuesAreImmutable() {
        MatcherAssert.assertThat(
            "values().clear() must throw exception",
            () -> {
                new Immutable<String, Integer>(
                    new MapOf<String, Integer>(
                        new MapEntry<>("a", 1)
                    )
                ).values().clear();
                return new Object();
            },
            new Throws<>(UnsupportedOperationException.class)
        );
    }

    @Test
    void entrySetIsImmutable() {
        MatcherAssert.assertThat(
            "entrySet().clear() must throw exception",
            () -> {
                new Immutable<String, Integer>(
                    new MapOf<String, Integer>(
                        new MapEntry<>("a", 1)
                    )
                ).entrySet().clear();
                return new Object();
            },
            new Throws<>(UnsupportedOperationException.class)
        );
    }

    @Test
    void entrySetValueIsBlocked() {
        MatcherAssert.assertThat(
            "Entry.setValue() must throw exception",
            () -> {
                final Iterator<Map.Entry<String, Integer>> iterator =
                    new Immutable<String, Integer>(
                        new MapOf<String, Integer>(
                            new MapEntry<>("a", 1)
                        )
                    ).entrySet().iterator();
                iterator.next().setValue(2);
                return new Object();
            },
            new Throws<>(
                "#setValue(V): the entry is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void notEqualsToObjectOfAnotherType() {
        MatcherAssert.assertThat(
            "must not equal to object of another type",
            new Immutable<String, Integer>(new MapOf<String, Integer>()),
            new IsNot<>(new IsEqual<>(new Object()))
        );
    }

    @Test
    void isEqualToItself() {
        final Map<String, Integer> map = new Immutable<>(
            new MapOf<String, Integer>(
                new MapEntry<>("a", 1)
            )
        );
        MatcherAssert.assertThat(
            "must be equal to itself",
            map,
            new IsEqual<>(map)
        );
    }

    @Test
    void isEqualToMapWithSameEntries() {
        MatcherAssert.assertThat(
            "must be equal to Map with the same entries",
            new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            ),
            new IsEqual<>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            )
        );
    }

    @Test
    void hashes() {
        MatcherAssert.assertThat(
            "hashCode() must be equal to hashCode of the corresponding Map",
            new Immutable<String, Integer>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1)
                )
            ).hashCode(),
            new IsEqual<>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1)
                ).hashCode()
            )
        );
    }

    @Test
    void stringRepresentation() {
        final Map<String, Integer> origin = new MapOf<String, Integer>(
            new MapEntry<>("a", 1)
        );
        MatcherAssert.assertThat(
            "toString() must be equal to toString of the corresponding Map",
            new Immutable<>(origin).toString(),
            new IsEqual<>(origin.toString())
        );
    }

    @Test
    void innerMapIsDecorated() {
        final Map<String, Integer> mutable = new HashMap<>();
        mutable.put("a", 1);
        final Map<String, Integer> immutable = new Immutable<>(mutable);
        mutable.put("b", 2);
        MatcherAssert.assertThat(
            "Must reflect inner map in the decorator",
            immutable,
            new IsEqual<>(mutable)
        );
    }

    @Test
    void returnsIteratorWithUnsupportedRemove() {
        MatcherAssert.assertThat(
            "Must return an iterator that does not support remove()",
            () -> {
                final Iterator<Map.Entry<String, Integer>> iterator =
                    new Immutable<String, Integer>(
                        new MapOf<String, Integer>(
                            new MapEntry<>("one", 1)
                        )
                    ).entrySet().iterator();
                iterator.next();
                iterator.remove();
                return true;
            },
            new Throws<>(UnsupportedOperationException.class)
        );
    }
}
