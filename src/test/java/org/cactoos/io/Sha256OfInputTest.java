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
import java.net.URL;
import org.cactoos.TextHasString;
import org.cactoos.text.BytesAsHex;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link Sha256OfInput}.
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class Sha256OfInputTest {

    @Test
    public void calculatesFromZero() {
        MatcherAssert.assertThat(
            "Can't calculate SHA-256 hash from zero",
            new BytesAsHex(
                new Sha256OfInput(
                    new DeadInput()
                )
            ),
            // @checkstyle LineLength (1 line)
            new TextHasString("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855")
        );
    }

    @Test
    public void calculatesFromString() {
        MatcherAssert.assertThat(
            "Can't calculate SHA-256 hash from string",
            new BytesAsHex(
                new Sha256OfInput(
                    new InputOf(
                        "What's up, друг?"
                    )
                )
            ),
            // @checkstyle LineLength (1 line)
            new TextHasString("8bf098f037acf1f4826599727331532af8b8d6ae11c9292dd3348b1ee866ad05")
        );
    }

    @Test
    public void calculatesFromFile() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate SHA-256 hash from file",
            new BytesAsHex(
                new Sha256OfInput(
                    new InputOf(
                        new URL(
                            // @checkstyle LineLength (1 line)
                            "file:src/test/resources/org/cactoos/large-text.txt"
                        )
                    )
                )
            ),
            // @checkstyle LineLength (1 line)
            new TextHasString("4b6995f1b4444d4682d8732de35e9bb700a6d57b6b08cc9ed48329e267e9c2aa")
        );
    }

}
