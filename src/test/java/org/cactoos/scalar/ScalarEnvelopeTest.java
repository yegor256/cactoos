/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link ScalarEnvelope}.
 *
 * @since 0.41
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 */
final class ScalarEnvelopeTest {

    @Test
    void envelopeDelegatesCalls() {
        MatcherAssert.assertThat(
            "must delegate calls to apply",
            new Static(1),
            new HasValue<>(1)
        );
    }

    @Test
    void propagatesException() {
        MatcherAssert.assertThat(
            "must not alter the exception thrown by original Scalar",
            new ScalarEnvelope<Integer>(
                () -> {
                    throw new UnsupportedOperationException("ok");
                }
            ) {
            },
            new Throws<>(
                new IsEqual<>("ok"),
                UnsupportedOperationException.class
            )
        );
    }

    private static final class Static extends ScalarEnvelope<Integer> {
        Static(final int result) {
            super(() -> result);
        }
    }
}
