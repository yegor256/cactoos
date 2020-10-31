/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
package org.cactoos.scalar;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.ScalarHasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link org.cactoos.scalar.Repeated}.
 *
 * @since 0.49.2
 * @checkstyle MagicNumberCheck (100 line)
 * @checkstyle JavadocMethodCheck (100 lines)
 */
final class RepeatedTest {
    @Test
    void runsMultipleTimes() throws Exception {
        final AtomicInteger atom = new AtomicInteger();
        new Assertion<>(
            "Must run scalar 3 times",
            new Repeated<>(
                new ScalarOf<>(
                    () -> atom.getAndIncrement()
                ),
                3
            ),
            new ScalarHasValue<>(3)
        );
    }

    @Test
    void throwsIfZero() {
        new Assertion<>(
            // @checkstyle LineLengthCheck (1 line)
            "Must throws an exception if number of repetitions not be at least 1",
            new Repeated<>(
                new ScalarOf<>(
                    () -> {
                        Assert.fail("intended to fail");
                        return false;
                    }
                ),
                0
            ),
            new Throws<>(
                "The number of repetitions must be at least 1",
                IllegalArgumentException.class
            )
        ).affirm();
    }
}
