/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link False}.
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class FalseTest {

    @Test
    void asValue() {
        MatcherAssert.assertThat(
            "Must return false",
            new False().value(),
            new IsEqual<>(false)
        );
    }

    @Test
    void asScalar() {
        MatcherAssert.assertThat(
            "Must be false",
            new False(),
            new HasValue<>(false)
        );
    }
}
