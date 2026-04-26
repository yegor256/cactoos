/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.IllegalFormatWidthException;
import org.cactoos.Fallback;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsApplicable;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link FuncWithFallback}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("unchecked")
final class FuncWithFallbackTest {

    @Test
    void usesMainFunc() {
        MatcherAssert.assertThat(
            "Can't use the main function if no exception",
            new FuncWithFallback<>(
                input -> "It's success",
                new Fallback.From<>(
                    Exception.class,
                    ex -> "In case of failure..."
                )
            ),
            new IsApplicable<>(1, "It's success")
        );
    }

    @Test
    void usesFallback() {
        MatcherAssert.assertThat(
            "Can't use the callback in case of exception",
            new FuncWithFallback<>(
                input -> {
                    throw new IOException("Failure");
                },
                new Fallback.From<>(IOException.class, ex -> "Never mind")
            ),
            new IsApplicable<>(1, "Never mind")
        );
    }

    @Test
    void usesFallbackOfInterruptedException() {
        MatcherAssert.assertThat(
            "Can't use a fallback from Interrupted in case of exception",
            new FuncWithFallback<>(
                input -> {
                    throw new InterruptedException(
                        "Failure with InterruptedException"
                    );
                },
                new Fallback.From<>(
                    InterruptedException.class,
                    exp -> "Fallback from InterruptedException"
                )
            ),
            new IsApplicable<>(1, "Fallback from InterruptedException")
        );
    }

    @Test
    void usesTheClosestFallback() {
        MatcherAssert.assertThat(
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
                        exp -> "Fallback from IllegalFormatException"
                    )
                )
            ),
            new IsApplicable<>(1, "Fallback from IllegalFormatException")
        );
    }

    @Test
    void noFallbackIsProvided() {
        MatcherAssert.assertThat(
            "can't find fallback",
            () -> new FuncWithFallback<>(
                input -> {
                    throw new IllegalFormatWidthException(1);
                },
                new IterableOf<>()
            ).apply(1),
            new Throws<>(Exception.class)
        );
    }
}
