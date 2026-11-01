/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.func.FuncOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Strict}.
 *
 * @since 0.56
 */
final class StrictTest {

    @Test
    void throwsExceptionOnFailure() {
        MatcherAssert.assertThat(
            "Must throw the provided exception on rule failure",
            new Strict<>(
                new False(),
                value -> new Equals<>(value, new True()),
                new FuncOf<>(() -> new IllegalArgumentException("not a true"))
            ),
            new Throws<>("not a true", IllegalArgumentException.class)
        );
    }

    @Test
    void returnsIncapsulatedValue() throws Exception {
        MatcherAssert.assertThat(
            "Must return the original value on rule match",
            new Strict<>(
                () -> 123,
                value -> new Equals<>(value, () -> 123),
                new FuncOf<>(() -> new IllegalArgumentException("unexpected"))
            ).value(),
            new IsEqual<>(123)
        );
    }
}
