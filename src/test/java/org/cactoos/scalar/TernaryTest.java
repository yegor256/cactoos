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
package org.cactoos.scalar;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Ternary}.
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class TernaryTest {

    @Test
    public void conditionTrue() throws Exception {
        MatcherAssert.assertThat(
            new Ternary<>(
                new True(),
                6,
                16
            ).value(),
            Matchers.equalTo(6)
        );
    }

    @Test
    public void conditionFalse() throws Exception {
        MatcherAssert.assertThat(
            new Ternary<>(
                new False(),
                6,
                16
            ).value(),
            Matchers.equalTo(16)
        );
    }

    @Test
    public void conditionBoolean() throws Exception {
        MatcherAssert.assertThat(
            new Ternary<>(
                true,
                6,
                16
            ).value(),
            Matchers.equalTo(6)
        );
    }

    @Test
    public void conditionFunc() throws Exception {
        MatcherAssert.assertThat(
            new Ternary<>(
                5,
                input -> input > 3,
                input -> input = 8,
                input -> input = 2
            ).value(),
            Matchers.equalTo(8)
        );
    }
}
