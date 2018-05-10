/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
import org.cactoos.io.BytesOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link org.cactoos.bytes.BytesBase64}.
 * @since 0.20.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class BytesBase64Test {

    @Test
    public void checkEncodeBasic() throws Exception {
        MatcherAssert.assertThat(
            "Can't encodes bytes using the Base64 basic encoding scheme",
            new BytesBase64(
                new BytesOf(
                    "Hello!"
                )
            ).asBytes(),
            Matchers.equalTo(
                new BytesOf("SGVsbG8h").asBytes()
            )
        );
    }

    @Test
    public void checkEncodeUrl() throws Exception {
        MatcherAssert.assertThat(
            "Can't encodes bytes using the Base64 URL encoding scheme",
            new BytesBase64(
                new BytesOf(
                    "Hello!"
                ), Base64.getUrlEncoder()
            ).asBytes(),
            Matchers.equalTo(
                new BytesOf("SGVsbG8h").asBytes()
            )
        );
    }

    @Test
    public void checkEncodeMime() throws Exception {
        MatcherAssert.assertThat(
            "Can't encodes bytes using the Base64 mime encoding scheme",
            new BytesBase64(
                new BytesOf(
                    "Hello!"
                ), Base64.getMimeEncoder()
            ).asBytes(),
            Matchers.equalTo(
                new BytesOf("SGVsbG8h").asBytes()
            )
        );
    }

}
