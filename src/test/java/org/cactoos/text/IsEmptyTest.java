/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link IsEmpty}.
 * @since 0.47
 */
final class IsEmptyTest {

    @Test
    void determinesEmptyText() {
        new Assertion<>(
            "Must determine empty text",
            new IsEmpty(new TextOf("")),
            new HasValue<>(Boolean.TRUE)
        ).affirm();
    }

    @Test
    void determinesNonemptyText() {
        new Assertion<>(
            "Must not detect non-empty text",
            new IsEmpty(new TextOf("abc")),
            new HasValue<>(Boolean.FALSE)
        ).affirm();
    }

    @Test
    void determinesBlankText() {
        new Assertion<>(
            "Must not detect blank text",
            new IsEmpty(new TextOf(" ")),
            new HasValue<>(Boolean.FALSE)
        ).affirm();
    }
}
