/**
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
package org.cactoos.io;

import java.io.IOException;
import org.cactoos.TextHasString;
import org.cactoos.text.HexOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link Sha256DigestOf}.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class Sha256DigestOfTest {

    @Test
    public void checksumOfEmptyString() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate the empty string's SHA-256 checksum",
            new HexOf(
                new Sha256DigestOf(
                    new InputOf("")
                )
            ),
            new TextHasString(
                // @checkstyle LineLengthCheck (1 lines)
                "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
            )
        );
    }

    @Test
    public void checksumOfString() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate the string's SHA-256 checksum",
            new HexOf(
                new Sha256DigestOf(
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
    public void checksumFromFile() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate the file's SHA-256 checksum",
            new HexOf(
                new Sha256DigestOf(
                    new InputOf(
                        new ResourceOf(
                            "org/cactoos/io/DigestEnvelope.class"
                        ).stream()
                    )
                )
            ),
            new TextHasString(
                // @checkstyle LineLengthCheck (1 lines)
                "a56c3be45f9be8dda0653e33ae7ef3abf2939f926eda801f329e0830b6e7cc22"
            )
        );
    }

}
