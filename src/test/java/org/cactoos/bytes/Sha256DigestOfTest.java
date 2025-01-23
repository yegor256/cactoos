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

import org.cactoos.io.InputOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.io.Sticky;
import org.cactoos.text.HexOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Sha256DigestOf}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class Sha256DigestOfTest {

    @Test
    void checksumOfEmptyString() {
        new Assertion<>(
            "Can't calculate the empty string's SHA-256 checksum",
            new HexOf(
                new Sha256DigestOf(
                    new InputOf("")
                )
            ),
            new HasString(
                "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
            )
        ).affirm();
    }

    @Test
    void checksumOfString() {
        new Assertion<>(
            "Can't calculate the string's SHA-256 checksum",
            new HexOf(
                new Sha256DigestOf(
                    new InputOf("Hello World!")
                )
            ),
            new HasString(
                "7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069"
            )
        ).affirm();
    }

    @Test
    void checksumFromFile() throws Exception {
        new Assertion<>(
            "Can't calculate the file's SHA-256 checksum",
            new HexOf(
                new Sha256DigestOf(
                    new Sticky(
                        new InputOf(
                            new ResourceOf(
                                "org/cactoos/digest-calculation.txt"
                            ).stream()
                        )
                    )
                )
            ),
            new HasString(
                "6b9da8f528f2c6523f292c2aa37be6befdd560ff917ed60836d63778ec04b46b"
            )
        ).affirm();
    }

}
