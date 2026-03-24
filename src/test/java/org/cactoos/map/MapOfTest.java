/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.io.IOException;
import java.util.Map;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Constant;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringStartsWith;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasEntry;

/**
 * Test case for {@link MapOf}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class MapOfTest {

    @Test
    void createsMapFromSingleKeyAndValue() {
        MatcherAssert.assertThat(
            "Must create a map from single key and value",
            new MapOf<>(-1, 1),
            new HasEntry<>(-1, 1)
        );
    }

    @Test
    void behavesAsMap() {
        MatcherAssert.assertThat(
            "Must behave as a map",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            ),
            new BehavesAsMap<>(1, 1)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void createsMapFromIterable() {
        MatcherAssert.assertThat(
            "Must behave as a map when created from iterable",
            new MapOf<>(
                new IterableOf<>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            ),
            new BehavesAsMap<>(1, 1)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void createsMapFromIterableVariable() {
        final Iterable<Map.Entry<Integer, Integer>> list = new IterableOf<>(
            new MapEntry<>(0, -1),
            new MapEntry<>(1, 1)
        );
        MatcherAssert.assertThat(
            "Must behave as a map when created from iterable",
            new MapOf<Integer, Integer>((Iterable) list),
            new BehavesAsMap<>(1, 1)
        );
    }

    @Test
    void convertsIterableToMap() {
        MatcherAssert.assertThat(
            "Must convert iterable to map",
            new MapOf<Integer, String>(
                new MapEntry<>(0, "hello, "),
                new MapEntry<>(1, "world!")
            ),
            new IsMapContaining<>(
                new IsEqual<>(0),
                new StringStartsWith("hello")
            )
        );
    }

    @Test
    void createsMapWithFunctions() {
        MatcherAssert.assertThat(
            "Must create a map with functions as values",
            new MapOf<Integer, Scalar<Boolean>>(
                new MapEntry<>(0, () -> true),
                new MapEntry<>(
                    1,
                    () -> {
                        throw new IOException("oops");
                    }
                )
            ),
            new IsMapContaining<>(new IsEqual<>(0), new IsAnything<>())
        );
    }

    @Test
    void integersToString() {
        MatcherAssert.assertThat(
            "Must convert map of integers to string",
            new MapOf<Integer, Integer>(
                new MapEntry<>(-1, 0),
                new MapEntry<>(1, 2)
            ).toString(),
            new IsEqual<>("{-1=0, 1=2}")
        );
    }

    @Test
    void mapsToString() {
        MatcherAssert.assertThat(
            "Must convert map op maps to string",
            new MapOf<Integer, Map<String, String>>(
                new MapEntry<Integer, Map<String, String>>(
                    -1,
                    new MapOf<String, String>(
                        new MapEntry<>("first", "second"),
                        new MapEntry<>("4", "7")
                    )
                ),
                new MapEntry<Integer, Map<String, String>>(
                    1,
                    new MapOf<String, String>(
                        new MapEntry<>("green", "red"),
                        new MapEntry<>("2.7", "3.1")
                    )
                )
            ).toString(),
            new IsEqual<>("{-1={4=7, first=second}, 1={green=red, 2.7=3.1}}")
        );
    }

    @Test
    void emptyToString() {
        MatcherAssert.assertThat(
            "Can't convert empty map to string",
            new MapOf<Integer, Map<String, String>>().toString(),
            new IsEqual<>("{}")
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void createsMapFromMapAndSingleKeyAndValue() {
        MatcherAssert.assertThat(
            "Must create a map from map and single key and value",
            new MapOf<>(
                new MapOf<>(
                    new MapEntry<>(0, 0)
                ),
                -1, 1
            ),
            new AllOf<>(
                new HasEntry<>(0, 0),
                new HasEntry<>(-1, 1)
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void createsMapFromMapAndMapEntries() {
        MatcherAssert.assertThat(
            "Must create a map from map and map entries",
            new MapOf<Integer, Integer>(
                new MapOf<>(
                    new MapEntry<>(0, 0)
                ),
                new MapEntry<>(1, 1)
            ),
            new AllOf<>(
                new HasEntry<>(0, 0),
                new HasEntry<>(1, 1)
            )
        );
    }

    @Test
    void createsMapFromFunctionsAndIterable() {
        MatcherAssert.assertThat(
            "Must create a map from functions and iterable.",
            new MapOf<>(
                new FuncOf<>(new Constant<>(0)),
                new FuncOf<>(new Constant<>(0)),
                new IterableOf<>(0)
            ),
            new HasEntry<>(0, 0)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void createsMapFromMapFunctionsAndIterable() {
        MatcherAssert.assertThat(
            "Must create a map from map, functions and iterable.",
            new MapOf<>(
                new FuncOf<>(new Constant<>(0)),
                new FuncOf<>(new Constant<>(0)),
                new MapOf<>(
                    new MapEntry<>(1, 1)
                ),
                new IterableOf<>(0)
            ),
            new AllOf<>(
                new HasEntry<>(0, 0),
                new HasEntry<>(1, 1)
            )
        );
    }

}
