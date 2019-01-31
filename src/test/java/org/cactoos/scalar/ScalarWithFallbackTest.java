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
package org.cactoos.scalar;

import java.io.IOException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link ScalarWithFallback}.
 *
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("unchecked")
public final class ScalarWithFallbackTest {

    @Test
    public void usesMainFunc() throws Exception {
        final String message = "Main function's result #1";
        new Assertion<>(
            "Can't use the main function if no exception",
            new ScalarWithFallback<>(
                () -> message,
                new IterableOf<>(
                    new FallbackFrom<>(
                        new IterableOf<>(Exception.class),
                        Throwable::getMessage
                    )
                )
            ),
            new IsEqual<>(message)
        );
    }

    @Test
    public void usesMainFuncInNonIterableCtor() {
        final String message = "Main function's result";
        new Assertion<>(
            "Can't match value of Scalar's main function",
            new ScalarWithFallback<>(
                () -> message,
                new FallbackFrom<>(
                    new IterableOf<>(Exception.class),
                    Throwable::getMessage
                )
            ),
            new IsEqual<>(message)
        ).affirm();
    }

    @Test
    public void usesFallback() throws Exception {
        final String message = "Fallback from IOException";
        new Assertion<>(
            "Can't use a single fallback in case of exception",
            new ScalarWithFallback<>(
                () -> {
                    throw new IOException("Failure with IOException");
                },
                new IterableOf<>(
                    new FallbackFrom<>(
                        new IterableOf<>(IOException.class),
                        exp -> message
                    )
                )
            ),
            new IsEqual<>(message)
        );
    }

    @Test
    public void usesFallbackOfInterruptedException() throws Exception {
        final String message = "Fallback from InterruptedException";
        new Assertion<>(
            "Can't use a fallback from Interrupted in case of exception",
            new ScalarWithFallback<>(
                () -> {
                    throw new InterruptedException(
                        "Failure with InterruptedException"
                    );
                },
                new IterableOf<>(
                    new FallbackFrom<>(
                        new IterableOf<>(InterruptedException.class),
                        exp -> message
                    )
                )
            ),
            new IsEqual<>(message)
        );
    }

    @Test
    public void usesTheClosestFallback() throws Exception {
        final String expected = "Fallback from IllegalStateException";
        new Assertion<>(
            "Can't find the closest fallback",
            new ScalarWithFallback<>(
                () -> {
                    throw new IllegalStateException("wat is raw");
                },
                new IterableOf<>(
                    new FallbackFrom<>(
                        new IterableOf<>(Exception.class),
                        exp -> "Fallback from Exception"
                    ),
                    new FallbackFrom<>(
                        new IterableOf<>(IllegalStateException.class),
                        exp -> expected
                    )
                )
            ),
            new IsEqual<>(expected)
        );
    }

    @Test(expected = Exception.class)
    public void noFallbackIsProvided() throws Exception {
        new ScalarWithFallback<>(
            () -> {
                throw new Exception();
            },
            new IterableOf<>()
        ).value();
    }
}
