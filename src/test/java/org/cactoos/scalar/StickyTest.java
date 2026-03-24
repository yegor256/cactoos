/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.security.SecureRandom;
import org.cactoos.Scalar;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Sticky}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class StickyTest {

    @Test
    void cachesScalarResults() throws Exception {
        final Scalar<Integer> scalar = new Sticky<>(
            () -> new SecureRandom().nextInt()
        );
        MatcherAssert.assertThat(
            "must compute value only once",
            scalar.value() + scalar.value(),
            new IsEqual<>(scalar.value() + scalar.value())
        );
    }

}
