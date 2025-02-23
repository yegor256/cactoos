/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link BoolOf}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class BoolOfTest {

    @Test
    void trueTest() {
        new Assertion<>(
            "Must be parsed string 'true'",
            new BoolOf("true"),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void falseTest() {
        new Assertion<>(
            "Must be parsed string 'false'",
            new BoolOf("false"),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void isFalseIfTextDoesNotRepresentABoolean() {
        new Assertion<>(
            "Must be parsed a non-boolean string",
            new BoolOf("abc"),
            new HasValue<>(false)
        ).affirm();
    }
}
