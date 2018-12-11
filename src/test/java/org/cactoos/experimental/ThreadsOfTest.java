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
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.cactoos.Proc;
import org.cactoos.func.Repeated;
import org.cactoos.func.TimedFunc;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.iterable.LengthOf;
import org.cactoos.scalar.UncheckedScalar;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Test case for {@link ThreadsOf}.
 *
 * @since 1.0.0
 * @checkstyle MagicNumberCheck (200 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class ThreadsOfTest {

    /**
     * Execute the tasks concurrently using {@link ThreadsOf}.
     */
    @Test
    public void cactoosWay() {
        this.repeatAtLeastTenTimesWithTimeoutInFiveSeconds(
            arg -> {
                final ExecutorService extor = Executors.newFixedThreadPool(3);
                try {
                    MatcherAssert.assertThat(
                        new ThreadsOf<String>(
                            () -> extor,
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
                        Matchers.containsInAnyOrder("txt 1", "txt 2", "txt 3")
                    );
                } finally {
                    try {
                        extor.shutdown();
                        if (!extor.awaitTermination(1, TimeUnit.SECONDS)) {
                            extor.shutdownNow();
                        }
                    } catch (final InterruptedException exp) {
                        Thread.currentThread().interrupt();
                        extor.shutdownNow();
                    }
                }
            }
        );
    }

    /**
     * Execute the test at least 10 times with timeout in 5 seconds each.
     * @param test The test to execute.
     * @todo #972:30min Move this method to new object RepeatWithTimeout.
     *  This object might be present in cactoos/src/test/java/o.c.experimental
     *  in order to be used with concurrent unit tests.
     */
    public void repeatAtLeastTenTimesWithTimeoutInFiveSeconds(
        final Proc<Boolean> test
    ) {
        MatcherAssert.assertThat(
            new UncheckedFunc<>(
                new Repeated<>(
                    new TimedFunc<Boolean, Boolean>(
                        input -> {
                            test.exec(input);
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

    /**
     * Execute 1 task within executor service and ensure that we'll get the
     *  expected exception type.
     */
    @Test(expected = CompletionException.class)
    public void executionIsFailedDueToException() {
        new UncheckedScalar<>(
            new LengthOf(
                new ThreadsOf<String>(
                    Executors::newSingleThreadExecutor,
                    () -> {
                        throw new IllegalStateException("Something went wrong");
                    }
                )
            )
        ).value();
    }

}
