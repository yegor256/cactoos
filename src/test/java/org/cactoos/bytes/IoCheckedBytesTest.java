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
 * Test case for {@link IoCheckedBytes}.
 * @since 0.52
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IoCheckedBytesTest {

    @Test
    void rethrowsCheckedToUncheckedException() {
        MatcherAssert.assertThat(
            "Must rethrow checked to io-checked exception",
            () -> new IoCheckedBytes(
                () -> {
                    throw new IOException("intended");
                }
            ).asBytes(),
            new Throws<>(IOException.class)
        );
    }

    @Test
    void worksNormallyWhenNoExceptionIsThrown() throws Exception {
        final Text source = new TextOf("hello, cactoos!");
        MatcherAssert.assertThat(
            "Must work normally when no exception is thrown",
            new IoCheckedBytes(
                new BytesOf(source)
            ).asBytes(),
            new IsEqual<>(new BytesOf(source).asBytes())
        );
    }

    @Test
    void worksWithFallback() throws IOException {
        MatcherAssert.assertThat(
            "Must work with fallback",
            new IoCheckedBytes(
                () -> {
                    throw new IllegalArgumentException("OK");
                },
                new Fallback.From<>(
                    IllegalArgumentException.class,
                    ex -> new byte[] {}
                )
            ).asBytes(),
            new IsEqual<>(new byte[] {})
        );
    }
}
