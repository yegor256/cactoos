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
 * Test case for {@link Md5OfInput}.
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class Md5OfInputTest {

    @Test
    public void calculatesFromZero() {
        MatcherAssert.assertThat(
            "Can't calculate MD5 hash from zero",
            new BytesAsHex(
                new Md5OfInput(
                    new DeadInput()
                )
            ),
            new TextHasString("d41d8cd98f00b204e9800998ecf8427e")
        );
    }

    @Test
    public void calculatesFromString() {
        MatcherAssert.assertThat(
            "Can't calculate MD5 hash from string",
            new BytesAsHex(
                new Md5OfInput(
                    new InputOf(
                        "What's up, друг?"
                    )
                )
            ),
            new TextHasString("a410d010ea31fa7757cb20912d463858")
        );
    }

    @Test
    public void calculatesFromFile() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate MD5 hash from file",
            new BytesAsHex(
                new Md5OfInput(
                    new InputOf(
                        new URL(
                            // @checkstyle LineLength (1 line)
                            "file:src/test/resources/org/cactoos/large-text.txt"
                        )
                    )
                )
            ),
            new TextHasString("374c645b65a1469d8c3fe742f2b65ea8")
        );
    }

}
