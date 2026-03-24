/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.io.InputOf;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasProperty;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link PropertiesOf}.
 *
 * @since 0.7
 */
final class PropertiesOfTest {

    @Test
    void readsStringContent() {
        MatcherAssert.assertThat(
            "Must read properties from an input string",
            new PropertiesOf(
                "foo=Hello, world!\nbar=works fine?\n"
            ),
            new HasValue<>(
                new HasProperty("foo", "Hello, world!")
            )
        );
    }

    @Test
    void readsInputContent() {
        MatcherAssert.assertThat(
            "Must read properties from an input",
            new PropertiesOf(
                new InputOf("greet=Hello, inner world!\nask=works fine?\n")
            ),
            new HasValue<>(
                new HasProperty("greet", "Hello, inner world!")
            )
        );
    }

    @Test
    void convertsMapToProperties() {
        MatcherAssert.assertThat(
            "Must convert map to properties",
            new PropertiesOf(
                new MapOf<Integer, String>(
                    new MapEntry<>(0, "hello, world"),
                    new MapEntry<>(1, "how are you?")
                )
            ),
            new HasValue<>(
                new HasProperty("0", "hello, world")
            )
        );
    }

    @Test
    void convertsMapEntriesToProperties() {
        MatcherAssert.assertThat(
            "Must convert map entries to properties",
            new PropertiesOf(
                new MapEntry<>(0, "hello world"),
                new MapEntry<>(1, "How are you?")
            ),
            new HasValue<>(
                new HasProperty("0", "hello world")
            )
        );
    }

}
