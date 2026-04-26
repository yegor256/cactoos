/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.HashMap;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsIterableContaining;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsEntry;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link NoNulls}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ParameterNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class NoNullsTest {

    @Test
    void getSize() {
        MatcherAssert.assertThat(
            "Can't calculate size",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            ).size(),
            new IsEqual<>(2)
        );
    }

    @Test
    void isEmptyTrue() {
        MatcherAssert.assertThat(
            "Can't get is empty true",
            new NoNulls<>(
                new MapOf<Integer, Integer>()
            ).isEmpty(),
            new IsEqual<>(true)
        );
    }

    @Test
    void isEmptyFalse() {
        MatcherAssert.assertThat(
            "Can't get is empty false",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).isEmpty(),
            new IsEqual<>(false)
        );
    }

    @Test
    void containsKeyTrue() {
        MatcherAssert.assertThat(
            "Can't get #containsKey() true",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsKey(0),
            new IsEqual<>(true)
        );
    }

    @Test
    void containsKeyFalse() {
        MatcherAssert.assertThat(
            "Can't get #containsKey() false",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsKey(-1),
            new IsEqual<>(false)
        );
    }

    @Test
    void containsKeyException() {
        MatcherAssert.assertThat(
            "Could no throw an IllegalStateException for null key",
            () -> new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsKey(null),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void containsValueFalse() {
        MatcherAssert.assertThat(
            "Can't get #containsValue() false",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsValue(0),
            new IsEqual<>(false)
        );
    }

    @Test
    void containsValueTrue() {
        MatcherAssert.assertThat(
            "Can't get #containsValue() true",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsValue(-1),
            new IsEqual<>(true)
        );
    }

    @Test
    void containsValueException() {
        MatcherAssert.assertThat(
            "Can't get #containsValue() exception",
            () -> new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsValue(null),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void getValue() {
        MatcherAssert.assertThat(
            "Can't call #get()",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).get(0),
            new IsEqual<>(-1)
        );
    }

    @Test
    void getValueByNullKey() {
        MatcherAssert.assertThat(
            "Can't call #get() with key null",
            () -> new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).get(null),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void getValueByNullValue() {
        MatcherAssert.assertThat(
            "Can't call #get() with null value",
            () -> new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, null)
                )
            ).get(0),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void put() {
        MatcherAssert.assertThat(
            "Can't call #put()",
            new NoNulls<>(
                NoNullsTest.mapOf(0, 0)
            ),
            new PutUpdatesValues<>(0, 1)
        );
    }

    @Test
    void putWithNullKey() {
        MatcherAssert.assertThat(
            "Can't call #put() with Null key",
            () -> new NoNulls<Integer, Integer>(
                NoNullsTest.mapOf(0, 0)
            ).put(null, 1),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void putWithNullValue() {
        MatcherAssert.assertThat(
            "Can't call #put() with Null value",
            () -> new NoNulls<Integer, Integer>(
                NoNullsTest.mapOf(0, 0)
            ).put(1, null),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    @Disabled
    void putWithNoMapping() {
        MatcherAssert.assertThat(
            "Can't call #put() with no mapping",
            new NoNulls<Integer, Integer>(
                NoNullsTest.mapOf(0, 0)
            ),
            new PutUpdatesValues<Integer, Integer>(1, 1)
        );
    }

    @Test
    void remove() {
        MatcherAssert.assertThat(
            "Can't call #remove()",
            new NoNulls<Integer, Integer>(
                NoNullsTest.mapOf(0, 0)
            ),
            new RemoveDeletesValues<Integer, Integer>(0, 0)
        );
    }

    @Test
    void removeWithNullKey() {
        MatcherAssert.assertThat(
            "Can't call #remove() with Null key",
            () -> new NoNulls<Integer, Integer>(
                NoNullsTest.mapOf(0, 0)
            ).remove(null),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    @Disabled
    void removeWithNoMapping() {
        MatcherAssert.assertThat(
            "Can't call #remove() with no mapping",
            new NoNulls<Integer, Integer>(
                NoNullsTest.mapOf(0, 0)
            ),
            new RemoveDeletesValues<Integer, Integer>(1, 0)
        );
    }

    @Test
    void putAll() {
        MatcherAssert.assertThat(
            "Can't call #putAll()",
            new NoNulls<Integer, Integer>(
                NoNullsTest.mapOf(0, 0)
            ),
            new PutAllUpdatesValues<Integer, Integer>(0, 1)
        );
    }

    @Test
    void clear() {
        MatcherAssert.assertThat(
            "Can't call #clear()",
            new NoNulls<Integer, Integer>(
                NoNullsTest.mapOf(0, 0, 1, 1, 2, 2)
            ),
            new ClearDeletesAllValues<Integer, Integer>()
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void entrySet() {
        MatcherAssert.assertThat(
            "Must call #entrySet()",
            new NoNulls<Integer, Integer>(
                NoNullsTest.mapOf(1, 1, 0, 0)
            ).entrySet(),
            new AllOf<>(
                new IsIterableContaining<>(new IsEntry<>(1, 1)),
                new IsIterableContaining<>(new IsEntry<>(0, 0))
            )
        );
    }

    @Test
    void putThrowsErrorIfValueNull() {
        MatcherAssert.assertThat(
            "Should throws an error if value is null",
            () -> new NoNulls<Integer, Integer>(
                new HashMap<>()
            ).put(1, null),
            new Throws<>(
                "Value at #put(1,V) is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void putThrowsErrorIfPreviousValueNull() {
        MatcherAssert.assertThat(
            "Should throws an error if previous value is null",
            () -> new NoNulls<>(
                NoNullsTest.mapWithNull(1)
            ).put(1, 2),
            new Throws<>(
                "Value returned by #put(1,2) is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void putThrowsErrorIfKeyIsNull() {
        MatcherAssert.assertThat(
            "Should throw an error if key is null",
            () -> new NoNulls<Integer, Integer>(
                new HashMap<>()
            ).put(null, 1),
            new Throws<>(
                "Key at #put(K,1) is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void removeThrowsErrorIfKeyIsNull() {
        MatcherAssert.assertThat(
            "Should throw an error if key is null",
            () -> new NoNulls<Integer, Integer>(
                new HashMap<>()
            ).remove(null),
            new Throws<>(
                "Key at #remove(K) is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void removeThrowsErrorIfValueIsNull() {
        MatcherAssert.assertThat(
            "Should throw an error if removed value is null",
            () -> new NoNulls<>(
                NoNullsTest.mapWithNull(1)
            ).remove(1),
            new Throws<>(
                "Value returned by #remove(1) is NULL",
                IllegalStateException.class
            )
        );
    }

    private static Map<Integer, Integer> mapOf(final int key, final int val) {
        final Map<Integer, Integer> map = new HashMap<>(1);
        map.put(key, val);
        return map;
    }

    private static Map<Integer, Integer> mapOf(
        final int first, final int fval,
        final int second, final int sval
    ) {
        final Map<Integer, Integer> map = new HashMap<>(2);
        map.put(first, fval);
        map.put(second, sval);
        return map;
    }

    private static Map<Integer, Integer> mapOf(
        final int first, final int fval,
        final int second, final int sval,
        final int third, final int tval
    ) {
        final Map<Integer, Integer> map = new HashMap<>(3);
        map.put(first, fval);
        map.put(second, sval);
        map.put(third, tval);
        return map;
    }

    private static Map<Integer, Integer> mapWithNull(final int key) {
        final Map<Integer, Integer> map = new HashMap<>(1);
        map.put(key, null);
        return map;
    }
}
