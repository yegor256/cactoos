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
 * Test case for {@link BytesBase64}.
 * @since 0.20.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class BytesBase64Test {

    @Test
    void checkEncodeBasic() throws Exception {
        MatcherAssert.assertThat(
            "Must encode bytes using the Base64 basic encoding scheme",
            new BytesBase64(
                new BytesOf(
                    "Hello!"
                )
            ).asBytes(),
            new IsEqual<>(
                new BytesOf("SGVsbG8h").asBytes()
            )
        );
    }

    @Test
    void checkEncodeUrl() throws Exception {
        MatcherAssert.assertThat(
            "Must encode bytes using the Base64 URL encoding scheme",
            new BytesBase64(
                new BytesOf(
                    "Hello!"
                ), Base64.getUrlEncoder()
            ).asBytes(),
            new IsEqual<>(
                new BytesOf("SGVsbG8h").asBytes()
            )
        );
    }

    @Test
    void checkEncodeMime() throws Exception {
        MatcherAssert.assertThat(
            "Must encode bytes using the Base64 mime encoding scheme",
            new BytesBase64(
                new BytesOf(
                    "Hello!"
                ), Base64.getMimeEncoder()
            ).asBytes(),
            new IsEqual<>(
                new BytesOf("SGVsbG8h").asBytes()
            )
        );
    }
}
