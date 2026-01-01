/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.bytes;

import java.util.Base64;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link BytesBase64}.
 * @since 0.20.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class BytesBase64Test {

    @Test
    void checkEncodeBasic() throws Exception {
        new Assertion<>(
            "Must encode bytes using the Base64 basic encoding scheme",
            new BytesBase64(
                new BytesOf(
                    "Hello!"
                )
            ).asBytes(),
            new IsEqual<>(
                new BytesOf("SGVsbG8h").asBytes()
            )
        ).affirm();
    }

    @Test
    void checkEncodeUrl() throws Exception {
        new Assertion<>(
            "Must encode bytes using the Base64 URL encoding scheme",
            new BytesBase64(
                new BytesOf(
                    "Hello!"
                ), Base64.getUrlEncoder()
            ).asBytes(),
            new IsEqual<>(
                new BytesOf("SGVsbG8h").asBytes()
            )
        ).affirm();
    }

    @Test
    void checkEncodeMime() throws Exception {
        new Assertion<>(
            "Must encode bytes using the Base64 mime encoding scheme",
            new BytesBase64(
                new BytesOf(
                    "Hello!"
                ), Base64.getMimeEncoder()
            ).asBytes(),
            new IsEqual<>(
                new BytesOf("SGVsbG8h").asBytes()
            )
        ).affirm();
    }

}
