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
package org.cactoos.list;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.cactoos.CountOf;
import org.cactoos.ScalarHasValue;
import org.cactoos.Text;
import org.cactoos.io.BytesOf;
import org.cactoos.io.InputOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link CountOf}.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class CountOfTest {

    @Test
    public void countOfCharsInString() throws Exception {
        MatcherAssert.assertThat(
            "Can't count the number of chars in the string",
            new CountOf(
                "The future is now"
            ),
            // @checkstyle MagicNumberCheck (1 line)
            new ScalarHasValue<>(17)
        );
    }

    @Test
    public void countOfCharsInText() throws Exception {
        MatcherAssert.assertThat(
            "Can't count the number of chars in the text",
            new CountOf(
                new TextOf("text ill")
            ),
            // @checkstyle MagicNumberCheck (1 line)
            new ScalarHasValue<>(8)
        );
    }

    @Test
    public void sizeOfBytes() throws IOException {
        final Text text = new TextOf("How are you, друг?");
        MatcherAssert.assertThat(
            "Can't calculate the size of bytes",
            new CountOf(
                new BytesOf(
                    text
                )
            ),
            new ScalarHasValue<>(
                text.asString().getBytes(
                    StandardCharsets.UTF_8
                ).length
            )
        );
    }

    @Test
    public void sizeInBytesOfInput() throws IOException {
        final Text text = new TextOf("What's up, друг?");
        MatcherAssert.assertThat(
            "Can't calculate the size on bytes of Input",
            new CountOf(
                new InputOf(
                    text
                )
            ),
            new ScalarHasValue<>(
                (long) new BytesOf(text).asBytes().length
            )
        );
    }

    @Test
    public void sizeAndCountAreEqualForAscii() throws IOException {
        final Text text = new TextOf(
            "A single ascii character is two unicode code points, friend."
        );
        MatcherAssert.assertThat(
            "Shoud be equal the count of bytes and the count characters",
            new CountOf(
                new InputOf(
                    text
                )
            ).value(),
            Matchers.equalTo(
                new CountOf(text).value().longValue()
            )
        );
    }

    @Test
    public void sizeAndCountAreNotEqualForUnicodes() throws IOException {
        final Text text = new TextOf(
            "A single character is two or more unicode code points, друг."
        );
        MatcherAssert.assertThat(
            "Should not be equal size in bytes and count in elements",
            new CountOf(
                new InputOf(
                    text
                )
            ).value(),
            Matchers.not(
                Matchers.equalTo(
                    new CountOf(text).value()
                )
            )
        );
    }

    @Test
    public void countOfElementsInIterator() throws Exception {
        final int size = 42;
        final int element = 11;
        MatcherAssert.assertThat(
            "Can't count the number of elements in the iterator",
            new CountOf(
                new RepeatedIterator<>(
                    element,
                    size
                )
            ),
            new ScalarHasValue<>(size)
        );
    }

    @Test
    public void countOfElementsInIterable() {
        MatcherAssert.assertThat(
            "Can't count the number of elements in the itrable",
            new CountOf(
                new ArrayOf<>(
                    new TextOf("abc")
                )
            ),
            // @checkstyle MagicNumber (1 line)
            new ScalarHasValue<>(1)
        );
    }

    @Test
    public void valueEmptyText() throws Exception {
        MatcherAssert.assertThat(
            new CountOf(
                new TextOf("")
            ),
            new ScalarHasValue<>(0)
        );
    }

    @Test
    public void valueEmptyString() throws Exception {
        MatcherAssert.assertThat(
            new CountOf(""),
            new ScalarHasValue<>(0)
        );
    }

}
