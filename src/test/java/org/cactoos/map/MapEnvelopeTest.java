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
package org.cactoos.map;

import java.util.Map;
import org.cactoos.func.FuncOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.llorllale.cactoos.matchers.MatcherOf;

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

    /**
     * A rule for handling an exception.
     */
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void putThrowsException() {
        this.exception.expect(UnsupportedOperationException.class);
        this.exception.expectMessage(
            "#put() is not supported, it's a read-only map"
        );
        MatcherAssert.assertThat(
            "put method did not throw exception",
            new MapNoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<Integer, Integer>(0, -1)
                )
            ),
            new MatcherOf<>(
                new FuncOf<>(
                    (map) -> map.put(2, 2),
                    true
                ))
        );
    }

    @Test
    public void removeThrowsException() {
        this.exception.expect(UnsupportedOperationException.class);
        this.exception.expectMessage(
            "#remove() is not supported, it's a read-only map"
        );
        MatcherAssert.assertThat(
            "remove method did not throw exception",
            new MapNoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ),
            new MatcherOf<>(
                new FuncOf<>(
                    (map) -> map.remove(0),
                    true
                ))
        );
    }

    @Test
    public void putAllThrowsException() {
        this.exception.expect(UnsupportedOperationException.class);
        this.exception.expectMessage(
            "#putAll() is not supported, it's a read-only map"
        );
        MatcherAssert.assertThat(
            "putAll method did not throw exception",
            new MapNoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ),
            new MatcherOf<>(
                new FuncOf<>(
                    (map) -> map.putAll(new MapOf<Integer, Integer>()),
                    true
                ))
        );
    }

    @Test
    public void clearThrowsException() {
        this.exception.expect(UnsupportedOperationException.class);
        this.exception.expectMessage(
            "#clear() is not supported, it's a read-only map"
        );
        MatcherAssert.assertThat(
            "clear method did not throw exception",
            new MapNoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ),
            new MatcherOf<>(
                new FuncOf<>(
                    Map::clear,
                    true
                ))
        );
    }

    @Test
    public void mapIsEmptyTrue() {
        MatcherAssert.assertThat(
            "#isEmpty() returns false for empty map",
            new MapNoNulls<>(
                new MapOf<Integer, Integer>()
            ).isEmpty(),
            Matchers.is(true)
        );
    }

    @Test
    public void mapIsEmptyFalse() {
        MatcherAssert.assertThat(
            "#isEmpty() returns true for not empty map",
            new MapNoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 0)
                )
            ).isEmpty(),
            Matchers.is(false)
        );
    }

    @Test
    public void mapContainsKeyTrue() {
        MatcherAssert.assertThat(
            "contains key returns false with exist key",
            new MapNoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 0)
                )
            ).containsKey(1),
            Matchers.is(true)
        );
    }

    @Test
    public void mapContainsKeyFalse() {
        MatcherAssert.assertThat(
            "contains key returns true with absent key",
            new MapNoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 0)
                )
            ).containsKey(0),
            Matchers.is(false)
        );
    }

    @Test
    public void mapContainsValueTrue() {
        MatcherAssert.assertThat(
            "contains value returns false with exist value",
            new MapNoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 0)
                )
            ).containsValue(0),
            Matchers.is(true)
        );
    }

    @Test
    public void mapContainsValueFalse() {
        MatcherAssert.assertThat(
            "contains value returns true with absent value",
            new MapNoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 0)
                )
            ).containsValue(1),
            Matchers.is(false)
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

    @Test(expected = NullPointerException.class)
    public void equalFailsOnNull() {
        final MapEntry<String, String> first =
            new MapEntry<>("key3", "value3");
        final MapEntry<String, String> second =
            new MapEntry<>("key4", null);
        MatcherAssert.assertThat(
            "Map allows null values, but shouldn't",
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

    @Test(expected = NullPointerException.class)
    public void hashCodeFailsOnNull() {
        final MapEntry<String, String> first =
            new MapEntry<>("key10", "value10");
        final MapEntry<String, String> second =
            new MapEntry<>("key11", null);
        new MapOf<String, String>(first, second).hashCode();
    }
}
