/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.cactoos.Proc;
import org.cactoos.func.Repeated;
import org.cactoos.func.Timed;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.HasValues;

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
     * Example of JDK8 way for handling of concurrent tasks.
     * This code base should be simplified.
     */
    @Test(timeout = 10000)
    public void oldSchoolWay() {
        final ExecutorService extor = Executors.newFixedThreadPool(5);
        try {
            final Collection<Future<String>> futures = extor.invokeAll(
                new ListOf<Callable<String>>(
                    () -> {
                        TimeUnit.SECONDS.sleep(2);
                        return "1st";
                    },
                    () -> {
                        TimeUnit.SECONDS.sleep(6);
                        return "3rd";
                    },
                    () -> {
                        TimeUnit.SECONDS.sleep(4);
                        return "2nd";
                    }
                )
            );
            MatcherAssert.assertThat(
                new Mapped<>(Future::get, futures),
                Matchers.hasItems("1st", "3rd", "2nd")
            );
        } catch (final InterruptedException exp) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(exp);
        } finally {
            try {
                extor.shutdown();
                if (!extor.awaitTermination(2, TimeUnit.SECONDS)) {
                    extor.shutdownNow();
                }
            } catch (final InterruptedException exp) {
                extor.shutdownNow();
            }
        }
    }

    /**
     * Execute the tasks concurrently using {@link Threads} when
     *  {@link ExecutorService} was initiated by someone else.
     */
    @Test
    public void cactoosWay() {
        this.repeatWithTimeout(
            arg -> {
                final ExecutorService extor = Executors.newFixedThreadPool(3);
                try {
                    MatcherAssert.assertThat(
                        new Threads<String>(
                            extor,
                            () -> {
                                TimeUnit.SECONDS.sleep(3);
                                return "txt 1";
                            },
                            () -> {
                                TimeUnit.SECONDS.sleep(3);
                                return "txt 2";
                            },
                            () -> {
                                TimeUnit.SECONDS.sleep(3);
                                return "txt 3";
                            }
                        ),
                        new HasValues<>("txt 1", "txt 2", "txt 3")
                    );
                } finally {
                    try {
                        extor.shutdown();
                        if (!extor.awaitTermination(1, TimeUnit.SECONDS)) {
                            extor.shutdownNow();
                        }
                    } catch (final InterruptedException exp) {
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
    @Test(expected = CompletionException.class)
    public void executionIsFailedDueToException() {
        new Threads<String>(
            Executors.newSingleThreadExecutor(),
            () -> {
                throw new IllegalStateException("Something went wrong");
            }
        ).iterator().next();
    }

    /**
     * Execute the tasks concurrently using {@link Threads} when
     *  {@link ExecutorService} was initiated by {@link Threads} itself.
     */
    @Test
    public void cactoosWayWithInlineExecutorService() {
        this.repeatWithTimeout(
            arg -> {
                MatcherAssert.assertThat(
                    new Threads<String>(
                        5,
                        () -> {
                            TimeUnit.SECONDS.sleep(3);
                            return "txt 1";
                        },
                        () -> {
                            TimeUnit.SECONDS.sleep(3);
                            return "txt 2";
                        },
                        () -> {
                            TimeUnit.SECONDS.sleep(3);
                            return "txt 3";
                        },
                        () -> {
                            TimeUnit.SECONDS.sleep(3);
                            return "txt 4";
                        },
                        () -> {
                            TimeUnit.SECONDS.sleep(3);
                            return "txt 5";
                        }
                    ),
                    new HasValues<>("txt 1", "txt 2", "txt 3", "txt 4", "txt 5")
                );
            }
        );
    }

    /**
     * Execute the test at least 10 times with timeout in 5 seconds each.
     * @param test The test to execute.
     */
    private void repeatWithTimeout(final Proc<?> test) {
        MatcherAssert.assertThat(
            new UncheckedFunc<>(
                new Repeated<>(
                    new Timed<Boolean, Boolean>(
                        input -> {
                            test.exec(null);
                            return true;
                        },
                        Duration.ofSeconds(5).toMillis()
                    ),
                    10
                )
            ).apply(true),
            new IsEqual<>(true)
        );
    }
}
