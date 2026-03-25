/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link ScalarOfSupplier}.
 * @since 0.47
 */
final class ScalarOfSupplierTest {

    @Test
    void createsScalarFromSupplier() {
        MatcherAssert.assertThat(
            "must hold the same value as given by supplier",
            new ScalarOfSupplier<>(() -> 1),
            new HasValue<>(1)
        );
    }
}
