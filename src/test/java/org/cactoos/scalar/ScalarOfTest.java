/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.func.FuncOf;
import org.cactoos.proc.ProcOf;
import org.cactoos.proc.RunnableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link ScalarOf}.
 *
 * @since 0.48
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class ScalarOfTest {
    @Test
    void worksWithCallable() {
        final Object obj = new Object();
        new Assertion<>(
            "Must convert Callable into Scalar",
            new ScalarOf<>(new CallableOf<>(new Constant<>(obj))),
            new HasValue<>(obj)
        ).affirm();
    }

    @Test
    void worksWithRunnable() {
        final Object obj = new Object();
        final Object result = new Object();
        final AtomicReference<Object> done = new AtomicReference<>();
        new Assertion<>(
            "Must convert Runnable into Scalar",
            new ScalarOf<>(
                new RunnableOf(
                    () -> {
                        done.set(result);
                    }
                ),
                obj
            ),
            new Satisfies<>(
                scalar -> {
                    final Object res = scalar.value();
                    return res.equals(obj) && done.get().equals(result);
                }
            )
        ).affirm();
    }

    @Test
    void worksWithFunc() {
        final Object ipt = new Object();
        new Assertion<>(
            "Must convert Func into Scalar",
            new ScalarOf<>(new FuncOf<>(input -> input), ipt),
            new HasValue<>(ipt)
        ).affirm();
    }

    @Test
    void worksWithProc() {
        final Object ipt = new Object();
        final Object result = new Object();
        final AtomicReference<Object> done = new AtomicReference<>();
        new Assertion<>(
            "Must convert Proc into Scalar",
            new ScalarOf<>(
                new ProcOf<>(
                    done::set
                ),
                ipt,
                result
            ),
            new Satisfies<>(
                scalar -> {
                    final Object res = scalar.value();
                    return res.equals(result) && done.get().equals(ipt);
                }
            )
        ).affirm();
    }

    @Test
    void worksWithLambda() {
        final Object obj = new Object();
        new Assertion<>(
            "Must convert Lambda into Scalar",
            new ScalarOf<>(() -> obj),
            new HasValue<>(obj)
        ).affirm();
    }
}
