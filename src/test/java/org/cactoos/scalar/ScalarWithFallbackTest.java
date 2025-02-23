/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.IllegalFormatWidthException;
import org.cactoos.Fallback;
import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link ScalarWithFallback}.
 *
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"unchecked",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class ScalarWithFallbackTest {

    @Test
    void usesMainFunc() {
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
    void usesMainFuncFromExceptionAndFallback() {
        final String message = "Main function's result #1 (exp & flbck)";
        new Assertion<>(
            "Using the main function if no exception (exp & flbck)",
            new ScalarWithFallback<>(
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
    void usesMainFuncFromIterableExceptionAndFallback() {
        final String message = "Main function's result #1 (exp iterable & flbck)";
        new Assertion<>(
            "Using the main function if no exception (exp iterable & flbck)",
            new ScalarWithFallback<>(
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
    void usesFallback() {
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
    void usesFallbackFromExceptionAndFallback() {
        final String message = "Fallback from IOException (exp & flbck)";
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void usesFallbackFromIterableExceptionAndFallback() {
        final String message = "Fallback from IOException (exp iterable & flbck)";
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void usesFallbackOfInterruptedException() {
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
    void usesTheClosestFallback() {
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

    @Test
    void noFallbackIsProvided() {
        new Assertion<>(
            "Exception is expected if there is no fallback",
            () -> new ScalarWithFallback<>(
                () -> {
                    throw new IllegalFormatWidthException(1);
                },
                new IterableOf<>()
            ).value(),
            new Throws<>(Exception.class)
        ).affirm();
    }
}
