/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.IsText;

/**
* Tests for {@link Flattened}.
*
* @since 0.49
*/
final class FlattenedTest {
    @Test
    void flattens() {
        final Text txt = new TextOf("txt");
        final Scalar<Text> sclr = new Constant<>(txt);
        new Assertion<>(
            "must flatten",
            new Flattened<>(
                new ScalarOf<>(() -> sclr)
            ),
            new HasValue<>(new IsText(txt))
        ).affirm();
    }
}
