/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link StartsWith}.
 * @since 0.44
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class StartsWithTest {

    @Test
    void emptyStartsWithEmpty() throws Exception {
        MatcherAssert.assertThat(
            "Empty is not prefix of empty",
            new StartsWith(
                new TextOf(""),
                ""
            ).value(),
            new IsTrue()
        );
    }

    @Test
    void textStartsWithEmpty() throws Exception {
        MatcherAssert.assertThat(
            "Empty is not prefix of any string",
            new StartsWith(
                "Any string",
                new TextOf("")
            ).value(),
            new IsTrue()
        );
    }

    @Test
    void textStartsWithPrefix() throws Exception {
        MatcherAssert.assertThat(
            "Foo is not prefix of FooBar",
            new StartsWith(
                "FooBar",
                "Foo"
            ).value(),
            new IsTrue()
        );
    }

    @Test
    void textStartsWithoutPrefix() throws Exception {
        MatcherAssert.assertThat(
            "Baz is prefix of FooBarBaz",
            new StartsWith(
                "FooBarBaz",
                "Baz"
            ).value(),
            new IsNot<>(new IsTrue())
        );
    }
}
