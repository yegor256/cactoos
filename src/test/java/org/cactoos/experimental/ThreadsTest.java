/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.cactoos.Proc;
import org.cactoos.func.Repeated;
import org.cactoos.func.UncheckedFunc;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Threads}.
 *
 * @since 1.0.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class ThreadsTest {

    /**
     * Execute the tasks concurrently using {@link Threads} when
     *  {@link ExecutorService} was initiated by someone else.
     */
    @Test
    public void containsResults() {
        this.repeat(
            arg -> {
                final ExecutorService extor = Executors.newFixedThreadPool(3);
                try {
                    new Assertion<>(
                        "contains results from callables",
                        new Threads<String>(
                            extor,
                            () -> {
                                this.sleep();
                                return "txt 1";
                            },
                            () -> {
                                this.sleep();
                                return "txt 2";
                            },
                            () -> {
                                this.sleep();
                                return "txt 3";
                            }
                        ),
                        new HasValues<>("txt 1", "txt 2", "txt 3")
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
            "wraps error into CompletionException",
            () -> new Threads<String>(
                Executors.newSingleThreadExecutor(),
                () -> {
                    throw new IllegalStateException("Something went wrong");
                }
            ).iterator().next(),
            new Throws<>(
                "java.lang.IllegalStateException: Something went wrong",
                CompletionException.class
            )
        ).affirm();
    }

    /**
     * Execute the tasks concurrently using {@link Threads} when
     *  {@link ExecutorService} was initiated by {@link Threads} itself.
     */
    @Test
    public void containsValuesWithInlineExecutorService() {
        this.repeat(
            arg -> new Assertion<>(
                // @checkstyle LineLength (1 line)
                "contains results from the callables when using the inline executor service",
                new Threads<String>(
                    3,
                    () -> {
                        this.sleep();
                        return "txt 1";
                    },
                    () -> {
                        this.sleep();
                        return "txt 2";
                    },
                    () -> {
                        this.sleep();
                        return "txt 3";
                    }
                ),
                new HasValues<>("txt 1", "txt 2", "txt 3")
            ).affirm()
        );
    }

    /**
     * Repeat the test several times.
     * @param test The test to execute.
     */
    private void repeat(final Proc<?> test) {
        new UncheckedFunc<>(
            new Repeated<>(
                arg -> {
                    test.exec(null);
                    return null;
                },
                5
            )
        ).apply(Boolean.TRUE);
    }

    /**
     * Sleep.
     */
    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(100L);
        } catch (final InterruptedException iex) {
            throw new IllegalStateException(iex);
        }
    }
}
