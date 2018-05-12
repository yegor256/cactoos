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
import java.util.IllegalFormatException;
import java.util.IllegalFormatWidthException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.ScalarHasValue;

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
        MatcherAssert.assertThat(
            "Can't use the main function if no exception",
            new ScalarWithFallback<>(
                () -> message,
                new IterableOf<>(
                    new FallbackFrom<>(
                        new IterableOf<>(IOException.class),
                        Throwable::getMessage
                    )),
                input -> input
            ),
            new ScalarHasValue<>(message)
        );
    }

    @Test
    public void usesFollowUpFunc() throws Exception {
        final String message = "There was follow up function";
        MatcherAssert.assertThat(
            "Can't use the follow up function if no exception",
            new ScalarWithFallback<>(
                () -> "Main function's result #2",
                new IterableOf<>(
                    new FallbackFrom<>(
                        new IterableOf<>(IOException.class),
                        Throwable::getMessage
                    )),
                input -> message
            ),
            new ScalarHasValue<>(message)
        );
    }

    @Test
    public void usesFallback() throws Exception {
        final String message = "Fallback from IOException";
        MatcherAssert.assertThat(
            "Can't use a single fallback in case of exception",
            new ScalarWithFallback<>(
                () -> {
                    throw new IOException("Failure with IOException");
                },
                new IterableOf<>(
                    new FallbackFrom<>(
                        new IterableOf<>(IOException.class),
                        exp -> message
                    )),
                input -> input
            ),
            new ScalarHasValue<>(message)
        );
    }

    @Test
    public void usesFallbackOfInterruptedException() throws Exception {
        final String message = "Fallback from InterruptedException";
        MatcherAssert.assertThat(
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
                    )),
                input -> input
            ),
            new ScalarHasValue<>(message)
        );
    }

    @Test
    public void usesTheClosestFallback() throws Exception {
        final String expected = "Fallback from IllegalFormatException";
        MatcherAssert.assertThat(
            "Can't find the closest fallback",
            new ScalarWithFallback<>(
                () -> {
                    throw new IllegalFormatWidthException(1);
                },
                new IterableOf<>(
                    new FallbackFrom<>(
                        new IterableOf<>(IllegalArgumentException.class),
                        exp -> "Fallback from IllegalArgumentException"
                    ),
                    new FallbackFrom<>(
                        new IterableOf<>(IllegalFormatException.class),
                        exp -> expected
                    )),
                input -> input
            ),
            new ScalarHasValue<>(expected)
        );
    }

    @Test(expected = Exception.class)
    public void noFallbackIsProvided() throws Exception {
        new ScalarWithFallback<>(
            () -> {
                throw new IllegalFormatWidthException(1);
            },
            new IterableOf<>(),
            input -> input
        ).value();
    }
}
