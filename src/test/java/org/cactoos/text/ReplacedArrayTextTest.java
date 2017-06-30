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

import java.io.IOException;
import org.cactoos.TextHasString;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link ReplacedArrayText}.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ReplacedArrayTextTest {

    @Test
    public void replacesAllOccurrencesWithReplacedArrayText() {
        MatcherAssert.assertThat(
            "Can't replace a text.",
            new ReplacedArrayText(
                new StringAsText("one cat, two cats, three cats"),
                new String[]{"cat"},
                "bird"
            ),
            new TextHasString("one bird, two birds, three birds")
        );
    }

    @Test
    public void checksCompareToForReplacedArrayText() throws IOException {
        MatcherAssert.assertThat(
            "Can't compare replaced text with expected.",
            new ReplacedArrayText(
                new StringAsText("When the child was a child"),
                new String[]{"child"},
                "boy"
            )
            .compareTo(
                new StringAsText("When the boy was a boy")
            ),
            Matchers.equalTo(0)
        );
    }

}
