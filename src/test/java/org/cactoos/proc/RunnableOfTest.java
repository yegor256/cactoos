/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.scalar.ScalarOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link RunnableOf}.
 *
 * @since 0.2
 */
final class RunnableOfTest {

    @Test
    void convertsProcIntoRunnable() {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object obj = new Object();
        new Assertion<>(
            "Must execute Runnable with Proc",
            new RunnableOf(
                new ProcOf<>(
                    done::set
                ),
                obj
            ),
            new Satisfies<>(
                runnable -> {
                    runnable.run();
                    return done.get().equals(obj);
                }
            )
        ).affirm();
    }

    @Test
    void convertsScalarIntoRunnable() {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object obj = new Object();
        new Assertion<>(
            "Must execute Runnable with Scalar",
            new RunnableOf(
                new ScalarOf<>(
                    () -> {
                        done.set(obj);
                        return "discarded";
                    }
                )
            ),
            new Satisfies<>(
                runnable -> {
                    runnable.run();
                    return done.get().equals(obj);
                }
            )
        ).affirm();
    }

    @Test
    void convertsLambdaIntoRunnable() {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object obj = new Object();
        new Assertion<>(
            "Must execute Runnable with Lambda",
            new RunnableOf(
                () -> {
                    done.set(obj);
                }
            ),
            new Satisfies<>(
                runnable -> {
                    runnable.run();
                    return done.get().equals(obj);
                }
            )
        ).affirm();
    }

    @Test
    void convertsCallableIntoRunnable() {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object obj = new Object();
        new Assertion<>(
            "Must execute Runnable with Callable",
            new RunnableOf(
                (Callable<Void>) () -> {
                    done.set(obj);
                    return null;
                }
            ),
            new Satisfies<>(
                runnable -> {
                    runnable.run();
                    return done.get().equals(obj);
                }
            )
        ).affirm();
    }

}
