/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017 Yegor Bugayenko
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.codec;

import java.io.IOException;
import java.io.StringReader;
import org.cactoos.InputHasContent;
import org.cactoos.io.ReaderAsInput;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Base64ToInput}.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class Base64ToInputTest {

    @Test
    public void decodeCodec() throws IOException {
        MatcherAssert.assertThat(
            "Can't decode a text",
            new Base64ToInput(
                new ReaderAsInput(
                    new StringReader("SGVsbG8h")
                )
            ),
            new InputHasContent("Hello!")
        );
    }

    @Test(expected = DecodingException.class)
    public void illegalCharacter() throws IOException {
        MatcherAssert.assertThat(
            "Can't decode a text",
            new Base64ToInput(
                new ReaderAsInput(
                    new StringReader("!@#$%^&*()")
                )
            ),
            new InputHasContent("Hello!")
        );
    }

}
