/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.object.HasToString;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Sticky}.
 * @since 0.47
 */
final class StickyTest {
    @Test
    void cachesResult() {
        final Text sticky = new Sticky(new Randomized());
        MatcherAssert.assertThat(
            "must be the same",
            sticky,
            new IsText(sticky)
        );
    }

    @Test
    void equalsItself() {
        final Text random = new Randomized();
        final Text sticky = new Sticky(random);
        MatcherAssert.assertThat(
            "must be the same as itself",
            sticky,
            new AllOf<Text>(
                new IsEqual<>(sticky),
                new IsNot<>(new IsEqual<>(random))
            )
        );
    }

    @Test
    void hasProperToString() {
        final String str = "Hello";
        MatcherAssert.assertThat(
            "must have toString method",
            new Sticky(() -> str),
            new HasToString<>(new IsEqual<>(str))
        );
    }
}
