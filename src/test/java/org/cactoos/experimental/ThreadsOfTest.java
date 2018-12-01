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

import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.cactoos.Scalar;
import org.cactoos.iterable.LengthOf;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test case for {@link ThreadsOf}.
 *
 * @since 1.0.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle IllegalThrowsCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MethodBodyCommentsCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class ThreadsOfTest {

    @Test(timeout = 10000)
    public void oldSchoolWay() throws InterruptedException {
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
                new Mapped<>(f -> f.get(8, TimeUnit.SECONDS), futures),
                Matchers.hasItems("1st", "3rd", "2nd")
            );
        } finally {
            extor.shutdown();
        }
    }

    @Test(timeout = 10000)
    public void cactoosWay() throws ConcurrentExecutionException {
        MatcherAssert.assertThat(
            new ThreadsOf<String>(
                () -> Duration.ofSeconds(7),
                () -> {
                    TimeUnit.SECONDS.sleep(2);
                    return "1st";
                },
                () -> {
                    TimeUnit.SECONDS.sleep(6);
                    return "3rd";
                },
                (Scalar<String>) () -> {
                    TimeUnit.SECONDS.sleep(4);
                    return "2nd";
                }
            ).complete(),
            Matchers.hasItems("1st", "3rd", "2nd")
        );
    }

    @Test(timeout = 2000, expected = CancellationException.class)
    public void cactoosWayWithTimeout() throws Throwable {
        try {
            new LengthOf(
                new ThreadsOf<String>(
                    () -> Duration.ofSeconds(1),
                    () -> {
                        TimeUnit.SECONDS.sleep(3);
                        return "1st";
                    },
                    () -> "2nd",
                    (Scalar<String>) () -> "3rd"
                ).complete()
            ).value();
        } catch (final UncheckedIOException exp) {
            // @todo #962:30m new matcher org.llorllale.cactoos-matchers
            //  is required in order to verify the exception hierarchy in
            //  a laconic way without code mess below (with exception's catch).
            throw new RootCauseOf(exp).value();
        }
        Assert.fail("The exception should be thrown");
    }
}
