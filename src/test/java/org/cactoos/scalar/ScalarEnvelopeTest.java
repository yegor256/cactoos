/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
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
        new Assertion<>(
            "must delegate calls to apply",
            new Static(1),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void propagatesException() {
        final String message = "ok";
        final Scalar<Integer> scalar = () -> {
            throw new UnsupportedOperationException(message);
        };
        new Assertion<>(
            "must not alter the exception thrown by original Scalar",
            new ScalarEnvelope<Integer>(scalar) {
            },
            new Throws<>(
                new IsEqual<>(message),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    private static final class Static extends ScalarEnvelope<Integer> {
        Static(final int result) {
            super(() -> result);
        }
    }
}
