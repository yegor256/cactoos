/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link StartsWith}.
 *
 * @since 0.44
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class StartsWithTest {

    @Test
    void emptyStartsWithEmpty() throws Exception {
        new Assertion<>(
            "Empty is not prefix of empty",
            new StartsWith(
                new TextOf(""),
                ""
            ).value(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void textStartsWithEmpty() throws Exception {
        new Assertion<>(
            "Empty is not prefix of any string",
            new StartsWith(
                "Any string",
                new TextOf("")
            ).value(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void textStartsWithPrefix() throws Exception {
        new Assertion<>(
            "Foo is not prefix of FooBar",
            new StartsWith(
                "FooBar",
                "Foo"
            ).value(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void textStartsWithoutPrefix() throws Exception {
        new Assertion<>(
            "Baz is prefix of FooBarBaz",
            new StartsWith(
                "FooBarBaz",
                "Baz"
            ).value(),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

}
