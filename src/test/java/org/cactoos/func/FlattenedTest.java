/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.scalar.BoolOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsApplicable;

/**
 * Tests for {@link Flattened}.
 *
 * @since 0.49
 */
final class FlattenedTest {
    @Test
    void flattens() {
        MatcherAssert.assertThat(
            "must flatten",
            new Flattened<>(
                new FuncOf<>(BoolOf::new)
            ),
            new IsApplicable<>("true", true)
        );
    }
}
