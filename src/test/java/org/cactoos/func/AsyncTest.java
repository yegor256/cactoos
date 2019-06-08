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
package org.cactoos.func;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.FuncApplies;
import org.llorllale.cactoos.matchers.MatcherOf;

/**
 * Test case for {@link Async}.
 *
 * @since 0.10
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class AsyncTest {
    @Test
    public void runsInBackground() {
        new Assertion<>(
            "Can't run in the background",
            new Async<>(
                input -> {
                    TimeUnit.DAYS.sleep(1L);
                    return "done!";
                }
            ),
            new FuncApplies<>(
                true,
                new MatcherOf<>(
                    future -> !future.isDone()
                )
            )
        ).affirm();
    }

    @Test
    public void runsAsProcInBackground() {
        new Assertion<>(
            "Can't run proc in the background",
            input -> {
                final CountDownLatch latch = new CountDownLatch(1);
                new Async<>(
                    new FuncOf<>(ipt -> latch.countDown(), true)
                ).exec(input);
                latch.await();
                return true;
            },
            new FuncApplies<>(
                true, new IsEqual<>(true)
            )
        ).affirm();
    }

    @Test
    public void runsInBackgroundWithoutFuture() {
        final CountDownLatch latch = new CountDownLatch(1);
        new Assertion<>(
            "Can't run in the background without us touching the Future",
            new Async<>(
                new FuncOf<>(input -> latch.countDown(), true)
            ),
            new FuncApplies<>(
                true,
                new MatcherOf<>(
                    future -> {
                        return latch.await(1L, TimeUnit.SECONDS);
                    }
                )
            )
        ).affirm();
    }

    @Test
    public void runsInBackgroundWithThreadFactory() {
        final String name = "secret name for thread factory";
        final ThreadFactory factory = r -> new Thread(r, name);
        final CountDownLatch latch = new CountDownLatch(1);
        new Assertion<>(
            "Can't run in the background with specific thread factory",
            new Async<>(
                new FuncOf<>(
                    input -> {
                        if (!input.equals(Thread.currentThread().getName())) {
                            throw new IllegalStateException(
                                "Another thread factory was used"
                            );
                        }
                        latch.countDown();
                    },
                    true
                ),
                factory
            ),
            new FuncApplies<>(
                name,
                new MatcherOf<>(
                    future -> {
                        future.get();
                        return latch.getCount() == 0;
                    }
                )
            )
        ).affirm();
    }

    @Test
    public void runsInBackgroundWithExecutorService() {
        final String name = "secret name for thread executor";
        final ThreadFactory factory = r -> new Thread(r, name);
        final CountDownLatch latch = new CountDownLatch(1);
        new Assertion<>(
            "Can't run in the background with specific thread executor",
            new Async<>(
                new FuncOf<>(
                    input -> {
                        if (!input.equals(Thread.currentThread().getName())) {
                            throw new IllegalStateException(
                                "Another thread executor was used"
                            );
                        }
                        latch.countDown();
                    },
                    true
                ),
                Executors.newSingleThreadExecutor(factory)
            ),
            new FuncApplies<>(
                name,
                new MatcherOf<>(
                    future -> {
                        future.get();
                        return latch.getCount() == 0;
                    }
                )
            )
        ).affirm();
    }
}
