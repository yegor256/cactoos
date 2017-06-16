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
import java.nio.charset.StandardCharsets;
import org.cactoos.ScalarHasValue;
import org.cactoos.text.StringAsText;
import org.cactoos.text.TextAsBytes;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link LengthOfInput}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class LengthOfInputTest {

    @Test
    public void calculatesLength() {
        final String text = "What's up, друг?";
        MatcherAssert.assertThat(
            "Can't calculate the length of Input",
            new LengthOfInput(
                new BytesAsInput(
                    new TextAsBytes(
                        new StringAsText(text)
                    )
                )
            ),
            new ScalarHasValue<>(
                (long) text.getBytes(StandardCharsets.UTF_8).length
            )
        );
    }

    @Test
    public void calculatesZeroLength() {
        MatcherAssert.assertThat(
            "Can't calculate the length of an empty input",
            new LengthOfInput(new DeadInput()),
            new ScalarHasValue<>(0L)
        );
    }

    @Test
    public void readsRealUrl() throws IOException {
        MatcherAssert.assertThat(
            "Can't calculate length of a real page at the URL",
            new LengthOfInput(
                new UrlAsInput(
                    // @checkstyle LineLength (1 line)
                    "file:src/test/resources/org/cactoos/large-text.txt"
                )
            ).asValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(73471L)
        );
    }

}
