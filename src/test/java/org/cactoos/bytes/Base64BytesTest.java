/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.bytes;

import java.util.Base64;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Base64Bytes}.
 *
 * @since 0.20.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class Base64BytesTest {

    @Test
    void checkDecodeBasicDecoder() throws Exception {
        MatcherAssert.assertThat(
            "Must decode bytes using the Base64 encoding basic scheme",
            new Base64Bytes(
                new BytesOf(
                    "SGVsbG8h"
                )
            ).asBytes(),
            new IsEqual<>(
                new BytesOf("Hello!").asBytes()
            )
        );
    }

    @Test
    void checkDecodeUrlDecoder() throws Exception {
        MatcherAssert.assertThat(
            "Must decode bytes using the Base64 encoding url scheme",
            new Base64Bytes(
                new BytesOf(
                    "SGVsbG8h"
                ), Base64.getUrlDecoder()
            ).asBytes(),
            new IsEqual<>(
                new BytesOf("Hello!").asBytes()
            )
        );
    }

    @Test
    void checkDecodeMimeDecoder() throws Exception {
        MatcherAssert.assertThat(
            "Must decode bytes using the Base64 encoding mime scheme",
            new Base64Bytes(
                new BytesOf(
                    "SGVsbG8h"
                ), Base64.getMimeDecoder()
            ).asBytes(),
            new IsEqual<>(
                new BytesOf("Hello!").asBytes()
            )
        );
    }

}
