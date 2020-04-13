/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.cactoos.experimental;

import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;
import org.cactoos.func.Repeated;
import org.cactoos.func.UncheckedFunc;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Timed}.
 *
 * @since 1.0.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class TimedTest {

    /**
     * First text for test.
     */
    private static final String FIRST_TEXT = "txt 1";

    /**
     * Second text for test.
     */
    private static final String SECOND_TEXT = "txt 2";

    /**
     * Third text for test.
     */
    private static final String THIRD_TEXT = "txt 3";

    /**
     * Threads count.
     */
    private static final int THREADS = 3;

    /**
     * Repetitions count for running test several times.
     */
    private static final int REPETITIONS_COUNT = 5;

    /**
     * Sleep time.
     */
    private static final Duration SLEEP_TIME = Duration.ofMillis(100L);

    /**
     * Execute the tasks concurrently using {@link Timed} when
     *  {@link ExecutorService} was initiated by someone else.
     */
    @Test
    public void containsResults() {
        this.repeat(
            arg -> {
                final ExecutorService extor = Executors.newFixedThreadPool(TimedTest.THREADS);
                try {
                    new Assertion<>(
                        "Contains results from callables",
                        new Timed<String>(
                            extor,
                            1L,
                            TimeUnit.SECONDS,
                            () -> {
                                this.sleep();
                                return TimedTest.FIRST_TEXT;
                            },
                            () -> {
                                this.sleep();
                                return TimedTest.SECOND_TEXT;
                            },
                            () -> {
                                this.sleep();
                                return TimedTest.THIRD_TEXT;
                            }
                        ),
                        new HasValues<>(
                            TimedTest.FIRST_TEXT,
                            TimedTest.SECOND_TEXT,
                            TimedTest.THIRD_TEXT
                        )
                    ).affirm();
                } finally {
                    extor.shutdown();
                    if (!extor.awaitTermination(1L, TimeUnit.SECONDS)) {
                        extor.shutdownNow();
                    }
                }
            }
        );
    }

    /**
     * Execution takes longer than timeout when
     *  {@link ExecutorService} was initiated by someone else.
     */
    @Test
    public void failsDueToTimeoutWithExternalExecutorService() {
        this.repeat(
            arg -> {
                final ExecutorService extor = Executors.newFixedThreadPool(TimedTest.THREADS);
                try {
                    new Assertion<>(
                        "Fails due to timeout when using the external executor service",
                        () -> new Timed<String>(
                            extor,
                            1L,
                            TimeUnit.MILLISECONDS,
                            () -> {
                                this.sleep();
                                return TimedTest.FIRST_TEXT;
                            },
                            () -> {
                                this.sleep();
                                return TimedTest.SECOND_TEXT;
                            },
                            () -> {
                                this.sleep();
                                return TimedTest.THIRD_TEXT;
                            }
                        ).iterator().next(),
                        new Throws<>(
                            new IsNull<>(),
                            CancellationException.class
                        )
                    ).affirm();
                } finally {
                    extor.shutdown();
                    if (!extor.awaitTermination(1L, TimeUnit.SECONDS)) {
                        extor.shutdownNow();
                    }
                }
            }
        );
    }

    /**
     * Execute 1 task within executor service and ensure that we'll get the
     *  expected exception type.
     */
    @Test
    public void failsDueToException() {
        new Assertion<>(
            "Wraps error into CompletionException",
            () -> new Timed<String>(
                Executors.newSingleThreadExecutor(),
                1L,
                TimeUnit.SECONDS,
                (Scalar<String>) () -> {
                    throw new IllegalStateException("Something went wrong");
                }
            ).iterator().next(),
            new Throws<>(
                // @checkstyle LineLengthCheck (1 line)
                "java.io.IOException: java.util.concurrent.ExecutionException: java.lang.IllegalStateException: Something went wrong",
                UncheckedIOException.class
            )
        ).affirm();
    }

    /**
     * Execute the tasks concurrently using {@link Timed} when
     *  {@link ExecutorService} was initiated by {@link Timed} itself.
     */
    @Test
    public void containsValuesWithInlineExecutorService() {
        this.repeat(
            arg -> new Assertion<>(
                "Contains results from the callables when using the inline executor service",
                new Timed<String>(
                    TimedTest.THREADS,
                    1L,
                    TimeUnit.SECONDS,
                    () -> {
                        this.sleep();
                        return TimedTest.FIRST_TEXT;
                    },
                    () -> {
                        this.sleep();
                        return TimedTest.SECOND_TEXT;
                    },
                    () -> {
                        this.sleep();
                        return TimedTest.THIRD_TEXT;
                    }
                ),
                new HasValues<>(TimedTest.FIRST_TEXT, TimedTest.SECOND_TEXT, TimedTest.THIRD_TEXT)
            ).affirm()
        );
    }

    /**
     * Execution takes longer than timeout when
     *  {@link ExecutorService} was initiated by {@link Timed} itself.
     */
    @Test
    public void failsDueToTimeoutWithInlineExecutorService() {
        this.repeat(
            arg -> new Assertion<>(
                "Fails due to timeout when using the inline executor service",
                () -> new Timed<String>(
                    TimedTest.THREADS,
                    1L,
                    TimeUnit.MILLISECONDS,
                    () -> {
                        this.sleep();
                        return TimedTest.FIRST_TEXT;
                    },
                    () -> {
                        this.sleep();
                        return TimedTest.SECOND_TEXT;
                    },
                    () -> {
                        this.sleep();
                        return TimedTest.THIRD_TEXT;
                    }
                ).iterator().next(),
                new Throws<>(
                    new IsNull<>(),
                    CancellationException.class
                )
            ).affirm()
        );
    }

    /**
     * Repeat the test several times.
     * @param test The test to execute.
     * @todo #1000:30min Create RepeatedProc decorator in order to get rid of this `dummy` object.
     *  After it's done:
     *  1. Leave a puzzle for creation RepeatedCallable decorator
     *  2. Leave a puzzle for splitting the entire `org.cactoos.func` package into
     *  `org.cactoos.func`, `org.cactoos.proc` and `org.cactoos.callable`, with each one holding
     *  their related decorators, classes and interfaces.
     */
    private void repeat(final Proc<Object> test) {
        final Object dummy = new Object();
        new UncheckedFunc<>(
            new Repeated<>(
                new FuncOf<>(test, dummy),
                TimedTest.REPETITIONS_COUNT
            )
        ).apply(dummy);
    }

    /**
     * Sleep.
     */
    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(TimedTest.SLEEP_TIME.toMillis());
        } catch (final InterruptedException iex) {
            throw new IllegalStateException(iex);
        }
    }
}
