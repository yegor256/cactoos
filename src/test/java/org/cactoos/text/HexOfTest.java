/*
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

import java.io.IOException;
import org.cactoos.io.BytesOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.TextHasString;

/**
 * Test case for {@link HexOf}.
 * @since 0.28
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class HexOfTest {

    @Test
    public void empytString() {
        MatcherAssert.assertThat(
            "Can't represent an empty string as hexadecimal",
            new HexOf(
                new BytesOf("")
            ),
            new TextHasString("")
        );
    }

    @Test
    public void notEmpytString() throws IOException {
        MatcherAssert.assertThat(
            "Can't represent a string as hexadecimal",
            new HexOf(
                new BytesOf("What's up, друг?")
            ),
            new TextHasString("5768617427732075702c20d0b4d180d183d0b33f")
        );
    }
}
