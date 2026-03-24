/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link True}.
 *
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class TrueTest {

    @Test
    void asValue() {
        MatcherAssert.assertThat(
            "Must be True",
            new True().value(),
            new IsTrue()
        );
    }
}
