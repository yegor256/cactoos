/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Text;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Tests for {@link Flattened}.
 * @since 0.49
 */
final class FlattenedTest {

    @Test
    void flattens() {
        final Text txt = new TextOf("txt");
        MatcherAssert.assertThat(
            "must flatten",
            new Flattened<>(
                new ScalarOf<>(() -> new Constant<>(txt))
            ),
            new HasValue<>(new IsText(txt))
        );
    }
}
