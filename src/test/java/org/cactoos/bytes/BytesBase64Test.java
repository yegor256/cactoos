/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
