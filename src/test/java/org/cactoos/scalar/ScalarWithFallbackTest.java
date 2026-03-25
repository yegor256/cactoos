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
        MatcherAssert.assertThat(
            "Must use the main function if no exception",
            new ScalarWithFallback<>(
                () -> "Main function's result #1",
                new IterableOf<>(
                    new Fallback.From<>(
                        new IterableOf<>(IOException.class),
                        Throwable::getMessage
                    )
                )
            ),
            new HasValue<>("Main function's result #1")
        );
    }

    @Test
    void usesMainFuncFromExceptionAndFallback() {
        MatcherAssert.assertThat(
            "Using the main function if no exception (exp & flbck)",
            new ScalarWithFallback<>(
                () -> "Main function's result #1 (exp & flbck)",
                new Fallback.From<>(
                    IOException.class,
                    Throwable::getMessage
                )
            ),
            new HasValue<>("Main function's result #1 (exp & flbck)")
        );
    }

    @Test
    void usesMainFuncFromIterableExceptionAndFallback() {
        MatcherAssert.assertThat(
            "Using the main function if no exception (exp iterable & flbck)",
            new ScalarWithFallback<>(
                () -> "Main function's result #1 (exp iterable & flbck)",
                new Fallback.From<>(
                    new IterableOf<>(IOException.class),
                    Throwable::getMessage
                )
            ),
            new HasValue<>("Main function's result #1 (exp iterable & flbck)")
        );
    }

    @Test
    void usesFallback() {
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
                            exp -> "Fallback from IOException"
                        )
                    )
                )
            ),
            new HasValue<>("Fallback from IOException")
        );
    }

    @Test
    void usesFallbackFromExceptionAndFallback() {
        MatcherAssert.assertThat(
            "Using a single fallback in case of exception (exp & flbck)",
            new ScalarWithFallback<>(
                () -> {
                    throw new IOException("Failure with IOException (exp & flbck)");
                },
                new Fallback.From<>(
                    IOException.class,
                    exp -> "Fallback from IOException (exp & flbck)"
                )
            ),
            new HasValue<>("Fallback from IOException (exp & flbck)")
        );
    }

    @Test
    void usesFallbackFromIterableExceptionAndFallback() {
        MatcherAssert.assertThat(
            "Using a single fallback in case of exception (exp iterable & flbck)",
            new ScalarWithFallback<>(
                () -> {
                    throw new IOException("Failure with IOException (exp iterable & flbck)");
                },
                new Fallback.From<>(
                    new IterableOf<>(IOException.class),
                    exp -> "Fallback from IOException (exp iterable & flbck)"
                )
            ),
            new HasValue<>("Fallback from IOException (exp iterable & flbck)")
        );
    }

    @Test
    void usesFallbackOfInterruptedException() {
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
                        exp -> "Fallback from InterruptedException"
                    )
                )
            ),
            new HasValue<>("Fallback from InterruptedException")
        );
    }

    @Test
    void usesTheClosestFallback() {
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
                        exp -> "Fallback from IllegalFormatException"
                    )
                )
            ),
            new HasValue<>("Fallback from IllegalFormatException")
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
