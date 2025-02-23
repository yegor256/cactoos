/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.func.FuncOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Strict}.
 *
 * @since 0.56
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class StrictTest {

    @Test
    void throwsExceptionOnFailure() {
        new Assertion<>(
            "Must throw the provided exception on rule failure",
            new Strict<>(
                new False(),
                value -> new Equals<>(value, new True()),
                new FuncOf<>(() -> new IllegalArgumentException("not a true"))
            ),
            new Throws<>("not a true", IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void returnsIncapsulatedValue() throws Exception {
        new Assertion<>(
            "Must return the original value on rule match",
            new Strict<>(
                () -> 123,
                value -> new Equals<>(value, () -> 123),
                new FuncOf<>(() -> new IllegalArgumentException("unexpected"))
            ).value(),
            new IsEqual<>(123)
        ).affirm();
    }
}
