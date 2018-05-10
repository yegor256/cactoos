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
package org.cactoos.func;

import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.IllegalFormatWidthException;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.FallbackFrom;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.FuncApplies;

/**
 * Test case for {@link FuncWithFallback}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("unchecked")
public final class FuncWithFallbackTest {

    @Test
    public void usesMainFunc() throws Exception {
        final String expected = "It's success";
        MatcherAssert.assertThat(
            "Can't use the main function if no exception",
            new FuncWithFallback<>(
                input -> expected,
                new FallbackFrom<>(
                    Exception.class,
                    ex -> "In case of failure..."
                )
            ),
            new FuncApplies<>(1, expected)
        );
    }

    @Test
    public void usesFallback() throws Exception {
        final String expected = "Never mind";
        MatcherAssert.assertThat(
            "Can't use the callback in case of exception",
            new FuncWithFallback<>(
                input -> {
                    throw new IOException("Failure");
                },
                new FallbackFrom<>(IOException.class, ex -> expected)
            ),
            new FuncApplies<>(1, expected)
        );
    }

    @Test
    public void usesFollowUp() throws Exception {
        final String expected = "follow up";
        MatcherAssert.assertThat(
            "Can't use the follow-up func",
            new FuncWithFallback<>(
                input -> "works fine",
                new FallbackFrom<>(Exception.class, ex -> "won't happen"),
                input -> expected
            ),
            new FuncApplies<>(1, expected)
        );
    }

    @Test
    public void usesFallbackOfInterruptedException() throws Exception {
        final String expected = "Fallback from InterruptedException";
        MatcherAssert.assertThat(
            "Can't use a fallback from Interrupted in case of exception",
            new FuncWithFallback<>(
                input -> {
                    throw new InterruptedException(
                        "Failure with InterruptedException"
                    );
                },
                new FallbackFrom<>(InterruptedException.class, exp -> expected)
            ),
            new FuncApplies<>(1, expected)
        );
    }

    @Test
    public void usesTheClosestFallback() throws Exception {
        final String expected = "Fallback from IllegalFormatException";
        MatcherAssert.assertThat(
            "Can't find the closest fallback",
            new FuncWithFallback<>(
                input -> {
                    throw new IllegalFormatWidthException(1);
                },
                new IterableOf<>(
                    new FallbackFrom<>(
                        IllegalArgumentException.class,
                        exp -> "Fallback from IllegalArgumentException"
                    ),
                    new FallbackFrom<>(
                        IllegalFormatException.class,
                        exp -> expected
                    )
                )
            ),
            new FuncApplies<>(1, expected)
        );
    }

    @Test(expected = Exception.class)
    public void noFallbackIsProvided() throws Exception {
        new FuncWithFallback<>(
            input -> {
                throw new IllegalFormatWidthException(1);
            },
            new IterableOf<>()
        ).apply(1);
    }

}
