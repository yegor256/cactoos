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
import org.cactoos.matchers.TextHasString;
import org.cactoos.text.HexOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link Md5DigestOf}.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class Md5DigestOfTest {

    @Test
    public void checksumOfEmptyString() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate the empty string's MD5 checksum",
            new HexOf(
                new Md5DigestOf(
                    new InputOf("")
                )
            ),
            new TextHasString(
                "d41d8cd98f00b204e9800998ecf8427e"
            )
        );
    }

    @Test
    public void checksumOfString() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate the string's MD5 checksum",
            new HexOf(
                new Md5DigestOf(
                    new InputOf("Hello World!")
                )
            ),
            new TextHasString(
                "ed076287532e86365e841e92bfc50d8c"
            )
        );
    }

    @Test
    public void checksumFromFile() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate the file's MD5 checksum",
            new HexOf(
                new Md5DigestOf(
                    new InputOf(
                        new ResourceOf(
                            "org/cactoos/digest-calculation.txt"
                        ).stream()
                    )
                )
            ),
            new TextHasString(
                "162665ab3d58424724f83f28e7a147d6"
            )
        );
    }

}
