/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
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
 * Test case for {@link IoCheckedBytes}.
 *
 * @since 0.52
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IoCheckedBytesTest {

    @Test
    void rethrowsCheckedToUncheckedException() {
        new Assertion<>(
            "Must rethrow checked to io-checked exception",
            () -> new IoCheckedBytes(
                () -> {
                    throw new IOException("intended");
                }
            ).asBytes(),
            new Throws<>(IOException.class)
        ).affirm();
    }

    @Test
    void worksNormallyWhenNoExceptionIsThrown() throws Exception {
        final Text source = new TextOf("hello, cactoos!");
        new Assertion<>(
            "Must work normally when no exception is thrown",
            new IoCheckedBytes(
                new BytesOf(source)
            ).asBytes(),
            new IsEqual<>(new BytesOf(source).asBytes())
        ).affirm();
    }

    @Test
    void worksWithFallback() throws IOException {
        final byte[] empty = {};
        new Assertion<>(
            "Must work with fallback",
            new IoCheckedBytes(
                () -> {
                    throw new IllegalArgumentException("OK");
                },
                new Fallback.From<>(
                    IllegalArgumentException.class,
                    ex -> empty
                )
            ).asBytes(),
            new IsEqual<>(empty)
        ).affirm();
    }
}
