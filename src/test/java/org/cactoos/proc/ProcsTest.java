/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.List;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Procs}.
 *
 * @since 1.0
 */
final class ProcsTest {

    @Test
    void runsAllProcsOnSameArgument() throws Exception {
        final List<Integer> visited = new ListOf<>();
        new Procs<Integer>(
            visited::add,
            input -> visited.add(input * 2),
            input -> visited.add(input * 3)
        ).exec(5);
        MatcherAssert.assertThat(
            "Must run all procs on the same argument, in order",
            visited,
            new IsEqual<>(new ListOf<>(5, 10, 15))
        );
    }

    @Test
    void throwsFromFailingProc() {
        MatcherAssert.assertThat(
            "Must throw the exception of the first proc that fails",
            () -> {
                new Procs<Integer>(
                    input -> { },
                    input -> {
                        throw new IllegalStateException("Intentionally failed");
                    },
                    input -> { }
                ).exec(1);
                return 1;
            },
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void stopsAtFirstFailure() throws Exception {
        final List<Integer> visited = new ListOf<>();
        try {
            new Procs<Integer>(
                visited::add,
                input -> {
                    throw new IllegalStateException("Intentionally failed");
                },
                input -> visited.add(-1)
            ).exec(1);
        } catch (final IllegalStateException failure) {
            MatcherAssert.assertThat(
                String.format(
                    "Must not run procs after %s was thrown",
                    failure.getMessage()
                ),
                visited,
                new IsEqual<>(new ListOf<>(1))
            );
        }
    }
}
