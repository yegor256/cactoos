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

import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.IllegalFormatWidthException;
import org.cactoos.Fallback;
import org.cactoos.iterable.IterableOf;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link ScalarWithFallback}.
 *
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("unchecked")
public final class ScalarWithFallbackTest {

    @Test
    public void usesMainFunc() throws Exception {
        final String message = "Main function's result #1";
        new Assertion<>(
            "Must use the main function if no exception",
            new ScalarWithFallback<>(
                () -> message,
                new IterableOf<>(
                    new Fallback.From<>(
                        new IterableOf<>(IOException.class),
                        Throwable::getMessage
                    )
                )
            ),
            new HasValue<>(message)
        ).affirm();
    }

    @Test
    public void usesMainFuncFromExceptionAndFallback() throws Exception {
        final String message = "Main function's result #1 (exp & flbck)";
        new Assertion<>(
            "Using the main function if no exception (exp & flbck)",
            new ScalarWithFallback<String>(
                () -> message,
                new Fallback.From<>(
                    IOException.class,
                    Throwable::getMessage
                )
            ),
            new HasValue<>(message)
        ).affirm();
    }

    @Test
    public void usesMainFuncFromIterableExceptionAndFallback() throws Exception {
        final String message = "Main function's result #1 (exp iterable & flbck)";
        new Assertion<>(
            "Using the main function if no exception (exp iterable & flbck)",
            new ScalarWithFallback<String>(
                () -> message,
                new Fallback.From<>(
                    new IterableOf<>(IOException.class),
                    Throwable::getMessage
                )
            ),
            new HasValue<>(message)
        ).affirm();
    }

    @Test
    public void usesFallback() throws Exception {
        final String message = "Fallback from IOException";
        new Assertion<>(
            "Must use a single fallback in case of exception",
            new ScalarWithFallback<>(
                () -> {
                    throw new IOException("Failure with IOException");
                },
                new IterableOf<>(
                    new Fallback.From<>(
                        new IterableOf<>(IOException.class),
                        new Fallback.From<>(
                            IOException.class,
                            exp -> message
                        )
                    )
                )
            ),
            new HasValue<>(message)
        ).affirm();
    }

    @Test
    public void usesFallbackFromExceptionAndFallback() throws Exception {
        final String message = "Fallback from IOException (exp & flbck)";
        new Assertion<>(
            "Using a single fallback in case of exception (exp & flbck)",
            new ScalarWithFallback<String>(
                () -> {
                    throw new IOException("Failure with IOException (exp & flbck)");
                },
                new Fallback.From<>(
                    IOException.class,
                    exp -> message
                )
            ),
            new HasValue<>(message)
        ).affirm();
    }

    @Test
    public void usesFallbackFromIterableExceptionAndFallback() throws Exception {
        final String message = "Fallback from IOException (exp iterable & flbck)";
        new Assertion<>(
            "Using a single fallback in case of exception (exp iterable & flbck)",
            new ScalarWithFallback<String>(
                () -> {
                    throw new IOException("Failure with IOException (exp iterable & flbck)");
                },
                new Fallback.From<>(
                    new IterableOf<>(IOException.class),
                    exp -> message
                )
            ),
            new HasValue<>(message)
        ).affirm();
    }

    @Test
    public void usesFallbackOfInterruptedException() throws Exception {
        final String message = "Fallback from InterruptedException";
        new Assertion<>(
            "Must use a fallback from Interrupted in case of exception",
            new ScalarWithFallback<>(
                () -> {
                    throw new InterruptedException(
                        "Failure with InterruptedException"
                    );
                },
                new IterableOf<>(
                    new Fallback.From<>(
                        new IterableOf<>(InterruptedException.class),
                        exp -> message
                    )
                )
            ),
            new HasValue<>(message)
        ).affirm();
    }

    @Test
    public void usesTheClosestFallback() throws Exception {
        final String expected = "Fallback from IllegalFormatException";
        new Assertion<>(
            "Must find the closest fallback",
            new ScalarWithFallback<>(
                () -> {
                    throw new IllegalFormatWidthException(1);
                },
                new IterableOf<>(
                    new Fallback.From<>(
                        new IterableOf<>(IllegalArgumentException.class),
                        exp -> "Fallback from IllegalArgumentException"
                    ),
                    new Fallback.From<>(
                        new IterableOf<>(IllegalFormatException.class),
                        exp -> expected
                    )
                )
            ),
            new HasValue<>(expected)
        ).affirm();
    }

    @Test(expected = Exception.class)
    public void noFallbackIsProvided() throws Exception {
        new ScalarWithFallback<>(
            () -> {
                throw new IllegalFormatWidthException(1);
            },
            new IterableOf<>()
        ).value();
    }
}
