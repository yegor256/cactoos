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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.cactoos.Scalar;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Stateless}.
 *
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class StatelessTest {

    @Test(timeout = 2000)
    public void complete() throws Exception {
        final ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            new Stateless<String>(
                () -> Duration.ofSeconds(1),
                new TasksOf<String>(
                    () -> {
                        System.out.println("before 1st");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("after 1st");
                        return "1st";
                    },
                    () -> {
                        System.out.println("before 2nd");
                        return "2nd";
                    },
                    (Scalar<String>) () -> {
                        System.out.println("before 3rd");
                        return "3rd";
                    }
                ),
                () -> Executors.newFixedThreadPool(5)
            ).complete();
        } catch (final UncheckedIOException cause) {
            MatcherAssert.assertThat(
                new RootCauseOf(cause).value(),
                Matchers.instanceOf(TimeoutException.class)
            );
        } finally {
            executor.shutdown();
        }
    }
}
