/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link ScalarOfSupplier}.
 * @since 0.47
 */
final class ScalarOfSupplierTest {

    @Test
    void createsScalarFromSupplier() {
        final Object obj = new Object();
        new Assertion<>(
            "must hold the same value as given by supplier",
            new ScalarOfSupplier<>(() -> obj),
            new HasValue<>(obj)
        ).affirm();
    }
}
