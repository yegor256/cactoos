/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasEntry;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link Sticky}.
 *
 * @since 0.56
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class StickyTest {

    @Test
    void behavesAsMap() {
        MatcherAssert.assertThat(
            "Must behave as a map",
            new Sticky<Integer, Integer>(
                new MapEntry<>(0, -1),
                new MapEntry<>(1, 1)
            ),
            new BehavesAsMap<>(1, 1)
        );
    }

    @Test
    void cachesEntriesFromUnderlyingMap() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Map<String, Integer> map = new Sticky<>(
            new MapOf<>(
                item -> new MapEntry<>(
                    item,
                    counter.incrementAndGet()
                ),
                new ListOf<>("ключ", "κλειδί", "鍵")
            )
        );
        MatcherAssert.assertThat(
            "Must cache entries and return same values on repeated access",
            map.get("ключ"),
            new IsEqual<>(map.get("ключ"))
        );
    }

    @Test
    void preservesEntryOrderFromVarargs() {
        MatcherAssert.assertThat(
            "Must preserve entries from varargs constructor",
            new Sticky<String, Integer>(
                new MapEntry<>("première", 1),
                new MapEntry<>("deuxième", 2),
                new MapEntry<>("troisième", 3)
            ),
            new AllOf<>(
                new HasEntry<>("première", 1),
                new HasEntry<>("deuxième", 2),
                new HasEntry<>("troisième", 3)
            )
        );
    }

    @Test
    void extendsExistingMapWithVarargs() {
        MatcherAssert.assertThat(
            "Must extend existing map with varargs entries",
            new Sticky<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, 100),
                    new MapEntry<>(1, 200)
                ),
                new MapEntry<>(2, 300),
                new MapEntry<>(3, 400)
            ),
            new AllOf<>(
                new HasEntry<>(0, 100),
                new HasEntry<>(2, 300)
            )
        );
    }

    @Test
    void createsMapFromKeyValueFunctions() {
        MatcherAssert.assertThat(
            "Must create map using key and value functions",
            new Sticky<>(
                item -> item,
                item -> item * item,
                new ListOf<>(2, 3, 5, 7)
            ),
            new AllOf<>(
                new HasEntry<>(2, 4),
                new HasEntry<>(3, 9),
                new HasEntry<>(5, 25)
            )
        );
    }

    @Test
    void extendsMapWithKeyValueFunctions() {
        MatcherAssert.assertThat(
            "Must extend map with entries from key-value functions",
            new Sticky<>(
                item -> item,
                item -> item + 10,
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, 99)
                ),
                new ListOf<>(1, 2, 3)
            ),
            new AllOf<>(
                new HasEntry<>(0, 99),
                new HasEntry<>(1, 11),
                new HasEntry<>(2, 12)
            )
        );
    }

    @Test
    void createsMapFromEntryFunction() {
        MatcherAssert.assertThat(
            "Must create map using entry function",
            new Sticky<>(
                item -> new MapEntry<>(item, item.length()),
                new ListOf<>("α", "αβ", "αβγ")
            ),
            new AllOf<>(
                new HasEntry<>("α", 1),
                new HasEntry<>("αβ", 2),
                new HasEntry<>("αβγ", 3)
            )
        );
    }

    @Test
    void extendsMapWithEntryFunction() {
        MatcherAssert.assertThat(
            "Must extend map with entries from entry function",
            new Sticky<>(
                item -> new MapEntry<>(item, item * 2),
                new MapOf<Integer, Integer>(
                    new MapEntry<>(-1, -2)
                ),
                new ListOf<>(10, 20, 30)
            ),
            new AllOf<>(
                new HasEntry<>(-1, -2),
                new HasEntry<>(10, 20),
                new HasEntry<>(20, 40)
            )
        );
    }

    @Test
    void createsMapFromIterable() {
        MatcherAssert.assertThat(
            "Must create map from iterable of entries",
            new Sticky<>(
                new MapOf<String, Integer>(
                    new MapEntry<>("key-one", 1),
                    new MapEntry<>("key-two", 2)
                )
            ),
            new AllOf<>(
                new HasEntry<>("key-one", 1),
                new HasEntry<>("key-two", 2)
            )
        );
    }

    @Test
    void extendsMapWithIterable() {
        MatcherAssert.assertThat(
            "Must extend map with entries from iterable",
            new Sticky<>(
                new MapOf<String, Integer>(
                    new MapEntry<>("base", 0)
                ),
                new MapEntry<>("extra", 1)
            ),
            new AllOf<>(
                new HasEntry<>("base", 0),
                new HasEntry<>("extra", 1)
            )
        );
    }

    @Test
    void copiesFromExistingMap() {
        MatcherAssert.assertThat(
            "Must copy entries from existing map",
            new Sticky<>(
                new MapOf<String, String>(
                    new MapEntry<>("first-key", "first-value"),
                    new MapEntry<>("second-key", "second-value")
                )
            ),
            new AllOf<>(
                new HasEntry<>("first-key", "first-value"),
                new HasEntry<>("second-key", "second-value")
            )
        );
    }

    @Test
    void ignoresSubsequentChangesToSourceCollection() {
        final AtomicInteger size = new AtomicInteger(1);
        final Map<Integer, Integer> map = new Sticky<>(
            new MapOf<Integer, Integer>(
                entry -> new MapEntry<>(entry, entry),
                () -> Collections.nCopies(
                    size.incrementAndGet(),
                    42
                ).iterator()
            )
        );
        final int initial = map.size();
        MatcherAssert.assertThat(
            "Must ignore changes to underlying collection after caching",
            map.size(),
            new IsEqual<>(initial)
        );
    }

    @Test
    void worksInMultipleThreads() {
        MatcherAssert.assertThat(
            "Must behave correctly when accessed from multiple threads",
            map -> {
                MatcherAssert.assertThat(
                    "Must behave as map in thread",
                    map,
                    new BehavesAsMap<>(1, 1)
                );
                return true;
            },
            new RunsInThreads<>(
                new Sticky<Integer, Integer>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            )
        );
    }
}
