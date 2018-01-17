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
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link FormattedText}.
 *
 * @author Sergey Sharov (zefick@mail.ru)
 * @version $Id$
 * @since 0.27
 * @checkstyle JavadocMethodCheck (100 lines)
 */
public final class ComparableTextTest {

    @Test
    public void comparesWithASubtext() throws Exception {
        MatcherAssert.assertThat(
            "Can't compare sub texts",
            new ComparableText(
                new TextOf(
                    "here to there"
                )
            ).compareTo(
                // @checkstyle MagicNumberCheck (2 lines)
                new ComparableText(
                    new SubText("from here to there", 5)
                )
            ),
            Matchers.is(0)
        );
    }

    @Test
    public void comparesToUncheckedText() {
        final String txt = "foobar";
        MatcherAssert.assertThat(
            "These UncheckedText are not equal",
            new ComparableText(
                new UncheckedText(
                    new TextOf(txt)
                )
            ).compareTo(
                new ComparableText(new TextOf(txt))
            ),
            Matchers.equalTo(0)
        );
    }
}
