/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link IsEmpty}.
 * @since 0.47
 */
final class IsEmptyTest {

    @Test
    void determinesEmptyText() {
        MatcherAssert.assertThat(
            "Must determine empty text",
            new IsEmpty(new TextOf("")),
            new HasValue<>(Boolean.TRUE)
        );
    }

    @Test
    void determinesNonemptyText() {
        MatcherAssert.assertThat(
            "Must not detect non-empty text",
            new IsEmpty(new TextOf("abc")),
            new HasValue<>(Boolean.FALSE)
        );
    }

    @Test
    void determinesBlankText() {
        MatcherAssert.assertThat(
            "Must not detect blank text",
            new IsEmpty(new TextOf(" ")),
            new HasValue<>(Boolean.FALSE)
        );
    }
}
