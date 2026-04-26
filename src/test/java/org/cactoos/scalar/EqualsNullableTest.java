/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link EqualsNullable}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class EqualsNullableTest {

    @Test
    void nullEqualsNull() {
        MatcherAssert.assertThat(
            "Must return true for both null objects",
            new EqualsNullable(() -> null, () -> null),
            new HasValue<>(true)
        );
    }

    @Test
    void equalObjects() {
        MatcherAssert.assertThat(
            "Must return true for equal objects",
            new EqualsNullable(1, 1),
            new HasValue<>(true)
        );
    }

    @Test
    void notEquals() {
        MatcherAssert.assertThat(
            "Must return false for non equal objects",
            new EqualsNullable(1, 2),
            new HasValue<>(false)
        );
    }

    @Test
    void equalsObjectAndScalar() {
        MatcherAssert.assertThat(
            "Must return true for object and scalar with the same value",
            new EqualsNullable(1, () -> 1),
            new HasValue<>(true)
        );
    }

    @Test
    void equalsScalarAndObject() {
        MatcherAssert.assertThat(
            "Must return true for scalar and object with the same value",
            new EqualsNullable(() -> 1, 1),
            new HasValue<>(true)
        );
    }
}
