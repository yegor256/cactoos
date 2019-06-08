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

import java.security.SecureRandom;
import java.time.Duration;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.FuncApplies;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Retry}.
 *
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 line)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class RetryTest {

    @Test
    public void runsFuncMultipleTimes() {
        new Assertion<>(
            "Didn't run multiple times",
            new Retry<>(
                input -> {
                    if (new SecureRandom().nextDouble() > 0.3d) {
                        throw new IllegalArgumentException("May happen");
                    }
                    return 0;
                },
                Integer.MAX_VALUE
            ),
            new FuncApplies<>(true, 0)
        ).affirm();
    }

    @Test
    public void runsFuncConditionMultipleTimes() {
        new Assertion<>(
            "Didn't check condition multiple times",
            new Retry<>(
                input -> {
                    if (new SecureRandom().nextDouble() > 0.3d) {
                        throw new IllegalArgumentException("May happen");
                    }
                    return true;
                },
                count -> count == Integer.MAX_VALUE
            ),
            new FuncApplies<>(true, true)
        ).affirm();
    }

    @Test
    public void processInterruptExceptionOnWait() {
        final Thread main = Thread.currentThread();
        new Thread(
            () -> {
                while (true) {
                    if (Thread.State.TIMED_WAITING == main.getState()) {
                        main.interrupt();
                        return;
                    }
                }
            }).start();
        new Assertion<>(
            "Must be interrupted on wait",
            () -> new Retry<>(
                input -> {
                    throw new IllegalArgumentException("Happens");
                },
                attempts -> false,
                Duration.ofMillis(Long.MAX_VALUE)
            ).apply(true),
            new Throws<>("sleep interrupted", InterruptedException.class)
        ).affirm();
    }
}
