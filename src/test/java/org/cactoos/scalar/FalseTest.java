/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link False}.
 *
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class FalseTest {

    @Test
    void asValue() {
        new Assertion<>(
            "Must return false",
            new False().value(),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void asScalar() {
        new Assertion<>(
            "Must be false",
            new False(),
            new HasValue<>(false)
        ).affirm();
    }
}
