/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.List;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
            Matchers.contains(5, 10, 15)
        );
    }

    @Test
    void stopsAtFirstFailure() {
        final List<Integer> visited = new ListOf<>();
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new Procs<Integer>(
                visited::add,
                input -> {
                    throw new IllegalStateException("Intentionally failed");
                },
                input -> visited.add(-1)
            ).exec(1),
            "Must stop at the first proc that throws"
        );
        MatcherAssert.assertThat(
            "Must not run procs after the failure",
            visited,
            Matchers.contains(1)
        );
    }
}
