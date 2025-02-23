/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Binary}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class BinaryTest {

    @Test
    void conditionTrue() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Binary binary = new Binary(
            new True(),
            counter::incrementAndGet
        );
        new Assertion<>(
            "Binary has to return true",
            binary,
            new HasValue<>(true)
        ).affirm();
        final int expected = 1;
        new Assertion<>(
            "Binary has to invoke increment method",
            counter.get(),
            new IsEqual<>(expected)
        ).affirm();
    }

    @Test
    void conditionFalse() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Binary binary = new Binary(
            new False(),
            counter::incrementAndGet
        );
        new Assertion<>(
            "Binary has to return false",
            binary,
            new HasValue<>(false)
        ).affirm();
        final int expected = 0;
        new Assertion<>(
            "Binary must not to invoke increment method",
            counter.get(),
            new IsEqual<>(expected)
        ).affirm();
    }

    @Test
    void throwsException() {
        final String msg = "Custom exception message";
        final Binary binary = new Binary(
            new True(),
            () -> {
                throw new IllegalArgumentException(msg);
            }
        );
        new Assertion<>(
            "Binary has to throw exception",
            binary,
            new Throws<>(msg, IllegalArgumentException.class)
        ).affirm();
    }
}
