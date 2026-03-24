/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Scalar;
import org.cactoos.experimental.Threads;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
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
        MatcherAssert.assertThat(
            "must compute value only once",
            scalar.value() + scalar.value(),
            new IsEqual<>(scalar.value() + scalar.value())
        );
    }

    @Test
    void worksInThreads() {
        MatcherAssert.assertThat(
            "must work well in multiple threads",
            scalar -> {
                MatcherAssert.assertThat(
                    "must compute value once",
                    scalar,
                    new HasValue<>(scalar.value())
                );
                return true;
            },
            new RunsInThreads<>(
                new Unchecked<>(
                    new Solid<>(() -> new ListOf<>(1, 2))
                )
            )
        );
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
        MatcherAssert.assertThat(
            "must compute null value only once",
            calls.get(),
            new IsEqual<>(1)
        );
    }

    @Test
    void returnsNullValue() throws Exception {
        MatcherAssert.assertThat(
            "must return cached null value",
            new Solid<>(() -> null).value(),
            new IsNull<>()
        );
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
        MatcherAssert.assertThat(
            "not compute null value only once in multiple threads",
            scalar -> solid.value() == null,
            new RunsInThreads<>(new Unchecked<>(solid::value))
        );
        MatcherAssert.assertThat(
            "must compute null value only once",
            calls.get(),
            new IsEqual<>(1)
        );
    }

    @Test
    void cacheNullInMultipleThreads() throws Exception {
        final int threads = 100;
        final AtomicInteger calls = new AtomicInteger(0);
        final Scalar<Object> solid = new Solid<>(
            () -> {
                calls.incrementAndGet();
                return null;
            }
        );
        final List<Scalar<Object>> tasks = new ListOf<>();
        for (int idx = 0; idx < threads; ++idx) {
            tasks.add(
                () -> {
                    solid.value();
                    return null;
                }
            );
        }
        new LengthOf(
            new Threads<>(threads, tasks)
        ).value();
        MatcherAssert.assertThat(
            "must cache null value in multiple threads",
            calls.get(),
            new IsEqual<>(1)
        );
    }
}
