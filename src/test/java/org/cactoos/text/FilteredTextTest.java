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

import java.util.Arrays;
import org.cactoos.TextHasString;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link FilteredText}.
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class FilteredTextTest {

    @Test
    public final void testFiltering() throws Exception {
        MatcherAssert.assertThat(
            "Can't filter a text",
            new FilteredText(
                new StringAsText("abc"), Arrays.asList(new StringAsText("c"))
            ),
            new TextHasString("ab")
        );
    }

    @Test
    public final void testFilteringEmptyText() throws Exception {
        MatcherAssert.assertThat(
            "Can't filter empty text",
            new FilteredText(
                new StringAsText(""), Arrays.asList(new StringAsText("c"))
            ),
            new TextHasString("")
        );
    }

    @Test
    public final void testComparing() throws Exception {
        MatcherAssert.assertThat(
            "Can't compare filtered text",
            new FilteredText(
                new StringAsText("abc"), Arrays.asList(new StringAsText("c"))
            ).compareTo(new StringAsText("ab")),
            Matchers.is(0)
        );
    }

}
