/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.HashMap;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsMapWithSize;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link MapEnvelope}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle DiamondOperatorCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class MapEnvelopeTest {

    @Test
    void mapIsEmptyTrue() {
        MatcherAssert.assertThat(
            "#isEmpty() returns false for empty map",
            new NoNulls<>(
                new MapOf<Integer, Integer>()
            ).isEmpty(),
            new IsEqual<>(true)
        );
    }

    @Test
    void mapIsEmptyFalse() {
        MatcherAssert.assertThat(
            "#isEmpty() returns true for not empty map",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 0)
                )
            ).isEmpty(),
            new IsEqual<>(false)
        );
    }

    @Test
    void mapContainsKeyTrue() {
        MatcherAssert.assertThat(
            "contains key returns false with exist key",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 0)
                )
            ).containsKey(1),
            new IsEqual<>(true)
        );
    }

    @Test
    void mapContainsKeyFalse() {
        MatcherAssert.assertThat(
            "contains key returns true with absent key",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 0)
                )
            ).containsKey(0),
            new IsEqual<>(false)
        );
    }

    @Test
    void mapContainsValueTrue() {
        MatcherAssert.assertThat(
            "contains value returns false with exist value",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 0)
                )
            ).containsValue(0),
            new IsEqual<>(true)
        );
    }

    @Test
    void mapContainsValueFalse() {
        MatcherAssert.assertThat(
            "contains value returns true with absent value",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 0)
                )
            ).containsValue(1),
            new IsEqual<>(false)
        );
    }

    @Test
    void mapEqualsToItself() {
        final MapOf<String, String> map =
            new MapOf<>(new MapEntry<>("key", "value"));
        MatcherAssert.assertThat(
            "Map doesn't equal to itself",
            map,
            new IsEqual<>(map)
        );
    }

    @Test
    void mapNotEqualsToAnotherClass() {
        final MapOf<String, String> map =
            new MapOf<>(new MapEntry<>("key1", "value1"));
        MatcherAssert.assertThat(
            "Map equals to an instance of another type",
            map,
            new IsNot<>(
                new IsEqual<>("Totally different type")
            )
        );
    }

    @Test
    void mapEqualsToMapWithSameEntries() {
        final String key = "key2";
        final String value = "value2";
        final MapEntry<String, String> input = new MapEntry<>(key, value);
        final MapEntry<String, String> expected = new MapEntry<>(key, value);
        MatcherAssert.assertThat(
            "Map doesn't equal to another map with same entries",
            new MapOf<String, String>(input),
            new IsEqual<>(new MapOf<String, String>(expected))
        );
    }

    @Test
    void equalsDoesNotFailOnNulls() {
        final MapEntry<String, String> first =
            new MapEntry<>("key3", "value3");
        final MapEntry<String, String> second =
            new MapEntry<>("key4", null);
        MatcherAssert.assertThat(
            "Map must allow null values",
            new MapOf<String, String>(first, second),
            new IsEqual<>(new MapOf<String, String>(first, second))
        );
    }

    @Test
    void mapNotEqualsToOtherWithDifferentKeys() {
        final String value = "value5";
        MatcherAssert.assertThat(
            "Map equals to another map with different keys",
            new MapOf<String, String>(new MapEntry<>("key5", value)),
            new IsNot<>(
                new IsEqual<>(
                    new MapOf<String, String>(
                        new MapEntry<>("key6", value)
                    )
                )
            )
        );
    }

    @Test
    void mapNotEqualsToOtherWithDifferentValues() {
        final String key = "key7";
        MatcherAssert.assertThat(
            "Map equals to another map with different values",
            new MapOf<String, String>(new MapEntry<>(key, "value7")),
            new IsNot<>(
                new IsEqual<>(
                    new MapOf<String, String>(
                        new MapEntry<>(key, "value8")
                    )
                )
            )
        );
    }

    @Test
    void hashCodeDependsOnItems() {
        final String key = "key9";
        final String value = "value9";
        final MapEntry<String, String> input = new MapEntry<>(key, value);
        final MapEntry<String, String> expected = new MapEntry<>(key, value);
        MatcherAssert.assertThat(
            "hashCode returns different results for same entries",
            new MapOf<String, String>(input).hashCode(),
            new IsEqual<>(new MapOf<String, String>(expected).hashCode())
        );
    }

    @Test
    void hashCodeDoesNotFailOnNulls() {
        final MapEntry<String, String> first =
            new MapEntry<>("key10", "value10");
        final MapEntry<String, String> second =
            new MapEntry<>("key11", null);
        new MapOf<String, String>(first, second).hashCode();
    }

    @Test
    @SuppressWarnings("unchecked")
    void emptyMapEnvelopeShouldBeEqualToEmptyDerivedMap() {
        final MapEnvelope<Integer, String> base = new MapOf<>();
        final DerivedMapEnvelope<Integer, String> derived =
            new DerivedMapEnvelope<>(new HashMap<>());
        new Assertion<>(
            "EmpBase and derived MapEnvelope which are empty should be equal.",
            base,
            new IsEqual<>(derived)
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void mapEnvelopeShouldCompareDerivedClasses() {
        final int key = 1;
        final String value = "one";
        final MapEntry<Integer, String> entry = new MapEntry<>(key, value);
        final MapEnvelope<Integer, String> base = new MapOf<>(entry);
        final Map<Integer, String> hashmap = new HashMap<>();
        hashmap.put(key, value);
        final DerivedMapEnvelope<Integer, String> derived =
            new DerivedMapEnvelope<>(hashmap);
        new Assertion<>(
            "Base and derived MapEnvelope of same content should be equal.",
            base,
            new IsEqual<>(derived)
        ).affirm();
    }

    @Test
    void putIsDelegated() {
        final Map<Integer, Integer> map = new DerivedMapEnvelope<>(
            new HashMap<>()
        );
        map.put(0, 1);
        new Assertion<>(
            "must contain element after #put()",
            map,
            new IsEqual<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, 1)
                )
            )
        ).affirm();
    }

    @Test
    void clearIsDelegated() {
        final Map<Integer, Integer> map = new DerivedMapEnvelope<>(
            new MapOf<Integer, Integer>(
                new MapEntry<>(0, 1)
            )
        );
        map.clear();
        new Assertion<>(
            "must be empty after #clear()",
            map,
            new IsMapWithSize<>(new IsEqual<>(0))
        ).affirm();
    }

    @Test
    void removeIsDelegated() {
        final Map<Integer, Integer> map = new DerivedMapEnvelope<>(
            new MapOf<Integer, Integer>(
                new MapEntry<>(0, 1)
            )
        );
        map.remove(0);
        new Assertion<>(
            "must be empty after #remove()",
            map,
            new IsMapWithSize<>(new IsEqual<>(0))
        ).affirm();
    }

    /**
     * Class derived from MapEnvelope to use in some tests.
     * @param <K> - key type
     * @param <V> - value type
     * @since 0.4
     */
    private static class DerivedMapEnvelope<K, V> extends MapEnvelope<K, V> {
        DerivedMapEnvelope(final Map<K, V> content) {
            super(content);
        }
    }
}
