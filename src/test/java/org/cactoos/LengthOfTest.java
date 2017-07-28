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
import org.cactoos.LengthOf;
import org.cactoos.ScalarHasValue;
import org.cactoos.Text;
import org.cactoos.io.BytesOf;
import org.cactoos.io.InputOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link LengthOf}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class LengthOfTest {

    @Test
    public void lengthOfSingleVisualCharactersInString() throws Exception {
        MatcherAssert.assertThat(
            "Can't calculate the size of the string",
            new LengthOf(
                "When the repente"
            ),
            // @checkstyle MagicNumberCheck (1 line)
            new ScalarHasValue<>(16)
        );
    }

    @Test
    public void lengthOfSingleVisualCharactersInText() throws Exception {
        MatcherAssert.assertThat(
            "Can't calculate the size of the text",
            new LengthOf(
                new TextOf("text ill")
            ),
            // @checkstyle MagicNumberCheck (1 line)
            new ScalarHasValue<>(8)
        );
    }

    @Test
    public void sizeOfInputOnBytes() throws IOException {
        final Text text = new TextOf("What's up, друг?");
        MatcherAssert.assertThat(
            "Can't calculate the size on bytes of Input",
            new LengthOf(
                new InputOf(
                    text
                )
            ),
            // @checkstyle MagicNumber (1 line)
            new ScalarHasValue<>(
                (long) new BytesOf(text).asBytes().length
            )
        );
    }

    @Test
    public void sizeAndLengthAreEqual() throws IOException {
        final Text text = new TextOf(
            "A single ascii character is two unicode code points, friend."
        );
        MatcherAssert.assertThat(
            "Can't match that lenght and size are equal for ascii chars",
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
    public void lengthIsNotSizeFor() throws IOException {
        final Text text = new TextOf(
            "A single character is two or more unicode code points, друг."
        );
        MatcherAssert.assertThat(
            "Can't get a difference between length and size",
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
    public void lengthOfIterator() throws Exception {
        final int size = 42;
        final int element = 11;
        MatcherAssert.assertThat(
            "Can't calculate the size of the interator",
            new LengthOf(
                new RepeatIterator<>(
                    element,
                    size
                )
            ),
            new ScalarHasValue<>(size)
        );
    }

    @Test
    public void lengthOfIterable() {
        MatcherAssert.assertThat(
            "Can't calculate the size of chars",
            new LengthOf(
                new ArrayOf<>(
                    new TextOf("abc")
                )
            ),
            // @checkstyle MagicNumber (1 line)
            new ScalarHasValue<>(3)
        );
    }

}
