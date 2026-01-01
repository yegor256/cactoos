/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Constant}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ConstantTest {

    @Test
    void returnsGivenValue() {
        final String value = "Hello World";
        new Assertion<>(
            "Must return given value",
            new Constant<>(value),
            new HasValue<>(value)
        ).affirm();
    }

    @Test
    void alwaysReturnsSameValue() {
        final Constant<String> constant = new Constant<>("Good Bye!");
        new Assertion<>(
            "Must return same value",
            constant,
            new HasValue<>(constant.value())
        ).affirm();
    }
}
