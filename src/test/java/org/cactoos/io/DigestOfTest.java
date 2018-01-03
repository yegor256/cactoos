/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
package org.cactoos.io;

import java.io.IOException;
import org.cactoos.TextHasString;
import org.cactoos.text.HexOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link DigestOf}.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.28
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class DigestOfTest {

    @Test
    // @checkstyle MethodNameCheck (1 line)
    public void md5() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate the MD5 checksum",
            new HexOf(
                new DigestOf(
                    new InputOf("Hi Guys!"),
                    "MD5"
                )
            ),
            new TextHasString(
                "371fda3e6a3104dcbc5fabd85a2aea42"
            )
        );
    }

    @Test
    // @checkstyle MethodNameCheck (1 line)
    public void sha1() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate the SHA-1 checksum",
            new HexOf(
                new DigestOf(
                    new InputOf("Elegant Objects!"),
                    "SHA-1"
                )
            ),
            new TextHasString(
                "17b44115042086bd5f43397552374932614e333d"
            )
        );
    }

    @Test
    // @checkstyle MethodNameCheck (1 line)
    public void sha256() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate the SHA-256 checksum",
            new HexOf(
                new DigestOf(
                    new InputOf("Hello World!")
                )
            ),
            new TextHasString(
                // @checkstyle LineLengthCheck (1 lines)
                "7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069"
            )
        );
    }

    @Test
    // @checkstyle MethodNameCheck (1 line)
    public void sha256File() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate the file SHA-256 checksum",
            new HexOf(
                new DigestOf(
                    new InputOf(
                        new ResourceOf(
                            "org/cactoos/io/DigestOf.class"
                        ).stream()
                    )
                )
            ),
            new TextHasString(
                // @checkstyle LineLengthCheck (1 lines)
                "6b395f5aacffa56fe67a148098d7bd86e09b406cc595c705c5fb6f437016835d"
            )
        );
    }

    @Test
    public void empty() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate the SHA-256 checksum of empty string",
            new HexOf(
                new DigestOf(
                    new InputOf("")
                )
            ),
            new TextHasString(
                // @checkstyle LineLengthCheck (1 lines)
                "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
            )
        );
    }

}
