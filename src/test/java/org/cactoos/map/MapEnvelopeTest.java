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
package org.cactoos.map;

import java.util.HashMap;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link MapEnvelope}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 * @checkstyle DiamondOperatorCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class MapEnvelopeTest {

    @Test
    public void putThrowsException() {
        new Assertion<>(
            "put method must throw exception",
            () -> new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).put(2, 2),
            new Throws<>(
                "#put() is not supported, it's a read-only map",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void removeThrowsException() {
        new Assertion<>(
            "remove method did not throw exception",
            () -> new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).remove(0),
            new Throws<>(
                "#remove() is not supported, it's a read-only map",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void putAllThrowsException() {
        new Assertion<>(
            "putAll method must throw exception",
            () -> {
                new NoNulls<>(
                    new MapOf<Integer, Integer>(
                        new MapEntry<>(0, -1)
                    )
                ).putAll(new MapOf<Integer, Integer>());
                return 0;
            },
            new Throws<>(
                "#putAll() is not supported, it's a read-only map",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void clearThrowsException() {
        new Assertion<>(
            "clear method must throw exception",
            () -> {
                new NoNulls<>(
                    new MapOf<Integer, Integer>(
                        new MapEntry<>(0, -1)
                    )
                ).clear();
                return 0;
            },
            new Throws<>(
                "#clear() is not supported, it's a read-only map",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void mapIsEmptyTrue() {
        MatcherAssert.assertThat(
            "#isEmpty() returns false for empty map",
            new NoNulls<>(
                new MapOf<Integer, Integer>()
            ).isEmpty(),
            new IsEqual<>(true)
        );
    }

    @Test
    public void mapIsEmptyFalse() {
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
    public void mapContainsKeyTrue() {
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
    public void mapContainsKeyFalse() {
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
    public void mapContainsValueTrue() {
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
    public void mapContainsValueFalse() {
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
    public void mapEqualsToItself() {
        final MapOf<String, String> map =
            new MapOf<String, String>(new MapEntry<>("key", "value"));
        MatcherAssert.assertThat(
            "Map doesn't equal to itself",
            map,
            new IsEqual<>(map)
        );
    }

    @Test
    public void mapNotEqualsToAnotherClass() {
        final MapOf<String, String> map =
            new MapOf<String, String>(new MapEntry<>("key1", "value1"));
        MatcherAssert.assertThat(
            "Map equals to an instance of another type",
            map,
            new IsNot<>(
                new IsEqual<>("Totally different type")
            )
        );
    }

    @Test
    public void mapEqualsToMapWithSameEntries() {
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
    public void equalsDoesNotFailOnNulls() {
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
    public void mapNotEqualsToOtherWithDifferentKeys() {
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
    public void mapNotEqualsToOtherWithDifferentValues() {
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
    public void hashCodeDependsOnItems() {
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
    public void hashCodeDoesNotFailOnNulls() {
        final MapEntry<String, String> first =
            new MapEntry<>("key10", "value10");
        final MapEntry<String, String> second =
            new MapEntry<>("key11", null);
        new MapOf<String, String>(first, second).hashCode();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void emptyMapEnvelopeShouldBeEqualToEmptyDerivedMap() {
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
    public void mapEnvelopeShouldCompareDerivedClasses() {
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

    /**
     * Class derived from MapEnvelope to use in some tests.
     * @param <K> - key type
     * @param <V> - value type
     */
    private static class DerivedMapEnvelope<K, V> extends MapEnvelope<K, V> {
        DerivedMapEnvelope(final Map<K, V> content) {
            super(() -> content);
        }
    }
}
