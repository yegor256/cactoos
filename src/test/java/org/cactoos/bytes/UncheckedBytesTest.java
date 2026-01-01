/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import java.io.IOException;
import org.cactoos.Fallback;
import org.cactoos.Text;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link UncheckedBytes}.
 *
 * @since 0.3
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class UncheckedBytesTest {

    @Test
    void rethrowsCheckedToUncheckedException() {
        new Assertion<>(
            "Must rethrow checked to unchecked exception",
            () -> new UncheckedBytes(
                () -> {
                    throw new IOException("intended");
                }
            ).asBytes(),
            new Throws<>(RuntimeException.class)
        ).affirm();
    }

    @Test
    void worksNormallyWhenNoExceptionIsThrown() throws Exception {
        final Text source = new TextOf("hello, cactoos!");
        new Assertion<>(
            "Must work normally when no exception is thrown",
            new UncheckedBytes(
                new BytesOf(source)
            ).asBytes(),
            new IsEqual<>(new BytesOf(source).asBytes())
        ).affirm();
    }

    @Test
    void worksWithFallback() {
        final byte[] empty = {};
        new Assertion<>(
            "Must work with fallback",
            new UncheckedBytes(
                () -> {
                    throw new IOException("OK");
                },
                new Fallback.From<>(
                    IOException.class,
                    ex -> empty
                )
            ).asBytes(),
            new IsEqual<>(empty)
        ).affirm();
    }
}
