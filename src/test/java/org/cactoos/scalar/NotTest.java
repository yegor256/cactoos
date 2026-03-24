/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Not}.
 *
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class NotTest {

    @Test
    void trueToFalse() throws Exception {
        MatcherAssert.assertThat(
            "Must be changed from true to false",
            new Not(new True()).value(),
            new IsEqual<>(new False().value())
        );
    }

    @Test
    void falseToTrue() throws Exception {
        MatcherAssert.assertThat(
            "Must be changed from false to true",
            new Not(new False()).value(),
            new IsEqual<>(new True().value())
        );
    }
}
