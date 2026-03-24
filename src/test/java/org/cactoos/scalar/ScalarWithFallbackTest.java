/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.IllegalFormatWidthException;
import org.cactoos.Fallback;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link ScalarWithFallback}.
 *
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("unchecked")
final class ScalarWithFallbackTest {

    @Test
    void usesMainFunc() {
        final String message = "Main function's result #1";
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void usesMainFuncFromExceptionAndFallback() {
        final String message = "Main function's result #1 (exp & flbck)";
        MatcherAssert.assertThat(
            "Using the main function if no exception (exp & flbck)",
            new ScalarWithFallback<>(
                () -> message,
                new Fallback.From<>(
                    IOException.class,
                    Throwable::getMessage
                )
            ),
            new HasValue<>(message)
        );
    }

    @Test
    void usesMainFuncFromIterableExceptionAndFallback() {
        final String message = "Main function's result #1 (exp iterable & flbck)";
        MatcherAssert.assertThat(
            "Using the main function if no exception (exp iterable & flbck)",
            new ScalarWithFallback<>(
                () -> message,
                new Fallback.From<>(
                    new IterableOf<>(IOException.class),
                    Throwable::getMessage
                )
            ),
            new HasValue<>(message)
        );
    }

    @Test
    void usesFallback() {
        final String message = "Fallback from IOException";
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void usesFallbackFromExceptionAndFallback() {
        final String message = "Fallback from IOException (exp & flbck)";
        MatcherAssert.assertThat(
            "Using a single fallback in case of exception (exp & flbck)",
            new ScalarWithFallback<>(
                () -> {
                    throw new IOException("Failure with IOException (exp & flbck)");
                },
                new Fallback.From<>(
                    IOException.class,
                    exp -> message
                )
            ),
            new HasValue<>(message)
        );
    }

    @Test
    void usesFallbackFromIterableExceptionAndFallback() {
        final String message = "Fallback from IOException (exp iterable & flbck)";
        MatcherAssert.assertThat(
            "Using a single fallback in case of exception (exp iterable & flbck)",
            new ScalarWithFallback<>(
                () -> {
                    throw new IOException("Failure with IOException (exp iterable & flbck)");
                },
                new Fallback.From<>(
                    new IterableOf<>(IOException.class),
                    exp -> message
                )
            ),
            new HasValue<>(message)
        );
    }

    @Test
    void usesFallbackOfInterruptedException() {
        final String message = "Fallback from InterruptedException";
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void usesTheClosestFallback() {
        final String expected = "Fallback from IllegalFormatException";
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void noFallbackIsProvided() {
        MatcherAssert.assertThat(
            "Exception is expected if there is no fallback",
            () -> new ScalarWithFallback<>(
                () -> {
                    throw new IllegalFormatWidthException(1);
                },
                new IterableOf<>()
            ).value(),
            new Throws<>(Exception.class)
        );
    }
}
