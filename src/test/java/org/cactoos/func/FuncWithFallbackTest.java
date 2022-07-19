/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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

import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.IllegalFormatWidthException;
import org.cactoos.Fallback;
import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsApplicable;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link FuncWithFallback}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("unchecked")
final class FuncWithFallbackTest {

    @Test
    void usesMainFunc() {
        final String expected = "It's success";
        new Assertion<>(
            "Can't use the main function if no exception",
            new FuncWithFallback<>(
                input -> expected,
                new Fallback.From<>(
                    Exception.class,
                    ex -> "In case of failure..."
                )
            ),
            new IsApplicable<>(1, expected)
        ).affirm();
    }

    @Test
    void usesFallback() {
        final String expected = "Never mind";
        new Assertion<>(
            "Can't use the callback in case of exception",
            new FuncWithFallback<>(
                input -> {
                    throw new IOException("Failure");
                },
                new Fallback.From<>(IOException.class, ex -> expected)
            ),
            new IsApplicable<>(1, expected)
        ).affirm();
    }

    @Test
    void usesFallbackOfInterruptedException() {
        final String expected = "Fallback from InterruptedException";
        new Assertion<>(
            "Can't use a fallback from Interrupted in case of exception",
            new FuncWithFallback<>(
                input -> {
                    throw new InterruptedException(
                        "Failure with InterruptedException"
                    );
                },
                new Fallback.From<>(InterruptedException.class, exp -> expected)
            ),
            new IsApplicable<>(1, expected)
        ).affirm();
    }

    @Test
    void usesTheClosestFallback() {
        final String expected = "Fallback from IllegalFormatException";
        new Assertion<>(
            "Can't find the closest fallback",
            new FuncWithFallback<>(
                input -> {
                    throw new IllegalFormatWidthException(1);
                },
                new IterableOf<>(
                    new Fallback.From<>(
                        IllegalArgumentException.class,
                        exp -> "Fallback from IllegalArgumentException"
                    ),
                    new Fallback.From<>(
                        IllegalFormatException.class,
                        exp -> expected
                    )
                )
            ),
            new IsApplicable<>(1, expected)
        ).affirm();
    }

    @Test
    void noFallbackIsProvided() {
        new Assertion<>(
            "Must fail for no fallback provided",
            () -> new FuncWithFallback<>(
                input -> {
                    throw new IllegalFormatWidthException(1);
                },
                new IterableOf<>()
            ).apply(1),
            new Throws<>(Exception.class)
        ).affirm();
    }

}
