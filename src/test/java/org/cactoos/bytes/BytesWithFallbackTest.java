/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import java.io.IOException;
import org.cactoos.Fallback;
import org.cactoos.Text;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link BytesWithFallback}.
 *
 * @since 1.0
 */
final class BytesWithFallbackTest {

    @Test
    void worksNormallyWhenNoExceptionIsThrown() throws Exception {
        final Text source = new TextOf("hello, cactoos!");
        MatcherAssert.assertThat(
            "Must work normally when no exception is thrown",
            new BytesWithFallback(new BytesOf(source)).asBytes(),
            new IsEqual<>(new BytesOf(source).asBytes())
        );
    }

    @Test
    void usesFallbackWhenOriginFails() throws Exception {
        MatcherAssert.assertThat(
            "Must use fallback when origin fails",
            new BytesWithFallback(
                () -> {
                    throw new IOException("intended");
                },
                new Fallback.From<>(
                    IOException.class,
                    ex -> new byte[] {1, 2, 3}
                )
            ).asBytes(),
            new IsEqual<>(new byte[] {1, 2, 3})
        );
    }

    @Test
    void rethrowsOriginalExceptionWhenFallbackDoesNotSupportIt() {
        MatcherAssert.assertThat(
            "Must rethrow original exception when fallback does not support it",
            () -> new BytesWithFallback(
                () -> {
                    throw new IOException("intended");
                },
                new Fallback.From<>(
                    IllegalArgumentException.class,
                    ex -> new byte[] {1}
                )
            ).asBytes(),
            new Throws<>(IOException.class)
        );
    }

    @Test
    void wrapsExceptionIntoUncheckedWhenNoFallback() {
        MatcherAssert.assertThat(
            "Must wrap exception into unchecked when no fallback",
            () -> new BytesWithFallback(
                () -> {
                    throw new IOException("intended");
                }
            ).asBytes(),
            new Throws<>(IllegalStateException.class)
        );
    }
}
