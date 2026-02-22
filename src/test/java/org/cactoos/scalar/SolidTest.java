/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link Solid}.
 *
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SolidTest {

    @Test
    void cachesScalarResults() throws Exception {
        final Scalar<Integer> scalar = new Solid<>(
            () -> new SecureRandom().nextInt()
        );
        new Assertion<>(
            "must compute value only once",
            scalar.value() + scalar.value(),
            new IsEqual<>(scalar.value() + scalar.value())
        ).affirm();
    }

    @Test
    void worksInThreads() {
        new Assertion<>(
            "must work well in multiple threads",
            scalar -> {
                new Assertion<>(
                    "must compute value once",
                    scalar,
                    new HasValue<>(scalar.value())
                ).affirm();
                return true;
            },
            new RunsInThreads<>(
                new Unchecked<>(
                    new Solid<>(() -> new ListOf<>(1, 2))
                )
            )
        ).affirm();
    }

    @Test
    void cachesNullValue() throws Exception {
        final AtomicInteger calls = new AtomicInteger(0);
        final Scalar<Object> scalar = new Solid<>(
            () -> {
                calls.incrementAndGet();
                return null;
            }
        );
        scalar.value();
        scalar.value();
        new Assertion<>(
            "must compute null value only once",
            calls.get(),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void returnsNullValue() throws Exception {
        new Assertion<>(
            "must return cached null value",
            new Solid<>(() -> null).value(),
            new IsNull<>()
        ).affirm();
    }

    @Test
    void cachesNullValueInThreads() throws Exception {
        final AtomicInteger calls = new AtomicInteger(0);
        final Scalar<Object> solid = new Solid<>(
            () -> {
                calls.incrementAndGet();
                return null;
            }
        );
        new Assertion<>(
            "not compute null value only once in multiple threads",
            scalar -> solid.value() == null,
            new RunsInThreads<>(new Unchecked<>(solid::value))
        ).affirm();
        new Assertion<>(
            "must compute null value only once",
            calls.get(),
            new IsEqual<>(1)
        ).affirm();
    }
}
