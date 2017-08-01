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
import org.cactoos.Text;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link UncheckedText}.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.3
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class UncheckedTextTest {

    @Test(expected = RuntimeException.class)
    public void rethrowsCheckedToUncheckedException() {
        new UncheckedText(
            new Text() {
                @Override
                public String asString() throws IOException {
                    throw new IOException("intended");
                }

                @Override
                public int compareTo(final Text text) {
                    throw new UnsupportedOperationException(
                        "#compareTo() not supported"
                    );
                }
            }
        ).asString();
    }

    @Test
    public void comparesToOtherUncheckedText() {
        final String txt = "foobar";
        MatcherAssert.assertThat(
            "These UncheckedText are not equal",
            new UncheckedText(
                new TextOf(txt)
            ).compareTo(new TextOf(txt)),
            Matchers.equalTo(0)
        );
    }

}
