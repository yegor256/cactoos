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

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Threads}.
 *
 * @since 1.0.0
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class ThreadsTest {

    /**
     * Example of JDK8 way for handling of concurrent tasks.
     * This code base should be simplified.
     * @todo #962:30min o.c.experimental Design the implementation of ThreadsOf.
     *  The initial scope is including:
     *  1) receive the ExecutorService;
     *  2) receive the tasks to complete;
     *  3) execute the tasks concurrently and return the values.
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
                Thread.currentThread().interrupt();
                extor.shutdownNow();
            }
        }
    }
}
