/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.scalar.BoolOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
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

    @Test
    void acceptsFuncOfScalarOfSubtype() throws Exception {
        MatcherAssert.assertThat(
            "Must accept func of scalar of a subtype",
            new Flattened<CharSequence, CharSequence>(
                (Object input) -> () -> input.toString()
            ).apply("abc"),
            new IsEqual<>("abc")
        );
    }
}
