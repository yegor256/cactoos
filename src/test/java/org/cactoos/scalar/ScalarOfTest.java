/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.func.FuncOf;
import org.cactoos.proc.ProcOf;
import org.cactoos.proc.RunnableOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link ScalarOf}.
 *
 * @since 0.48
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class ScalarOfTest {
    @Test
    void worksWithCallable() {
        MatcherAssert.assertThat(
            "Must convert Callable into Scalar",
            new ScalarOf<>(new CallableOf<>(new Constant<>(1))),
            new HasValue<>(1)
        );
    }

    @Test
    void worksWithRunnable() {
        final AtomicReference<Object> done = new AtomicReference<>();
        MatcherAssert.assertThat(
            "Must convert Runnable into Scalar",
            new ScalarOf<>(
                new RunnableOf(
                    () -> done.set(true)
                ),
                1
            ),
            new Satisfies<>(
                scalar -> scalar.value().equals(1) && done.get().equals(true)
            )
        );
    }

    @Test
    void worksWithFunc() {
        MatcherAssert.assertThat(
            "Must convert Func into Scalar",
            new ScalarOf<>(new FuncOf<>(input -> input), 1),
            new HasValue<>(1)
        );
    }

    @Test
    void worksWithProc() {
        final AtomicReference<Object> done = new AtomicReference<>();
        MatcherAssert.assertThat(
            "Must convert Proc into Scalar",
            new ScalarOf<>(
                new ProcOf<>(done::set),
                1,
                2
            ),
            new Satisfies<>(
                scalar -> scalar.value().equals(2) && done.get().equals(1)
            )
        );
    }

    @Test
    void worksWithLambda() {
        MatcherAssert.assertThat(
            "Must convert Lambda into Scalar",
            new ScalarOf<>(() -> 1),
            new HasValue<>(1)
        );
    }
}
