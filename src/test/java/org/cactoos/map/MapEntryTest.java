/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link MapEntry}.
 *
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class MapEntryTest {

    @Test
    void getKey() {
        final String key = "hello";
        final String value = "world";
        MatcherAssert.assertThat(
            "Can't get key in the map entry",
            new MapEntry<>(key, value).getKey(),
            new IsEqual<>(key)
        );
    }

    @Test
    void getValue() {
        final String key = "foo";
        final String value = "bar";
        MatcherAssert.assertThat(
            "Can't get value in the map entry",
            new MapEntry<>(key, value).getValue(),
            new IsEqual<>(value)
        );
    }

    @Test
    void cantSetValue() {
        MatcherAssert.assertThat(
            "Exception is expected on change operations",
            () -> new MapEntry<>("one", "two").setValue("three"),
            new Throws<>(UnsupportedOperationException.class)
        );
    }

    @Test
    void equalsTo() {
        final String key = "eo";
        final String value = "book";
        MatcherAssert.assertThat(
            "MapEntries are not equals",
            new MapEntry<>(key, value).equals(new MapEntry<>(key, value)),
            new IsEqual<>(true)
        );
    }

    @Test
    void equalsWithNullKey() {
        MatcherAssert.assertThat(
            "MapEntries with null keys are not equal",
            new MapEntry<>(null, "v").equals(new MapEntry<>(null, "v")),
            new IsEqual<>(true)
        );
    }

    @Test
    void equalsWithNullValue() {
        MatcherAssert.assertThat(
            "MapEntries with null values are not equal",
            new MapEntry<>("k", null).equals(new MapEntry<>("k", null)),
            new IsEqual<>(true)
        );
    }

    @Test
    void notEqualsWhenOneKeyIsNull() {
        MatcherAssert.assertThat(
            "MapEntries with different keys must not be equal",
            new MapEntry<>(null, "v").equals(new MapEntry<>("k", "v")),
            new IsEqual<>(false)
        );
    }

    @Test
    void compareHash() {
        MatcherAssert.assertThat(
            "the hash code are not equals",
            new MapEntry<>("elegant", "objects").hashCode(),
            new IsEqual<>(-1_673_632_025)
        );
    }

    @Test
    void toStringMethod() {
        MatcherAssert.assertThat(
            "ToString method returns unexpected value",
            new MapEntry<>("somekey", "somevalue").toString(),
            new IsEqual<>("somekey=somevalue")
        );
    }
}
