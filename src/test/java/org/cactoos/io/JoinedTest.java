/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Unit tests for {@link Joined}.
 * @since 0.36
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class JoinedTest {
    /**
     * Must join inputs in the given order.
     * @throws IOException If an error occurs
     */
    @Test
    void joinsOk() {
        new Assertion<>(
            "Cannot properly join inputs",
            new Joined(
                new InputOf("first"),
                new InputOf("second"),
                new InputOf("third")
            ),
            new HasContent("firstsecondthird")
        ).affirm();
    }

    /**
     * Must join inputs of the iterable in the given order.
     */
    @Test
    void fromIterable() {
        new Assertion<>(
            "Can't join iterable of inputs",
            new Joined(
                new IterableOf<>(
                    new InputOf("ab"),
                    new InputOf("cde"),
                    new InputOf("fghi")
                )
            ),
            new HasContent("abcdefghi")
        ).affirm();
    }
}
