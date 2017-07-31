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
package org.cactoos;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.cactoos.io.BytesOf;
import org.cactoos.io.DeadInput;
import org.cactoos.io.InputOf;
import org.cactoos.iterable.ArrayOf;
import org.cactoos.iterable.Repeated;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link LengthOf}.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class LengthOfTest {

    @Test
    public void lengthOfCharsInString() throws Exception {
        MatcherAssert.assertThat(
            "Can't count the number of chars in the string",
            new LengthOf(
                "The future is now"
            ),
            // @checkstyle MagicNumberCheck (1 line)
            new ScalarHasValue<>(17)
        );
    }

    @Test
    public void lengthOfCharsInText() throws Exception {
        MatcherAssert.assertThat(
            "Can't count the number of chars in the text",
            new LengthOf(
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
            new LengthOf(
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
            new LengthOf(
                new InputOf(
                    text
                )
            ),
            new ScalarHasValue<>(
                (long) text.asString().getBytes(StandardCharsets.UTF_8).length
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
            new LengthOf(
                new InputOf(
                    text
                )
            ).value(),
            Matchers.equalTo(
                new LengthOf(text).value().longValue()
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
            new LengthOf(
                new InputOf(
                    text
                )
            ).value(),
            Matchers.not(
                Matchers.equalTo(
                    new LengthOf(text).value()
                )
            )
        );
    }

    @Test
    public void lengthOfElementsInIterator() throws Exception {
        final int size = 42;
        final int element = 11;
        MatcherAssert.assertThat(
            "Can't count the number of elements in the iterator",
            new LengthOf(
                new Repeated<>(
                    element,
                    size
                )
            ),
            new ScalarHasValue<>(size)
        );
    }

    @Test
    public void lengthOfElementsInIterable() {
        MatcherAssert.assertThat(
            "Can't count the number of elements in the itrable",
            new LengthOf(
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
            new LengthOf(
                new TextOf("")
            ),
            new ScalarHasValue<>(0)
        );
    }

    @Test
    public void valueEmptyString() throws Exception {
        MatcherAssert.assertThat(
            new LengthOf(""),
            new ScalarHasValue<>(0)
        );
    }

    @Test
    public void calculatesZeroLength() {
        MatcherAssert.assertThat(
            "Can't calculate the length of an empty input",
            new LengthOf(new DeadInput()),
            new ScalarHasValue<>(0L)
        );
    }

    @Test
    public void readsRealUrl() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate length of a real page at the URL",
            new LengthOf(
                new InputOf(
                    new URL(
                        // @checkstyle LineLength (1 line)
                        "file:src/test/resources/org/cactoos/large-text.txt"
                    )
                )
            ).value(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(73471L)
        );
    }

}

