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
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.cactoos.Scalar;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Closable}.
 *
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class ClosableTest {

    @Test(timeout = 7000)
    public void complete() throws ConcurrentExecutionException {
        MatcherAssert.assertThat(
            new Closable<String>(
                () -> Duration.ofSeconds(5),
                new TasksOf<String>(
                    () -> {
                        TimeUnit.SECONDS.sleep(1);
                        return "1st";
                    },
                    () -> {
                        TimeUnit.SECONDS.sleep(4);
                        return "3rd";
                    },
                    (Scalar<String>) () -> {
                        TimeUnit.SECONDS.sleep(2);
                        return "2nd";
                    }
                ),
                () -> Executors.newFixedThreadPool(3)
            ).complete(),
            Matchers.hasItems("1st", "3rd", "2nd")
        );
    }
}
