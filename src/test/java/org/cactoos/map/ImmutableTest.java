/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
 * @since 0.67
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
final class ImmutableTest {

    @Test
    void size() {
        new Assertion<>(
            "size() must be equals to original",
            new Immutable<>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            ).size(),
            new IsEqual<>(2)
        ).affirm();
    }

    @Test
    void isEmpty() {
        new Assertion<>(
            "isEmpty() must return false for non-empty map",
            new Immutable<>(
                new MapOf<>(new MapEntry<>("x", 10))
            ).isEmpty(),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void isEmptyOnEmptyMap() {
        new Assertion<>(
            "isEmpty() must return true for empty map",
            new Immutable<>(new MapOf<String, Integer>()).isEmpty(),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    void containsKey() {
        new Assertion<>(
            "containsKey() must find existing key",
            new Immutable<>(
                new MapOf<String, Integer>(
                    new MapEntry<>("one", 1),
                    new MapEntry<>("two", 2)
                )
            ).containsKey("two"),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    void containsKeyReturnsFalseForMissing() {
        new Assertion<>(
            "containsKey() must return false for missing key",
            new Immutable<>(
                new MapOf<>(new MapEntry<>("one", 1))
            ).containsKey("two"),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void containsValue() {
        new Assertion<>(
            "containsValue() must find existing value",
            new Immutable<>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 100),
                    new MapEntry<>("b", 200)
                )
            ).containsValue(200),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    void get() {
        new Assertion<>(
            "get() must return correct value",
            new Immutable<>(
                new MapOf<>(
                    new MapEntry<>("key", "value")
                )
            ).get("key"),
            new IsEqual<>("value")
        ).affirm();
    }

    @Test
    void getReturnsNullForMissingKey() {
        new Assertion<>(
            "get() must return null for missing key",
            new Immutable<>(
                new MapOf<>(new MapEntry<>("a", 1))
            ).get("missing"),
            new IsEqual<>(null)
        ).affirm();
    }

    @Test
    void put() {
        new Assertion<>(
            "put() must throw exception",
            () -> new Immutable<>(
                new MapOf<>(new MapEntry<>("a", 1))
            ).put("b", 2),
            new Throws<>(
                "#put(K,V): the map is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void remove() {
        new Assertion<>(
            "remove() must throw exception",
            () -> new Immutable<>(
                new MapOf<>(new MapEntry<>("a", 1))
            ).remove("a"),
            new Throws<>(
                "#remove(Object): the map is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void putAll() {
        new Assertion<>(
            "putAll() must throw exception",
            () -> {
                new Immutable<>(
                    new MapOf<>(new MapEntry<>("a", 1))
                ).putAll(new MapOf<>(new MapEntry<>("b", 2)));
                return new Object();
            },
            new Throws<>(
                "#putAll(Map): the map is read-only",
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
                    new MapOf<>(new MapEntry<>("a", 1))
                ).clear();
                return new Object();
            },
            new Throws<>(
                "#clear(): the map is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void keySet() {
        new Assertion<>(
            "keySet() must return all keys",
            new Immutable<>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            ).keySet(),
            new HasValues<>("a", "b")
        ).affirm();
    }

    @Test
    void keySetIsImmutable() {
        new Assertion<>(
            "keySet() must be immutable",
            () -> new Immutable<>(
                new MapOf<>(new MapEntry<>("a", 1))
            ).keySet().add("b"),
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void keySetIteratorIsImmutable() {
        new Assertion<>(
            "keySet().iterator() must not support remove()",
            () -> {
                final Set<String> keys = new Immutable<>(
                    new MapOf<>(new MapEntry<>("a", 1))
                ).keySet();
                final Iterator<String> iter = keys.iterator();
                iter.next();
                iter.remove();
                return new Object();
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void values() {
        new Assertion<>(
            "values() must return all values",
            new Immutable<>(
                new MapOf<String, Integer>(
                    new MapEntry<>("x", 10),
                    new MapEntry<>("y", 20)
                )
            ).values(),
            new HasValues<>(10, 20)
        ).affirm();
    }

    @Test
    void valuesIsImmutable() {
        new Assertion<>(
            "values() must be immutable",
            () -> new Immutable<>(
                new MapOf<>(new MapEntry<>("a", 1))
            ).values().add(2),
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void valuesIteratorIsImmutable() {
        new Assertion<>(
            "values().iterator() must not support remove()",
            () -> {
                final Collection<Integer> vals = new Immutable<>(
                    new MapOf<>(new MapEntry<>("a", 1))
                ).values();
                final Iterator<Integer> iter = vals.iterator();
                iter.next();
                iter.remove();
                return new Object();
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void entrySet() {
        new Assertion<>(
            "entrySet() must return correct number of entries",
            new Immutable<>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            ).entrySet().size(),
            new IsEqual<>(2)
        ).affirm();
    }

    @Test
    void entrySetIsImmutable() {
        new Assertion<>(
            "entrySet() must be immutable",
            () -> {
                new Immutable<>(
                    new MapOf<>(new MapEntry<>("a", 1))
                ).entrySet().clear();
                return new Object();
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void entrySetIteratorIsImmutable() {
        new Assertion<>(
            "entrySet().iterator() must not support remove()",
            () -> {
                final Set<Map.Entry<String, Integer>> entries = new Immutable<>(
                    new MapOf<>(new MapEntry<>("a", 1))
                ).entrySet();
                final Iterator<Map.Entry<String, Integer>> iter =
                    entries.iterator();
                iter.next();
                iter.remove();
                return new Object();
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void entrySetValueIsImmutable() {
        new Assertion<>(
            "entrySet entry's setValue() must throw exception",
            () -> {
                final Set<Map.Entry<String, Integer>> entries = new Immutable<>(
                    new MapOf<>(new MapEntry<>("a", 1))
                ).entrySet();
                final Map.Entry<String, Integer> entry =
                    entries.iterator().next();
                entry.setValue(999);
                return new Object();
            },
            new Throws<>(
                "#setValue(V): the entry is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void worksWithHashMapBackedEntries() {
        final Map<String, Integer> hashmap = new HashMap<>();
        hashmap.put("a", 1);
        hashmap.put("b", 2);
        new Assertion<>(
            "entrySet entry's setValue() must throw even for HashMap entries",
            () -> {
                final Set<Map.Entry<String, Integer>> entries =
                    new Immutable<>(hashmap).entrySet();
                final Map.Entry<String, Integer> entry =
                    entries.iterator().next();
                entry.setValue(999);
                return new Object();
            },
            new Throws<>(
                "#setValue(V): the entry is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void testEquals() {
        new Assertion<>(
            "must be equal to map with same entries",
            new Immutable<>(
                new MapOf<>(new MapEntry<>("a", 1))
            ),
            new IsEqual<>(new MapOf<>(new MapEntry<>("a", 1)))
        ).affirm();
    }

    @Test
    void testEqualsItself() {
        final Map<String, Integer> map = new Immutable<>(
            new MapOf<>(new MapEntry<>("a", 1))
        );
        new Assertion<>(
            "must be equal to itself",
            map,
            new IsEqual<>(map)
        ).affirm();
    }

    @Test
    void notEqualsToMapWithDifferentEntries() {
        new Assertion<>(
            "must not be equal to map with different entries",
            new Immutable<>(new MapOf<>(new MapEntry<>("a", 1))),
            new IsNot<>(
                new IsEqual<>(new MapOf<>(new MapEntry<>("b", 2)))
            )
        ).affirm();
    }

    @Test
    void notEqualsToObjectOfAnotherType() {
        new Assertion<>(
            "must not be equal to object of another type",
            new Immutable<>(new MapOf<>(new MapEntry<>("a", 1))),
            new IsNot<>(new IsEqual<>("not a map"))
        ).affirm();
    }

    @Test
    void testHashCode() {
        new Assertion<>(
            "hashCode() must be equal to hashCode of equivalent map",
            new Immutable<>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            ).hashCode(),
            new IsEqual<>(
                new MapOf<String, Integer>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                ).hashCode()
            )
        ).affirm();
    }

    @Test
    void testToString() {
        new Assertion<>(
            "toString() must match original map's toString()",
            new Immutable<>(
                new MapOf<>(new MapEntry<>("x", 42))
            ).toString(),
            new IsEqual<>(
                new MapOf<>(new MapEntry<>("x", 42)).toString()
            )
        ).affirm();
    }

    @Test
    void reflectsChangesToUnderlyingMap() {
        final Map<String, Integer> mutable = new HashMap<>();
        mutable.put("a", 1);
        final Map<String, Integer> immutable = new Immutable<>(mutable);
        mutable.put("b", 2);
        new Assertion<>(
            "must reflect changes made to the underlying map",
            immutable.size(),
            new IsEqual<>(2)
        ).affirm();
    }

    @Test
    void emptyMapEqualsEmptyMap() {
        new Assertion<>(
            "empty immutable must equal empty immutable",
            new Immutable<>(new MapOf<String, Integer>()),
            new IsEqual<>(new Immutable<>(new MapOf<String, Integer>()))
        ).affirm();
    }

    // ===== Additional tests for iteration correctness =====

    @Test
    void iteratesThroughAllEntries() {
        final Map<String, Integer> map = new Immutable<>(
            new MapOf<String, Integer>(
                new MapEntry<>("a", 1),
                new MapEntry<>("b", 2),
                new MapEntry<>("c", 3)
            )
        );
        int count = 0;
        int sum = 0;
        for (final Map.Entry<String, Integer> entry : map.entrySet()) {
            count = count + 1;
            sum = sum + entry.getValue();
        }
        new Assertion<>(
            "must iterate through all 3 entries",
            count,
            new IsEqual<>(3)
        ).affirm();
        new Assertion<>(
            "sum of values must be 6",
            sum,
            new IsEqual<>(6)
        ).affirm();
    }

    @Test
    void iteratesThroughAllKeys() {
        final Map<String, Integer> map = new Immutable<>(
            new MapOf<String, Integer>(
                new MapEntry<>("x", 10),
                new MapEntry<>("y", 20)
            )
        );
        int count = 0;
        for (final String key : map.keySet()) {
            count = count + 1;
        }
        new Assertion<>(
            "must iterate through all 2 keys",
            count,
            new IsEqual<>(2)
        ).affirm();
    }

    @Test
    void iteratesThroughAllValues() {
        final Map<String, Integer> map = new Immutable<>(
            new MapOf<String, Integer>(
                new MapEntry<>("p", 100),
                new MapEntry<>("q", 200)
            )
        );
        int sum = 0;
        for (final Integer val : map.values()) {
            sum = sum + val;
        }
        new Assertion<>(
            "sum of iterated values must be 300",
            sum,
            new IsEqual<>(300)
        ).affirm();
    }

    @Test
    void entryIterationReadsCorrectKeyAndValue() {
        final Map<String, Integer> map = new Immutable<>(
            new MapOf<>(new MapEntry<>("thekey", 42))
        );
        final Map.Entry<String, Integer> entry = map.entrySet().iterator().next();
        new Assertion<>(
            "entry key must be correct",
            entry.getKey(),
            new IsEqual<>("thekey")
        ).affirm();
        new Assertion<>(
            "entry value must be correct",
            entry.getValue(),
            new IsEqual<>(42)
        ).affirm();
    }

    // ===== Additional tests for view mutation attempts =====

    @Test
    void keySetRemoveAllThrows() {
        new Assertion<>(
            "keySet().removeAll() must throw exception",
            () -> {
                new Immutable<>(
                    new MapOf<>(new MapEntry<>("a", 1))
                ).keySet().removeAll(java.util.Arrays.asList("a"));
                return new Object();
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void keySetRetainAllThrows() {
        new Assertion<>(
            "keySet().retainAll() must throw exception",
            () -> {
                new Immutable<>(
                    new MapOf<>(new MapEntry<>("a", 1))
                ).keySet().retainAll(java.util.Arrays.asList("b"));
                return new Object();
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void keySetClearThrows() {
        new Assertion<>(
            "keySet().clear() must throw exception",
            () -> {
                new Immutable<>(
                    new MapOf<>(new MapEntry<>("a", 1))
                ).keySet().clear();
                return new Object();
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void valuesRemoveThrows() {
        new Assertion<>(
            "values().remove() must throw exception",
            () -> new Immutable<>(
                new MapOf<>(new MapEntry<>("a", 1))
            ).values().remove(1),
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void valuesClearThrows() {
        new Assertion<>(
            "values().clear() must throw exception",
            () -> {
                new Immutable<>(
                    new MapOf<>(new MapEntry<>("a", 1))
                ).values().clear();
                return new Object();
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void entrySetAddThrows() {
        new Assertion<>(
            "entrySet().add() must throw exception",
            () -> new Immutable<>(
                new MapOf<>(new MapEntry<>("a", 1))
            ).entrySet().add(new MapEntry<>("b", 2)),
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void entrySetRemoveThrows() {
        new Assertion<>(
            "entrySet().remove() must throw exception",
            () -> new Immutable<>(
                new MapOf<>(new MapEntry<>("a", 1))
            ).entrySet().remove(new MapEntry<>("a", 1)),
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    // ===== Additional tests for lookup edge cases =====

    @Test
    void containsValueReturnsFalseForMissing() {
        new Assertion<>(
            "containsValue() must return false for missing value",
            new Immutable<>(
                new MapOf<>(new MapEntry<>("a", 1))
            ).containsValue(999),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void keySetContainsWorks() {
        new Assertion<>(
            "keySet().contains() must find existing key",
            new Immutable<>(
                new MapOf<>(new MapEntry<>("hello", 42))
            ).keySet().contains("hello"),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    void valuesContainsWorks() {
        new Assertion<>(
            "values().contains() must find existing value",
            new Immutable<>(
                new MapOf<>(new MapEntry<>("key", 123))
            ).values().contains(123),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    void entrySetContainsWorks() {
        new Assertion<>(
            "entrySet().contains() must find existing entry",
            new Immutable<>(
                new MapOf<>(new MapEntry<>("k", 1))
            ).entrySet().contains(new MapEntry<>("k", 1)),
            new IsEqual<>(true)
        ).affirm();
    }

    // ===== Test that contents remain stable after failed modification =====

    @Test
    void contentsUnchangedAfterFailedPut() {
        final Map<String, Integer> map = new Immutable<>(
            new MapOf<>(new MapEntry<>("a", 1))
        );
        try {
            map.put("b", 2);
        } catch (final UnsupportedOperationException ex) {
            // expected
        }
        new Assertion<>(
            "size must remain 1 after failed put",
            map.size(),
            new IsEqual<>(1)
        ).affirm();
        new Assertion<>(
            "original entry must still exist after failed put",
            map.get("a"),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void contentsUnchangedAfterFailedRemove() {
        final Map<String, Integer> map = new Immutable<>(
            new MapOf<>(new MapEntry<>("x", 99))
        );
        try {
            map.remove("x");
        } catch (final UnsupportedOperationException ex) {
            // expected
        }
        new Assertion<>(
            "entry must still exist after failed remove",
            map.get("x"),
            new IsEqual<>(99)
        ).affirm();
    }

    @Test
    void contentsUnchangedAfterFailedClear() {
        final Map<String, Integer> map = new Immutable<>(
            new MapOf<String, Integer>(
                new MapEntry<>("a", 1),
                new MapEntry<>("b", 2)
            )
        );
        try {
            map.clear();
        } catch (final UnsupportedOperationException ex) {
            // expected
        }
        new Assertion<>(
            "size must remain 2 after failed clear",
            map.size(),
            new IsEqual<>(2)
        ).affirm();
    }
}
