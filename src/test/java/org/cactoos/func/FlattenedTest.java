/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.scalar.BoolOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsApplicable;

/**
 * Tests for {@link Flattened}.
 *
 * @since 0.49
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class FlattenedTest {
    @Test
    void flattens() {
        new Assertion<>(
            "must flatten",
            new Flattened<>(
                new FuncOf<>(BoolOf::new)
            ),
            new IsApplicable<>("true", true)
        ).affirm();
    }
}
