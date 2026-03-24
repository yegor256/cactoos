/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.ScalarOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;

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
            new Flattened(
                new ScalarOf<>(() -> new TextOf("txt"))
            ),
            new IsText("txt")
        );
    }

    @Test
    void flattensTextThatChanges() {
        final Iterable<Text> txts = new IterableOf<>(
            new TextOf("txt1"),
            new TextOf("txt2")
        );
        MatcherAssert.assertThat(
            "must flatten a scalar that changes",
            new Flattened(
                new ScalarOf<>(txts.iterator()::next)
            ),
            new AllOf<Text>(new Mapped<>(IsText::new, txts))
        );
    }
}
