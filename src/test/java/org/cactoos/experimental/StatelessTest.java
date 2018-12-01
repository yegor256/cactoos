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
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.cactoos.Scalar;
import org.cactoos.iterable.LengthOf;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test case for {@link Stateless}.
 *
 * @since 1.0.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle IllegalThrowsCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class StatelessTest {

    @Test(timeout = 2000, expected = CancellationException.class)
    public void complete() throws Throwable {
        final ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            new LengthOf(
                new Stateless<String>(
                    () -> Duration.ofSeconds(1),
                    new TasksOf<String>(
                        () -> {
                            TimeUnit.SECONDS.sleep(5);
                            return "1st";
                        },
                        () -> "2nd",
                        (Scalar<String>) () -> "3rd"
                    ),
                    () -> Executors.newFixedThreadPool(5)
                ).complete()
            ).value();
        } catch (final UncheckedIOException cause) {
            throw new RootCauseOf(cause).value();
        } finally {
            executor.shutdown();
        }
        Assert.fail("The exception should be thrown");
    }
}
