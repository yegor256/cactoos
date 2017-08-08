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
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link SplitText}.
 * @author Alexey Semenyuk (semenyukalexey@gmail.com)
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class SplitTextTest {

    @Test
    public void splitText() throws Exception {
        MatcherAssert.assertThat(
            "Can't split a text",
            new SplitText(
                "Hello world!", "\\s+"
            ),
            Matchers.contains(
                "Hello",
                "world!"
            )
        );
    }

    @Test
    public void splitEmptyText() throws Exception {
        MatcherAssert.assertThat(
            "Can't split an empty text",
            new SplitText("", "\n"),
            Matchers.emptyIterable()
        );
    }

    @Test
    public void splitStringWithTextRegex() throws Exception {
        MatcherAssert.assertThat(
            "Can't split an string with text regex",
            new SplitText("Cactoos OOP!", new TextOf("\\s")),
            Matchers.contains(
                "Cactoos",
                "OOP!"
            )
        );
    }

    @Test
    public void splitTextWithStringRegex() throws Exception {
        MatcherAssert.assertThat(
            "Can't split an text with string regex",
            new SplitText(new TextOf("Cact4Primitives!"), "\\d+"),
            Matchers.contains(
                "Cact",
                "Primitives!"
            )
        );
    }

    @Test
    public void splitTextWithTextRegex() throws Exception {
        MatcherAssert.assertThat(
            "Can't split an text with text regex",
            new SplitText(new TextOf("Split#OOP!"), "\\W+"),
            Matchers.contains(
                "Split",
                "OOP"
            )
        );
    }

}
